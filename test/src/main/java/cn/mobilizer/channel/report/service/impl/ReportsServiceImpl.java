package cn.mobilizer.channel.report.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.vo.DateVoSupport;
import cn.mobilizer.channel.report.dao.BaseReportsDao;
import cn.mobilizer.channel.report.po.Column;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.po.DimWeek;
import cn.mobilizer.channel.report.po.Filter;
import cn.mobilizer.channel.report.po.Report;
import cn.mobilizer.channel.report.po.ReportVo;
import cn.mobilizer.channel.report.service.ReportsService;
import cn.mobilizer.channel.report.vo.FerreroReportSupport;
import cn.mobilizer.channel.report.vo.MapKeyComparator;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	private BaseReportsDao baseReportsDao;
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public Report getReportByReportId(Integer reportId,Integer clientId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", reportId);
		params.put("clientId", clientId);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		Report report = baseReportsDao.selectReports(params);
		if(null!=report){
			List<ReportVo> reportVos = baseReportsDao.selectReportVoByReportId(reportId,Constants.IS_DELETE_FALSE);
			report.setReportVo(reportVos);
		}
		return report;
	}
	
	@SuppressWarnings("unchecked")
	public void getData(Report baseReport,Map<String, Object> filterMap){
		
		List<ReportVo> vos = baseReport.getReportVo();
		for (ReportVo reportVo : vos) {
			Map<String, Object> _params = new HashMap<String, Object>();
			_params.put("arg_procedure", reportVo.getMyBatisSql(filterMap));
			
			List<?> list = null;
			if(reportVo.getChartType().contains(ReportGlobal.GRID2)){
				list = baseReportsDao.getDataList(reportVo.getMyBatisSql(filterMap));
				//需要格式转换
				if(!list.isEmpty()){
					for (int i = 0; i < list.size();i+=2) {
						List<TreeMap<String, String>> head = (List<TreeMap<String, String>>) list.get(i);
						String headVals = DateVoSupport.getHeadVals(head);
						String[] arr = headVals.split("@");
						String sheetName = arr[0];
						String[] arrHeads = arr[1].split(",");
						List<String> heads = Arrays.asList(arrHeads);
						List<TreeMap<String, String>> values = (List<TreeMap<String, String>>) list.get(i+1);
						DataInfo data = new DataInfo();
						data.setSheetName(sheetName);
						data.setHeads(heads);
						data.setValues(DateVoSupport.sort(values, data));
						reportVo.setDataInfo(data);
					}
				}
			}else{
				list = baseReportsDao.getExecutionDataList(_params);
				int items = 0;
				boolean flag = false;
				if(reportVo.isLimit() && !baseReport.isExport()){
					items = this.getItems(list);
					flag = true;
				}
				List<TreeMap<String, String>> result = this.getValues(list,flag);
				
				List<TreeMap<String, DataVo>> resultPart = new ArrayList<TreeMap<String,DataVo>>();
				Map<String,Column> columnMap = reportVo.getMapColumn();
				
				if(null!=result){
					for (TreeMap<String, String> map : result) {//全集,需要从全集中剔除不要显示的列
						TreeMap<String, DataVo> partMap = new TreeMap<String, DataVo>(new MapKeyComparator());
						for (String k : reportVo.getColumnNames()) {
							if(columnMap.containsKey(k)){
								
								int index = reportVo.getColumnIndex(k);
								Object val = map.get(k);
								String _key = index+"@"+k;
								val = val==null?"-":val;
								
								Column column = reportVo.getColumnByKey(k);
								DataVo dataVo = new DataVo(column);
								dataVo.setValue(val);
								dataVo.setKey(_key);
								dataVo.setIndex(index);
								
								partMap.put(_key+"",dataVo);
							}
						}
						resultPart.add(partMap);
					}
				}
				
				if(reportVo.isLimit() && !baseReport.isExport()){
					reportVo.setAllItems(items);
				}
				reportVo.setValues(resultPart);
			}
			
		}
		this.coverViewDate(baseReport, filterMap);
	}
	
	@SuppressWarnings("unchecked")
	private List<TreeMap<String, String>> getValues(List<?> list,boolean flag){
		List<TreeMap<String, String>> valList = null;
		if(!list.isEmpty()){
			if(flag){
				valList = (List<TreeMap<String, String>>) list.get(ReportGlobal.Procedure.VALUE);
			}else{
				valList = (List<TreeMap<String, String>>) list;
			}
		}
		return valList;
	}
	
	@SuppressWarnings("unchecked")
	private int getItems(List<?> list){
		List<Integer> object = null;
		int items = 0;
		if(!list.isEmpty() && ReportGlobal.Procedure.ITEMS<list.size()){
			object = (List<Integer>) list.get(ReportGlobal.Procedure.ITEMS);
			if(null!=object && !object.isEmpty()){
				items = object.get(0);
			}
		}
		return items;
	}
	
	
	/**
	 * 日期格式转换为页面需要的格式
	 * @param baseReport
	 * @param filterMap
	 */
	public void coverViewDate(Report baseReport,Map<String, Object> filterMap){
		//格式化日期<页面显示的日期格式为yyyy-MM-dd>
		List<Filter> list = baseReport.getReportFilterList();
		if(null==list){
			return;
		}
		for (Filter l : list) {
			if(l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START || l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END || 
					l.getType() == ReportGlobal.Filters.SELECT_WORK_START || l.getType() == ReportGlobal.Filters.SELECT_WORK_END
					||l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START_7 || l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START_15
					||l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END_7 || l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END_15
					||l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START_100 || l.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END_100
					||l.getType() == ReportGlobal.Filters.INPUT_DATETIME_START_100 || l.getType() == ReportGlobal.Filters.INPUT_DATETIME_END_100
					){
				Date d = DateTimeUtils.StringToDate(filterMap.get(l.getArg())+"", DateTimeUtils.yyyyMMdd2);
				String fdate = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd);
				filterMap.put(l.getArg(),fdate);
			}
		}
	}
	
	@Override
	public Report getReportBypartId(Integer partId,Integer clientId) {
		List<ReportVo> vos = baseReportsDao.selectReportVoByPartId(partId,Constants.IS_DELETE_FALSE);
		ReportVo vo = vos.get(0); //根据reportId只能查到一条记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", vo.getReportId());
		params.put("clientId", clientId);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		Report report = baseReportsDao.selectReports(params);
		report.setReportVo(vos);
		return report;
	}

	@Override
	public Report getReportByElementId(Integer elementId, Integer clientId) {
		List<ReportVo> vos = baseReportsDao.selectReportVoByElementId(elementId,Constants.IS_DELETE_FALSE);
		ReportVo vo = vos.get(0);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", vo.getReportId());
		params.put("clientId", clientId);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		Report report = baseReportsDao.selectReports(params);
		report.setReportVo(vos);
		return report;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DataInfo> getDataList(Report baseReport,Map<String, Object> filterMap) {
		List<ReportVo> vos = baseReport.getReportVo();
		ReportVo reportVo = vos.get(0);
		List<TreeMap<String, String>> list = baseReportsDao.getDataList(reportVo.getMyBatisSql(filterMap));
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		if(!list.isEmpty()){
			for (int i = 0; i < list.size();i+=2) {
				DataInfo data = new DataInfo();
				List<TreeMap<String, String>> head = (List<TreeMap<String, String>>) list.get(i);
				String headVals = DateVoSupport.getHeadVals(head);
				String[] arr = headVals.split("@");
				String sheetName = arr[0];
				String[] arrHeads = arr[1].split(",");
				List<String> heads = Arrays.asList(arrHeads);
				data.setHeads(heads);
				data.setSheetName(sheetName);
				
				List<TreeMap<String, String>> values = (List<TreeMap<String, String>>) list.get(i+1);
				data.setValues(DateVoSupport.sort(values, data));
				dataList.add(data);
			}
		}
		return dataList;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public void getDataInfo(Report baseReport,Map<String, Object> filterMap) {
		List<ReportVo> vos = baseReport.getReportVo();
		ReportVo reportVo = vos.get(0);
		List<TreeMap<String, String>> list = baseReportsDao.getDataList(reportVo.getMyBatisSql(filterMap));
		DataInfo data = new DataInfo();
		if(!list.isEmpty()){
			for (int i = 0; i < list.size();i+=2) {
				List<TreeMap<String, String>> head = (List<TreeMap<String, String>>) list.get(i);
//				List<TreeMap<String, String>> head = FerreroReportSupport.getHeads();
				String headVals = DateVoSupport.getHeadVals(head);
				String[] arr = headVals.split("@");
				String sheetName = arr[0];
				String[] arrHeads = arr[1].split(",");
				List<String> heads = Arrays.asList(arrHeads);
				data.setHeads(heads);
				data.setSheetName(sheetName);
				
				List<TreeMap<String, String>> values = (List<TreeMap<String, String>>) list.get(i+1);
//				List<TreeMap<String, String>> values = FerreroReportSupport.getDataList();
				data.setValues(DateVoSupport.sort(values, data));
			}
		}
		
		List<String> headList = data.getHeads();
		List<Column> columns = new ArrayList<Column>();
		
		for (int i = 0; i < headList.size(); i++) {
			String str = headList.get(i);
			Column column = new Column();
			column.setColName(str);
			column.setArgName(i+"@"+str);
			columns.add(column);
		}
		String key = FerreroReportSupport.getKey(baseReport.getReportId());
		String legendKey = FerreroReportSupport.getLegendKey(baseReport.getReportId());
		
		reportVo.setPartName(data.getSheetName());
		
		List<Column> coList = FerreroReportSupport.getColumn(columns,key);
		reportVo.setColumns(coList);
		reportVo.setValues(data.getValues());
		reportVo.setMapValues(data.getValues(),key,legendKey);
	}
	
	@Override
	public List<DimWeek> getDimWeek(Map<String, Object> parmar) {
		
		return baseReportsDao.getLoadDimWeek(parmar);
	}

	@Override
	public String getMgStoreInfoAlertInfo(Integer clientId) {
		Date date = baseReportsDao.getLastUpdateTime(clientId);
		String sdate = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMddHHmmss);
		StringBuffer buffer = new StringBuffer("数据已更新至："+sdate);
		return buffer.toString();
	}

	@Override
	public String getMgMonthlySalesInfo(Integer clientId) {
		Date date = baseReportsDao.getMonthlySalesLastUpdateTime(clientId);
		String sdate = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMddHHmmss);
		StringBuffer buffer = new StringBuffer("最近一次导入销量的时间："+sdate);
		return buffer.toString();
	}
	
	public String getAlertMessage(Integer clientId,Integer reportId){
		String alertMsg = null;
		if(reportId == 9){//特殊处理
			alertMsg = this.getMgStoreInfoAlertInfo(clientId);
		}else if(reportId == 30){
			alertMsg = this.getMgMonthlySalesInfo(clientId);
		}else if(reportId == 8){
			alertMsg = this.getMgMonthlySalesInfo(clientId);
		}else if(reportId == 3){//BA考勤和排版明细报表
			String dateTime = DateTimeUtils.formatTime(DateTimeUtils.addDays(DateTimeUtils.getCurrentDate(), -1), DateTimeUtils.yyyyMMdd);
			alertMsg = "人员考勤数据的同步时间更新至"+dateTime+" 6:00";
		}
		return alertMsg;
	}

	@Override
	public List<DimWeek> getLongDimWeek(Map<String, Object> parmar) {
		return baseReportsDao.getloadLongDimWeek(parmar);
	}
	
}
