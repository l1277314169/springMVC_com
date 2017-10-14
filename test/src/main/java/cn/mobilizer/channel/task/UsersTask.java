/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.image.po.ExecTimeLog;
import cn.mobilizer.channel.image.service.ExecTimeLogService;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.PropertiesHelper;

public class UsersTask {

	
	private static Log log = LogFactory.getLog(ImageTask.class);
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ExecTimeLogService execTimeLogService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private CityService cityService;
	@Autowired
	private ClientStructureService clientStructureService;
	
	private static String usersTask_enabled = null;
	
	static{
		usersTask_enabled = PropertiesHelper.getInstance().getProperty(PropertiesHelper.USERS_TASK_ENABLED);
	}
	
	public void processUser() throws Exception{
		if(usersTask_enabled.equals("true")){
			process();
		}
	}
	
	/**
	 * eHR系统人员信息更新定时任务
	 * @throws Exception
	 */
	private void process() throws Exception{
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId",8);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			Map<String, ClientUser> clinetUsers = clientUserService.queryAllClinetUser(params);
			Map<String, City> citys = cityService.selectAllCity(params);
			Map<String, ClientStructure> structuresMap = clientStructureService.getClientStructureMapByName(8);
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			params.put("keyCode", "EHR_USER_TASK");
			ExecTimeLog execTimeLog = execTimeLogService.getLastExecuted(params); //获取上一次扫描的时间
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			String time = clientUserService.updateClientUser(8,clinetUsers,citys,execTimeLog,structuresMap);
			if(!StringUtil.isEmptyString(time)){
				//更新本次操作
				CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
				params.put("lastExecuted",time);
				params.put("keycode","EHR_USER_TASK");
				params.put("isDelete", Constants.IS_DELETE_FALSE);
				execTimeLogService.insertSelective(params);
			}
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
	}

}
