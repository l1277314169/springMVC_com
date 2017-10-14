package cn.mobilizer.channel.task;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.colgate.service.DownloadDataListService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

public class ColgateRawDataTask {

	private static Log log = LogFactory.getLog(ColgateRawDataTask.class);
	private static String colgateRawData_enabled = null;
	@Autowired
	private DownloadDataListService downloadDataListService;
	
	static{
		colgateRawData_enabled = PropertiesHelper.getInstance().getProperty(PropertiesHelper.COLGATERAWDATA_ENABLED);
	}
	
	public void processData(){
		if(colgateRawData_enabled.equals("true")){
			process();
		}
	}
	
	public void process(){
		log.info("colgate raw Data task start ");
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			Date date = DateTimeUtils.getCurrentDate();
			for (int j = 0; j <= 3; j++) {
				Date tempDate = DateTimeUtils.addMonths(date,-j);
				downloadDataListService.createMainQuestionnaire(tempDate);
				downloadDataListService.createSecondaryDisplayQuestionnaire(tempDate);
				downloadDataListService.createSupplementaryQuestionnaire(tempDate);
				
			}
			for (int j = 0; j <= 3; j++) {
				Date tempDate = DateTimeUtils.addMonths(date,-j);
				CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
				String fileName = downloadDataListService.createTrend(tempDate);
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
				downloadDataListService.updateLastUpdateTime(tempDate,fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		
	}
}
