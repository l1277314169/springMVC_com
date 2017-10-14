package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingPopMapping;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class VisitingPopMappingDao extends MyBatisDao {
	
	public VisitingPopMappingDao() {
		super("VISITING_POP_MAPPING");
	}
	
	public int insert(VisitingPopMapping visitingPopMapping){
		return super.insert ("insertSelective", visitingPopMapping);
	}
	
	public String getPopsByTaskId(Map<String,Object> params){
		return super.get("getPopsByTaskId",params);
	}
	
	public int currentMapppingisdelte(Map<String,Object> params){
		return super.update("currentMapppingisdelte", params);
	}
}
