package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.vo.CallPlanningVO;

@Repository
public class CallPlanningDao extends MyBatisDao {
	
	public CallPlanningDao() {
		super("CALL_PLANNING");
	}
	
	public List<CallPlanning> getCallPlaning(Map<String, Object> pageParam){ 
		return super.queryForList("selectCallPlanning", pageParam);
	}
	
	public List<CallPlanning> getNotCallPlaning(Map<String, Object> pageParam){
		return super.queryForList("selectNotCallPlanning", pageParam);
	}
	public boolean saveAll(List<CallPlanning> list){
		boolean re = true;
		for (CallPlanning cp : list) {
			int i = super.insert("insertSelective", cp);
			if(i == 0){
				re = false;
				break;
			}
		}
		return re;
	}
	
	public List<CallPlanning> findListByParams(Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
									 
	}
	
	public List<CallPlanning> findListByParams4union(Map<String, Object> params) {
		return super.queryForList("findListByParams4union", params);
		
	}

	public Integer queryCallPlanningCount(Map<String, Object> parameters) {
		return super.get("queryTotalCount", parameters);
	}


	public CallPlanning getCallPlanningById(String planningId) {
		return super.get("selectByPrimaryKey", planningId);
	}

	public void updateCallPlanning(Map<String, Object> parameters) {
		super.update("updateByPlanningId", parameters);
	}

	
	public int insert(CallPlanning callPlanning){
		return super.insert("insertSelective", callPlanning);
	}

	public List<CallPlanning> getCallPlanningByClientUserId(Map<String, Object> params) {
		return super.getList("findListByClientUserId", params);
	}

	public List<CallPlanning> getCallPlanningByClientUserIdAndStoreId(
			Map<String, Object> parameters) {
		return super.getList("getCallPlanningByClientUserIdAndStoreId", parameters);
	}
	
	public List<CallPlanning> getCallPlanningByWorkType(
			Map<String, Object> parameters) {
		return super.getList("getCallPlanningByWorkType", parameters);
	}
	
	public int cancelCallPalnning(Map<String, Object> parameters){
		return super.update("cancelCallPalnning", parameters);
		
	}
	public int isdeleteCallpalnning(Map<String, Object> parameters){
		return super.update("isdeleteCallpalnning", parameters);
	}
	public int deleteCallpalnning(Map<String, Object> parameters){
		return super.update("deleteCallpalnning", parameters);
	}
	public List<Object> findCallplanningByMap(Map<String,Object> parameters){
		return super.queryForList("findCallplanning", parameters);
	}
	public int updateCallPlanning(CallPlanning callPlanning){
		return super.update("updateByPrimaryKeySelective", callPlanning);
		
	}
	public int addCallPlanning(Map<String,Object> parameters){
		return super.insert("addCallPlanning", parameters);
		
	}
	public CallPlanning findCallPlanningIsdelete(Map<String,Object> parameters){
		return super.get("findCallPlanningIsdelete", parameters);
	}
	public int deleteCallPlanningByDay(Map<String,Object> parameters){
		return super.update("deleteCallPlanningByDay", parameters);
		
	}
	
	public List<CallPlanning> findCallPlanningExport(Map<String,Object> parameter){
		return super.getList("findCallPlanningExport", parameter);
	}
	
	public int isdeleteExistCallplanning(Map<String,Object> parameter){
		return super.update("isdeleteExistCallplanning", parameter);
	}
	
	public int batchIsdeleteExistCallplanning(Map<String,Object> parameter){
		return super.update("batchIsdeleteExistCallplanning", parameter);
	}
	
	public List<CallPlanning> findCallIsVisit(Map<String,Object> parameter){
		return super.getList("findCallIsVisit", parameter);
	}
	
	public int inserList(List<CallPlanning> callPlanningList){
		return super.insert("inserList", callPlanningList);
	}
}
