package cn.mobilizer.channel.export.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.POIExcelSupport;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.po.DumpDataSetting;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.po.Filter;
import cn.mobilizer.channel.report.po.ReportVo;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.util.DateTimeUtils;

public class ActionSupport extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientRolesService clientRolesService;
	
	
	/**
	 * 设置默认值，主要针对保存在session对象中的某些字段，比如登陆用户id，用户部门等
	 * @param filterMap
	 * @param key
	 * @param value
	 */
	protected void setValue(Map<String, Object> filterMap,String key,Object value){
		if(!StringUtil.isEmptyString(key)){
			if(null == filterMap.get(key) || "".equals(filterMap.get(value))){
				filterMap.put(key, value);
			}
		}
	}
	
	/*protected void initParams(Map<String, Object> filterMap,DumpDataSetting dds){
		Set<Integer> keys = ReportGlobal.Filters.filters;
		for (Integer type : keys) {
			switch (type) {
			case ReportGlobal.Filters.SELECT_DEPT:
				break;
			case ReportGlobal.Filters.INPUT_DATETEXT_START:
				
				break;
			default:
				break;
			}
		}
	}*/
	
	
	/**
	 * 批量设置默认值
	 * @param filterMap
	 * @param key
	 * @param value
	 */
	protected void doAllDefault(Map<String, Object> filterMap,DumpDataSetting dds){
		
		String clientStructureKey = dds.getFilterName(ReportGlobal.Filters.SELECT_DEPT);
		this.setValue(filterMap, clientStructureKey, getClientStructureId());
		
		String clientKey = dds.getFilterName(ReportGlobal.Filters.HIDE_CLIENT_ID);
		this.setValue(filterMap, clientKey, getClientId());
		
		String levelKey = dds.getFilterName(ReportGlobal.Filters.HIDE_LEVEL);
		this.setValue(filterMap,levelKey, ReportGlobal.DEFAULT_LEVEL); //1：表示最低级别,待确认
		
		
		String drillKey = dds.getFilterName(ReportGlobal.Filters.HIDE_DRILL);
		this.setValue(filterMap,drillKey, ReportGlobal.Drill.OFF); //是否钻取
		
		String chainKey = dds.getFilterName(ReportGlobal.Filters.HIDE_CHAIN_ID);
		this.setValue(filterMap, chainKey, "");
		
		String channelKey = dds.getFilterName(ReportGlobal.Filters.DAILOG_STORE_CHANNEL);
		this.setValue(filterMap, channelKey,"");
		
		String pageStartKey = dds.getFilterName(ReportGlobal.Filters.HIDE_PAGE_START);
		this.setValue(filterMap,pageStartKey, 0);
		
		String pageSizeKey = dds.getFilterName(ReportGlobal.Filters.HIDE_PAGE_SIZE);
		this.setValue(filterMap, pageSizeKey, Page.DEFAULT_PAGE_SIZE);
		
		String argFlagKey = dds.getFilterName(ReportGlobal.Filters.HIDE_ARG_FLAG);
		this.setValue(filterMap, argFlagKey, ReportGlobal.ReportFlag.QUERY);
		
		String brandKey = dds.getFilterName(ReportGlobal.Filters.SELECT_BRAND);
		this.setValue(filterMap, brandKey, 0);
		
		String cityKey = dds.getFilterName(ReportGlobal.Filters.CITY);
		this.setValue(filterMap, cityKey, 0);
		
		String proviceKey = dds.getFilterName(ReportGlobal.Filters.PROVINCE);
		this.setValue(filterMap, proviceKey, 0);
		
		String argTypeKey = dds.getFilterName(ReportGlobal.Filters.SELECT_CATEGORY);
		this.setValue(filterMap, argTypeKey, 52);
		
		
		String randKey2 = dds.getFilterName(ReportGlobal.Filters.SELECT_BRAND2);
		this.setValue(filterMap, randKey2, "");
		
		String categoryKey2 = dds.getFilterName(ReportGlobal.Filters.SELECT_CATEGORY2);
		this.setValue(filterMap, categoryKey2, "");
		
		/**
		 * 拜访类型，默认为门店拜访
		 */
		String visitTypeKey = dds.getFilterName(ReportGlobal.Filters.SELECT_VISIT_TYPE);
		this.setValue(filterMap, visitTypeKey, 1);
		
		String filterUserIdsKey = dds.getFilterName(ReportGlobal.Filters.HIDE_FILTER_USER_IDS);
		if(!StringUtil.isEmptyString(filterUserIdsKey)){
			String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());
			filterMap.put(filterUserIdsKey, subordinates);
		}
		String filterStructureIds = dds.getFilterName(ReportGlobal.Filters.HIDE_FILTER_STRUCTURE_IDS);
		if(!StringUtil.isEmptyString(filterStructureIds)){
			String deptIds = this.getDeptIds(getClientStructureId());
			filterMap.put(filterStructureIds, deptIds);
		}
		
		String roleKey = dds.getFilterName(ReportGlobal.Filters.HIDE_LOGIN_USER_ROLE);
		if(!StringUtil.isEmptyString(roleKey)){
			String roleIds = this.getRoles();
			filterMap.put(roleKey, roleIds);
		}
		
		String userKey = dds.getFilterName(ReportGlobal.Filters.CLIENT_USER_ID);
		this.setValue(filterMap, userKey, super.getCurrentUserId());
		
	}
	
	/**
	 * 获取用户角色信息
	 * @return
	 */
	protected String getRoles(){
		List<String> roles = clientRolesService.getUserRolesByClientUserId(super.getCurrentUserId());
		StringBuffer roleIds = new StringBuffer();
		for (String string : roles) {
			roleIds.append(string).append(",");
		}
		if(roleIds.toString().endsWith(",")){
			roleIds.deleteCharAt(roleIds.length()-1);
		}
		return roleIds.toString();
	}
	
	
	/**
	 * 获取所有子部门
	 * @param clientStructureId
	 * @return
	 */
	protected String getDeptIds(String clientStructureId){
		CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		//String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(deptIds.endsWith(",")){
			deptIds = deptIds.substring(0,deptIds.length()-1);
		}
		return deptIds;
	}
	
	protected void isReport(Map<String, Object> filterMap,DumpDataSetting dds,int flag){
		String argFlagKey = dds.getFilterName(ReportGlobal.Filters.HIDE_ARG_FLAG);
		filterMap.put(argFlagKey,flag);
	}
	
	protected void setPageStart(Map<String, Object> filterMap,Integer page,DumpDataSetting dds){
		page = page==null?1:page;
		String pageStartKey = dds.getFilterName(ReportGlobal.Filters.HIDE_PAGE_START);
		if(null!=pageStartKey){
			int startItems = (page-1)*Page.DEFAULT_PAGE_SIZE;
			filterMap.put(pageStartKey,startItems);
		}
	}
	
	protected void setFilterParams(List<Filter> list,Map<String, Object> filterMap,HttpServletRequest request){
		for (Filter filter : list) {
			this.setParameter(filter, filterMap,request);
		}
	}
	
	protected void exportFromDataInfoList(List<DataInfo> vos,OutputStream out) throws Exception{
		POIExcelSupport poi = new POIExcelSupport();
		for (DataInfo vo : vos) {
			List<TreeMap<String, DataVo>> values = vo.getValues();
	        List<String> heards = vo.getHeads();
			String fileName = vo.getSheetName();
			poi.export(fileName,heards,values);
		}
		poi.workFlush(out);
	}
	
	protected List<DataInfo> getOutOfMaxErrorInfo(){
		List<DataInfo> dataInfos = new ArrayList<DataInfo>();
		DataInfo data = new DataInfo();
		List<String> heads = new ArrayList<String>();
		heads.add("单次数据导出不能超过20w条，请缩短查询时间，重新导出。");
		data.setSheetName("错误提示");
		data.setHeads(heads);
		dataInfos.add(data);
		return dataInfos;
	}
	
	
	protected boolean isOutOfLimit(List<DataInfo> vos){
		boolean flag = false;
		if(null!=vos){
			for (DataInfo vo : vos) {
				List<TreeMap<String, DataVo>> values = vo.getValues();
				if(null!=values && values.size()>ReportGlobal.EXCEL_MAX_DATA){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	protected boolean isOutOfLimit2(List<ReportVo> vos){
		boolean flag = false;
		if(null!=vos){
			for (ReportVo vo : vos) {
				List<TreeMap<String, DataVo>> values = vo.getValues();
				if(null!=values && values.size()>ReportGlobal.EXCEL_MAX_DATA){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	
	protected void exportFromDataInfo(DataInfo data,OutputStream out) throws Exception{
		POIExcelSupport poi = new POIExcelSupport();
		List<TreeMap<String, DataVo>> values = data.getValues();
        List<String> heards = data.getHeads();
		String fileName = data.getSheetName();
		poi.export(fileName,heards,values);
		poi.workFlush(out);
	}
	
	protected void limit(Integer page,Integer count,Map<String, Object> params,List<TreeMap<String, DataVo>> values,Model model,HttpServletRequest request){
		int pagenum = page == null ? 1 : page;
		Page<TreeMap<String, DataVo>> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum); //Page.DEFAULT_PAGE_SIZE
		pageParam.buildUrl(request);
		pageParam.setItems(values);
		model.addAttribute("pageParam", pageParam);
	}
	
	/**
	 * 设置查询参数
	 * @param l
	 * @param filterMap
	 */
	protected void setParameter(Filter filter,Map<String, Object> filterMap,HttpServletRequest request){
		String val = request.getParameter(filter.getArg());
		log.info("request:"+filter.getArg()+","+val);
		
		//默认查询当前日期到一周前的日期
		if(filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date, -30);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), sd);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START_7){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date, -6);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), sd);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START_15){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date, -14);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), sd);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_START_100){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date, -99);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), sd);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END || filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END_7 || filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END_15 || filter.getType() == ReportGlobal.Filters.INPUT_DATETEXT_END_100 ){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				String d = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), d);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_MONTH){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				int index = filter.getDefaultIndex();
				Date tDate = DateTimeUtils.addMonths(date, index);
				String d = DateTimeUtils.formatTime(tDate, DateTimeUtils.yyyyMM);
				filterMap.put(filter.getArg(), d);
			}else{
				filterMap.put(filter.getArg(), val);
			}			
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_START){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date, -30);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd);
				filterMap.put(filter.getArg(), sd);
			}else{
				filterMap.put(filter.getArg(), val);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_START_7){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date, ReportGlobal.Day.D7);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd);
				filterMap.put(filter.getArg(), sd);
			}else{
				filterMap.put(filter.getArg(), val);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_START_15){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date,-14);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd);
				filterMap.put(filter.getArg(), sd);
			}else{
				filterMap.put(filter.getArg(), val);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_START_100){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				Date d = DateTimeUtils.addDays(date,-99);
				String sd = DateTimeUtils.formatTime(d, DateTimeUtils.yyyyMMdd);
				filterMap.put(filter.getArg(), sd);
			}else{
				filterMap.put(filter.getArg(), val);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_END || filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_END_7 || filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_END_15 || filter.getType() == ReportGlobal.Filters.INPUT_DATETIME_END_100){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				String d = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd);
				filterMap.put(filter.getArg(), d);
			}else{
				filterMap.put(filter.getArg(), val);
			}
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_MONTH_START){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				int index = filter.getDefaultIndex();
				Date tDate = DateTimeUtils.addMonths(date, index);
				String d = DateTimeUtils.formatTime(tDate, DateTimeUtils.yyyyMM);
				filterMap.put(filter.getArg(), d);
			}else{
				filterMap.put(filter.getArg(), val);
			}			
		}else if(filter.getType() == ReportGlobal.Filters.INPUT_MONTH_END){
			if(StringUtil.isEmptyString(val)){
				Date date = DateTimeUtils.getCurrentDate();
				int index = filter.getDefaultIndex();
				Date tDate = DateTimeUtils.addMonths(date, index);
				String d = DateTimeUtils.formatTime(tDate, DateTimeUtils.yyyyMM);
				filterMap.put(filter.getArg(), d);
			}else{
				filterMap.put(filter.getArg(), val);
			}			
		}else if(filter.getType() == ReportGlobal.Filters.SELECT_WORK_START){
			if(StringUtil.isEmptyString(val)){
				Date startWeek = DateTimeUtils.getFirstDayOfCurrentWeek();
				String startWeekStr = DateTimeUtils.formatTime(startWeek, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(),startWeekStr);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else if(filter.getType() == ReportGlobal.Filters.SELECT_WORK_END){
			if(StringUtil.isEmptyString(val)){
				Date startWeek = DateTimeUtils.getFirstDayOfCurrentWeek();
				Date endWeek = DateTimeUtils.addDays(startWeek, +7);
				String endWeekStr = DateTimeUtils.formatTime(endWeek, DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(),endWeekStr);
			}else{
				Date d = DateTimeUtils.StringToDate(val, DateTimeUtils.yyyyMMdd);
				String fVal = DateTimeUtils.formatTime(d,DateTimeUtils.yyyyMMdd2);
				filterMap.put(filter.getArg(), fVal);
			}
		}else{
			filterMap.put(filter.getArg(), val);
		}
	}
	
}
