package cn.mobilizer.channel.report.po;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.report.vo.ReportGlobal;

public class Report implements Serializable{

	private static final long serialVersionUID = 1L;
	// Reports
	private String reportName;
	private String reportFilter;
	private String funcList;
	private Integer clientId;
	private List<Filter> reportFilterList;
	private List<Func> funcs;
	private String reportId;
	private boolean hasDept; //是否包含部门,如果包含部门
	private boolean isExport;
	private List<ReportVo> reportVo;
	private int reportType; //展示类型，如果为1不需要展示，界面没有查询按钮，只能导出
	private int exportType; //报表导出类型，如果为0默认的导出类型，通过colum_list确定导出字段，如果为1存储过程返回什么就导出什么
	private String remark;
	
	public Filter getFilter(int type){
		Filter _filter = null;
		for (Filter filter : reportFilterList) {
			if(filter.getType()==type){
				_filter = filter;
				break;
			}
		}
		return _filter;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public int getExportType() {
		return exportType;
	}

	public void setExportType(int exportType) {
		this.exportType = exportType;
	}

	public List<ReportVo> getReportVo() {
		return reportVo;
	}
	
	public boolean isExport() {
		return isExport;
	}

	public void setExport(boolean isExport) {
		this.isExport = isExport;
	}

	public String getFilterName(int type){
		String name = null;
		for (Filter rep : getReportFilterList()) {
			if(rep.getType() == type){
				name = rep.getArg();
				break;
			}
		}
		return name;
	}
	
	public boolean isHasDept() {
		for (Filter rep : getReportFilterList()) {
			if(rep.getType()==ReportGlobal.Filters.SELECT_DEPT){
				this.hasDept = true;
				break;
			}
		}		
		return hasDept;
	}

	public void setHasDept(boolean hasDept) {
		this.hasDept = hasDept;
	}

	public void setReportVo(List<ReportVo> reportVo) {
		this.reportVo = reportVo;
	}
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public void setReportFilter(String reportFilter) {
		this.reportFilter = reportFilter;
		this.reportFilterList = new Filter().transJSON(reportFilter);
	}

	public String getFuncList() {
		return funcList;
	}

	public void setFuncList(String funcList) {
		this.funcList = funcList;
		this.funcs = new Func().transJSON(funcList);
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public List<Filter> getReportFilterList() {
		return reportFilterList;
	}

	public void setReportFilterList(List<Filter> reportFilterList) {
		this.reportFilterList = reportFilterList;
	}

	public List<Func> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<Func> funcs) {
		this.funcs = funcs;
	}

}
