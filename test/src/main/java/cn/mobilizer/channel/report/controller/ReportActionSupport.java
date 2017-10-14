package cn.mobilizer.channel.report.controller;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.POIExcelSupport;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.export.controller.ActionSupport;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.po.Filter;
import cn.mobilizer.channel.report.po.Report;
import cn.mobilizer.channel.report.po.ReportVo;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.util.DateTimeUtils;

public class ReportActionSupport extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientRolesService clientRolesService;
	
	/**
	 * 批量设置默认值
	 * @param filterMap
	 * @param key
	 * @param value
	 */
	protected void doAllDefault(Map<String, Object> filterMap,Report baseReport){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			String clientStructureKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
			super.setValue(filterMap, clientStructureKey, getClientStructureId());
			
			String clientKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_CLIENT_ID);
			this.setValue(filterMap, clientKey, getClientId());
			
			String levelKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_LEVEL);
			this.setValue(filterMap,levelKey, ReportGlobal.DEFAULT_LEVEL); //1：表示最低级别,待确认
			
			
			String drillKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_DRILL);
			this.setValue(filterMap,drillKey, ReportGlobal.Drill.OFF); //是否钻取
			
			String chainKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_CHAIN_ID);
			this.setValue(filterMap, chainKey, "");
			
			String channelKey = baseReport.getFilterName(ReportGlobal.Filters.DAILOG_STORE_CHANNEL);
			this.setValue(filterMap, channelKey,"");
			
			String pageStartKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_PAGE_START);
			this.setValue(filterMap,pageStartKey, 0);
			
			String pageSizeKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_PAGE_SIZE);
			this.setValue(filterMap, pageSizeKey, Page.DEFAULT_PAGE_SIZE);
			
			String argFlagKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_ARG_FLAG);
			this.setValue(filterMap, argFlagKey, ReportGlobal.ReportFlag.QUERY);
			
			String brandKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_BRAND);
			this.setValue(filterMap, brandKey, "");
			
			String argTypeKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_CATEGORY);
			this.setValue(filterMap, argTypeKey, "");
			
			String randKey2 = baseReport.getFilterName(ReportGlobal.Filters.SELECT_BRAND2);
			this.setValue(filterMap, randKey2, 0);
			
			String categoryKey2 = baseReport.getFilterName(ReportGlobal.Filters.SELECT_CATEGORY2);
			this.setValue(filterMap, categoryKey2, 0);
			
			String ThreeMonthKey2 = baseReport.getFilterName(ReportGlobal.Filters.SUPERSANMONTH);
			this.setValue(filterMap, ThreeMonthKey2, 0);
			
			String argKey1 = baseReport.getFilterName(ReportGlobal.Filters.SALESTYPE);
			this.setValue(filterMap, argKey1, 1);
			
			String argKey2 = baseReport.getFilterName(ReportGlobal.Filters.PROJECTSALESTYPE);
			this.setValue(filterMap, argKey2, 2);
			
			String defaultVal = baseReport.getFilterName(ReportGlobal.Filters.DEFAULTSTR);
			this.setValue(filterMap, defaultVal, "");
			
			/**
			 * 拜访类型，默认为门店拜访
			 */
			String visitTypeKey = baseReport.getFilterName(ReportGlobal.Filters.SELECT_VISIT_TYPE);
			this.setValue(filterMap, visitTypeKey, 1);
			
			String filterUserIdsKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_FILTER_USER_IDS);
			if(!StringUtil.isEmptyString(filterUserIdsKey)){
				String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());
				filterMap.put(filterUserIdsKey, subordinates);
			}
			String filterStructureIds = baseReport.getFilterName(ReportGlobal.Filters.HIDE_FILTER_STRUCTURE_IDS);
			if(!StringUtil.isEmptyString(filterStructureIds)){
				String deptIds = this.getDeptIds(getClientStructureId());
				filterMap.put(filterStructureIds, deptIds);
			}
			
			String userKey = baseReport.getFilterName(ReportGlobal.Filters.CLIENT_USER_ID);
			this.setValue(filterMap, userKey, super.getCurrentUserId());
			
			String roleKey = baseReport.getFilterName(ReportGlobal.Filters.HIDE_LOGIN_USER_ROLE);
			if(!StringUtil.isEmptyString(roleKey)){
				String roleIds = this.getRoles();
				filterMap.put(roleKey, roleIds);
			}
			
			String yearKey = baseReport.getFilterName(ReportGlobal.Filters.YEAR);
			Integer currYear = DateTimeUtils.getCurrentYear();
			this.setValue(filterMap, yearKey, currYear);
			
			Filter groupFilter = baseReport.getFilter(ReportGlobal.Filters.INPUT_HIDDEN);
			if(null!=groupFilter && null != groupFilter.getDefaultVal()){
				this.setValue(filterMap, groupFilter.getArg(), groupFilter.getDefaultVal());
			}
			
			String year2Key = baseReport.getFilterName(ReportGlobal.Filters.YEARS2);
			this.setValue(filterMap, year2Key, currYear);
			
			
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
		}
	}
	
	
	/**
	 * 批量设置默认值
	 * @param filterMap
	 * @param key
	 * @param value
	 */
	protected void doReportVoDefault(Map<String, Object> filterMap,ReportVo reportVo){
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			String clientStructureKey = reportVo.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
			super.setValue(filterMap, clientStructureKey, getClientStructureId());
			
			String clientKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_CLIENT_ID);
			this.setValue(filterMap, clientKey, getClientId());
			
			String levelKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_LEVEL);
			this.setValue(filterMap,levelKey, ReportGlobal.DEFAULT_LEVEL); //1：表示最低级别,待确认
			
			String drillKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_DRILL);
			this.setValue(filterMap,drillKey, ReportGlobal.Drill.OFF); //是否钻取
			
			String chainKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_CHAIN_ID);
			this.setValue(filterMap, chainKey, "");
			
			String channelKey = reportVo.getFilterName(ReportGlobal.Filters.DAILOG_STORE_CHANNEL);
			this.setValue(filterMap, channelKey,"");
			
			String pageStartKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_PAGE_START);
			this.setValue(filterMap,pageStartKey, 0);
			
			String pageSizeKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_PAGE_SIZE);
			this.setValue(filterMap, pageSizeKey, Page.DEFAULT_PAGE_SIZE);
			
			String argFlagKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_ARG_FLAG);
			this.setValue(filterMap, argFlagKey, ReportGlobal.ReportFlag.QUERY);
			
			
			String brandKey = reportVo.getFilterName(ReportGlobal.Filters.SELECT_BRAND);
			this.setValue(filterMap, brandKey, "");
			
			Filter filter = reportVo.getFilter(ReportGlobal.Filters.SELECT_CATEGORY);
			if(null!=filter){
				Object val = filter.getDefaultVal()==null?"":filter.getDefaultVal();
				this.setValue(filterMap, filter.getArg(),val);
			}
			
			String randKey2 = reportVo.getFilterName(ReportGlobal.Filters.SELECT_BRAND2);
			this.setValue(filterMap, randKey2, 0);
			
			Filter filter2 = reportVo.getFilter(ReportGlobal.Filters.SELECT_CATEGORY2);
			if(null!=filter2){
				Object val = filter2.getDefaultVal()==null?0:filter2.getDefaultVal();
				this.setValue(filterMap, filter2.getArg(),val);
			}
			
			Filter gradeFilter = reportVo.getFilter(ReportGlobal.Filters.STRUCTURE_GRADE);
			if(null!=gradeFilter){
				Object val = gradeFilter.getDefaultVal()==null?0:gradeFilter.getDefaultVal();
				this.setValue(filterMap, gradeFilter.getArg(), val);
			}
			
			Filter argTypeFilter = reportVo.getFilter(ReportGlobal.Filters.SALESTYPE);
			if(null!=argTypeFilter){
				Object val = argTypeFilter.getDefaultVal()==null?0:argTypeFilter.getDefaultVal();
				this.setValue(filterMap, argTypeFilter.getArg(), val);
			}
			
			/*开始月份结束月份默认为空*/
			String monthStartKey = reportVo.getFilterName(ReportGlobal.Filters.INPUT_MONTH_START);
			if(!StringUtil.isEmptyString(monthStartKey)){
				filterMap.put(monthStartKey,null);
			}
			
			String monthEndKey = reportVo.getFilterName(ReportGlobal.Filters.INPUT_MONTH_END);
			if(!StringUtil.isEmptyString(monthEndKey)){
				filterMap.put(monthEndKey, null);
			}
			
			/**
			 * 拜访类型，默认为门店拜访
			 */
			String visitTypeKey = reportVo.getFilterName(ReportGlobal.Filters.SELECT_VISIT_TYPE);
			this.setValue(filterMap, visitTypeKey, 1);
			
			String filterUserIdsKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_FILTER_USER_IDS);
			if(!StringUtil.isEmptyString(filterUserIdsKey)){
				String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());
				filterMap.put(filterUserIdsKey, subordinates);
			}
			String filterStructureIds = reportVo.getFilterName(ReportGlobal.Filters.HIDE_FILTER_STRUCTURE_IDS);
			if(!StringUtil.isEmptyString(filterStructureIds)){
				String deptIds = this.getDeptIds(getClientStructureId());
				filterMap.put(filterStructureIds, deptIds);
			}
			
			String roleKey = reportVo.getFilterName(ReportGlobal.Filters.HIDE_LOGIN_USER_ROLE);
			if(!StringUtil.isEmptyString(roleKey)){
				String roleIds = this.getRoles();
				filterMap.put(roleKey, roleIds);
			}
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
		}
	}
	
	
	
	public void setReport(Map<String, Object> filterMap,Report report){
		String argFlagKey = report.getFilterName(ReportGlobal.Filters.HIDE_ARG_FLAG);
		filterMap.put(argFlagKey,ReportGlobal.ReportFlag.EXPORT);
	}
	
	/**
	 * 设置分页开始位置
	 * @param filterMap
	 * @param page
	 */
	protected void setPageStart(Map<String, Object> filterMap,Integer page,Report report){
		page = page==null?1:page;
		String pageStartKey = report.getFilterName(ReportGlobal.Filters.HIDE_PAGE_START);
		if(null!=pageStartKey){
			int startItems = (page-1)*Page.DEFAULT_PAGE_SIZE;
			filterMap.put(pageStartKey,startItems);
		}
	}
	
	public void exportFromReoprtVo(List<ReportVo> vos,OutputStream out) throws Exception{
		POIExcelSupport poi = new POIExcelSupport();
		for (ReportVo vo : vos) {
			List<TreeMap<String, DataVo>> values = vo.getValues();
	        List<String> heads = vo.getHeards();
			String fileName = vo.getPartName();
			poi.export(fileName,heads,values);
		}
		poi.workFlush(out);
	}
	
}
