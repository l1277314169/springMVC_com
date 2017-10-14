package cn.mobilizer.channel.report.controller;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping(value = "/dashboard")
//@Scope("request")
public class DashboardController extends ReportActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportsService reportsService;
	
	public ReportsService getReportsService() {
		return reportsService;
	}

	public void setReportsService(ReportsService reportsService) {
		this.reportsService = reportsService;
	}

	/**
	 * 报表统计
	 * @param model
	 * @param reportId
	 * @return
	 */
	@RequestMapping(value = "/query/{reportId}")
	public String query(Model model,@PathVariable("reportId")int reportId){

		log.info("reportId="+reportId);
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			
			//验证有无权限
			if(null == baseReport){
				log.error("用户无此报表查看权限,client_id="+getClientId()+",reportId="+reportId);
			}else{
				List<ReportVo> vos = baseReport.getReportVo();
				for (ReportVo reportVo : vos) {
					Map<String, Object> filterMap = new HashMap<String, Object>();
					super.doReportVoDefault(filterMap,reportVo);
					reportVo.setParams(filterMap);
					reportVo.setDashboard(ReportGlobal.DASHBOARD);
				}
				model.addAttribute("baseReport",baseReport);
			}
		} catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		model.addAttribute("clientId", super.getClientId());
		return "/report/DashboardList";
	}
	
	
	/**
	 * 异步加载Echarts数据
	 * @param partId
	 * @return
	 */
	@RequestMapping(value = "/loadEcharts/{partId}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object loadEcharts(@PathVariable("partId")int partId,HttpServletRequest req){
		log.info("partId="+partId);
		
		ReportVo reportVo = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportBypartId(partId, getClientId());
			
			Map<String, Object> filterMap = new HashMap<String, Object>();
			super.setFilterParams(baseReport.getReportVo().get(0).getFilterList(),filterMap,req);
			super.doReportVoDefault(filterMap,baseReport.getReportVo().get(0));
			
			String deptKey = null;
			Integer oldDeptValue = null;
			if(baseReport.getReportVo().get(0).isHasDept()){
				deptKey = baseReport.getReportVo().get(0).getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				oldDeptValue = Integer.parseInt(filterMap.get(deptKey).toString());
				String deptIds = super.getDeptIds(oldDeptValue);
				filterMap.put(deptKey, deptIds);
			}
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			reportsService.getData(baseReport,filterMap);
			
			if(!StringUtil.isEmptyString(deptKey)){
				filterMap.put(deptKey, oldDeptValue);
			}
			reportVo = baseReport.getReportVo().get(0);
			reportVo.setParams(filterMap);
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return reportVo;
	} 
	
}
