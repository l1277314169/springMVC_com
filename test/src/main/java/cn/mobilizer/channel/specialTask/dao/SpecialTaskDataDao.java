package cn.mobilizer.channel.specialTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;
@Repository
public class SpecialTaskDataDao extends MyBatisDao {
	public SpecialTaskDataDao(){
		super("SPECIAL_TASK_DATA");
	}
	
	public List<SpecialTaskObject> selectSpecialTaskDataRelationStoreList(Map<String,Object> parameter){
		return super.queryForList("selectSpecialTaskDataRelationStoreList", parameter);
	}
	
	public List<SpecialTaskObject> selectSpecialTaskDataRelationClientUserList(Map<String,Object> parameter){
		return super.queryForList("selectSpecialTaskDataRelationClientUserList", parameter);
	}
	
}
