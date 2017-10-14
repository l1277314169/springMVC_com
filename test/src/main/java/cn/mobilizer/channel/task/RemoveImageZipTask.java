package cn.mobilizer.channel.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.base.po.AttachmentDownload;
import cn.mobilizer.channel.base.service.AttachmentDownloadService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.util.PropertiesHelper;

public class RemoveImageZipTask {
	
	private Log log = LogFactory.getLog(RemoveImageZipTask.class);
	
	@Autowired
	private AttachmentDownloadService attachmentDownloadService;
	
	private static String removeImageZip_enabled = null;
	
	static{
		removeImageZip_enabled = PropertiesHelper.getInstance().getProperty(PropertiesHelper.REMOVEIMAGEZIP_ENABLED);
	}
	
	public void removeImageZip(){
		if(removeImageZip_enabled.equals("true")){
			process();
		}
	}
	
	public void process(){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			log.info("removeImageZipTask begin.....");
			Map<String, Object> params = new HashMap<String, Object>();
			Date currentTime = new Date();
			params.put("currentTime", currentTime);
			Integer count = attachmentDownloadService.getEntitysCountByInvalidTime(params);
			int num = count%500==0?count/500:count/500 + 1;
			for(int i = 1;i <= num ; i++){
				int pagenum = i+1;
				Page<AttachmentDownload> pageParam = Page.page(count, 500, pagenum);
				params.put("_start", pageParam.getStartRows());
				params.put("_size", pageParam.getPageSize());
				List<AttachmentDownload> attachmentDownloads = attachmentDownloadService.getEntitysByInvalidTime(params);
				attachmentDownloadService.removeImageZipList(attachmentDownloads);
			}
			log.info("removeImageZipTask end.....");
		} finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
	}
}
