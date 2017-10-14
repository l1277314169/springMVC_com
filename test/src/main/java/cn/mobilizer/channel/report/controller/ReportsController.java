package cn.mobilizer.channel.report.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.report.po.Column;
import cn.mobilizer.channel.report.po.DimWeek;
import cn.mobilizer.channel.report.po.Drill;
import cn.mobilizer.channel.report.po.Report;
import cn.mobilizer.channel.report.po.ReportVo;
import cn.mobilizer.channel.report.service.ReportsService;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.util.DateTimeUtils;

@Controller
@RequestMapping(value = "/report")
public class ReportsController extends ReportActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportsService reportsService;
	
	/**
	 * 报表统计
	 * @param model
	 * @param reportId
	 * @return
	 */
	@RequestMapping(value = "/query/{reportId}")
	public String query(Model model,HttpServletRequest request,@PathVariable("reportId")int reportId,@ModelAttribute Drill drill,String PCode){
		//log.info("reportId="+reportId);
		int clientId = getClientId();
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = null;
			if(drill.getIsDrill().equals(ReportGlobal.Drill.ON)){
				log.info("drill:"+drill.toString());
				baseReport = reportsService.getReportBypartId(drill.getPartId(), getClientId());
				Column column = baseReport.getReportVo().get(0).getDrillColumn(drill.getDrillType()); //获取钻取统计列
				baseReport = reportsService.getReportBypartId(Integer.parseInt(column.getPartId()),getClientId());
			}else{
				baseReport = reportsService.getReportByReportId(reportId,getClientId());
			}
			
			//验证有无权限
			if(null == baseReport){
				log.error("用户无此报表查看权限,client_id="+getClientId()+",reportId="+reportId);
			}else{
				Map<String, Object> filterMap = new HashMap<String, Object>();
				
				super.setFilterParams(baseReport.getReportFilterList(),filterMap,request);
				super.doAllDefault(filterMap,baseReport);
				
				if(drill.getIsDrill().equals(ReportGlobal.Drill.ON)){
					String val = request.getParameter(drill.getForeignName());
					filterMap.put(drill.getArgName(), val);
					if(StringUtil.isNotEmptyString(drill.getForeignNamex())){
						String val1 = request.getParameter(drill.getForeignNamex());
						filterMap.put(drill.getArgNamex(), val1);
					}
				}
				//调用存储过程获取数据<此处不查询报表数据，报表数据等页面加载成功以后，异步加载>
				//reportsService.getExecutionData(baseReport, filterMap);
				
				reportsService.coverViewDate(baseReport, filterMap);
				
				//获取报表的提示信息
				String alertMsg = reportsService.getAlertMessage(clientId, reportId);
				model.addAttribute("alertMsg", alertMsg);
				
				model.addAttribute("filterMap", filterMap);
				model.addAttribute("baseReport",baseReport);
			}
		} catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		model.addAttribute("PCode", PCode);
		model.addAttribute("clientId", super.getClientId());
		return "/report/ReporstList";
	}
	
	
	/**
	 * 异步加载Echarts数据
	 * @param partId
	 * @return
	 */
	@RequestMapping(value = "/loadEcharts/{partId}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object loadEcharts(@PathVariable("partId")int partId,HttpServletRequest req){
		//log.info("partId="+partId);
		
		ReportVo reportVo = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportBypartId(partId, getClientId());
			
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(baseReport.getReportFilterList(),filterMap,req);
			super.doAllDefault(filterMap,baseReport);
			
			//部门，获取下级部门
			/*String deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
			String oldDeptValue = filterMap.get(deptKey).toString();
			if(baseReport.isHasDept()){
				String deptIds = super.getDeptIds(getClientStructureId());
				filterMap.put(deptKey, deptIds);
			}*/
			String deptKey = null;
			String oldDeptValue = null;
			if(baseReport.isHasDept()){
				deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				oldDeptValue = filterMap.get(deptKey).toString();
				String deptIds = super.getDeptIds(oldDeptValue);
				filterMap.put(deptKey, deptIds);
			}
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			reportsService.getData(baseReport,filterMap);
			//filterMap.put(deptKey, oldDeptValue);
			
			if(!StringUtil.isEmptyString(deptKey)){
				filterMap.put(deptKey, oldDeptValue);
			}
			reportVo = baseReport.getReportVo().get(0);
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return reportVo;
	} 
	
	/**
	 * 异步加载Grid数据
	 * @param model
	 * @param partId
	 * @return
	 */
	@RequestMapping(value = "/loadGrid/{partId}")
	public String loadGrid(Model model,Integer page,@PathVariable("partId")int partId,HttpServletRequest request){
		//log.info("partId="+partId);
		ReportVo reportVo = null;
		boolean isGrid2 = false;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportBypartId(partId, getClientId());
			
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(baseReport.getReportFilterList(),filterMap,request);
			super.doAllDefault(filterMap,baseReport);
			
			//部门，获取下级部门
			String deptKey = null;
			String oldDeptValue = null;
			if(baseReport.isHasDept()){
				deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				oldDeptValue = filterMap.get(deptKey).toString();
				String deptIds = super.getDeptIds(oldDeptValue);
				filterMap.put(deptKey, deptIds);
			}
			super.setPageStart(filterMap, page, baseReport);
			
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			reportsService.getData(baseReport,filterMap);
			reportVo = baseReport.getReportVo().get(0);
			isGrid2 = reportVo.getChartType().contains(ReportGlobal.GRID2);
			
			if(!StringUtil.isEmptyString(deptKey)){
				filterMap.put(deptKey, oldDeptValue);
			}
			
			if(reportVo.isLimit() && !isGrid2){
				super.limit(page, reportVo.getAllItems(), filterMap,reportVo.getValues(),model,request);
			}
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		model.addAttribute("reportVo", reportVo);
		
		if(isGrid2){
			return "/report/chart/grid2_table";
		}else{
			return "/report/chart/grid_table";
		}
	}
	
	/**
	 * 验证规则
	 * @return
	 */
	@RequestMapping(value = "/verify/{reportId}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object verify(Model model,Integer page,@PathVariable("reportId")int reportId,HttpServletRequest request){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId, getClientId());
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(baseReport.getReportFilterList(),filterMap,request);
			
			//获取开始日期30天
			String startTimeKey = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START);
			if(filterMap.containsKey(startTimeKey)){
				//获取结束日期
				String endTimeKey = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey).toString(), DateTimeUtils.yyyyMMdd);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey).toString(), DateTimeUtils.yyyyMMdd);
				int day = DateTimeUtils.daysBetween(start, end);
				
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>31){
					return ResultMessage.REPORT_DATE_ERROR;
				}
			}
			
			String startKey = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START);
			if(filterMap.containsKey(startKey)){
				String endKey = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END);
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
			String startTimeKey7 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START_7);
			if(filterMap.containsKey(startTimeKey7)){
				//获取结束日期
				String endTimeKey7 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END_7);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey7).toString(), DateTimeUtils.yyyyMMdd2);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey7).toString(), DateTimeUtils.yyyyMMdd2);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>7){
					return ResultMessage.REPORT_DATE_ERROR_7;
				}
			}
			
			String startTimeKey_7 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START_7);
			if(filterMap.containsKey(startTimeKey_7)){
				//获取结束日期
				String endTimeKey_7 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END_7);
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
			String startTimeKey15 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START_15);
			if(filterMap.containsKey(startTimeKey15)){
				//获取结束日期
				String endTimeKey15 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END_15);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey15).toString(), DateTimeUtils.yyyyMMdd2);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey15).toString(), DateTimeUtils.yyyyMMdd2);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>15){
					return ResultMessage.REPORT_DATE_ERROR_15;
				}
			}
			
			String startTimeKey_15 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START_15);
			if(filterMap.containsKey(startTimeKey_15)){
				//获取结束日期
				String endTimeKey_15 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END_15);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey_15).toString(), DateTimeUtils.yyyyMMdd);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey_15).toString(), DateTimeUtils.yyyyMMdd);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>15){
					return ResultMessage.REPORT_DATE_ERROR_15;
				}
			}
			
			String startTimeKey_100 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_START_100);
			if(filterMap.containsKey(startTimeKey_100)){
				//获取结束日期
				String endTimeKey_100 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETIME_END_100);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey_100).toString(), DateTimeUtils.yyyyMMdd);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey_100).toString(), DateTimeUtils.yyyyMMdd);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>100){
					return ResultMessage.REPORT_DATE_ERROR_100;
				}
			}
			
			
			//开始日期结束日期不能大于15天
			String startTimeKey100 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_START_100);
			if(filterMap.containsKey(startTimeKey100)){
				//获取结束日期
				String endTimeKey100 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END_100);
				Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey100).toString(), DateTimeUtils.yyyyMMdd2);
				Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey100).toString(), DateTimeUtils.yyyyMMdd2);
				int day = DateTimeUtils.daysBetween(start, end);
				if(day<0){
					return ResultMessage.REPORT_DATE_ERROR2;
				}else if(day>100){
					return ResultMessage.REPORT_DATE_ERROR_100;
				}
			}
			
			/*if(reportId==7){ //如果为总部，不能超过2天
				String deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				if(filterMap.containsKey(deptKey)){
					String dept = filterMap.get(deptKey).toString();
					if(dept.equals("41")){
						String endTimeKey7 = baseReport.getFilterName(ReportGlobal.Filters.INPUT_DATETEXT_END_7);
						Date start = DateTimeUtils.StringToDate(filterMap.get(startTimeKey7).toString(), DateTimeUtils.yyyyMMdd2);
						Date end = DateTimeUtils.StringToDate(filterMap.get(endTimeKey7).toString(), DateTimeUtils.yyyyMMdd2);
						int day = DateTimeUtils.daysBetween(start, end);
						if(day<0){
							return ResultMessage.REPORT_DATE_ERROR2;
						}else if(day>2){
							return new ResultMessage(ResultMessage.ERROR, "选中的为总部，开始日期与结束日期不能大于2天");
						}
					}
				}
			}*/
			
			//开始结束月份不能大于2月
			/*String startMonth = baseReport.getFilterName(ReportGlobal.Filters.INPUT_MONTH_START);
			if(filterMap.containsKey(startMonth)){
				String endMonth = baseReport.getFilterName(ReportGlobal.Filters.INPUT_MONTH_END);
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
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return ResultMessage.REPORT_DATE_SUCCESS;
	}
	/**
	 * 加载周编号
	 * @return
	 */
	@RequestMapping(value = "/loadDimWeek",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<DimWeek> loadDimWeek(){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			int clientId = getClientId();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			return reportsService.getDimWeek(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return null;
	}
	
	/**
	 * 加载一周日期编号
	 * @return
	 */
	@RequestMapping(value = "/loadLongDimWeek",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<DimWeek> loadLongDimWeek(){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			int clientId = getClientId();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			return reportsService.getLongDimWeek(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return null;
	}
	
	/**
	 * 报表统计
	 * @param model
	 * @param reportId
	 * @return
	 */
	@RequestMapping(value = "/queryColgate/{reportId}")
	public String queryColgate(Model model,HttpServletRequest request,@PathVariable("reportId")int reportId,HttpServletRequest req){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			//验证有无权限
			if(null == baseReport){
				log.error("用户无此报表查看权限,client_id="+getClientId()+",reportId="+reportId);
			}else{
				Map<String, Object> filterMap = new HashMap<String, Object>();
				super.setFilterParams(baseReport.getReportFilterList(),filterMap,req);
				super.doAllDefault(filterMap,baseReport);
				reportsService.coverViewDate(baseReport, filterMap);
				model.addAttribute("filterMap", filterMap);
				model.addAttribute("baseReport",baseReport);
			}
		} catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return "/report/reportsFilter";
	}
	
	
	/**
	 * 报表统计
	 * @param model
	 * @param reportId
	 * @return
	 */
	@RequestMapping(value = "/queryApple/{reportId}")
	public String queryApple(Model model,HttpServletRequest request,@PathVariable("reportId")int reportId,HttpServletRequest req){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			//验证有无权限
			if(null == baseReport){
				log.error("用户无此报表查看权限,client_id="+getClientId()+",reportId="+reportId);
			}else{
				Map<String, Object> filterMap = new HashMap<String, Object>();
				super.setFilterParams(baseReport.getReportFilterList(),filterMap,req);
				super.doAllDefault(filterMap,baseReport);
				reportsService.coverViewDate(baseReport, filterMap);
				model.addAttribute("filterMap", filterMap);
				model.addAttribute("baseReport",baseReport);
			}
		} catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return "/report/appleReportsFilter";
	}
	
}
