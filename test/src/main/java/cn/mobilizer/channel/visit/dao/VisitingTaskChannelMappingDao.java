package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingTaskChannelMapping;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class VisitingTaskChannelMappingDao extends MyBatisDao {
	
	public VisitingTaskChannelMappingDao() {
		super("VISITING_TASK_CHANNEL_MAPPING");
	}
	
	public int insert(VisitingTaskChannelMapping visitingTaskChannelMapping){
		return super.insert ("insertSelective", visitingTaskChannelMapping);
	}
	
	public VisitingTaskChannelMapping getEntityByTaskId(VisitingTaskChannelMapping visitingTaskChannelMapping){
		return super.get("getEntityByTaskId", visitingTaskChannelMapping);
	}
	
	public int update(VisitingTaskChannelMapping visitingTaskChannelMapping){
		return super.update("updateByPrimaryKeySelective", visitingTaskChannelMapping);
	}
	
}
