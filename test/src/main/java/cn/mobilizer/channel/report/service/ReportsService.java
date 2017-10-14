package cn.mobilizer.channel.report.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.report.po.DimWeek;
import cn.mobilizer.channel.report.po.Report;


public interface ReportsService {

	public Report getReportByReportId(Integer reportId,Integer clientId);
	
	public Report getReportBypartId(Integer partId,Integer clientId);
	
	public Report getReportByElementId(Integer elementId,Integer clientId);
	
	public void getData(Report baseReport,Map<String, Object> filterMap);
	
	public List<DataInfo> getDataList(Report baseReport,Map<String, Object> filterMap);
	
	public void coverViewDate(Report baseReport,Map<String, Object> filterMap);
	
	public List<DimWeek> getDimWeek(Map<String, Object> parmar);
	
	public List<DimWeek> getLongDimWeek(Map<String, Object> parmar);
	
	public String getMgStoreInfoAlertInfo(Integer clientId);
	
	public String getMgMonthlySalesInfo(Integer clientId);
	
	public void getDataInfo(Report baseReport,Map<String, Object> filterMap);
	
	/**
	 * 获取报表的提示信息
	 * @param clInteger
	 * @param reportId
	 * @return
	 */
	public String getAlertMessage(Integer clientId,Integer reportId);
}
