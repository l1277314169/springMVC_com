package cn.mobilizer.channel.systemManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.NormalSettings;
@Repository
public class NormalSettingDao extends MyBatisDao {
	public NormalSettingDao() {
		super("NORMAL_SETTINGS");
	}
	
	public NormalSettings getByKeyCode(Integer clientId, String keyCode){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("keyCode", keyCode);
		List<NormalSettings> list = super.queryForList("selectByKeyCode", params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
