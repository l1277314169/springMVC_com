package cn.mobilizer.channel.report.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.report.po.Report;
import cn.mobilizer.channel.report.po.ReportVo;
import cn.mobilizer.channel.report.service.ReportsService;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.report.vo.ZipCompressing;


@Controller
@RequestMapping(value = "/reportExport")
public class ReportExportController extends ReportActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportsService reportsService;
	
	/**
	 * 导出方法
	 * @param model
	 * @param request
	 * @param funcId
	 * @param reportId
	 * @throws Exception 
	 */
	@RequestMapping(value = "/execute/{funcId}")
	public void execute(Model model,HttpServletRequest request,@PathVariable("funcId")String funcId,Integer reportId,HttpServletRequest req,HttpServletResponse response) throws Exception{
		if(funcId.equals(ReportGlobal.Func.EXPORT_EXCEL)){
			this.exportReportExcel(reportId,req,response);
		}else if(funcId.equals(ReportGlobal.Func.EXPORT_IMAGE)){
			this.exportImages(reportId,req,response);
		}
	}
	
	
	/**
	 * 报表导出为Excel
	 * @throws IOException 
	 */
	void exportReportExcel(int reportId,HttpServletRequest req,HttpServletResponse response) throws Exception {
		OutputStream out = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			Map<String, Object> filterMap = new HashMap<String, Object>();
			
			super.setFilterParams(baseReport.getReportFilterList(),filterMap,req);
			super.doAllDefault(filterMap,baseReport);
			super.setReport(filterMap, baseReport);
			
			//部门，获取下级部门
			if(baseReport.isHasDept()){
				String deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				String oldDeptValue = filterMap.get(deptKey).toString();
				String deptIds = super.getDeptIds(oldDeptValue);
				filterMap.put(deptKey, deptIds);
			}
			baseReport.setExport(true);
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			
			List<DataInfo> dataInfos = null;
			if(baseReport.getExportType()==ReportGlobal.ExportType.PROCEDURE){
				dataInfos = reportsService.getDataList(baseReport, filterMap);
			}else{
				reportsService.getData(baseReport, filterMap);
			}
			
			String fileName = baseReport.getReportName();
			
			//POS导入门店销量月报表 导出的文件名随着查询的年份显示在文件名称上
			if(reportId==8){
				String key = baseReport.getFilterName(ReportGlobal.Filters.YAER_2008);
				Object year = filterMap.get(key);
				fileName = fileName + "_"+year;
			}
			
			boolean flag = false;
            flag = isOutOfLimit(dataInfos);
			if(flag){
				baseReport.setExportType(ReportGlobal.ExportType.PROCEDURE);
 				dataInfos = super.getOutOfMaxErrorInfo();
			}
			flag = isOutOfLimit2(baseReport.getReportVo());
			if(flag){
				baseReport.setExportType(ReportGlobal.ExportType.PROCEDURE);
				dataInfos = super.getOutOfMaxErrorInfo();
			}
			if(flag){
				fileName = "数据导出失败";
			}
			
			response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
	         //文件名编码成UTF-8
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName+".xlsx",ReportGlobal.CHARTSET));
            
            if(baseReport.getExportType()==ReportGlobal.ExportType.PROCEDURE){
            	super.exportFromDataInfoList(dataInfos, out);
            }else{
            	super.exportFromReoprtVo(baseReport.getReportVo(), out);
            }
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			if(null!=out){
				out.close();
			}
		}
	}
	
	/**
	 * 导出图片
	 * @param model
	 * @param request
	 * @param reportId
	 * @throws IOException 
	 */
	void exportImages(int reportId,HttpServletRequest req,HttpServletResponse response) throws Exception{
		OutputStream out = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			Map<String, Object> filterMap = new HashMap<String, Object>();
			
			super.setFilterParams(baseReport.getReportFilterList(), filterMap,req);
			super.doAllDefault(filterMap,baseReport);
			super.setReport(filterMap, baseReport);
			
			//部门，获取下级部门
			if(baseReport.isHasDept()){
				String deptKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
				String oldDeptValue = filterMap.get(deptKey).toString();
				String deptIds = super.getDeptIds(Integer.parseInt(oldDeptValue));
				filterMap.put(deptKey, deptIds);
			}
			baseReport.setExport(true);
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			reportsService.getData(baseReport, filterMap);
//			filterMap.put(deptKey, oldDeptValue);
			
			ReportVo reportVo = baseReport.getReportVo().get(0);
	        String fileName = reportVo.getPartName();
			response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
			
			//打包图片文件
			Set<String> fileNames = reportVo.getTagColName(ReportGlobal.ColumnTag.IMAGE_COLUMN);
			String exportFileName = null;
			if(null==fileNames || fileNames.isEmpty()){
				exportFileName = "没有可供导出的图片.txt";
			}else{
				exportFileName = fileName+"_images.zip";
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(exportFileName,ReportGlobal.CHARTSET));
			ZipCompressing.exportFileByName(fileNames,exportFileName,out);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			out.close();
		}
	}
	
	/**
	 * @deprecated
	 * 跳转到导出界面
	 */
	//@RequestMapping(value = "/exportPage/{reportId}")
	/*public String exportPage(Model model,HttpServletRequest request,@PathVariable("reportId")int reportId){
		log.info("reportId="+reportId);
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Report baseReport = reportsService.getReportByReportId(reportId,getClientId());
			//验证有无权限
			if(null == baseReport){
				log.error("用户无此报表查看权限,client_id="+getClientId());
			}else{
				Map<String, Object> filterMap = new HashMap<String, Object>();
				super.setFilterParams(baseReport.getReportFilterList(),filterMap);
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
		return "/report/exportPage";
	}*/
	
}
