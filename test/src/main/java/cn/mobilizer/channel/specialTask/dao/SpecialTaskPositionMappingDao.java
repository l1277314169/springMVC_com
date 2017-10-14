package cn.mobilizer.channel.specialTask.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskPositionMapping;
@Repository
public class SpecialTaskPositionMappingDao extends MyBatisDao{
	public SpecialTaskPositionMappingDao(){
		super("SPECIAL_TASK_POSITION_MAPPING");
	}
	
	public int addSpecialTaskPositionMapping(SpecialTaskPositionMapping specialTaskPositionMapping){
		return super.insert("insertSelective", specialTaskPositionMapping);
	}
	public int updateSpecialTaskPositionMapping(SpecialTaskPositionMapping specialTaskPositionMapping){
		return super.update("updateByPrimaryKeySelective", specialTaskPositionMapping);
	}
	
	public SpecialTaskPositionMapping findSpecialTaskPositionMappingBySpecialTaskId(Map<String,Object> parameter){
		return super.get("findSpecialTaskPositionMappingBySpecialTaskId", parameter);
	}
	
}
