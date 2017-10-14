package cn.mobilizer.channel.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.log.po.Sms;
import cn.mobilizer.channel.log.service.SmsService;
import cn.mobilizer.channel.log.service.impl.SmsProcessorManager;
import cn.mobilizer.channel.util.PropertiesHelper;

public class PushMessageTask {
	
	private static final Log LOG = LogFactory.getLog(PushMessageTask.class);
	@Autowired
	private SmsService smsService;
	@Autowired
	private SmsProcessorManager smsProcessorManager;

	public void pushMessages(){		
		String pushMessageJob_enabled =  PropertiesHelper.getInstance().getProperty(PropertiesHelper.PUSHMESSAGEJOB_ENABLED);
		if(pushMessageJob_enabled.equals("true")){
			//切换到业务数据库
			LOG.info("PushMessage Job Running...");
			Map<String,Object> param = new HashMap<String,Object>();			
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
			Date currentTime = new Date();
			param.put("currentTime", currentTime);
			Integer count = smsService.findListByParamsCount(param);
			int num = count%500==0?count/500:count/500 + 1;
			for(int i = 1;i <= num ; i++){
				int pagenum = i+1;
				Page<Sms> pageParam = Page.page(count, 500, pagenum);
				param.put("_start", pageParam.getStartRows());
				param.put("_size", pageParam.getPageSize ());
				CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
				List<Sms> smsList = smsService.findListByParams(param);	
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);   			    
				try {
					smsProcessorManager.smsProcesso(smsList);
				} catch (Exception e) {					
					e.printStackTrace();
					continue;
				}				 					 	 						
			}			
			LOG.info("PushMessage Job end...");		
		}				
	}
}
