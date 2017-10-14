package cn.mobilizer.channel.log.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

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
import cn.mobilizer.channel.util.Md5Utils;
import cn.mobilizer.channel.util.PropertiesHelper;

@Service
public class AndroidSmsPushProcessor implements SmsPushProcessor {

	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	
	private static final Log LOG = LogFactory.getLog(AndroidSmsPushProcessor.class);

	@Override
	public void pushMessage(List<Sms> smsList) throws Exception {

		String api_key = PropertiesHelper.getInstance().getProperty(PropertiesHelper.API_KEY);
		String secret_key = PropertiesHelper.getInstance().getProperty(PropertiesHelper.SECRET_KEY);
		for (Sms sms : smsList) {
			try {
				ClientUserExpand clientUserExpand = clientUserExpandService.findByPrimaryKey(sms.getReceiverId());
				if (StringUtils.isEmpty(clientUserExpand.getMsgPushToken())) {
					continue;
				}
				String[] strs = clientUserExpand.getMsgPushToken().split(",");
				String userId = strs[1];
				String channelId = strs[2];
				String url = "http://channel.api.duapp.com/rest/2.0/channel/channel";
				URL postUrl = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				String[] params = new String[9];
				params[0] = "method=push_msg";
				params[1] = "apikey=" + api_key;
				params[2] = "push_type=1"; 									// 1单个人、2一群人、3所有人
				params[3] = "device_type=3"; 								// 设备类型 ： 3 Andriod设备、4 iOS设备
				params[4] = "messages=" + sms.getSmsContent(); 				// 消息内容
				params[5] = "msg_keys=" + sms.getSmsId(); 					// 消息标识
				params[6] = "timestamp=12344543232"; 						// 用户发起请求时的unix时间戳。本次请求签名的有效时间为该时间戳+10分钟。
				params[7] = "user_id=" + userId;
				params[8] = "channel_id=" + channelId;
				String paramStr = StringUtils.join(params, "&");
				Arrays.sort(params); 								// 参数按字典升序排序
				String signParam = StringUtils.join(params);
				String signStr = getSign(url, "POST", signParam, secret_key);
				LOG.info("签名:" + signStr);		
				paramStr += "&sign=" + signStr; 					// 签名加入请求参数
				LOG.info("请求参数:" + paramStr);
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				// 发送请求参数
				out.print(paramStr);
				// flush输出流的缓冲
				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				String result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				LOG.info(result);
				sms.setSmsStatus((byte) 1);
				sms.setSendCount((byte) (sms.getSendCount() + 1));
				CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
				smsService.updateByPrimaryKeySelective(sms);
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			} catch (Exception e) {
				e.printStackTrace();
	        	sms.setSmsStatus((byte)2);
	        	sms.setSendCount((byte)(sms.getSendCount()+1));
	        	CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
	        	smsService.updateByPrimaryKeySelective(sms);
	        	CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
				continue;
			}
		}
	}

	public static String getSign(String url, String method, String signParam,String secretKey) throws Exception {
		String str = method + url + signParam + secretKey;
		LOG.info("签名字符串：" + str);
		String encodeUrl = URLEncoder.encode(str, "utf-8");
		encodeUrl.replace("*", "%2A");
		return Md5Utils.MD5(encodeUrl).toLowerCase();
	}

}
