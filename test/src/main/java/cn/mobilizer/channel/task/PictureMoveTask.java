package cn.mobilizer.channel.task;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.base.service.impl.SkuGroupMappigServiceImpl;
import cn.mobilizer.channel.util.FileDirectoryUtil;
import cn.mobilizer.channel.util.PropertiesHelper;


public class PictureMoveTask {
	private static final Log LOG = LogFactory.getLog(SkuGroupMappigServiceImpl.class);
	
	public void checkAndMovePictures(){
		try {
			String pictureMove_enabled =  PropertiesHelper.getInstance().getProperty(PropertiesHelper.PICTUREMOVE_ENABLED);
			if(pictureMove_enabled.equals("true") ){
				LOG.info("Picture Job Running...");
				String destFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
				String targetFoleder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
				int count = FileDirectoryUtil.copyDirectiory(destFolder, targetFoleder);
				LOG.info("Move Count..." + count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
