package cn.mobilizer.channel.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.api.po.AppDataChecklist;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class AppDataChecklistDao extends MyBatisDao {
	public AppDataChecklistDao() {
		super("APP_DATA_CHECKLIST");
	}
	
	public Map<String, Map<String,Integer>> getVisitMasterDateCount(Map<String,Object> params){
		return super.queryForMap("getVisitMasterDateCount", params, "id");
				
	}
	
	public Map<String, Map<String,Integer>> getPlanningMasterDate(Map<String,Object> params){
		return super.queryForMap("getPlanningMasterDate", params, "id");
	}
	
	public Map<String, Map<String,Integer>> getCtTaskData(Map<String,Object> params){
		return super.queryForMap("getCtTaskData", params, "id");
	}
	
	public int inertAll(List<AppDataChecklist> tlist){
		return super.insert("inertByList", tlist);
	}

}
