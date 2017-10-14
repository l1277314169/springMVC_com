package cn.mobilizer.channel.visit.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.visit.po.WorkTimeSettings;
import cn.mobilizer.channel.visit.service.WorkTimeSettingsService;
import cn.mobilizer.channel.visit.vo.WorkTimeSettingsSearchVO;

/**排班计划
 * @author Nany
 * 2015年1月15日下午7:20:42
 */
@Controller
@RequestMapping(value = "/workTimeSettings")
public class WorkTimeSettingsController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8454175764849603090L;
	
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private WorkTimeSettingsService workTimeSettingsService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ClientPositionService clientPositionService;
	
	/**排班计划--查询列表
	 * @author Nany
	 * 2015年1月16日上午10:26:33
	 * @param model
	 * @param page
	 * @param workTimeSettingsSearchVO
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page,WorkTimeSettingsSearchVO workTimeSettingsSearchVO,HttpServletRequest req) {
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		Integer clientStructureId = workTimeSettingsSearchVO.getClientStructureId();
		String workDate1 = workTimeSettingsSearchVO.getWorkDate1();
		String workDate2 = workTimeSettingsSearchVO.getWorkDate2();
		/**如果没有部门Id，默认取登录用户的部门Id(默认显示)*/
		if(clientStructureId == null){
			clientStructureId = getClientStructureId();
			workTimeSettingsSearchVO.setClientStructureId(clientStructureId);
		}
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**初始化时，取当周的排班计划*/
		if(StringUtils.isEmpty(workDate1) && StringUtils.isEmpty(workDate2)){
			Calendar cal =Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
			workDate1 = DateTimeUtils.formatTime(cal.getTime(), DateTimeUtils.yyyyMMdd);
			workDate2 = DateTimeUtils.formatTime(DateTimeUtils.addDays(DateTimeUtils.getFirstDayOfCurrentWeek() , 7), DateTimeUtils.yyyyMMdd);
		}
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		String isActivations = null;
		Byte  isActivation = workTimeSettingsSearchVO.getIsActivation();
		Byte  status = workTimeSettingsSearchVO.getStatus()==null?ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey():workTimeSettingsSearchVO.getStatus();
		if(status != null && status.equals(ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey()) && isActivation == null){
			isActivations = ChannelEnum.CLIENT_USER_ISACTIVATION.QY.getKey()+","+ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey();
		}
		if(status != null && status.equals(ChannelEnum.CLIENT_USER_STATUS.LZ.getKey()) && isActivation == null){
			isActivations = ChannelEnum.CLIENT_USER_ISACTIVATION.JY.getKey()+"";
		}
		if(isActivation != null){
			isActivations = isActivation+"";
		}
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("clientUserName", workTimeSettingsSearchVO.getClientUserName());
		parameters.put("clientStructureId", clientStructureId);
		parameters.put("workDate1", workDate1);
		parameters.put("workDate2",workDate2);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("status",status);
		parameters.put("clientPositionIds",clientPositionIds);
		parameters.put("clientPositionId", workTimeSettingsSearchVO.getClientPositionId());
		parameters.put("isActivations", isActivations);
		int count = workTimeSettingsService.queryWorKTimeSettingsCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<WorkTimeSettings> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		
		List<WorkTimeSettings> workTimeSettingsList = workTimeSettingsService.queryWorkTimeSettingsList(parameters);
		for (WorkTimeSettings workTimeSettings : workTimeSettingsList) {
			Byte workTimeType = workTimeSettings.getWorkTimeType();
			/**获取排班类型*/
			String workType = optionsService.getOptionText("work_time_type",workTimeType,clientId);
			workTimeSettings.setWorkType(workType);
		}
		pageParam.setItems(workTimeSettingsList);
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			model.addAttribute("clientPositionlist", clientPositionlist);
		}
		workTimeSettingsSearchVO.setStatus(status);
		workTimeSettingsSearchVO.setWorkDate1(workDate1);
		workTimeSettingsSearchVO.setWorkDate2(workDate2);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("clientPositionId", workTimeSettingsSearchVO.getClientPositionId());
		model.addAttribute("workTimeSettingsSearchVO", workTimeSettingsSearchVO);
		return "/visit/workTimeSettingsList";
	}
	/**编辑排班计划
	 * @author Nany
	 * 2015年1月16日下午5:17:24
	 * @param settingId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showEditDialog/{settingId}")
	public String showEditDialog(@PathVariable("settingId")String settingId ,Model model) {
		WorkTimeSettings workTimeSettings = workTimeSettingsService.getWorkTimeSettings(settingId);
		model.addAttribute("workTimeSettings", workTimeSettings);
		return "/visit/editWorkTimeSettings";
	}
	/**
	 * 加载排班计划类型
	 * @return
	 */
	@RequestMapping(value = "/loadWorkTimeType")
	@ResponseBody
	public Object loadWorkTimeTypeId(){
		int clientId = getClientId();
		List<Options> workTimeTypelist =  optionsService.findOptionsByOptionName("work_time_type",clientId);
		return  workTimeTypelist;
	}
	/**编辑排班计划--保存
	 * @author Nany
	 * 2015年1月19日下午12:22:52
	 * @param workTimeSettings
	 * @return
	 */
	@RequestMapping(value = "/updateWorkTimeSettings",produces="application/json")
	@ResponseBody
	public Object updateWorkTimeSettings(WorkTimeSettings workTimeSettings) {
		workTimeSettingsService.updateWorkTimesSetting(workTimeSettings);
		return ResultMessage.UPDATE_WORKTIMESET_SUCCESS;
	}
	
	/**导出排班计划
	 * @author Nany
	 * 2015年1月19日下午12:23:06
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportWTS")
	private void  showImportWorkTimeSettings(String workDate1,String workDate2,
			String structureName,Integer clientStructureId,Byte isActivation,Byte status,String clientUserName,
			HttpServletRequest req, HttpServletResponse resp) throws Exception, IOException{
		Map<String, Object> paras = new HashMap<String, Object>();
		int clientId = getClientId();
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		
		/**初始化时，取当周的排班计划*/
		if(StringUtils.isEmpty(workDate1) && StringUtils.isEmpty(workDate2)){
			Calendar cal =Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
			workDate1 = DateTimeUtils.formatTime(cal.getTime(), DateTimeUtils.yyyyMMdd);
			workDate2 = DateTimeUtils.formatTime(DateTimeUtils.addDays(DateTimeUtils.getFirstDayOfCurrentWeek() , 7), DateTimeUtils.yyyyMMdd);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		String isActivations = null;
		status = status == null?ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey():status;
		if(status != null && status.equals(ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey()) && isActivation == null){
			isActivations = ChannelEnum.CLIENT_USER_ISACTIVATION.QY.getKey()+","+ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey();
		}
		if(status != null && status.equals(ChannelEnum.CLIENT_USER_STATUS.LZ.getKey()) && isActivation == null){
			isActivations = ChannelEnum.CLIENT_USER_ISACTIVATION.JY.getKey()+"";
		}
		if(isActivation != null){
			isActivations = isActivation+"";
		}
		paras.put("subStructureId", deptIds);
		paras.put("subordinates", subordinates);
		paras.put("clientId", clientId);
		paras.put("isDelete", Constants.IS_DELETE_FALSE);
		paras.put("workDate1", workDate1);
		paras.put("workDate2", workDate2);
		paras.put("status",status);
		paras.put("clientPositionIds",clientPositionIds);
		paras.put("isActivations", isActivations);
		paras.put("clientUserName", clientUserName);
		
		List<WorkTimeSettings> workTimeSettingsList = workTimeSettingsService.queryWorkTimeSettingsList(paras);
		if(workTimeSettingsList!=null && workTimeSettingsList.size()>0){
			for (WorkTimeSettings workTimeSettings : workTimeSettingsList) {
				Byte workTimeType = workTimeSettings.getWorkTimeType();
				/**获取排班类型*/
				String workType = optionsService.getOptionText("work_time_type",workTimeType,clientId);
				workTimeSettings.setWorkType(workType);
			}
		}
		/**创建一个Excel文件*/
		HSSFWorkbook  wb = new HSSFWorkbook();
		ClientStructure clientStructure = clientStructureService.getClientStructureById(clientStructureId);
		String clientStructureName = clientStructure.getStructureName();
		String excelName = null;
		if(clientStructureName.equals(structureName)){
			 excelName = clientStructure.getStructureName()+workDate1+"--"+workDate2+"排班计划"+"_"+System.currentTimeMillis();
		}else{
			excelName = clientStructure.getStructureName()+workDate1+"--"+workDate2+"排班计划"+"_"+System.currentTimeMillis();
		}
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
		resp.setContentType("application/vnd.ms-excel");
		
		
		/**在we中创建一个sheet*/
		HSSFSheet wTSSheet = wb.createSheet("排班计划表");
		wTSSheet.setDefaultColumnWidth((short) 20);
		/**在wTSSheet值添加表头(第0行)*/
		HSSFRow row = wTSSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中*/
		HSSFCellStyle style = this.createHeaderStyle(wb);
		HSSFCell cell = row.createCell(0);  
		cell.setCellValue("人员姓名");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("在职状态");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("账号状态");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("职位");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("人员部门");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("工作日期");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("班次");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("开始时间");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("结束时间");
		cell.setCellStyle(style);
		cell = row.createCell(10);
		cell.setCellValue("排班门店");
		cell.setCellStyle(style);
		cell = row.createCell(11);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		if (workTimeSettingsList!=null && workTimeSettingsList.size()>0) {
			for (int i = 0; i < workTimeSettingsList.size(); i++) {
				row = wTSSheet.createRow((int) i + 1); 
				WorkTimeSettings wst = workTimeSettingsList.get(i);
				row.createCell(0).setCellValue(wst.getClientUserName() == null?"/":wst.getClientUserName());
				row.createCell(1).setCellValue(wst.getLoginName() == null?"/":wst.getLoginName());
				row.createCell(2).setCellValue(wst.getClientUserStatus() == null?"/":wst.getClientUserStatus().equals(Constants.ZZINTEGER)?Constants.ZZ:Constants.LZ);
				row.createCell(3).setCellValue(wst.getIsActivation() == null?"/":wst.getIsActivation().equals(Constants.ZZINTEGER)?Constants.QY:wst.getIsActivation().equals(Constants.JYINTEGER)?Constants.JY:Constants.WSY);
				row.createCell(4).setCellValue(wst.getClientPositionName() == null?"/":wst.getClientPositionName());
				row.createCell(5).setCellValue(wst.getStructureName() == null?"/":wst.getStructureName());
				if(wst.getWorkDate() != null){
					row.createCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(wst.getWorkDate()));
				}else{
					row.createCell(6).setCellValue("/");
				}
				row.createCell(7).setCellValue(wst.getWorkType() == null?"/":wst.getWorkType());
				
				if(wst.getStartTime() != null){
					row.createCell(8).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(wst.getStartTime()));
				}else{
					row.createCell(8).setCellValue("/");
				}
				if (wst.getEndTime() != null) {
					row.createCell(9).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(wst.getEndTime()));
				} else {
					row.createCell(9).setCellValue("/");
				}
				
				row.createCell(10).setCellValue(wst.getStoreName() == null?"/":wst.getStoreName());
				row.createCell(11).setCellValue(wst.getRemark() == null?"/":wst.getRemark());
			}
			
		}
		try {
			OutputStream fout = resp.getOutputStream();
			wb.write(fout);
			fout.close();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导入模板页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(Model model){
		String fileName = null;
		fileName = "workTimeSettings_import_template.xlsx";
		model.addAttribute("clientId", getClientId());
		model.addAttribute("fileName", fileName);
		return "visit/importWorkTimeSettings";
	}
	
	/**
	 * 导入
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultMessage importAttendance(MultipartFile file, HttpServletRequest request,HttpServletResponse resp){
		int clientId = getClientId();
		ResultMessage imprtWorkTimeSettingsResultMessage = workTimeSettingsService.addImprtWorkTimeSettings(file, request, clientId, resp);
		return imprtWorkTimeSettingsResultMessage;
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
