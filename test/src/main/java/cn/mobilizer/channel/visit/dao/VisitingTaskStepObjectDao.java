package cn.mobilizer.channel.visit.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingTaskStepObject;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class VisitingTaskStepObjectDao extends MyBatisDao {
	
	public VisitingTaskStepObjectDao() {
		super("VISITING_TASK_STEP_OBJECT");
	}
	
	public int insert(VisitingTaskStepObject visitingTaskStepObject){
		return super.insert ("insertSelective", visitingTaskStepObject);
	}
	
	public List<VisitingTaskStepObject> getVisitingSkuListByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingSkuListByParams", params);
	}
	
	public List<VisitingTaskStepObject> getVisitingSkuList4jgztByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingSkuList4jgztByParams", params);
	}
	
	public List<VisitingTaskStepObject> getVisitingSkuList4fxByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingSkuList4fxByParams", params);
	}
	
	public List<VisitingTaskStepObject> getVisitingBrandListByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingBrandListByParams", params);
	}
	
	public List<VisitingTaskStepObject> getVisitingCategoryListByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingCategoryListByParams", params);
	}
	
	public List<VisitingTaskStepObject> getVisitingUserListByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingUserListByParams", params);
	}
	
	public List<VisitingTaskStepObject> getVisitingPromotionListByParams(Map<String, Object> params){
		return super.queryForList ("getVisitingPromotionListByParams", params);
	}
	
	public String getTarget1IdsByStepId(Map<String, Object> params){
		return super.get("getTarget1IdsByStepId", params);
	}
	
	public int currentMapppingisdelte(Map<String, Object> params){
		return super.update("currentMapppingisdelte",params);
	}
	
}
