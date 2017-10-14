package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingParameter;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class VisitingParameterDao extends MyBatisDao {
	
	public VisitingParameterDao() {
		super("VISITING_PARAMETER");
	}
	
	public int insert(VisitingParameter visitingParameter){
		return super.insert ("insertSelective", visitingParameter);
	}
	
	public List<VisitingParameter> getVisitingParameterListByStepId(Map<String, Object> params){
		return super.queryForList ("getVisitingParameterListByStepId", params);
	}
	
	public int update(VisitingParameter visitingParameter){
		return super.update("updateByPrimaryKeySelective", visitingParameter);
	}
}
