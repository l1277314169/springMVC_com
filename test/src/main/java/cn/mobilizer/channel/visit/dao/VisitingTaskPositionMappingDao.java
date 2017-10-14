package cn.mobilizer.channel.visit.dao;


import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingTaskPositionMapping;

@Repository
public class VisitingTaskPositionMappingDao extends MyBatisDao {
	
	public VisitingTaskPositionMappingDao() {
		super("VISITING_TASK_POSITION_MAPPING");
	}
	
	public int insert(VisitingTaskPositionMapping visitingTaskPositionMapping){
		return super.insert("insertSelective", visitingTaskPositionMapping);
	}
	
	public VisitingTaskPositionMapping getEntityByTaskId(VisitingTaskPositionMapping visitingTaskPositionMapping){
		return super.get("getEntityByTaskId", visitingTaskPositionMapping);
	}
	
	public int update(VisitingTaskPositionMapping visitingTaskPositionMapping){
		return super.update("updateByPrimaryKeySelective", visitingTaskPositionMapping);
	}
}
