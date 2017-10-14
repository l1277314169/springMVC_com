package cn.mobilizer.channel.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.LoginRequestEntity;
import cn.mobilizer.channel.api.vo.LoginResultDataEntity;
import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.api.vo.ResultEntityWithObject;
import cn.mobilizer.channel.autho.ShiroConstants;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.log.po.UserActivityLog;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.sync.po.Constants;
import cn.mobilizer.channel.util.PasswordHelper;

@RestController
@RequestMapping(value = "/api/autho")
public class AppLoginController implements Constants{
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ActivityLogService activityLogService;

	@RequestMapping(value = "/mobilogin", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResultEntityWithObject<LoginResultDataEntity> doLog(LoginRequestEntity requestEntity){
		ResultEntityWithObject<LoginResultDataEntity> lre = new ResultEntityWithObject<LoginResultDataEntity>();
		Client client = clientService.findClientByClientName(requestEntity.getClientName());
		if(client == null){
			lre.setResultCode(-1);
			lre.setResultMSG("客户不存在！");
			return lre;
		}
		ClientUser cu = clientUserService.findByLoginName(requestEntity.getLoginName(), client.getClientId());
		if(cu == null){
			lre.setResultCode(-1);
			lre.setResultMSG("用户名不存在！");
			return lre;
		}else{
			if(!cu.getRoleList().contains(ShiroConstants.ROLE_MOBILE)){
				lre.setResultCode(-1);
				lre.setResultMSG("该用户没有手机登陆权限！");
				return lre;
			}
			
			String pwd = PasswordHelper.encodePasswd(requestEntity.getPasswd(), cu.getSalt());
			if(!pwd.equals(cu.getLoginPwd())){
				lre.setResultCode(-1);
				lre.setResultMSG("用户名或者密码错误！");
				return lre;
			}
			
			if(cu.getIsActivation() == 0){
				lre.setResultCode(-1);
				lre.setResultMSG("该账户已被禁用！");
				return lre;
			}
			
//			if(cu.getIsActivation() != 1){
//				lre.setResultCode(-1);
//				lre.setResultMSG("无法正常使用APP");
//				return lre;
//			}
			
			if(cu.getStatus() == 0){
				lre.setResultCode(-1);
				lre.setResultMSG("该员工已离职！");
				return lre;
			}
		}
		lre.setResultCode(100);
		lre.setResultMSG("登录成功！");
		LoginResultDataEntity lrde = new LoginResultDataEntity();
		lrde.setClientID(client.getClientId());
		lrde.setClientUserID(cu.getClientUserId());
		lre.setDataInfo(lrde);
		//记录登录日志
		//AppVersion#平台（android,ios,ipad）-平台版本#手机型号#网络类型（移动网络、wifi）#mac地址#IMEI
		UserActivityLog log = new UserActivityLog();
		log.setClientUserId(cu.getClientUserId());
		log.setClientId(cu.getClientId());
		log.setLogType(UserActivityLog.LOG_TYPE_LOGIN);
		if(!StringUtils.isEmpty(requestEntity.getAppInfo())){
			log.setLogContent(requestEntity.getAppInfo());
			String[] args = requestEntity.getAppInfo().split("#");
			if(args != null && args.length > 0){
				log.setAppVersion(args[0]);
			}
			if(args != null && args.length > 1){
				log.setPlatform(args[1]);
			}
		}
		log.setResponseCode("Success");
		activityLogService.insert(log);
		//TODO 返回登录后的信息
		
		return lre;
	}
	
	@RequestMapping(value = "/changepasswd", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResultEntity changePasswd(Integer clientId, Integer clientUserId, String oldPasswd, String newPasswd){
		ResultEntity re = new ResultEntity();
		ClientUser cu = clientUserService.selectByPrimaryKey(clientUserId);
		if(cu != null){
			if(PasswordHelper.checkPasswd(cu, oldPasswd)){
				cu.setPlainPassword(newPasswd);
				PasswordHelper.entryptPassword(cu);;
				int i = clientUserService.changePasswd(cu);
				if(i > 0){
					re.setResultCode(RESULT_SUCCESS);
					re.setResultMSG(RESULT_SUCCESS_DESC);
				}else{
					re.setResultCode(RESULT_FAILED);
					re.setResultMSG("修改密码失败！");
				}
			}else{
				re.setResultCode(RESULT_FAILED);
				re.setResultMSG("当前密码错误！");
			}
		}else{
			re.setResultCode(-1);
			re.setResultMSG("用户不存在");
		}
		
		return re;
	}
}

