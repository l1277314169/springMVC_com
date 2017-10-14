package cn.mobilizer.channel.visit.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserRelationService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISITING_TASK_TYPE;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.po.VisitingParameter;
import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.po.VisitingTaskData;
import cn.mobilizer.channel.visit.po.VisitingTaskStep;
import cn.mobilizer.channel.visit.po.VisitingTaskStepObject;
import cn.mobilizer.channel.visit.service.CallPlanningService;
import cn.mobilizer.channel.visit.service.VisitTaskDataService;
import cn.mobilizer.channel.visit.service.VisitingParameterService;
import cn.mobilizer.channel.visit.service.VisitingTaskDetailDataService;
import cn.mobilizer.channel.visit.service.VisitingTaskStepObjectService;
import cn.mobilizer.channel.visit.service.VisitingTaskStepService;
import cn.mobilizer.channel.visit.vo.VisitTaskDataReportVO;

@Controller
@RequestMapping(value = "/visitTaskData")
public class VisitTaskDataController extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private VisitTaskDataService visitTaskDataService;
	@Autowired
	private VisitingTaskDetailDataService visitingTaskDetailDataService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private VisitingTaskStepService visitingTaskStepService;
	@Autowired
	private VisitingParameterService visitingParameterService;
	@Autowired
	private VisitingTaskStepObjectService visitingTaskStepObjectService;
	@Autowired
	private CallPlanningService callPlanningService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private DataSource dataSourceBase;
	@Autowired
	private ClientUserRelationService clientUserRelationService;

	@RequestMapping(value = "/query/{taskType}", method = RequestMethod.GET)
	public String query(@PathVariable("taskType") Byte taskType, Model model, Integer page,/*
																							 * Integer
																							 * task1id
																							 * ,
																							 */HttpServletRequest req, String userName, Integer structureId, Integer positionId, String visitingDate, Integer visitedFlag) throws BusinessException {

		int pagenum = page == null ? 1 : page;
		Integer clientId = getClientId();
		structureId = structureId == null ? getClientStructureId() : structureId;
		/** 获取组织架构级别该部门的所有子部门 **/
		String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
		/** subStructureId 从权限中获取所有部门","号隔开 **/
		String subStructureId = getClientUser().getPermissionsData();
		if (subStructureId == null || subStructureId == "") {
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**
		 * deptIds 通过页面传过来的部门获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);

		/** subordinates 所有下级人员","号隔开 **/
		String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());

		visitingDate = visitingDate == null ? DateUtil.formatDate(new Date(), DateUtil.dateFormat) : visitingDate;

		visitedFlag = visitedFlag == null ? 1 : visitedFlag;
		// if(task1id!=null){
		// int count =visitTaskDataService.getVisitTaskDataReportCount(getClientId(),userName, deptIds, positionId, subordinates, taskType, visitingDate, visitedFlag);
		// Page<VisitTaskDataReportVO> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		//pageParam.buildUrl(req);
		Page<VisitTaskDataReportVO> pageParam = Page.page(Page.DEFAULT_PAGE_SIZE * (pagenum+1), Page.DEFAULT_PAGE_SIZE, pagenum);
		
		userName = userName == null ? "" : userName;
		List<VisitTaskDataReportVO> list = visitTaskDataService.getVisitTaskDataReport(getClientId(), subordinates, subStructureId, deptIds, userName, positionId, visitingDate, taskType, visitedFlag, pageParam.getStartRows(), pageParam.getPageSize());
		int count = 0;
		List<Integer> object = null;
		if (!list.isEmpty() && ReportGlobal.Procedure.ITEMS < list.size()) {
			object = (List<Integer>) list.get(ReportGlobal.Procedure.ITEMS);
			if (null != object && !object.isEmpty()) {
				count = object.get(0);
			}
		}
		pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		pageParam.setItems((List<VisitTaskDataReportVO>) list.get(ReportGlobal.Procedure.VALUE));
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		// }
		model.addAttribute("clientId", clientId);
		model.addAttribute("taskType", taskType);
		model.addAttribute("visitedFlag", visitedFlag);
		model.addAttribute("structureId", structureId);
		model.addAttribute("positionId", positionId);
		model.addAttribute("userName", userName);
		model.addAttribute("visitingDate", visitingDate);
		model.addAttribute("startDate", DateUtil.formatDate(new Date(), DateUtil.dateFormat));
		model.addAttribute("endDate", DateUtil.formatDate(DateUtil.dsDay_Date(new Date(), 1), DateUtil.dateFormat));

		return "visit/visitTaskDataList";
	}

	/**
	 * 拜访明细页面
	 * 
	 * @param model
	 * @param clientPositionId
	 *            职位id
	 * @param clientUserId
	 *            用户id
	 * @param visitDate
	 *            拜访日期
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showVisitTaskDataDetail")
	public String showVisitTaskDataDetail(Model model, Integer clientPositionId, Integer clientUserId, String visitDate, Byte taskType) throws BusinessException {
		Integer clientId = getClientId();
		ClientUser clientUser = clientUserService.findByPrimaryKey(clientUserId);
		List<CallPlanning> cpList = new ArrayList<CallPlanning>();
		/**
		 * 获取某用户某一天需要拜访的对象 拜访任务类型为"店内工作"，那么直接通过门店关系查询门店 反之按计划查询
		 **/
		if (taskType.equals(VISITING_TASK_TYPE.DJ.getKey())) {
			List<Store> storeList = storeService.getRelationStore(clientUserId, getClientId(), visitDate, taskType);
			if (storeList != null && storeList.size() > 0) {
				for (Store st : storeList) {
					CallPlanning cp = new CallPlanning();
					cp.setPopId(st.getStoreId());
					cp.setPopName(st.getStoreName());
					cp.setInTime(st.getInTime());
					cp.setOutTime(st.getOutTime());
					cp.setPopType((byte) 1);
					cpList.add(cp);
				}
			}
		} else {
			cpList = callPlanningService.findListByParams4union(clientUserId, clientId, visitDate, taskType);
		}
		model.addAttribute("clientUser", clientUser);
		model.addAttribute("cpList", cpList);
		model.addAttribute("visitDate", visitDate);
		model.addAttribute("taskType", taskType);
		return "/visit/showVisitTaskDataDetail";
	}

	/**
	 * 点击对象获取（对象/人/天）的详细信息
	 * 
	 * @param objectId
	 * @param objectType
	 * @param clientUserId
	 * @param clientPositionId
	 * @param visitDate
	 * @return
	 */
	@RequestMapping(value = "/showTaskDataDetailByObjectId")
	public Object showTaskDataDetailByObjectId(Model model, String popId, Byte popType, Integer clientUserId, Integer clientPositionId, String visitDate, Byte taskType) {
		Integer clientId = getClientId();
		/** 获取拜访任务 **/
		if (popId != null && popId != "") {
			List<VisitingTask> vtList = visitTaskDataService.getTasksByUserAndObject(clientUserId, clientPositionId, clientId, popId, popType, visitDate, taskType);
			List<VisitingTaskStep> vtStepList = null;
			/** 如果拜访任务不为空，获取所有步骤 **/
			if (vtList != null && vtList.size() > 0) {
				String vtIds = "";
				for (Iterator<VisitingTask> it = vtList.iterator(); it.hasNext();) {
					if (!it.hasNext()) {// 最后一个元素
						vtIds += it.next().getVisitingTaskId();
					} else {
						vtIds += it.next().getVisitingTaskId() + ",";
					}
				}
				/** 获取所有步骤 **/
				vtStepList = visitingTaskStepService.getVisitingTaskStepListByVtIds(vtIds);
				/** 如果不为空，默认取第一个步骤初始化数据-已经由异步实现 **/
			}
			model.addAttribute("taskType", taskType);
			model.addAttribute("clientUserId", clientUserId);
			model.addAttribute("clientId", clientId);
			model.addAttribute("popId", popId);
			model.addAttribute("popType", popType);
			model.addAttribute("visitDate", visitDate);
			model.addAttribute("vtStepList", vtStepList);
		}
		return "/visit/visitTaskStepDetail";
	}

	/**
	 * 通过某一步骤加载对应的拜访数据
	 * 
	 * @param visitingTaskStepId
	 * @param stepType
	 * @param popId
	 * @param popType
	 * @param clientUserId
	 * @param clientPositionId
	 * @param visitDate
	 * @return
	 */
	@RequestMapping(value = "/showTaskDataDetailByStepId")
	public Object showTaskDataDetailByStepId(Model model, Integer visitingTaskStepId, Byte stepType, String popId, Byte popType, Integer clientUserId, Integer clientPositionId, String visitDate, Byte taskType, Byte selectType) {
		Integer clientId = getClientId();
		List<VisitingParameter> vParameterList = null;
		List<VisitingTaskStepObject> vObjectList = null;
		Map<String, HashMap<String, String>> detailMap = new HashMap<String, HashMap<String, String>>();

		/** 通过visitingTaskStepId获取对象参数 **/
		vParameterList = visitingParameterService.getVisitingParameterListByStepId(visitingTaskStepId, clientId);
		/** 获取对象 **/
		vObjectList = visitingTaskStepObjectService.getVisitingObjectListByStep(clientUserId, visitingTaskStepId, stepType, clientId, selectType, popId, popType, visitDate);
		/** 获取拜访对象数据 **/
		detailMap = visitingTaskDetailDataService.getTaskStepDataByParameter(clientUserId, popId, popType, visitDate, visitingTaskStepId, stepType, clientId, taskType);

		model.addAttribute("stepType", stepType.toString());
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("clientId", clientId);
		model.addAttribute("vParameterList", vParameterList);
		model.addAttribute("vObjectList", vObjectList);
		model.addAttribute("detailMap", detailMap);

		return "/visit/visitTaskStepDataDetail";

	}

	/**
	 * 进出店详细-页面
	 * 
	 * @param model
	 * @param clientUserId
	 * @param popId
	 * @param popType
	 * @param visitDate
	 * @param taskType
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showVisitTaskDataMain")
	public String showVisitTaskDataMain(Model model, Integer clientUserId, String popId, Byte popType, String visitDate, Byte taskType) {
		Integer clientId = getClientId();
		Store store = storeService.selectByPrimaryKey(popId);
		VisitingTaskData visitingTaskData = visitTaskDataService.selectVisitTaskDataByParam(clientId, clientUserId, popId, popType, visitDate, taskType, store);

		model.addAttribute("store", store);
		model.addAttribute("visitingTaskData", visitingTaskData);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);

		return "/visit/showVisitTaskDataMain";
	}

	/***
	 * 门店拜访查看导出
	 * 
	 * @param clientStructureId
	 * @param username
	 * @param clientPositionId
	 * @param startTime
	 * @param endTime
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/showExportDialog/{visitTypeId}")
	public void exportVisitTaskData(@PathVariable("visitTypeId") Integer visitTypeId, Integer structureId, String structureSel, String userName, Integer positionId, String startDate, String endDate, HttpServletRequest req, HttpServletResponse resp) {
		Integer clientId = getClientId();
		structureId = structureId != null ? structureId : getClientStructureId();
		/** 获取组织架构级别该部门的所有子部门 **/
		String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
		/** subStructureId 从权限中获取所有部门","号隔开 **/
		String subStructureId = getClientUser().getPermissionsData();
		if (subStructureId == null || subStructureId == "") {
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/** 用户所属角色可以查看的部门 */
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		/** 直接下级 */
		Map<String, Object> childsmap = new HashMap<String, Object>();
		childsmap.put("clientId", clientId);
		childsmap.put("parentId", getCurrentUserId());
		String chlids = clientUserRelationService.getDirectChilds(childsmap);

		VisitingResultProcedure sp = new VisitingResultProcedure(dataSourceBase);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_client_id", clientId);
		map.put("arg_filter_user_ids", chlids);
		map.put("arg_filter_structure_ids", subStructureId);
		map.put("arg_dept_ids", deptIds);
		map.put("arg_user_name", userName);
		map.put("arg_position_id", positionId);
		map.put("arg_start_time", DateUtil.getDayStart(startDate));
		map.put("arg_end_time", DateUtil.getDayEnd(endDate));
		map.put("arg_visit_type", visitTypeId);
		Map<String, Object> results = sp.execute(map);
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int i = 0; i < results.keySet().size(); i = i + 2) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> al = (List<Map<String, Object>>) results.get("#result-set-" + (i + 1));
			if (al != null && al.size() > 0) {
				for (int j = 0; j < al.size(); j++) {
					String[] head = al.get(j).toString().split(",");
					resp.setContentType("application/vnd.ms-excel");
					/** 在we中创建一个sheet */
					String NewStrhead = head[0].replace("`", "").replace("}", "").substring(4);
					HSSFSheet wTSSheet = wb.createSheet(NewStrhead);
					/** 在wTSSheet值添加表头(第0行) */
					HSSFRow row = wTSSheet.createRow((int) 0);
					/** 创建单元格，并设置表头，设置表头居中 */
					HSSFCellStyle style = wb.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_FILL);
					/** 表头列 */
					HSSFCell cell = row.createCell(0);
					for (int k = 1; k < head.length; k++) {
						String oldStr = head[k];
						String NewStr = "";
						if (k == head.length - 1) {
							NewStr = oldStr.replace("`", "").replace("}", "");
						} else {
							NewStr = oldStr.replace("`", "");
						}
						cell.setCellValue(NewStr);
						cell = row.createCell(k);
					}
					/** excel数据 */
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> resultal = (List<Map<String, Object>>) results.get("#result-set-" + (i + 2));
					for (int k = 0; k < resultal.size(); k++) {
						row = wTSSheet.createRow((int) k + 1);
						Map<String, Object> resultmap = resultal.get(k);
						for (int l = 1; l < head.length; l++) {
							String oldStr = head[l];
							String NewStr = oldStr.replace("`", "");
							String[] sdf = NewStr.split(",");
							for (int m = 0; m < sdf.length; m++) {
								if ((String) resultmap.get(sdf[m]) != null && (boolean) ((String) resultmap.get(sdf[m])).contains(".jpg")) {
									row.createCell(l - 1).setCellValue("=" + (String) (resultmap.get(sdf[m]) == null ? "" : (String) resultmap.get(sdf[m])));
								} else {
									row.createCell(l - 1).setCellValue((String) (resultmap.get(sdf[m]) == null ? "" : (String) resultmap.get(sdf[m])));
								}
							}
						}
					}
				}

			}
		}
		String excelName = structureSel + "-门店拜访数据";
		try {
			resp.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("gb2312"), "iso8859-1") + ".xls");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private class VisitingResultProcedure extends StoredProcedure {
		public VisitingResultProcedure(DataSource dataSource) {
			super(dataSource, "sp_export_visited_data");// 获取表定义存储过程
			declareParameter(new SqlParameter("arg_client_id", Types.INTEGER));
			declareParameter(new SqlParameter("arg_filter_user_ids", Types.VARCHAR));
			declareParameter(new SqlParameter("arg_filter_structure_ids", Types.VARCHAR));
			declareParameter(new SqlParameter("arg_dept_ids", Types.VARCHAR));
			declareParameter(new SqlParameter("arg_user_name", Types.VARCHAR));
			declareParameter(new SqlParameter("arg_position_id", Types.INTEGER));
			declareParameter(new SqlParameter("arg_start_time", Types.TIMESTAMP));
			declareParameter(new SqlParameter("arg_end_time", Types.TIMESTAMP));
			declareParameter(new SqlParameter("arg_visit_type", Types.INTEGER));
		}
	}

}
