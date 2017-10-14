package cn.mobilizer.channel.task;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.image.vo.FileSupport;
import cn.mobilizer.channel.image.vo.ImageGlobal;
import cn.mobilizer.channel.image.vo.ThreadSender;
import cn.mobilizer.channel.util.PropertiesHelper;

public class ImageMoveTask {
	
	private static Log log = LogFactory.getLog(ImageMoveTask.class);
	private static String sourceDir = null;
	private static int scanImageSize = 0; //每10分钟扫描一次，每次扫描1000张图片。
	private static String imageMove_enabled = null;
	
	static{
		sourceDir = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
		String size = PropertiesHelper.getInstance().getProperty(PropertiesHelper.SCAN_IMAGE_SIZE);
		scanImageSize = Integer.parseInt(size);
		imageMove_enabled = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMAGEMOVE_ENABLED);
	}

	public void processMove() throws Exception{
		if(imageMove_enabled.equals("true") ){
			log.info("ImageMoveTask start ");
			List<String> files2 = new FileSupport().loadFiles(new File(sourceDir),ImageGlobal.Global.SUFFIX,scanImageSize);
			log.info("ImageMoveTask scan file size: "+ files2.size());
			ThreadSender.getInstance().send(files2);
			log.info("ImageMoveTask end ");
		}
	}
}
