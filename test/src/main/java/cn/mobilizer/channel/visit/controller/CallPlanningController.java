package cn.mobilizer.channel.visit.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserRelationService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.DistributorService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.service.StoreUserMappingService;
import cn.mobilizer.channel.base.vo.StoreSearchVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.POIExcelSupport;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ChannelEnum.CALL_STATUS;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_POP_TYPE;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.service.CallPlanningService;
import cn.mobilizer.channel.visit.vo.CallPlanningQuery;
@Controller
@RequestMapping(value = "/callPlanning")
public class CallPlanningController extends BaseActionSupport { 
	private static final long serialVersionUID = 1L;
	@Autowired
	private CallPlanningService callPlanningService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientPositionService   clientPositionService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private DistributorService distributorService;
	@Autowired
	private StoreUserMappingService storeUserMappingService;
	@Autowired
	private ClientUserRelationService clientUserRelationService;
	@Autowired
	private OptionsService	optionsService;
	@Autowired
	private ClientStructureService clientStructureService;
	
	@RequestMapping(value = "/query",method = RequestMethod.GET)
	public String query(Model model, Integer page,HttpServletRequest req, CallPlanningQuery callQuery) throws BusinessException, ParseException{
		Map<String, Object> parameters = new HashMap<String, Object>();
		Integer structureId = callQuery.getStructureId()!= null?callQuery.getStructureId():getClientStructureId();
		String startDate = callQuery.getStartDate();
		String endDate = callQuery.getEndDate();
		//初始化时，取当周的计划(从周一开始)
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(callQuery.getEndDate())){
			Calendar cal =Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
			startDate = DateTimeUtils.formatTime(cal.getTime(), DateTimeUtils.yyyyMMdd);
			endDate = DateTimeUtils.formatTime(DateTimeUtils.addDays(DateTimeUtils.getFirstDayOfCurrentWeek() , 7), DateTimeUtils.yyyyMMdd);
		}
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
		
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		callQuery.setSubordinates(subordinates);
		
		parameters.put("clientId", getClientId());
		parameters.put("startCallDate", startDate);
		parameters.put("endCallDate", endDate);
		parameters.put("clientStructureId", structureId);
		parameters.put("clientUserName", callQuery.getClientUserName());
		parameters.put("clientStructureName", callQuery.getClientStructureName());
		parameters.put("clientPositionId", callQuery.getClientPositionId());
		parameters.put("visitType", callQuery.getVisitType());
		Integer enumValue = callQuery.getEnumValue();
		if(enumValue != null && enumValue == Constants.WEB_VISIT){  
				parameters.put("workType",Constants.WEB_VISIT);
		}else{
			parameters.put("enumValue",enumValue);
		}
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", callQuery.getSubordinates());
		if(callQuery.getStructureId() == null){
			callQuery.setStructureId(getClientStructureId());
		}
		//人员总数
		int count = callPlanningService.queryCallPlanningCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<CallPlanning> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<CallPlanning> list = callPlanningService.getCallPlanning(parameters);
		
		pageParam.setItems(list);
		/**获取当前时间的下一天*/
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String today1 = sim.format(new Date());
		Date today =sim.parse(today1);
		if (list != null && list.size() > 0) {
			for (CallPlanning callPlanning : list) {
				if (callPlanning != null) {
					if (callPlanning.getCallDate() != null){
						Date callDate = callPlanning.getCallDate();
						if (today.getTime() > callDate.getTime()) {
							callPlanning.setShowEdit(Constants.NO_SHOW_EDIT);
						}if (today.getTime() <= callDate.getTime()) {
							callPlanning.setShowEdit(Constants.SHOW_EDIT);
						}
					}else {
						callPlanning.setShowEdit(Constants.NO_SHOW_EDIT);
					}
				}
			}
		}
		
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("sysDate", new Date());
		model.addAttribute("clientUserName", callQuery.getClientUserName());
		model.addAttribute("clientStructureId", callQuery.getStructureId());
		model.addAttribute("clientPositionId", callQuery.getClientPositionId());
		model.addAttribute("visitType",callQuery.getVisitType());
		model.addAttribute("enumValue",callQuery.getEnumValue());
		return "visit/callPlanningList";
	}
	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(Model model){
		int clientId = getClientId();
		model.addAttribute("clientId",clientId);
		return "visit/importCallPlanning";
	}
	
	@RequestMapping(value = "import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Object importCallPlanning(Model model, MultipartFile file, HttpServletRequest req, HttpServletResponse res,Integer clientId) throws Exception {
		if(clientId.equals(Constants.CTBATIMPORT)){
			ResultMessage reMessage = callPlanningService.addPlannImport(file, req, res, clientId);
			return reMessage;
		}
		List<CallPlanning> cps = new ArrayList<CallPlanning>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		String[] titles = values.get(0);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.CALL_PLANNING_TITLE, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		//数据校验
		//拼装数据
		String[] ctitles = values.get(0);
		
		for (int i = 1; i < values.size(); i++) {
			CallPlanning cp = new CallPlanning();
			Method[] methods = cp.getClass().getMethods();
			String[] vv = values.get(i);
			for (int j = 0; j < vv.length; j++) {
				String kvalue = vv[j];//值
				String cvalue = null;//列名
				if(StringUtils.isEmpty(kvalue)){
					ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
					rm.setMessage("内容不能为空");
					return rm;
				}
				for (int k = 0; k < ImportConstants.CALL_PLANNING_TITLE.length; k++) {
					if(ImportConstants.CALL_PLANNING_TITLE[k].equals(ctitles[j])){
						cvalue = ImportConstants.CALL_PLANNING_COLUMN[k];
						break;
					}
				}
				
				for (int k = 0; k < methods.length; k++) {
					if(methods[k].getName().equalsIgnoreCase("set" + cvalue)){
						methods[k].invoke(cp, kvalue);
						break;
					}
				}
				if(ImportConstants.CLIENT_USER_NAME.equals(cvalue)){
					Map<String, Object> paras = new HashMap<String, Object>();
					paras.put("name", kvalue);
					paras.put("clientId", getClientId());
					ClientUser user = clientUserService.getObject(paras);
					if(user != null){
						cp.setClientUserId(user.getClientUserId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知姓名：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.POP_TYPE.equals(cvalue)){
					if("门店".equals(kvalue)){
						cp.setPopType(VISIT_POP_TYPE.STORE.getKey ());
					}else if("经销商".equals(kvalue)){
						cp.setPopType(VISIT_POP_TYPE.DISTRIBUTOR.getKey ());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知门店类型：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.POP_NAME.equals(cvalue)){
					Map<String, Object> paras = new HashMap<String, Object>();
					paras.put("storeName", kvalue);
					paras.put("clientId", getClientId());
					List<Store> stores = storeService.getObjectList(paras);
					if(stores != null && stores.size() > 1){
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("发现名称不唯一门店：" + kvalue);
						return rm;
					}else{
						Store store = stores.get(0);
						if(store != null){
							cp.setPopId(store.getStoreId());
						}else{
							ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
							rm.setMessage("未知门店：" + kvalue);
							return rm;
						}						
					}
				}else if(ImportConstants.CALL_DATE.equals(cvalue)){
					try {
						cp.setCallDate(DateUtil.getDateByStr(kvalue, DateUtil.dateFormat));
					} catch (Exception e) {
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("拜访日期格式错误：" + kvalue);
						return rm;
					}
				}
			}
			//设置关联属性
			cp.setPlanningId(UUID.randomUUID().toString());
			cp.setClientId(getClientId());
			cps.add(cp);
		}
		if(callPlanningService.saveAll(cps))
			return ResultMessage.IMPORT_SUCCESS_RESULT;
		else
			return ResultMessage.IMPORT_FAIL_RESULT;
	}
	/***
	 * 拜访编辑页面
	 * @param model
	 * @param req
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/showVisitCallPlanning/{clientUserId}/{callDate}/{workType}/{visitType}")
	public String showVisitCallPlanning(@PathVariable("clientUserId")Integer clientUserId,@PathVariable("callDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,@PathVariable("workType")Integer workType,String searchStoreName,String callStatus,@PathVariable("visitType")Integer visitType, Model model,HttpServletRequest req){
		Integer clientId = getClientId();
		if (workType == 0) {
			Map<String,Object> parameters = new HashMap<String, Object>();
			//parameters.put("storeName", searchStoreName);
			parameters.put("clientUserId", clientUserId);
			parameters.put("clientId", clientId);
			parameters.put("workType", workType);
			//parameters.put("callStatus", callStatus);
			parameters.put("parentId", clientUserId);
			parameters.put("startDate", DateUtil.getDayStart(callDate));
			parameters.put("endDate", DateUtil.getDayEnd(callDate));
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			if(visitType != null && visitType == 2){
				String chilStr=clientUserRelationService.getDirectChilds(parameters);
				parameters.put("chilStr", chilStr);
			}else{
				parameters.put("chilStr", clientUserId);
			}
			List<Store> storeList = storeUserMappingService.queryStoreList(parameters);
			String oldStoreIds="";
			if(storeList != null && storeList.size() > 0){
				for (Store store : storeList) {
					if(store.getVisitYesOrNot() != null){
						oldStoreIds +=","+store.getVisitYesOrNot();
					}
				}
			}
			model.addAttribute("oldStoreIds", oldStoreIds);
		}
		model.addAttribute("searchStoreName", searchStoreName);
		model.addAttribute("callStatuslist", CALL_STATUS.values());
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("callDate", callDate);
		model.addAttribute("workType", workType);
		model.addAttribute("visitType", visitType);
		return "/visit/showEditCallPlanning";
	}
	
	/**编辑门店拜访嵌入页面
	 * @author Nany
	 * 2015年1月26日下午5:14:14
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/showEditDialog")
	public String showEditDialog(Integer clientUserId,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,Integer workType,Integer visitType,
			String searchStoreName,Integer callStatus,Integer page,Model model,HttpServletRequest req) throws ParseException {
		Integer clientId = getClientId();
		//门店拜访
		if (workType == Constants.WEB_VISIT) {
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("storeName", searchStoreName);
			parameters.put("clientUserId", clientUserId);
			parameters.put("clientId", clientId);
			parameters.put("workType", workType);
			parameters.put("callStatus", callStatus);
			parameters.put("parentId", clientUserId);
			parameters.put("startDate", DateUtil.getDayStart(callDate));
			parameters.put("endDate", DateUtil.getDayEnd(callDate));
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			if(visitType != null && visitType == 2){
				String chilStr=clientUserRelationService.getDirectChilds(parameters);
				parameters.put("chilStr", chilStr);
			}else{
				parameters.put("chilStr", clientUserId);
			}
			//获取该业务员相关联的门店
			int count = storeUserMappingService.queryStoreUserMappingCount(parameters);
			int pagenum = page == null ? 1 : page;
			Page<Store> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Store> storeList = storeUserMappingService.queryStoreList(parameters);
			pageParam.setItems(storeList);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", pageParam.getPage().toString());
			model.addAttribute("clientUserId", clientUserId);
			model.addAttribute("callDate", callDate);
			model.addAttribute("forematCallDate", DateUtil.getDateTime("yyyy-MM-dd",callDate));
			model.addAttribute("toDate", DateUtil.getDateTime("yyyy-MM-dd",new Date()));
			model.addAttribute("workType", workType);
			return "/visit/editVisitCallPlanning";
		}
		return null;
	}
	/**新增执行计划管理(复选框选中保存)
	 * @author Nany
	 * 2015年2月10日上午11:25:03
	 * @param storeId
	 * @param clientUserId
	 * @param callDate
	 * @param visitType
	 * @param isDelete
	 * @throws ParseException
	 */
	@RequestMapping(value="/addCallPlanning")
	@ResponseBody
	public Object addCallPlanning(String storeId,Integer clientUserId,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,Integer visitType,Integer workType,Integer enumValue,Byte isDelete) throws ParseException {
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", DateUtil.getDayStart(callDate));
		parameters.put("endDate", DateUtil.getDayEnd(callDate));
		parameters.put("clientId",clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("callDate",callDate);
		parameters.put("visitType", visitType);
		parameters.put("workType", Constants.NOT_WEB_VISIT);
		String[]  strStoreIds=(storeId != null && !storeId.equals(""))?storeId.split(","):null;
		if(strStoreIds != null){
			for (int j = 0; j < strStoreIds.length; j++) {
				parameters.put("storeId", strStoreIds[j]);
				List<CallPlanning> callPlanningList = callPlanningService.getCallPlanningByClientUserIdAndStoreId(parameters);
				if (callPlanningList != null && callPlanningList.size() > 0) {
					for (CallPlanning callPlanning : callPlanningList) {
						parameters.put("isDelete", Constants.IS_DELETE_FALSE);
						parameters.put("planningId", callPlanning.getPlanningId());
						List<Object> findCallPlanning = callPlanningService.findCallPlanning(parameters);
						if(findCallPlanning != null && findCallPlanning.size() > 0){
							return ResultMessage.CALL_PLANNING_TYPE;
						}else{
							callPlanningService.updateCallPlanning(parameters);
						}
					}
				}else {
					CallPlanning callPlanning = new CallPlanning();
					callPlanning.setPopId(strStoreIds[j]);
					callPlanning.setClientId(clientId);
					callPlanning.setClientUserId(clientUserId);
					callPlanning.setCallDate(callDate);
					callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
					callPlanning.setPopType(Constants.STORE_POP_TYPE);
					callPlanning.setVisitType(visitType);
					callPlanning.setCallStatus(Constants.NO_CALL_STATUS);
					callPlanning.setWorkType(Constants.WEB_VISIT);
					callPlanning.setEnumValue(null);
					String planningId = UUID.randomUUID ().toString ();
					callPlanning.setPlanningId(planningId);
					List<Object> findCallPlanning = callPlanningService.findCallPlanning(parameters);
					if(findCallPlanning != null && findCallPlanning.size() > 0){
						return ResultMessage.CALL_PLANNING_TYPE;
					}else{
						callPlanningService.addCallPlanning(callPlanning);
					}
				}
			}
			return ResultMessage.ADD_SUCCESS_RESULT;
		}
		return ResultMessage.ADD_FAIL_RESULT;
	}
	/**
	 * 删除非拜访 新增 拜访
	 * @return
	 */
	@RequestMapping(value="/isDeleteNotCallplanning")
	@ResponseBody
	public Object isDeleteNotCallplanning(String storeId,Integer clientUserId,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,Integer visitType,Integer workType,Integer enumValue,Byte isDelete){
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", DateUtil.getDayStart(callDate));
		parameters.put("endDate", DateUtil.getDayEnd(callDate));
		parameters.put("clientId",clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("callDate",callDate);
		parameters.put("visitType",visitType);
		/**删除当天的非拜访计划，重新新增拜访一条**/
		callPlanningService.deleteCallPlanningByDay(parameters);
		String[]  strStoreIds=(storeId != null && !storeId.equals(""))?storeId.split(","):null;
		if(strStoreIds != null){
			for (int j = 0; j < strStoreIds.length; j++) {
				parameters.put("storeId", strStoreIds[j]);
				List<CallPlanning> callPlanningList = callPlanningService.getCallPlanningByClientUserIdAndStoreId(parameters);
				if (callPlanningList != null && callPlanningList.size() > 0) {
					for (CallPlanning callPlanning : callPlanningList) {
						parameters.put("isDelete", Constants.IS_DELETE_FALSE);
						parameters.put("planningId", callPlanning.getPlanningId());
						callPlanningService.updateCallPlanning(parameters);
					}
				}else {
					CallPlanning callPlanning = new CallPlanning();
					callPlanning.setPopId(strStoreIds[j]);
					callPlanning.setClientId(clientId);
					callPlanning.setClientUserId(clientUserId);
					callPlanning.setCallDate(callDate);
					callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
					callPlanning.setPopType(Constants.STORE_POP_TYPE);
					callPlanning.setVisitType(visitType);
					callPlanning.setCallStatus(Constants.NO_CALL_STATUS);
					callPlanning.setWorkType(Constants.WEB_VISIT);
					callPlanning.setEnumValue(null);
					String planningId = UUID.randomUUID ().toString ();
					callPlanning.setPlanningId(planningId);
					callPlanningService.addCallPlanning(callPlanning);
				}
			}
			return ResultMessage.ADD_SUCCESS_RESULT;
		}
		return ResultMessage.WEB_OPERATE_FAIL;
	}
	/**新增非执行计划管理
	 * @author Nany
	 * 2015年2月10日上午11:25:03
	 * @param storeId
	 * @param clientUserId
	 * @param callDate
	 * @param visitType
	 * @param isDelete
	 * @throws ParseException
	 */
	@RequestMapping(value="/addNotCallPlanning")
	@ResponseBody
	public Object addNotCallPlanning(String storeId,Integer clientUserId,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,Integer visitType,Integer workType,Integer enumValue,Byte isDelete,Boolean deleteOld){
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", DateUtil.getDayStart(callDate));
		parameters.put("endDate", DateUtil.getDayEnd(callDate));
		parameters.put("clientId",clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("callDate",callDate);
		if(!deleteOld) {
			List<Object> findCallPlanning = callPlanningService.findCallPlanning(parameters);
			if(findCallPlanning != null && findCallPlanning.size() > 0){
				return ResultMessage.WEB_CALL_PLANNING_TYPE;
			} else {
				CallPlanning callPlanning = new CallPlanning();
				callPlanning.setClientUserId(clientUserId);
				callPlanning.setPopType(Constants.STORE_POP_TYPE);
				callPlanning.setCallStatus(Constants.NO_CALL_STATUS);
				callPlanning.setCallDate(callDate);
				callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
				callPlanning.setVisitType(visitType);
				callPlanning.setWorkType(Constants.NOT_WEB_VISIT);
				callPlanning.setEnumValue(enumValue);
				callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
				String planningId = UUID.randomUUID ().toString ();
				callPlanning.setPlanningId(planningId);
				callPlanning.setClientId(clientId);
				callPlanningService.addCallPlanning(callPlanning);
			}
		} else {
			/**删除当天的计划，重新新增一条**/
			parameters.put("visitType",visitType);
			callPlanningService.deleteCallPlanningByDay(parameters);
			CallPlanning callPlanning = new CallPlanning();
			callPlanning.setClientUserId(clientUserId);
			callPlanning.setCallDate(callDate);
			callPlanning.setPopType(Constants.STORE_POP_TYPE);
			callPlanning.setCallStatus(Constants.NO_CALL_STATUS);
			callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
			callPlanning.setVisitType(visitType);
			callPlanning.setWorkType(Constants.NOT_WEB_VISIT);
			callPlanning.setEnumValue(enumValue);
			callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
			String planningId = UUID.randomUUID ().toString ();
			callPlanning.setPlanningId(planningId);
			callPlanning.setClientId(clientId);
			callPlanningService.addCallPlanning(callPlanning);
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	/**
	 * 编辑拜访
	 * @param storeId
	 * @param clientUserId
	 * @param callDate
	 * @param visitType
	 * @param isDelete
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/editCallPlanning")
	@ResponseBody
	public Object editCallPlanning(String storeId,String oldStr,Integer clientUserId,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,Integer visitType,Byte isDelete) throws ParseException {
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId",clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("callDate", callDate);
		parameters.put("visitType", visitType);
		parameters.put("storeId", storeId);
		String planningId = UUID.randomUUID ().toString ();
		parameters.put("planningId", planningId);
		parameters.put("popType", Constants.STORE_POP_TYPE);
		parameters.put("planningType", Constants.WEB_PLANNING_TYPE);
		parameters.put("workType", Constants.WEB_VISIT);
		parameters.put("callStatus", Constants.WEB_VISIT);
		parameters.put("enumValue", null);
		//选中的门店全删
		String[]  newStoreIds = (storeId != null && !storeId.equals(""))?storeId.split(","):null;
		String[] oldStoreIds = (oldStr != null && !oldStr.equals(""))?oldStr.split(","):null;
		if (oldStoreIds == null) {
			if (newStoreIds != null) {
				findCallplanning(newStoreIds,parameters,clientId,clientUserId,callDate,visitType);
			}
		} else {
			if (newStoreIds == null) {
				parameters.put("popIds", oldStr);
				callPlanningService.updateisdelte(parameters);
			} else {
				/** 获取old中存在而new中不存在的门店，删除 **/
				String oldStrStoresIds = ArrayUtil.arraySubtract2Str(oldStoreIds, newStoreIds);
				if (oldStrStoresIds != null && oldStrStoresIds != "") {
					parameters.put("popIds", oldStrStoresIds);
					callPlanningService.updateisdelte(parameters);
				}
				/** 获取new中存在而old中不存在的门店，新增 **/
				String[] newStrStoresIds = ArrayUtil.arraySubtract(newStoreIds, oldStoreIds);
				if (newStrStoresIds != null && newStrStoresIds.length > 0) {
					findCallplanning(newStrStoresIds,parameters,clientId,clientUserId,callDate,visitType);
				}
			}
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	private void findCallplanning(String[] str,Map<String, Object> parameters,Integer clientId,Integer clientUserId,Date callDate,Integer visitType){
		for (int i = 0; i < str.length; i++) {
			parameters.put("popId", str[i]);
			CallPlanning	oldcallPlanning = callPlanningService.findCallPlanningIsdelete(parameters);
			if(oldcallPlanning != null){
				oldcallPlanning.setIsDelete(Constants.IS_DELETE_FALSE);
				callPlanningService.updateCallPlanning(oldcallPlanning);
			}else{
				CallPlanning callPlanning = new CallPlanning();
				callPlanning.setPopId(str[i]);
				callPlanning.setClientId(clientId);
				callPlanning.setClientUserId(clientUserId);
				callPlanning.setCallDate(callDate);
				callPlanning.setPopType(Constants.STORE_POP_TYPE);
				callPlanning.setCallStatus(Constants.NO_CALL_STATUS);
				callPlanning.setPlanningType(Constants.WEB_PLANNING_TYPE);
				callPlanning.setVisitType(visitType);
				String planningId = UUID.randomUUID ().toString ();
				callPlanning.setPlanningId(planningId);
				callPlanningService.addCallPlanning(callPlanning);
			}
		}
	}
	/**
	 * 编辑非拜访
	 * @param storeId
	 * @param clientUserId
	 * @param callDate
	 * @param visitType
	 * @param isDelete
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/editNotCallPlanning")
	@ResponseBody
	public Object editNotCallPlanning(CallPlanning callPlanning){
		Integer clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", DateUtil.getDayStart(callPlanning.getCallDate()));
		parameters.put("endDate", DateUtil.getDayEnd(callPlanning.getCallDate()));
		parameters.put("clientId",clientId);
		parameters.put("clientUserId", callPlanning.getClientUserId());
//		parameters.put("planningId",callPlanning.getPlanningId());
		//parameters.put("workType", Constants.WEB_VISIT);
		List<Object> findCallPlanning = callPlanningService.findCallPlanning(parameters);
		if(findCallPlanning != null && findCallPlanning.size() > 0 && !callPlanning.getCallDate().equals(callPlanning.getOldCallDate())){
			return ResultMessage.WEB_CALL_PLANNING_TYPE;
		}else{
			Integer rows = callPlanningService.updateCallPlanning(callPlanning);
			if(rows > 0){
				return ResultMessage.UPDATE_SUCCESS_RESULT;
			}else{
				return ResultMessage.UPDATE_FAIL_RESULT;
			}
		}
		
	}
	
	/**查看
	 * @author Nany
	 * 2015年2月10日下午12:06:56
	 * @param clientUserId
	 * @param callDate
	 * @param visitType
	 * @param searchStoreName
	 * @param page
	 * @param model
	 * @param req
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/moreDetail/{clientUserId}/{callDate}/{visitType}/{workType}")
	public String moreDetail(@PathVariable("clientUserId")Integer clientUserId,@PathVariable("callDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,@PathVariable("visitType")Integer visitType,@PathVariable("workType")Integer workType,
			String searchStoreName,Integer callStatus,Integer page,Model model,HttpServletRequest req) throws ParseException {
		Integer clientId = getClientId();
		//门店拜访
		if (workType == Constants.WEB_VISIT) {
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("storeName", searchStoreName);
			parameters.put("clientUserId", clientUserId);
			parameters.put("clientId", clientId);
			parameters.put("visitType", visitType);
			parameters.put("workType", workType);
			parameters.put("callStatus", callStatus);
			parameters.put("parentId", clientUserId);
			parameters.put("startDate", DateUtil.getDayStart(callDate));
			parameters.put("endDate", DateUtil.getDayEnd(callDate));
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			if(DateUtil.getTodayYMDDate().getTime() > callDate.getTime()){
				parameters.put("LookIsdelete", Constants.IS_DELETE_TRUE);
			}
			if(visitType != null && visitType == 2){
				String chilStr=clientUserRelationService.getDirectChilds(parameters);
				parameters.put("chilStr", chilStr);
			}else{
				parameters.put("chilStr", clientUserId);
			}
			//获取该业务员相关联的门店
			int count = storeUserMappingService.queryStoreUserMappingCount(parameters);
			int pagenum = page == null ? 1 : page;
			Page<Store> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Store> storeList = storeUserMappingService.queryStoreList(parameters);
			
			pageParam.setItems(storeList);
			String oldStoreIds="";
			if(storeList != null && storeList.size() > 0){
				for (Store store : storeList) {
					if(store.getVisitYesOrNot() != null){
						oldStoreIds +=","+store.getVisitYesOrNot();
					}
				}
			}
			model.addAttribute("oldStoreIds", oldStoreIds);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", pageParam.getPage().toString());
			model.addAttribute("clientUserId", clientUserId);
			model.addAttribute("callDate", callDate);
			model.addAttribute("visitType", visitType);
			model.addAttribute("callStatusId", callStatus);
			model.addAttribute("searchStoreName", searchStoreName);
			model.addAttribute("callStatuslist", CALL_STATUS.values());
			return "/visit/showCallPlanningDetail";
		}
	
		return null;
	}
	/**新增执行计划
	 * @author Nany
	 * 2015年2月26日上午10:17:02
	 * @param model
	 * @param callDate
	 * @param storeSearchVO
	 * @return
	 */
	@RequestMapping(value="/showAddCallPlanning")
	public String showAddCallPlanning(Model model,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,StoreSearchVO storeSearchVO) {
		
		model.addAttribute("workType", ChannelEnum.VISIT_TYPE.values());
		Date minDate = DateUtil.getAfterDay(new Date());
		if (callDate == null) {
			Date today = new Date();
			callDate = DateUtil.getAfterDay(today);
		}
		
		model.addAttribute("storeSearchVO", storeSearchVO);
		model.addAttribute("callDate", callDate);
		model.addAttribute("minDate", minDate);
		return "/visit/showAddCallPlanning";
	}
	/**新增非拜访执行计划
	 * @author Nany
	 * 2015年2月26日上午10:17:02
	 * @param model
	 * @param callDate
	 * @param storeSearchVO
	 * @return
	 */
	@RequestMapping(value="/showAddOldCallPlanning")
	public String showAddOdlCallPlanning(Model model,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,StoreSearchVO storeSearchVO) {
		
		model.addAttribute("workType", ChannelEnum.VISIT_TYPE.values());
		Date minDate = DateUtil.getAfterDay(new Date());
		if (callDate == null) {
			Date today = new Date();
			callDate = DateUtil.getAfterDay(today);
		}
		
		model.addAttribute("storeSearchVO", storeSearchVO);
		model.addAttribute("callDate", callDate);
		model.addAttribute("minDate", minDate);
		return "/visit/showAddOdlCallPlanning";
	}
	/**查询出业务员要拜访的门店
	 * @author Nany
	 * 2015年2月13日下午3:33:17
	 * @param model
	 * @param visiByte
	 * @param callDate
	 * @param clientUserId
	 * @param page
	 * @param storeSearchVO
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/showAddCallPlanningDetail")
	public String showAddCallPlanning(Model model,Integer visitType,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate,Integer clientUserId,String clientUserName,Integer page,StoreSearchVO storeSearchVO,HttpServletRequest req) {
		//拜访
			Integer clientId = getClientId();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("startDate", DateUtil.getDayStart(callDate));
			parameters.put("endDate", DateUtil.getDayEnd(callDate));
			parameters.put("clientId", clientId);
			parameters.put("parentId", storeSearchVO.getClientUserId ());
			parameters.put("clientUserId", clientUserId);
			parameters.put("visitType", visitType);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			if(visitType != null && visitType == 2){
				String chilStr=clientUserRelationService.getDirectChilds(parameters);
				parameters.put("chilStr", chilStr);
			}else{
				parameters.put("chilStr", storeSearchVO.getClientUserId ());
			}
			//获取该业务员相关联的门店
			int count = storeUserMappingService.queryStoreUserMappingCount(parameters);
			if (clientUserId == null) {
				count = 0;
			}
			int pagenum = page == null ? 1 : page;
			Page<Store> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Store> storeList = storeUserMappingService.queryStoreList(parameters);
			ClientUser clientUser = null;
			if (clientUserId != null) {
				clientUser = clientUserService.getClientUser(clientUserId, clientId);
			}
			if (clientUserId == null) {
				storeList = null;
			}
			pageParam.setItems(storeList);
			List<Object> findCallPlanning = callPlanningService.findCallPlanning(parameters);
			model.addAttribute("findCallPlanning", findCallPlanning);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", page);
			model.addAttribute("clientUser", clientUser);
			model.addAttribute("page", pageParam.getPage().toString());
			return "/visit/addVisitCallPlanning";
	}
	/**
	 * 取消拜访计划
	 * @param parmas
	 * @return
	 */
	@RequestMapping(value="/cancelCallPlanning")
	@ResponseBody
	public Object cancelCallPlanning(String storeId,Integer clientUserId,Byte visitType,@DateTimeFormat(pattern="yyyy-MM-dd")Date callDate){
		Integer clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("storeId", storeId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("clientId", clientId);
		parameters.put("visitType", visitType);
		parameters.put("callDate", callDate);
		int rows=callPlanningService.updateCallStatus(parameters);
		if(rows>0){
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		}else{
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	/**
	 * 任务内容
	 * @return
	 */
	@RequestMapping(value = "/getTaskContentsAjax")
	@ResponseBody
	public List<Options> getTaskContentsAjax(){
		Integer clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("optionName", "work_type");
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		List<Options> queryOptions = optionsService.queryOptions(parameters);
		return queryOptions;
		
	}
	/**
	 * 删除执行计划
	 * @param planningId
	 * @return
	 */
	@RequestMapping(value = "/deleteCallPlanning")
	@ResponseBody
	public Object deleteCallPlanning(String callDate,Integer clientUserId,Integer visitType){
		Integer clientId = getClientId();
		Integer rows=callPlanningService.deleteCallPlanning(clientId,callDate,clientUserId,visitType);
		if(rows > 0){
			return ResultMessage.DELETE_SUCCESS_RESULT;
		}else{
			return ResultMessage.DELETE_FAIL_RESULT;
		}
	}
	/**
	 * 非执行计划页面
	 * @param planningId
	 * @return
	 */
	@RequestMapping(value = "/showEditVisitCallPlanning/{planningId}")
	public String showEditVisitCallPlanning(Model model,@PathVariable("planningId")String planningId){
		Date minDate = DateUtil.getAfterDay(new Date());
		CallPlanning callPlanning = callPlanningService.getCallPlanningById(planningId);
		model.addAttribute("callPlanning", callPlanning);
		model.addAttribute("minDate", minDate);
		return "/visit/showEditVisitCallPlanning";
	}
	/**
	 * 查看页面
	 * @param planningId
	 * @return
	 */
	@RequestMapping(value = "/showLookVisitCallPlanning/{planningId}")
	public String showLookVisitCallPlanning(Model model,@PathVariable("planningId")String planningId){
		CallPlanning callPlanning = callPlanningService.getCallPlanningById(planningId);
		model.addAttribute("callPlanning", callPlanning);
		return "/visit/showLookVisitCallPlanning";
	}
	/**
	 * 计划导出
	 * @param req
	 * @param resp
	 * @param callQuery
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value ="/showExportDialog")
	public void showExportDialog(HttpServletRequest req,HttpServletResponse resp, CallPlanningQuery callQuery) throws UnsupportedEncodingException{
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		Integer structureId = callQuery.getStructureId()!= null?callQuery.getStructureId():getClientStructureId();
		String startDate = callQuery.getStartDate();
		String endDate = callQuery.getEndDate();
		//初始化时，取当周的计划(从周一开始)
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(callQuery.getEndDate())){
			Calendar cal =Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
			startDate = DateTimeUtils.formatTime(cal.getTime(), DateTimeUtils.yyyyMMdd);
			endDate = DateTimeUtils.formatTime(DateTimeUtils.addDays(DateTimeUtils.getFirstDayOfCurrentWeek() , 7), DateTimeUtils.yyyyMMdd);
		}
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
		
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		callQuery.setSubordinates(subordinates);
		
		parameters.put("clientId", getClientId());
		parameters.put("startCallDate", startDate);
		parameters.put("endCallDate", endDate);
		parameters.put("clientStructureId", structureId);
		parameters.put("clientUserName", callQuery.getClientUserName());
		parameters.put("clientStructureName", callQuery.getClientStructureName());
		parameters.put("clientPositionId", callQuery.getClientPositionId());
		parameters.put("visitType", callQuery.getVisitType());
		Integer enumValue = callQuery.getEnumValue();
		if(enumValue != null && enumValue == Constants.WEB_VISIT){  
				parameters.put("workType",Constants.WEB_VISIT);
		}else{
			parameters.put("enumValue",enumValue);
		}
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", callQuery.getSubordinates());
		if(callQuery.getStructureId() == null){
			callQuery.setStructureId(getClientStructureId());
		}
		List<CallPlanning> callPlanningList = callPlanningService.exportCallPlanning(parameters);
	/*	if(callPlanningList != null && !callPlanningList.isEmpty()){*/
			HSSFWorkbook  wb = new HSSFWorkbook();
			ClientStructure clientStructure = clientStructureService.getClientStructureById(structureId);
			String clientStructureName = clientStructure.getStructureName();
			String excelName = null;
			if(clientStructureName.equals(callQuery.getClientStructureName())){
				excelName = clientStructure.getStructureName()+"计划数据";
			}else{
				excelName = clientStructure.getStructureName()+"-"+callQuery.getClientStructureName()+"计划数据";
			}
			resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
			resp.setContentType("application/vnd.ms-excel");
			/**在we中创建一个sheet*/
			HSSFSheet wTSSheet = wb.createSheet("计划数据");
			wTSSheet.setDefaultColumnWidth((short) 20);
			/**在wTSSheet值添加表头(第0行)*/
			HSSFRow row = wTSSheet.createRow((int)0);
			/**创建单元格，并设置表头，设置表头居中*/
			HSSFCellStyle headStyle = this.createHeaderStyle(wb);
			HSSFCellStyle bodyStyle = this.createContentStyle(wb);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(headStyle);
			String[] callHead = ImportConstants.CALLPLANNING_EXPORT;
			for (int i = 0; i < callHead.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(callHead[i]);
				cell.setCellStyle(headStyle);
			}
			for (int i = 0; i < callPlanningList.size(); i++) {
				row = wTSSheet.createRow((int) i + 1); 
				CallPlanning callPlanning = callPlanningList.get(i);
				HSSFCell createCell = row.createCell(0);
				createCell.setCellValue(i+1);
				createCell.setCellStyle(bodyStyle);
				row.createCell(1).setCellValue(callPlanning.getParentStructureName() == null?"":callPlanning.getParentStructureName());
				row.createCell(2).setCellValue(callPlanning.getStructureName() == null?"":callPlanning.getStructureName());
				row.createCell(3).setCellValue(callPlanning.getName() == null?"":callPlanning.getName());
				row.createCell(4).setCellValue(callPlanning.getLoginName() == null?"":callPlanning.getLoginName());
				row.createCell(5).setCellValue(callPlanning.getPositionName() == null?"":callPlanning.getPositionName());
				row.createCell(6).setCellValue(callPlanning.getParentName() == null?"":callPlanning.getParentName());
				if(callPlanning.getCallDate() instanceof Date){
					String callDate = DateTimeUtils.formatTime(callPlanning.getCallDate(),"yyyy-MM-dd");
					row.createCell(7).setCellValue(callDate == null?"":callDate);
				}else{
					row.createCell(7).setCellValue("");
				}
				if(callPlanning.getWorkType() == 0){
					row.createCell(8).setCellValue("走店");
				}else{
					row.createCell(8).setCellValue(callPlanning.getOptionText() == null?"":callPlanning.getOptionText());
				}
				if(callPlanning.getVisitType().equals(Constants.RUN_VISIT_TYPE)){
					row.createCell(9).setCellValue("门店拜访");
				}else if(callPlanning.getVisitType().equals(Constants.TO_VISIT_TYPE)){
					row.createCell(9).setCellValue("门店协访");
				}else{
					row.createCell(9).setCellValue("店内工作");
				}
				row.createCell(10).setCellValue(callPlanning.getStoreNo() == null?"":callPlanning.getStoreNo());
				row.createCell(11).setCellValue(callPlanning.getStoreName() == null?"":callPlanning.getStoreName());
			}
			try {
				OutputStream out = resp.getOutputStream();
				wb.write(out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
	}
	/**
	 * 设置表头样式
	 * @param style
	 */
	private HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
		
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}
	
	/**
	 * 设置正文样式
	 * @param style
	 */
	private HSSFCellStyle createContentStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style.setFont(font);
		return style;
	}
}
