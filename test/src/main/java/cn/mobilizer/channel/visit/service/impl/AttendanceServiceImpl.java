package cn.mobilizer.channel.visit.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mobilizer.channel.comm.utils.StringUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.ClientPositionDao;
import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.impl.StoreServiceImpl;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.NormalSettingDao;
import cn.mobilizer.channel.systemManager.po.NormalSettings;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PropertiesHelper;
import cn.mobilizer.channel.visit.dao.AttendanceDao;
import cn.mobilizer.channel.visit.po.Attendance;
import cn.mobilizer.channel.visit.service.AttendanceService;
@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	private static final Log LOG = LogFactory.getLog(StoreServiceImpl.class);
	@Autowired
	public AttendanceDao attendanceDao;
	@Autowired
	public NormalSettingDao normalSettingDao;
	@Autowired
	private ClientUserDao clientUserDao;
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private ClientPositionDao clientPositionDao;

	@Override
	public int insert(Attendance attendance) {
		return attendanceDao.insert(attendance);
	}
	
	public Attendance doAttendance(Integer clientId, Integer clientUserId, Integer type, String longitude, String latitude, String pics){
		
		//标准时间
		String startTime = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat);
		String endTime = DateUtil.getFormatDate(DateUtils.addDays(new Date(), +1), DateUtil.dateFormat);
		
		//取考勤时间节点
		NormalSettings ns = normalSettingDao.getByKeyCode(clientId, "K1");
		if(ns != null){
			try {
				String timeLine = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat) +  " " + ns.getKeyValue();
				Date timeDate = DateUtils.parseDate(timeLine, DateUtil.dateTimeFormat);
				if(System.currentTimeMillis() < timeDate.getTime()){
					startTime = DateUtil.getFormatDate(DateUtils.addDays(new Date(), -1), DateUtil.dateFormat)+  " " + ns.getKeyValue();
					endTime = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat)+  " " + ns.getKeyValue();
				}else{
					startTime = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat)+  " " + ns.getKeyValue();
					endTime = DateUtil.getFormatDate(DateUtils.addDays(new Date(), +1), DateUtil.dateFormat)+  " " + ns.getKeyValue();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		Attendance attendance = attendanceDao.selectAttendanceByClientUserIdAndDate(clientId,clientUserId,startTime,endTime);
		//上班考勤
		if(type == 1){
			if(attendance == null){
				Attendance newAtt = new Attendance();
				newAtt.setClientId(clientId);
				newAtt.setClientUserId(clientUserId);
				newAtt.setInLongitude(longitude);
				newAtt.setInLatitude(latitude);
				newAtt.setInPic(pics);
				newAtt.setInTime(new Date());
				attendanceDao.insert(newAtt);
				attendance = attendanceDao.selectAttendanceByClientUserIdAndDate(clientId,clientUserId,startTime,endTime);
			}
		}else if(type == 0 && attendance != null){
			attendance.setOutTime(new Date());
			attendance.setOutLongitude(longitude);  
			attendance.setOutLatitude(latitude);
			attendance.setOutPic(pics);
			attendanceDao.updateByPrimaryKey(attendance);
		}
		return attendance;
	}

	public Attendance getAttendance(Integer clientId, Integer clientUserId){
		//标准时间
		String startTime = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat);
		String endTime = DateUtil.getFormatDate(DateUtils.addDays(new Date(), +1), DateUtil.dateFormat);
		
		//取考勤时间节点
		NormalSettings ns = normalSettingDao.getByKeyCode(clientId, "K1");
		if(ns != null){
			try {
				String timeLine = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat) +  " " + ns.getKeyValue();
				Date timeDate = DateUtils.parseDate(timeLine, DateUtil.dateTimeFormat);
				if(System.currentTimeMillis() < timeDate.getTime()){
					startTime = DateUtil.getFormatDate(DateUtils.addDays(new Date(), -1), DateUtil.dateFormat)+  " " + ns.getKeyValue();
					endTime = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat)+  " " + ns.getKeyValue();
				}else{
					startTime = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat)+  " " + ns.getKeyValue();
					endTime = DateUtil.getFormatDate(DateUtils.addDays(new Date(), +1), DateUtil.dateFormat)+  " " + ns.getKeyValue();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return attendanceDao.selectAttendanceByClientUserIdAndDate(clientId,clientUserId,startTime,endTime);
	}

	@Override
	public List<Attendance> getAttendanceList(Map<String, Object> parames) {
		return attendanceDao.getListByParames(parames);
	}

	@Override
	public Attendance getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryCount(Map<String, Object> searchParams) throws BusinessException {
		return attendanceDao.queryCount(searchParams);
	}
	
	public static void main(String[] args) {
		String startD = DateUtil.getFormatDate(new Date(), DateUtil.dateFormat);
		String endD = DateUtil.getFormatDate(DateUtils.addDays(new Date(), -1), DateUtil.dateFormat);
		System.out.println(startD);
		System.out.println(endD);
	}

	@Override
	public boolean attendanceRemark(Integer clientId, Integer clientUserId, Integer attendanceId, String remark) {
		boolean re = false;
		Attendance att = attendanceDao.selectByPrimaryKey(attendanceId);
		if(att != null){
			att.setRemark(remark);
			attendanceDao.updateByPrimaryKey(att);
			re = true;
		}
		return re;
	}

	@Override
	public List<Attendance> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attendance> exportAttendance(Map<String, Object> paras)
			throws BusinessException {
		List<Attendance> list = null;
		try {
			list = attendanceDao.exportAttendance(paras);
		} catch (BusinessException e) {
			LOG.error("method exportAttendance error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public ImportVO addAttendanceByList(List<Attendance> attendanceList)
			throws BusinessException {
		ImportVO ivo = new ImportVO();
		try {
			if(attendanceList != null && attendanceList.size() > 0){
				attendanceDao.insertBylist(attendanceList);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ivo;
	}

	@Override
	public ResultMessage addImportAttendance(MultipartFile file,
			HttpServletRequest request, Integer clientId,
			HttpServletResponse resp) throws BusinessException {
		List<Attendance> attendancelist = new ArrayList<Attendance>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		
		List<String[]> errorData = new  ArrayList<String[]>();
		
		List<String> errorMessage = new ArrayList<String>();
		
		/**得到长促和促销队的clientPositionId*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionDao.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		params.put("clientPositionIds", clientPositionIds);
//		params.put("status", ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey());
//		params.put("isActivation", ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey());
		/**存在的登录名*/
		Map<String, ClientUser> findClientUserByKeylogName = clientUserDao.findClientUserByKeylogName(params);
		
		if(values != null && !values.isEmpty()){
			/**表头*/
			String[] titles = values.get(0);
			errorData.add(titles);
			/**列名校验*/
			for (int i = 0; i < titles.length; i++) {
				if(StringUtil.isEmptyString(titles[i])){
					ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
					rm.setMessage("列名不能为空");
					return rm;
				}
				if(!MobiStringUtils.contains(ImportConstants.ATTENDANCE_TITLE, titles[i])){
					ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
					rm.setMessage("存在不能识别的列名：" +titles[i]);
					return rm;
				}
			}
			Integer clientUserId = null;
			/**数据校验*/
			for (int i = 1; i < values.size(); i++) {
				String[] rows = values.get(i);
				Attendance attend =  new Attendance();
				if(!findClientUserByKeylogName.containsKey(rows[1])){
					errorData.add(rows);
					errorMessage.add("未知的登录名");
					continue;
				}else if(!findClientUserByKeylogName.get(rows[1]).getStatus().equals(ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey())){
					errorData.add(rows);
					errorMessage.add("人员不在职");
					continue;
				}else if(!findClientUserByKeylogName.get(rows[1]).getIsActivation().equals(ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey())){
					errorData.add(rows);
					errorMessage.add("人员不属于无法正常使用APP");
					continue;
				}else{
					clientUserId = findClientUserByKeylogName.get(rows[1]).getClientUserId();
				}
				Date startDate = null;
				Date endDate  = null;
				try {
					startDate = DateUtil.toDate(rows[4],DateUtil.dateTimeFormat);
					endDate = DateUtil.toDate(rows[5], DateUtil.dateTimeFormat);
				} catch (Exception e) {
					errorData.add(rows);
					errorMessage.add(titles[4]+"或者"+titles[5]+"-日期格式不正确");
					continue;
				}
				String currentDate = DateUtil.formatDate(new Date(), DateUtil.dateFormat);
				long newTime = new Date().getTime();
				long setTimeNeighbor = DateUtil.toDate(DateUtil.formatSimpleDate(startDate)+Constants.HMS,DateUtil.dateTimeFormat).getTime();
				if(startDate.getTime() > endDate.getTime()){
					errorData.add(rows);
					errorMessage.add("上班时间大于下班时间");
					continue;
				}
				/**下班时间不能超过上班时间第二天的05:59:59*/
				long nextDay = DateUtil.toDate(DateUtil.getFormatDate(DateUtil.dsDay_Date(startDate,1),DateUtil.dateFormat)+Constants.HMS,DateUtil.dateTimeFormat).getTime();
				long currDay =  DateUtil.toDate(DateUtil.getFormatDate(startDate,DateUtil.dateFormat)+Constants.HMS,DateUtil.dateTimeFormat).getTime();
				if(startDate.getTime() > setTimeNeighbor){
					if(endDate.getTime() > nextDay){
						errorData.add(rows);
						errorMessage.add("下班时间不能超过上班时间第二天的05:59:59");
						continue;
					}
				}else{
					if(endDate.getTime() > currDay){
						errorData.add(rows);
						errorMessage.add("下班时间不能超过上班时间当天的05:59:59");
						continue;
					}
				}
				if(startDate.getTime() < setTimeNeighbor){
					if(DateUtil.formatDate(DateUtil.dsDay_Date(startDate,-1), DateUtil.dateFormat).equals(currentDate) ||
							DateUtil.dsDay_Date(startDate,-1).getTime() > newTime){
						errorData.add(rows);
						errorMessage.add("未来的考勤日期");
						continue;
					}
				}else{
					if(DateUtil.formatDate(startDate, DateUtil.dateFormat).equals(currentDate) ||
							startDate.getTime() > newTime){
						errorData.add(rows);
						errorMessage.add("未来的考勤日期");
						continue;
					}
				}
				Map<String, Object> paramsDate = new HashMap<String, Object>();
				paramsDate.put("clientId", clientId);
				paramsDate.put("clientUserId", clientUserId);
				if(startDate.getTime() < setTimeNeighbor){
					paramsDate.put("startDate", DateUtil.formatSimpleDate(DateUtil.dsDay_Date(startDate,-1)));
				}else{
					paramsDate.put("startDate", DateUtil.formatSimpleDate(startDate));
				}
				List<Attendance> attendance = attendanceDao.findAttendanceByTime(paramsDate);
				if(attendance != null && !attendance.isEmpty()){
					errorData.add(rows);
					errorMessage.add("该时间用户已存在考勤");
					continue;
				}
				attend.setClientUserId(clientUserId);
				attend.setInTime(startDate);
				attend.setOutTime(endDate);
				attend.setRemark(rows[6]);
				attend.setClientId(clientId);
				attendancelist.add(attend);
			}
			/**插入考勤数据*/
			if (null != attendancelist && !attendancelist.isEmpty()) {
				attendanceDao.insertBylist(attendancelist);
			}
			Map<String, Object> resultMessage = new HashMap<String, Object>();
			resultMessage.put("errorCount", errorData.size() - 1);
			resultMessage.put("successCount", attendancelist.size());
			if (errorMessage != null && !errorMessage.isEmpty()) {
				this.importResult(request, resp, resultMessage,errorData,errorMessage,clientId);
			}
			ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
			rm.setAttributes(resultMessage);
			return rm;
		}else{
			ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
			return rm;
		}
	}
	
	private void importResult(HttpServletRequest req, HttpServletResponse resp,
			Map<String, Object> map,List<String[]> errDataList,List<String> strError,Integer clientId) {
		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			resp.addHeader("Content-Disposition", "attachment;filename="
					+ new String("导入结果查看".getBytes("gb2312"), "iso8859-1")
					+ ".xls");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		resp.setContentType("application/vnd.ms-excel");
		/** 在we中创建一个sheet */
		XSSFSheet wTSSheet = wb.createSheet("错误数据");
		/** 在wTSSheet值添加表头(第0行) */
		XSSFRow row = wTSSheet.createRow((int) 0);
		/** 创建单元格，并设置表头，设置表头居中 */
		XSSFCellStyle style = this.createHeaderStyle(wb);

		XSSFCell cell = row.createCell(0);
		String[] errDataArray = errDataList.get(0);
		cell = row.createCell(0);
		cell.setCellValue("错误信息");
		cell.setCellStyle(style);
		for (int i = 0; i < errDataArray.length; i++) {
			cell = row.createCell(i + 1);
			cell.setCellValue(errDataArray[i]);
			cell.setCellStyle(style);
		}
		for (int i = 1; i < errDataList.size(); i++) {
			row = wTSSheet.createRow((int) i);
			String[] date = errDataList.get(i);
			for (int j = 0; j < date.length; j++) {
				row.createCell(0).setCellValue(strError.get(i - 1));
				row.createCell(j + 1).setCellValue(
						date[j] == null ? "" : date[j]);
			}
		}
		try {
			String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
			File fileMkdir = new File(errDataExcelPath+clientId);
			if (!fileMkdir.exists()) {
				fileMkdir.mkdirs();
			}
			String excelName = "errAttendanceExcel" + "_"
					+ System.currentTimeMillis() + ".xlsx";
			map.put("errDataExcelPath", excelName);
			File file = new File(fileMkdir.getPath() + File.separator
					+ excelName);
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置表头样式
	 * 
	 * @param style
	 */
	private XSSFCellStyle createHeaderStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中

		XSSFFont font = wb.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}

	@Override
	public Integer unicharmCount(Map<String, Object> searchParams) {
		
		return attendanceDao.unicharmQueryCount(searchParams);
	}

	@Override
	public List<Attendance> unicharmQueryList(Map<String, Object> searchParams) {
		return attendanceDao.unicharmQueryColumnList(searchParams);
	}
}
