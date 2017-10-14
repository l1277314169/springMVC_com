package cn.mobilizer.channel.log.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.po.ClientUserExpand;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.log.po.Sms;
import cn.mobilizer.channel.log.service.SmsPushProcessor;
import cn.mobilizer.channel.log.service.SmsService;
import cn.mobilizer.channel.util.PropertiesHelper;

@Service
public class IosSmsPushProcessor implements SmsPushProcessor{
	
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	
	private static final Log LOG = LogFactory.getLog(IosSmsPushProcessor.class);

	@Override
	public void pushMessage(List<Sms> smsList) throws Exception {
		PushNotificationManager pushManager = null;
		for(Sms sms : smsList){
			ClientUserExpand clientUserExpand = clientUserExpandService.findByPrimaryKey(sms.getReceiverId());
			if(StringUtils.isEmpty(clientUserExpand.getMsgPushToken())){
				continue;
			}
			String[] msgPushToken = clientUserExpand.getMsgPushToken().split(",");
			String deviceToken = msgPushToken[1];
	        String ksType = "PKCS12";          
	        String ksPassword = PropertiesHelper.getInstance().getProperty(PropertiesHelper.KS_PASSWORD);	
	        String plusPushPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.PLUSPUSH_PATH);   //前面生成的用于JAVA后台连接APNS服务的*.p12文件位置
	        try {       	
	        	//定义消息模式		  
	        	PushNotificationPayload payLoad = new PushNotificationPayload();
	        	payLoad.addAlert(sms.getSmsContent());
				payLoad.addBadge(1);
				payLoad.addSound("default");
				pushManager =new PushNotificationManager();			
				//String host = "gateway.sandbox.push.apple.com";
				String host = "gateway.push.apple.com";
	           int port = 2195;		
	           AppleNotificationServer appleNotificationServer = new AppleNotificationServerBasicImpl(plusPushPath,ksPassword,ksType,host,port);
	           pushManager.initializeConnection(appleNotificationServer);
	           //发送推送	    
	           deviceToken = deviceToken.trim().replace(" ", "");
	           BasicDevice device = new BasicDevice("iPhone", deviceToken, new Timestamp(Calendar.getInstance().getTime().getTime()));        
	           LOG.info("推送消息: " + device.getToken()+"\n"+payLoad.toString() +"");
	           pushManager.sendNotification(device, payLoad);	                  
	           LOG.info("Push End");
		       sms.setSmsStatus((byte)1);
		       sms.setSendCount((byte)(sms.getSendCount()+1));
		       CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);  
		       smsService.updateByPrimaryKeySelective(sms);
		       CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE); 
	        }catch(Exception ex){
				ex.printStackTrace();
				sms.setSmsStatus((byte) 2);
				sms.setSendCount((byte) (sms.getSendCount() + 1));
				CustomerContextHolder
						.setCustomerType(CustomerContextHolder.LOG);
				smsService.updateByPrimaryKeySelective(sms);
				CustomerContextHolder
						.setCustomerType(CustomerContextHolder.BASE);
				continue;
	        }   
		}
		//停止连接APNS
        pushManager.stopConnection();
	}
}
