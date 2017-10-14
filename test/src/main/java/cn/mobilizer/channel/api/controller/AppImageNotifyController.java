package cn.mobilizer.channel.api.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.image.vo.FileSupport;
import cn.mobilizer.channel.image.vo.FileUtils;
import cn.mobilizer.channel.image.vo.ImageGlobal;
import cn.mobilizer.channel.image.vo.ThreadSender;
import cn.mobilizer.channel.util.PropertiesHelper;

@RestController
@RequestMapping(value = "/api/image")
public class AppImageNotifyController {
	
	private static String ftpFolder = null;
	
	private static Log log = LogFactory.getLog(AppImageNotifyController.class);
	
	static{
		ftpFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
	}

	
	/**
	 * 图片通知压缩接口，该方法无返回值，为异步的接口，根据http的状态码判断是否调用成功
	 * @param clientId 客户ID
	 * @param userId 用户ID
	 * @param type 类型，1：普通通知  2：有门头照片需要扫描bn文件夹(bn开头的照片)
	 * @throws Exception
	 */
	@Async
	@RequestMapping(value = "/notify")
	public void notify(Integer clientId,Integer userId,Integer type) throws Exception{
		Set<String> setDirs = new HashSet<String>();
		String dir = FileUtils.getDir(clientId, userId);
		setDirs.add(dir);
		if(type==ImageGlobal.NotifyType.BN){ //有门头照片，需要扫描门头照文件夹
			String bnDir = ImageGlobal.Global.BN_DIR+File.separator+clientId;
			setDirs.add(bnDir);
		}
		
		List<String> listFiles = new ArrayList<String>();
		for (String str : setDirs) {
			String filePath = ftpFolder+File.separator+str;
			List<String> files = new FileSupport().loadFiles(new File(filePath),ImageGlobal.Global.SUFFIX,-1);
			listFiles.addAll(files);
		}
		log.info("image notify scan file size: "+listFiles.size());
		ThreadSender.getInstance().send(listFiles);
	}
}
