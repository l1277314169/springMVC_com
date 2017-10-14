package cn.mobilizer.channel.base.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.AppCommand;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class AppCommandDao extends MyBatisDao {
	public AppCommandDao() {
		super("APP_COMMAND");
	}
	
	public List<AppCommand> getUnExeCommand(Integer clientUserId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientUserId", clientUserId);
		return super.queryForList("selectByClientUser", params);
	}
	
	public Integer exeCommand(Integer commandId,String exeResult){
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("cmdId", commandId);
		AppCommand ac = super.get("selectByPrimaryKey", commandId);
		if(ac != null){
			ac.setExecResult(exeResult);
			ac.setExecTime(new Date());
			return super.update("updateByPrimaryKeySelective", ac);
		}else{
			return 0;
		}
	}

}
