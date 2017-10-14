package cn.mobilizer.channel.ctTask.controller;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.PromotionMaterialService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.service.StoreUserMappingService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.po.CtTaskData;
import cn.mobilizer.channel.ctTask.po.CtTaskDetailData;
import cn.mobilizer.channel.ctTask.po.CtTaskObject;
import cn.mobilizer.channel.ctTask.po.CtTaskParameter;
import cn.mobilizer.channel.ctTask.service.CtTaskDataService;
import cn.mobilizer.channel.ctTask.service.CtTaskObjectService;
import cn.mobilizer.channel.ctTask.service.CtTaskParameterService;
import cn.mobilizer.channel.ctTask.service.CtTaskService;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;
import cn.mobilizer.channel.ctTask.vo.ExportCtTaskDataSerchVo;
import cn.mobilizer.channel.ctTask.vo.StoreTask;
import cn.mobilizer.channel.ctTask.vo.TaskCycle;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

/**
 * @author LiuYong
 * @date 2015年5月29日 上午1:13:51
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ctTask")
public class CtTaskController extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private CtTaskService ctTaskService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private CtTaskObjectService ctTaskObjectService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private CtTaskParameterService ctTaskParameterService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private CtTaskDataService ctTaskDataService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientUserService clientUserService;
    @Autowired
    private ClientPositionService clientPositionService;
    @Autowired
    private StoreUserMappingService storeUserMappingService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PromotionMaterialService promotionMaterialService;
    @Autowired
    private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;

	/**
	 * 查询周期任务列表
	 * @param model
	 * @param page
	 * @param ctTaskGroup
	 * @param taskName
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page,Integer ctTaskGroup,String taskName,HttpServletRequest req) throws BusinessException {
		int clientId = getClientId();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("ctTaskGroup", ctTaskGroup);
		params.put("taskName", taskName);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		Integer count = ctTaskService.selectByParamCount(params);
		int pagenum = page == null ? 1 : page;		
		Page<CtTask> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		params.put("_start", pageParam.getStartRows());
		params.put("_size", pageParam.getPageSize ());
		List<CtTask> ctTasks = ctTaskService.selectByParams(params);
		pageParam.setItems(ctTasks);		
		Map<String,String> taskTypeMap = new HashMap<String, String>();
		for(ChannelEnum.VISIT_TASK_STEP_TYPE item:ChannelEnum.VISIT_TASK_STEP_TYPE.values()){
			taskTypeMap.put(item.getKey().toString(), item.getCnName());
		}
		List<Options> ctTaskGroupOptions = optionsService.findOptionsByOptionName("ct_task_group", clientId);
		model.addAttribute("ctTaskGroupOptions", ctTaskGroupOptions);
		model.addAttribute("taskTypeMap",taskTypeMap);
		model.addAttribute("taskTypes" ,ChannelEnum.VISIT_TASK_STEP_TYPE.values());
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("ctTaskGroup", ctTaskGroup);
		model.addAttribute("taskName", taskName);
		model.addAttribute("clientId", clientId);
		return "/ctTask/ctTaskList";
	}	 
	
	/**
	 * 显示门店历史数据列表
	 * @param model
	 * @param page
	 * @param storeTask
	 * @param req
	 * @return
	 */
	@RequestMapping("showCtTaskList")
    public String showCtTask(Model model, Integer page,String ctTaskId, StoreTask storeTask,HttpServletRequest req){
		CtTask ctTask = ctTaskService.getCtTask(storeTask.getCtTaskId());
		storeTask.setCycle(ctTask.getCycleType());
		Integer clientId = getClientId ();
		Integer clientUserId = getCurrentUserId();
		storeTask.setClientId(clientId);
		String clientUserIds = channelCommService.getSubordinates(clientUserId.toString());
		storeTask.setUserIds(clientUserIds);
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		storeTask.setSubAllStructureId(deptIds);
		//初始化时，默认选中当前周期
		if(StringUtils.isEmpty(storeTask.getTaskCycle())){
			if(ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()){
		   	 	if(StringUtils.isEmpty(storeTask.getStartDate()) && StringUtils.isEmpty(storeTask.getEndDate())){
		   	 		storeTask.setStartDate(DateUtil.formatDate(DateUtil.getBeginDateByCycle(ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()),DateTimeUtils.yyyyMMddHHmmss));
		   	 		storeTask.setEndDate(DateUtil.formatDate(DateUtil.getEndDateByCycle(ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()),DateTimeUtils.yyyyMMddHHmmss));
		   	 	}
			}else{
				storeTask.setStartDate(DateUtil.formatDate(DateUtil.getBeginDateByCycle(ctTask.getCycleType().byteValue()),DateTimeUtils.yyyyMMddHHmmss));
		   	 	storeTask.setEndDate(DateUtil.formatDate(DateUtil.getEndDateByCycle(ctTask.getCycleType().byteValue()),DateTimeUtils.yyyyMMddHHmmss));
			}
		}
		Integer count = ctTaskService.selectTaskDataStoreCount(storeTask,clientId);
		int pagenum = page == null ? 1 : page;
		Page<StoreTask> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		List<StoreTask> storeTasks = ctTaskService.selectTaskDataStore(storeTask,pageParam.getStartRows(),pageParam.getPageSize(),clientId);
		pageParam.setItems(storeTasks);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("storeTask", storeTask);	
		model.addAttribute("ctTask", ctTask);
		model.addAttribute("clientId", clientId);
		model.addAttribute("ctTaskId", ctTaskId);
		return "/ctTask/showCtTaskList";
	}
	
	/**
	 * 编辑周期任务
	 * @param model
	 * @param page
	 * @param ctTaskId
	 * @param req
	 * @return
	 */
	@RequestMapping("showEditCtTask")
    public String showEditCtTask(Model model,CtTaskDataSerchVo ctTaskDataSerchVo,HttpServletRequest req,boolean isInCycel){
		Integer clientId = getClientId();
		ctTaskDataSerchVo.setClientId(clientId);
		Integer ctTaskId = ctTaskDataSerchVo.getCtTaskId();
		CtTask ctTask = ctTaskService.getCtTask(ctTaskId);
		//根据任务类型筛选对象
		List<CtTaskObject> ctTaskObjects = ctTaskObjectService.selectByParams(clientId,ctTaskId); 		
		List<CtTaskParameter> ctTaskParameters = ctTaskParameterService.selectByParams(clientId,ctTaskId);
		model.addAttribute("ctTaskParameters", ctTaskParameters);	
		model.addAttribute("ctTaskObjects", ctTaskObjects);	
		model.addAttribute("ctTask", ctTask);	
		model.addAttribute("ctTaskId", ctTaskId);
		model.addAttribute("startTime",DateUtil.getFormatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		ctTaskDataSerchVo.setClientUserId(getCurrentUserId());
		//根据门店和任务获取数据
		CtTaskData ctTaskData = ctTaskDataService.selectDataByPopIdAndTaskId(ctTaskDataSerchVo);
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		if(ctTaskData!=null){			
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				parameterMap.put(ctTaskDetailData.getCtTaskParameterId()+"_"+(ctTaskDetailData.getTarget1Id()==null?"":ctTaskDetailData.getTarget1Id()), ctTaskDetailData.getValue());	
			}
		}else{
			parameterMap = null;
		}
		if(ctTaskData!=null){
			String initDate = "";
			Calendar car = Calendar.getInstance();
			car.setTime(ctTaskData.getStartTime());
			int month =  car.get(Calendar.MONTH)+1;
			if(month<10){
				initDate = car.get(Calendar.YEAR)+"-0"+month;
			}else{
				initDate = car.get(Calendar.YEAR)+"-"+month;
			}
			model.addAttribute("initDate",initDate);
		}
		model.addAttribute("ctTaskDataSerchVo",ctTaskDataSerchVo);
		model.addAttribute("parameterMap", parameterMap);	
		model.addAttribute("ctTaskData", ctTaskData);	
		model.addAttribute("isInCycel", isInCycel);
		return "/ctTask/showEditCtTask";
	}
	
	/**
	 * 修改周期任务历史数据
	 * @param model
	 * @param page
	 * @param ctTaskId
	 * @param req
	 * @return
	 */
	@RequestMapping("showEditCtTaskData")
    public String showEditCtTaskData(Model model,CtTaskDataSerchVo ctTaskDataSerchVo,HttpServletRequest req,boolean isInCycel){
		Integer clientId = getClientId();
		ctTaskDataSerchVo.setClientId(clientId);
		Integer ctTaskId = ctTaskDataSerchVo.getCtTaskId();
		CtTask ctTask = ctTaskService.getCtTask(ctTaskId);
		//根据任务类型筛选对象
		List<CtTaskObject> ctTaskObjects = ctTaskObjectService.selectByParams(clientId,ctTaskId); 		
		List<CtTaskParameter> ctTaskParameters = ctTaskParameterService.selectByParams(clientId,ctTaskId);
		model.addAttribute("ctTaskParameters", ctTaskParameters);	
		model.addAttribute("ctTaskObjects", ctTaskObjects);	
		model.addAttribute("ctTask", ctTask);	
		model.addAttribute("ctTaskId", ctTaskId);
		model.addAttribute("startTime",DateUtil.getFormatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		ctTaskDataSerchVo.setClientUserId(getCurrentUserId());
		//根据门店和任务获取数据
		CtTaskData ctTaskData = ctTaskDataService.getCtTaskDataById(ctTaskDataSerchVo.getCtTaskDataId());
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		if(ctTaskData!=null){			
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				parameterMap.put(ctTaskDetailData.getCtTaskParameterId()+"_"+(ctTaskDetailData.getTarget1Id()==null?"":ctTaskDetailData.getTarget1Id()), ctTaskDetailData.getValue());	
			}
		}else{
			parameterMap = null;
		}
		if(ctTaskData!=null){
			String initDate = "";
			Calendar car = Calendar.getInstance();
			car.setTime(ctTaskData.getStartTime());
			int month =  car.get(Calendar.MONTH)+1;
			if(month<10){
				initDate = car.get(Calendar.YEAR)+"-0"+month;
			}else{
				initDate = car.get(Calendar.YEAR)+"-"+month;
			}
			model.addAttribute("initDate",initDate);
		}
		model.addAttribute("ctTaskDataSerchVo",ctTaskDataSerchVo);
		model.addAttribute("parameterMap", parameterMap);	
		model.addAttribute("ctTaskData", ctTaskData);	
		model.addAttribute("isInCycel", isInCycel);
		return "/ctTask/showEditCtTaskData";
	}
	
	/**
	 * 查询周期任务历史数据详情
	 * @param model
	 * @param page
	 * @param ctTaskDataSerchVo
	 * @param req
	 * @return
	 */
	@RequestMapping("showCtTaskDataDetail")
    public String showCtTaskDataDetail(Model model, Integer page,CtTaskDataSerchVo ctTaskDataSerchVo,HttpServletRequest req){
		Integer clientId = getClientId();
		Integer clientUserId = getCurrentUserId();
		ctTaskDataSerchVo.setClientUserId(clientUserId);
		ctTaskDataSerchVo.setClientId(clientId);
		CtTask ctTask = ctTaskService.getCtTask(ctTaskDataSerchVo.getCtTaskId());
		ctTaskDataSerchVo.setTaskType(ctTask.getTaskType());				
		List<CtTaskObject> ctTaskObjects = ctTaskObjectService.selectTaskObjectBySku(ctTaskDataSerchVo);	
		List<CtTaskParameter> ctTaskParameters = ctTaskParameterService.selectByParams(clientId,ctTaskDataSerchVo.getCtTaskId());
		model.addAttribute("ctTaskParameters", ctTaskParameters);	
		model.addAttribute("ctTaskObjects", ctTaskObjects);	
		model.addAttribute("ctTask", ctTask);	
		model.addAttribute("popId", ctTaskDataSerchVo.getPopId());	
		//根据门店和任务获取数据
		CtTaskData ctTaskData = ctTaskDataService.getCtTaskDataById(ctTaskDataSerchVo.getCtTaskDataId());
		if(ctTaskData!=null){
			Map<String,Object> parameterMap = new HashMap<String, Object>();
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				parameterMap.put(ctTaskDetailData.getCtTaskParameterId()+"_"+(ctTaskDetailData.getTarget1Id()==null?"":ctTaskDetailData.getTarget1Id()), ctTaskDetailData.getValue());	
			}
			model.addAttribute("parameterMap", parameterMap);
		}	
		model.addAttribute("objectFilters",ctTask.getObjectFilter()==null?null:ctTask.getObjectFilter().split(","));
		model.addAttribute("ctTaskDataSerchVo", ctTaskDataSerchVo);
		model.addAttribute("ctTaskData", ctTaskData);
		return "/ctTask/showCtTaskDataDetail";
	}
	
	/**
	 * 根据taskId获取门店列表
	 * @param model
	 * @param page
	 * @param storeTask
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "ctTaskStoreList")
	public String ctTaskStoreList(Model model,Integer page,StoreTask storeTask,HttpServletRequest req){
		CtTask ctTask = ctTaskService.getCtTask(storeTask.getCtTaskId());
		Integer clientId = getClientId(); 
		Integer clientUserId = getCurrentUserId();
		storeTask.setClientId(clientId);
		ClientUser clientUser = clientUserService.selectByPrimaryKey(clientUserId);
		String clientUserIds = channelCommService.getSubordinates(clientUserId.toString());
		storeTask.setUserIds(clientUserIds);		
		int pagenum = page == null ? 1 : page;				
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		storeTask.setSubAllStructureId(deptIds);
		Integer count = storeService.selectTaskDataCountByPositionId(storeTask);
		List<Store> stores = null;
		if(count==0){      							//如果按职位没取到数据就按照门店关系取	
			count = storeService.getStoreTaskCount(storeTask);
			Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			stores = storeService.getStoreTaskList(storeTask,pageParam.getStartRows(),pageParam.getPageSize());
			pageParam.setItems(stores);
			model.addAttribute("pageParam", pageParam);
		}else{
			Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);			
 			stores = storeService.selectTaskDataByPositionId(storeTask,pageParam.getStartRows(),pageParam.getPageSize());	
 			pageParam.setItems(stores);
			model.addAttribute("pageParam", pageParam);
		}
		model.addAttribute("ctTask", ctTask);		
		return "/ctTask/ctTaskStoreList";
	}
	
	/**
	 * 根据门店和任务获取数据
	 * @param model
	 * @param ctTaskDataSerchVo
	 * @return
	 */
	@RequestMapping(value = "getCtTaskData")
	@ResponseBody
	public Map<String,Object> getCtTaskData(Model model,CtTaskDataSerchVo ctTaskDataSerchVo){
		ctTaskDataSerchVo.setClientUserId(getCurrentUserId());
		//根据门店和任务获取数据
		CtTaskData ctTaskData = ctTaskDataService.selectDataByPopIdAndTaskId(ctTaskDataSerchVo);
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		if(ctTaskData!=null){			
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				parameterMap.put(ctTaskDetailData.getCtTaskParameterId()+"_"+ctTaskDetailData.getTarget1Id(), ctTaskDetailData.getValue());	
			}
		}else{
			parameterMap = null;
		}		
		return parameterMap;
	}
	
	/** 
	 * 异步任务周期时间段
	 * @param ctTaskId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getTaskDate", produces="application/json")
	@ResponseBody	
	public List<TaskCycle> getTaskDate(Integer ctTaskId) throws BusinessException  {
		return ctTaskService.getTaskCycelList(ctTaskId);
	}
	
	
	/**
	 * 高露洁周期任务需求
	 * 根据用户选择的访问日期月份，控制访问日期范围
	 * @param visitDate
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getMaxVisitDate")
	@ResponseBody
	public Integer getMaxVisitDate(String visitDate) throws Exception{
		Date date = DateUtil.getDateByStr(visitDate, DateTimeUtils.yyyyMMdd);
		Calendar calendar = Calendar.getInstance();				 						
		calendar.setTime(date);										//用户选中的月份日期									
		Integer maxDate = calendar.getActualMaximum(Calendar.DATE);    
		Integer visitYear = calendar.get(Calendar.YEAR);
		Integer visitMonth = calendar.get(Calendar.MONTH);
		Calendar currentCalendar = Calendar.getInstance();			//当前系统时间
		Integer currentYear = currentCalendar.get(Calendar.YEAR);
		Integer currentMonth = currentCalendar.get(Calendar.MONTH);
		Integer currentDate = currentCalendar.get(Calendar.DATE);
		if(visitYear<currentYear){
			return maxDate;
		}else{
			if(visitMonth<currentMonth){
				return maxDate;
			}else{
				return currentDate;
			}
		} 
	}
	
	/**
	 * 周期任务数据导出
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportCtTaskData")
	public void exportCtTaskData(ExportCtTaskDataSerchVo exportCtTaskDataSerchVo,HttpServletRequest req, HttpServletResponse resp) throws Exception{
		String clientUserIds = channelCommService.getSubordinates(getCurrentUserId().toString());
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		exportCtTaskDataSerchVo.setClientId(getClientId());
		exportCtTaskDataSerchVo.setUserIds(clientUserIds);
		exportCtTaskDataSerchVo.setStructureIds(deptIds);
		exportCtTaskDataSerchVo.setClientUserId(getCurrentUserId());
		List<?> lists = (List<?>)ctTaskService.exportCtTaskData(exportCtTaskDataSerchVo);
		List<String> strs = (List<String>) lists.get(ReportGlobal.Procedure.VALUE);
		String str = strs.get(0).replace("`","");		
		String taskName = str.split("@")[0].replace("/", "--"); 		//第一个是任务名称
		String[] titles = str.substring(str.indexOf("@")+1, str.length()).split(",");
		List<Map> ctTaskDataList = (List<Map>) lists.get(ReportGlobal.Procedure.ITEMS);	
		/**创建一个Excel文件*/
		XSSFWorkbook  wb = new XSSFWorkbook();
		/**导出Excel文档名字*/
		String excelName = taskName+"_"+System.currentTimeMillis();
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xlsx");
		resp.setContentType("application/vnd.ms-excel");
		
		/**在we中创建一个sheet*/
		XSSFSheet wTSSheet = wb.createSheet(taskName);
		/**在wTSSheet值添加表头(第0行)*/
		XSSFRow row = wTSSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中,背景颜色*/
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		for(int i=0;i<titles.length;i++){
			String cellTitle = titles[i];
			XSSFCell cell = row.createCell(i);  
			cell.setCellStyle(style);
			cell.setCellValue(cellTitle);
		}
		if (ctTaskDataList != null && ctTaskDataList.size() >0) {
			for (int i = 0; i < ctTaskDataList.size(); i++) {
				row = wTSSheet.createRow((int) i + 1); 
				for(int j=0;j<titles.length;j++){
					String cellTitle = titles[j];
					row.createCell(j).setCellValue((ctTaskDataList.get(i).get(cellTitle))==null?"":(ctTaskDataList.get(i).get(cellTitle).toString())); 
				}
			}
			
		}
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
