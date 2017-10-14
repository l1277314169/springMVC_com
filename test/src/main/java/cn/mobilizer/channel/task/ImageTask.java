package cn.mobilizer.channel.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.image.po.ExecTimeLog;
import cn.mobilizer.channel.image.service.ExecTimeLogService;
import cn.mobilizer.channel.image.vo.FileSupport;
import cn.mobilizer.channel.image.vo.ImageGlobal;
import cn.mobilizer.channel.image.vo.ThreadSender;
import cn.mobilizer.channel.log.po.UserActivityLog;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.PropertiesHelper;

public class ImageTask {
	
	private static Log log = LogFactory.getLog(ImageTask.class);

	@Autowired
	private ExecTimeLogService execTimeLogService;
	@Autowired
	private ActivityLogService activityLogService;
	
	private static String ftpFolder = null;
	private static String imageTask_enabled = null;
	
	static{
		ftpFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
		imageTask_enabled = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMAGETASK_ENABLED);
	}
	
	
	public void processImage() throws Exception{
		if(imageTask_enabled.equals("true")){
			process();
		}
	}
	
	/**
	 * 缩略图生成定时任务
	 * @throws Exception
	 */
	private void process() throws Exception{
		try {
			log.info("ImageTask start ");
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			params.put("keyCode", ImageGlobal.Global.KEY_CODE);
			ExecTimeLog execTimeLog = execTimeLogService.getLastExecuted(params); //获取上一次扫描的时间
			//获取系统当前时间
			Date systemDate = execTimeLogService.selectNow();
			params.clear();
			params.put("startLogTime", execTimeLog.getLastExecuted());
			params.put("endLogTime",systemDate);
			
			List<UserActivityLog> userActivityLog = activityLogService.selectBylogTime(params);
			Set<String> fileSet = new HashSet<String>();
			for (UserActivityLog ual : userActivityLog) {
				StringBuffer buffer = new StringBuffer(ftpFolder+File.separator);
				buffer.append(ual.getClientId()+File.separator+ual.getClientUserId());
				fileSet.add(buffer.toString());
			}
			
			/**
			 * 去除重复文件目录后再遍历文件
			 */
			List<String> files = new ArrayList<String>();
			for (String dir : fileSet) {
				log.debug(dir);
				List<String> files2 = new FileSupport().loadFiles(new File(dir), ImageGlobal.Global.SUFFIX,-1);
				files.addAll(files2);
			}
			ThreadSender.getInstance().send(files);
			log.info("ImageTask scan file size:"+files.size());
			
			//更新本次操作
			params.clear();
			params.put("lastExecuted",systemDate);
			params.put("keycode",ImageGlobal.Global.KEY_CODE);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			execTimeLogService.insertSelective(params);
			log.info("ImageTask end ");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
	}
}
