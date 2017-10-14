package cn.mobilizer.channel.report.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.report.po.Report;
import cn.mobilizer.channel.report.po.ReportVo;
import cn.mobilizer.channel.report.service.ReportsService;
import cn.mobilizer.channel.report.vo.ReportGlobal;

@Controller
public class FerreroReportsController extends ReportActionSupport{

	/**
	 * 费列罗定制化报表
	 */
	private static final long serialVersionUID = 1947305792924171392L;

	@Autowired
	private ReportsService reportsService;
	
	/**
	 * 报表统计
	 * @param model
	 * @param reportId
	 * @return
	 */
	@RequestMapping(value = "/report/ferrero/query/{reportId}")
	public String query(Model model,HttpServletRequest request,@PathVariable("reportId")int reportId){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			if(null == baseReport){
				log.error("用户无此报表查看权限,client_id="+getClientId()+",reportId="+reportId);
			}else{
				Map<String, Object> filterMap = new HashMap<String, Object>();
				super.setFilterParams(baseReport.getReportFilterList(),filterMap,request);
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
		return "/report/ferreroReporstList";
	}
	
	
	
	/**
	 * 异步加载Echarts数据
	 * @param partId
	 * @return
	 */
	@RequestMapping(value = "/report/ferrero/loadEcharts/{partId}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object loadEcharts(@PathVariable("partId")int partId,HttpServletRequest request){
		ReportVo reportVo = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportBypartId(partId, getClientId());
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(baseReport.getReportFilterList(),filterMap,request);
			super.doAllDefault(filterMap,baseReport);
			String deptKey = null;
			Integer oldDeptValue = null;
			if(baseReport.isHasDept()){
				deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				oldDeptValue = Integer.parseInt(filterMap.get(deptKey).toString());
				String deptIds = super.getDeptIds(oldDeptValue);
				filterMap.put(deptKey, deptIds);
			}
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			reportsService.getDataInfo(baseReport,filterMap);
			if(!StringUtil.isEmptyString(deptKey)){
				filterMap.put(deptKey, oldDeptValue);
			}
			reportVo = baseReport.getReportVo().get(0);
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return reportVo;
	} 
}
