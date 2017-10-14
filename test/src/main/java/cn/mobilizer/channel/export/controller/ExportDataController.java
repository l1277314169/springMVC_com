package cn.mobilizer.channel.export.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.po.DumpDataSetting;
import cn.mobilizer.channel.export.service.ExportDataService;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.util.DateTimeUtils;

@Controller
@RequestMapping(value = "/export")
public class ExportDataController extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExportDataService exportDataService;
	
	
	@RequestMapping(value = "/query/{settingId}")
	public String query(Model model,HttpServletRequest request,@PathVariable("settingId")int settingId,HttpServletRequest req,String PCode){
		DumpDataSetting dds = exportDataService.getDumpDataSettingInfo(getClientId(),settingId);
		if(null==dds){
			log.error("用户无此报表查看权限,client_id="+getClientId()+",settingId="+settingId);
		}else{
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(dds.getFilterList(),filterMap,req);
			super.doAllDefault(filterMap,dds);
			super.isReport(filterMap, dds,ReportGlobal.ReportFlag.EXPORT);
			
			model.addAttribute("filterMap", filterMap);
			model.addAttribute("dds",dds);
		}
		model.addAttribute("PCode", PCode);
		return "/report/exportPage";
	}
	
	
	@RequestMapping(value = "/execute/{settingId}")
	public void execute(Model model,HttpServletRequest request,@PathVariable("settingId")int settingId,HttpServletRequest req,HttpServletResponse response) throws Exception{
		OutputStream out = null;
		try {
			DumpDataSetting dds = exportDataService.getDumpDataSettingInfo(getClientId(),settingId);
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(dds.getFilterList(),filterMap,req);
			
			//部门，获取下级部门
			
			//String oldDeptValue = filterMap.get(deptKey).toString();
			if(dds.isHasDept()){
				String deptKey = dds.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				if(null!=filterMap.get(deptKey) && !"".equals(filterMap.get(deptKey))){
//					Integer id = Integer.parseInt();
					String deptIds = super.getDeptIds(filterMap.get(deptKey).toString());
					filterMap.put(deptKey, deptIds);
				}else{
					filterMap.put(deptKey, "");
				}
			}
			
			String sql = dds.getMyBatisSql(filterMap);
			
			List<DataInfo> dataList = null;
			DataInfo dataInfo = null;
			if(dds.isHasSearch()){
				dataInfo = exportDataService.getDataInfo(sql, dds,false);
			}else{
				dataList = exportDataService.getExportData(sql);
			}
			
			//List<DataInfo> dataList = exportDataService.getExportData(sql);
			//filterMap.put(deptKey, oldDeptValue);
			
			response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
			 //文件名编码成UTF-8
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dds.getName()+".xlsx",ReportGlobal.CHARTSET));
			
			if(dds.isHasSearch()){
				super.exportFromDataInfo(dataInfo, out);
			}else{
				super.exportFromDataInfoList(dataList, out);
			}
			
		}finally{
			if(null!=out){
				out.close();
			}
		}
	}
	
	/**
	 * 报表查看
	 * @param model
	 * @param request
	 * @param settingId
	 * @return
	 */
	@RequestMapping(value = "/loadGrid/{settingId}")
	public String loadGrid(Model model,HttpServletRequest request,@PathVariable("settingId")int settingId,Integer page,HttpServletRequest req){
		DumpDataSetting dds = exportDataService.getDumpDataSettingInfo(getClientId(),settingId);
		Map<String, Object> filterMap = new HashMap<String, Object>();
		super.setFilterParams(dds.getFilterList(),filterMap,req);
		//super.doAllDefault(filterMap,dds);
		super.isReport(filterMap, dds,ReportGlobal.ReportFlag.QUERY);
		
		//部门，获取下级部门
		//String oldDeptValue = filterMap.get(deptKey).toString();
		if(dds.isHasDept()){
			String deptKey = dds.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
//			Integer id = Integer.parseInt(filterMap.get(deptKey).toString());
			String deptIds = super.getDeptIds(filterMap.get(deptKey).toString());
			filterMap.put(deptKey, deptIds);
		}
		
		super.setPageStart(filterMap, page, dds);
		String sql = dds.getMyBatisSql(filterMap);
		DataInfo dataInfo = exportDataService.getDataInfo(sql,dds,true);
		
		int pagenum = page == null ? 1 : page;
		Page<TreeMap<String, DataVo>> pageParam = Page.page(dataInfo.getItems(),Page.DEFAULT_PAGE_SIZE, pagenum); //Page.DEFAULT_PAGE_SIZE
		pageParam.buildJSONUrl(request);
		pageParam.setItems(dataInfo.getValues());
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("dataInfo", dataInfo);
		model.addAttribute("dds", dds);
		//filterMap.put(deptKey, oldDeptValue);
		return "/report/chart/export_Grid";
	}
	
	
	
	/**
	 * 验证规则
	 * @return
	 */
	@RequestMapping(value = "/verify/{settingId}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object verify(Model model,Integer page,@PathVariable("settingId")int settingId,HttpServletRequest request){
		try {
			DumpDataSetting dds = exportDataService.getDumpDataSettingInfo(getClientId(),settingId);
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(dds.getFilterList(),filterMap,request);
			
			//获取开始日期
			String startTimeKey = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START);
			if(filterMap.containsKey(startTimeKey)){
				//获取结束日期
				String endTimeKey = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey).toString(), DateTimeUtils.yyyyMMdd);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey).toString(), DateTimeUtils.yyyyMMdd);
				int day = DateTimeUtils.daysBetween(start, end);
				
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>31){
					return ResultMessage.REPORT_DATE_ERROR;
				}
			}
			
			String startKey = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START);
			if(filterMap.containsKey(startKey)){
				String endKey = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startKey).toString(), DateTimeUtils.yyyyMMdd2);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endKey).toString(), DateTimeUtils.yyyyMMdd2);
				int day = DateTimeUtils.daysBetween(start, end);
				
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>31){
					return ResultMessage.REPORT_DATE_ERROR;
				}
			}
			
			
			//开始日期结束日期不能大于7天
			String startTimeKey7 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START_7);
			if(filterMap.containsKey(startTimeKey7)){
				//获取结束日期
				String endTimeKey7 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END_7);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey7).toString(), DateTimeUtils.yyyyMMdd2);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey7).toString(), DateTimeUtils.yyyyMMdd2);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>7){
					return ResultMessage.REPORT_DATE_ERROR_7;
				}
			}
			
			String startTimeKey_7 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START_7);
			if(filterMap.containsKey(startTimeKey_7)){
				//获取结束日期
				String endTimeKey_7 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END_7);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey_7).toString(), DateTimeUtils.yyyyMMdd);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey_7).toString(), DateTimeUtils.yyyyMMdd);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>7){
					return ResultMessage.REPORT_DATE_ERROR_7;
				}
			}
			
			//开始日期结束日期不能大于15天
			String startTimeKey15 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START_15);
			if(filterMap.containsKey(startTimeKey15)){
				//获取结束日期
				String endTimeKey15 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END_15);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey15).toString(), DateTimeUtils.yyyyMMdd2);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey15).toString(), DateTimeUtils.yyyyMMdd2);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>15){
					return ResultMessage.REPORT_DATE_ERROR_15;
				}
			}
			
			String startTimeKey_15 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START_15);
			if(filterMap.containsKey(startTimeKey_15)){
				//获取结束日期
				String endTimeKey_15 = dds.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END_15);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey_15).toString(), DateTimeUtils.yyyyMMdd);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey_15).toString(), DateTimeUtils.yyyyMMdd);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>15){
					return ResultMessage.REPORT_DATE_ERROR_15;
				}
			}
			
			//开始结束月份不能大于2月
			/*String startMonth = dds.getFilterName(ReportGlobal.Filters.INPUT_MONTH_START);
			if(filterMap.containsKey(startMonth)){
				String endMonth = dds.getFilterName(ReportGlobal.Filters.INPUT_MONTH_END);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startMonth).toString(), DateTimeUtils.yyyyMM);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endMonth).toString(), DateTimeUtils.yyyyMM);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR4;
				}else if(day>62){
					return ResultMessage.REPORT_DATE_ERROR3;
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.REPORT_DATE_SYS_ERROR;
		}
		return ResultMessage.REPORT_DATE_SUCCESS;
	}
	
}