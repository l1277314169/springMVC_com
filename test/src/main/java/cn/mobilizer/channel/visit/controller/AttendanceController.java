/**
 * 
 */
package cn.mobilizer.channel.visit.controller;



import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.model.ConvertAnchor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.exception.ImprotException;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.po.Attendance;
import cn.mobilizer.channel.visit.service.AttendanceService;

@Controller
@RequestMapping(value = "/attendance")
public class AttendanceController extends BaseActionSupport {

	private static final long serialVersionUID = 8651502976076192726L;
	
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientUserService clientUserService;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String clientUserName, String clientStructureName,String firstDayOfCurrentMonth,String lastDayOfCurrentMonth,
			Integer clientStructureId,HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		
		if(StringUtils.isEmpty(firstDayOfCurrentMonth) && StringUtils.isEmpty(lastDayOfCurrentMonth)){
			Calendar cal =Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            firstDayOfCurrentMonth = sdf.format(DateTimeUtils.getFirstDayOfCurrentMonth());
            lastDayOfCurrentMonth = sdf.format(DateTimeUtils.getLastDayOfCurrentMonth());
		}
		
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserName", clientUserName);
		parameters.put("clientStructureId", clientStructureId);
		parameters.put("startInTime", DateUtil.getDayStart(firstDayOfCurrentMonth));
		parameters.put("endInTime", DateUtil.getDayEnd(lastDayOfCurrentMonth));
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("roleName", Constants.PROMOTER);
		int count = 0;
		if(clientId == Constants.UNICHARM){
			count = attendanceService.unicharmCount(parameters);
		}else{
			count = attendanceService.queryCount(parameters);
		}
		int pagenum = page == null ? 1 : page;
		Page<Attendance> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Attendance> list = null;
		if(clientId == Constants.UNICHARM){
			list = attendanceService.unicharmQueryList(parameters);
		}else{
			list = attendanceService.getAttendanceList(parameters);
		}
		for (Attendance attendance : list) {
			if(attendance.getInTime() == null && attendance.getLeaveType() != null){
				int leaveType = attendance.getLeaveType();
				String leaveTypeText = optionsService.getLeaveTypeText("leave_type",leaveType,clientId);
				attendance.setLeaveTypeText(leaveTypeText);
			}else{
				if(attendance.getInTime() != null){
					Date date = attendance.getInTime();
					String 	dateStr = DateUtil.formatDate(date, DateUtil.dateFormat)+Constants.HMS;
					Date newDate = DateUtil.toDate(dateStr, DateUtil.dateTimeFormat);
					if(!DateUtil.isCompareTime(newDate, attendance.getInTime())){
						attendance.setAttrDate(DateUtil.dsDay_Date(attendance.getInTime(), Constants.DATEDAY));
					}
				}
				
			}
			
		}
		pageParam.setItems(list);
		
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("clientUserName", clientUserName);
		model.addAttribute("clientStructureName", clientStructureName);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("firstDayOfCurrentMonth", firstDayOfCurrentMonth);
		model.addAttribute("lastDayOfCurrentMonth", lastDayOfCurrentMonth);
		model.addAttribute("page", pageParam.getPage().toString());
		
		return "/visit/attendanceList";
	}
	/**考勤导出
	 * @author Nany
	 * 2015年1月20日下午5:17:38
	 * @param inTime
	 * @param clientUserName
	 * @param structureId
	 * @param structureName
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportAttendance")
	public void exportAttendance(String inStartTime,String inEndTime,String clientUserName,Integer structureId,String structureName,
			HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, Object> paras = new HashMap<String, Object>();
		int clientId = getClientId();
		structureId = structureId !=null ? structureId : getClientStructureId();
		String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		paras.put("clientStructureId", structureId);
		paras.put("subStructureId", deptIds);
		paras.put("subordinates", subordinates);
		paras.put("clientUserName", clientUserName);
		paras.put("clientId", clientId);
		paras.put("isDelete", Constants.IS_DELETE_FALSE);
		paras.put("startInTime", DateUtil.getDayStart(inStartTime));
		paras.put("endInTime", DateUtil.getDayEnd(inEndTime));
		paras.put("roleName", Constants.PROMOTER);
		List<Attendance> attendanceList = null;
		if(clientId == Constants.UNICHARM){
			attendanceList = attendanceService.unicharmQueryList(paras);
		}else{
			attendanceList = attendanceService.exportAttendance(paras);
		}
		if (attendanceList != null && attendanceList.size()>0) {
			for (Attendance attendance : attendanceList) {
				if(attendance.getInTime() == null && attendance.getLeaveType() != null){
					int leaveType = attendance.getLeaveType();
					String leaveTypeText = optionsService.getLeaveTypeText("leave_type",leaveType,clientId);
					attendance.setLeaveTypeText(leaveTypeText);
				}
			}
		}
		/**创建一个Excel文件*/
		HSSFWorkbook  wb = new HSSFWorkbook();
		int clientStructureId = getClientStructureId();
		ClientStructure clientStructure = clientStructureService.getClientStructureById(clientStructureId);
		String clientStructureName = clientStructure.getStructureName();
		String excelName = null;
		if(clientStructureName.equals(structureName)){
			excelName = clientStructure.getStructureName()+"-"+inStartTime+"-"+inEndTime+"期间考勤数据"+"_"+System.currentTimeMillis();
		}else{
			excelName = clientStructure.getStructureName()+"-"+structureName+"-"+inStartTime+"-"+inEndTime+"期间考勤数据"+"_"+System.currentTimeMillis();
		}
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
		resp.setContentType("application/vnd.ms-excel");
		
		/**在we中创建一个sheet*/
		HSSFSheet wTSSheet = wb.createSheet("考勤数据");
		/**在wTSSheet值添加表头(第0行)*/
		HSSFRow row = wTSSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中*/
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFCell cell = row.createCell(0);  
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("登录名");
		
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("部门");
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("上班时间");
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("下班时间");
		cell = row.createCell(5);
		cell.setCellStyle(style);
		cell.setCellValue("上班拍照");
		cell = row.createCell(6);
		cell.setCellStyle(style);
		cell.setCellValue("下班拍照");
		/**unicharm 导出逻辑*/
		if(clientId == Constants.UNICHARM){
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue("事假");
			cell = row.createCell(8);
			cell.setCellStyle(style);
			cell.setCellValue("病假");
			cell = row.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue("其他");
		}else{
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue("备注");
		}
		String path = req.getContextPath();
		String basePath = req.getScheme()+"://"+req.getServerName()+path+"/uploadfiles/";
		if (attendanceList != null && attendanceList.size()>0) {
			for (int i = 0; i < attendanceList.size(); i++) {
				row = wTSSheet.createRow((int) i + 1); 
				Attendance attendance = attendanceList.get(i);
				
				row.createCell(0).setCellValue(attendance.getClientUserName() == null?"":attendance.getClientUserName());
				row.createCell(1).setCellValue(attendance.getLoginName() == null?"":attendance.getLoginName());
				row.createCell(2).setCellValue(attendance.getStructureName() == null?"":attendance.getStructureName());
				if(attendance.getInTime() != null){
					row.createCell(3).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(attendance.getInTime()));
					row.createCell(5).setCellValue(basePath+attendance.getClientId()+"/"+attendance.getClientUserId()+"/"+(attendance.getInPic() == null?"":attendance.getInPic()));
				}else{
					row.createCell(3).setCellValue("");
					row.createCell(5).setCellValue("");
				}
				if(attendance.getOutTime() != null){
					row.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(attendance.getOutTime()));
					row.createCell(6).setCellValue(basePath+attendance.getClientId()+"/"+attendance.getClientUserId()+"/"+(attendance.getOutPic() == null?"":attendance.getOutPic()));
				}else{
					row.createCell(4).setCellValue("");
					row.createCell(6).setCellValue("");
				}
				/**unicharm 导出逻辑*/
				if(attendance.getClientId().equals(Constants.UNICHARM)){
					if(!StringUtils.isEmpty(attendance.getRemark()) && attendance.getRemark().equals(Constants.CASUAL_LEAVE)){
						row.createCell(7).setCellValue(Constants.CASUAL_LEAVE);
					}else{
						row.createCell(7).setCellValue("");
					}
					if(!StringUtils.isEmpty(attendance.getRemark()) && attendance.getRemark().equals(Constants.SICK_LEAVE)){
						row.createCell(8).setCellValue(Constants.SICK_LEAVE);
					}else{
						row.createCell(8).setCellValue("");
					}
					if(!StringUtils.isEmpty(attendance.getRemark()) && !attendance.getRemark().equals(Constants.CASUAL_LEAVE) && !attendance.getRemark().equals(Constants.SICK_LEAVE)){
						row.createCell(9).setCellValue(attendance.getRemark());
					}else{
						row.createCell(9).setCellValue("");
					}
				}else{
					row.createCell(7).setCellValue(attendance.getRemark() == null?"":attendance.getRemark());
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
	/**
	 * 导入模板页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(Model model){
		String fileName = null;
		fileName = "attendance_import_template.xlsx";
		model.addAttribute("clientId", getClientId());
		model.addAttribute("fileName", fileName);
		return "visit/importAttendance";
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
		ResultMessage attendanceMessage = attendanceService.addImportAttendance(file, request, clientId, resp);
		return attendanceMessage;
	}
}
