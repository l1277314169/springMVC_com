package cn.mobilizer.channel.report.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.report.po.DimWeek;
import cn.mobilizer.channel.report.po.Report;
import cn.mobilizer.channel.report.po.ReportVo;

@Repository
public class BaseReportsDao extends MyBatisDao{

	public BaseReportsDao(){
		super("BASE_REPORT");
	}
	
	public Report selectReports(Map<String, Object> params){
		return super.get("selectReports",params);
	}
	
	public List<ReportVo> selectReportVoByReportId(int reportId,int isDelete){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", reportId);
		params.put("isDelete", isDelete);
		return super.queryForList("selectReportVoByReportId", params);
	}
	
	public List<ReportVo> selectReportVoByPartId(int partId,int isDelete){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partId", partId);
		params.put("isDelete", isDelete);
		return super.queryForList("selectReportVoByPartId", params);
	}
	
	
	public List<ReportVo> selectReportVoByElementId(int elementId,int isDelete){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("elementId", elementId);
		params.put("isDelete", isDelete);
		return super.queryForList("selectReportVoByElementId", params);
	}
	
	public List<?> getExecutionDataList(Map<String, Object> params){
		List<?> list = super.getList("getExecutionDataList",params);
		return list;
	}
	
	public List<TreeMap<String, String>> getDataList(String sql){
		Map<String, String> params = new HashMap<String, String>();
		params.put("arg_procedure", sql);
		List<TreeMap<String, String>> list = super.getList("getDataList",params);
		return list;
	}
	
	public List<DimWeek> getLoadDimWeek(Map<String, Object> params){
		return  super.queryForList("loadDimWeek", params);
	}
	
	public List<DimWeek> getloadLongDimWeek(Map<String, Object> params){
		return  super.queryForList("loadLongDimWeek", params);
	}
	
	public Date getLastUpdateTime(Integer clientId){
		return super.get("getLastUpadateTime",clientId);
	}
	
	public Date getMonthlySalesLastUpdateTime(Integer clientId){
		return super.get("getMonthlySalesLastUpdateTime", clientId);
	}
	
}
