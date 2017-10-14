package cn.mobilizer.channel.survey.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.survey.dao.SurveyFeedbackDao;
import cn.mobilizer.channel.survey.dao.SurveyFeedbackDetailDao;
import cn.mobilizer.channel.survey.dao.SurveyObjectDao;
import cn.mobilizer.channel.survey.dao.SurveyObjectGroupDao;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.SurveyObject;
import cn.mobilizer.channel.survey.po.SurveyObjectGroup;
import cn.mobilizer.channel.survey.service.HistoryDataImportService;
import cn.mobilizer.channel.survey.vo.HistoryDataImportUtil;
import cn.mobilizer.channel.survey.vo.SqlUtil;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.PropertiesHelper;

@Service
public class HistoryDataImportServiceImpl implements HistoryDataImportService{
	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private SurveyFeedbackDao surveyFeedbackDao;
	@Autowired
	private SurveyFeedbackDetailDao surveyFeedbackDetailDao;
	@Autowired
	private SurveyObjectDao surveyObjectDao;
	@Autowired
	private SurveyObjectGroupDao surveyObjectGroupDao;
	
	
	protected Logger log = Logger.getLogger(this.getClass());

	@Override
	public Object ImportColgateHistoryData(MultipartFile fileField,HttpServletRequest req, HttpServletResponse response, Integer clientId,Integer clientUserId,String fileName,String month) throws RuntimeException{
		String batchId = UUID.randomUUID().toString();
		log.info("batchId==>"+batchId);
		
		ExcelReader reader = new ExcelReader(fileField);
		List<SurveyFeedback> surveyFeedbacks = new ArrayList<SurveyFeedback>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails = new ArrayList<SurveyFeedbackDetail>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails2 = new ArrayList<SurveyFeedbackDetail>();
		/**将一个sheet里面数据分成多个批次导入：每次导入5000条数据*/
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		List<String> errStrList2 = new ArrayList<String>();
		List<String[]> errDataList2 = new ArrayList<String[]>(); 
		Integer eachCount = 2000;
		Integer rowCount = reader.getRowNum(0)+1;    //sheet1
		Integer rowCount2 = reader.getRowNum(1)+1;    //sheet2
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		int num2 = rowCount2%eachCount==0?rowCount2/eachCount:rowCount2/eachCount + 1;
		List<SurveyFeedback> surveyFeedCacheList = null;
		Map<String, SurveyFeedback> surveyFeedCacheMap = null;
		
		/**查询出所属clientId所有的surveyObjects对象，放入map中 key为getObjectNo**/
		Map<String, SurveyObject> surveyObjectMap = new HashMap<String, SurveyObject>();
		List<SurveyObject> allSurveyObjects = surveyObjectDao.selectAllSurveyObjectsByClientId(clientId);
		if (allSurveyObjects !=null && allSurveyObjects.size() >0) {
			for ( SurveyObject surveyObject : allSurveyObjects ) {
				surveyObjectMap.put(surveyObject.getObjectNo(), surveyObject);
			}
		}
		
		Map<String, Object> _params = new HashMap<String, Object>();
		_params.put("clientId", 15);
		Map<String, Store> mapStore = storeDao.getStoreNoMap(_params);
		
		for(int a = 0;a < num ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num-1)){
				if(rowCount%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<SurveyFeedback> subSurveyFeedbacks = new ArrayList<SurveyFeedback>();
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails = new ArrayList<SurveyFeedbackDetail>();
			List<String[]> values = reader.getSubSheetData(0,beginRowIndex,endRowIndex);
			for (int i = 1; i < values.size(); i++) {
				String[] row = values.get(i);
				String feedbackNo = row[0];
				String feedbackDate = row[1];
				String storeNo = row[2];
				String storeName = row[3];
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				String imgs = row[12].replaceAll("http", ",http");
				imgs = StringUtil.removeStrComma(imgs);     //去掉首尾逗号
				List<String> imgList = new ArrayList<String>();
				for(String img : imgs.split(",")){
					if(StringUtils.isNotEmpty(img)){
						img = img.substring(img.indexOf("web")+4,img.length());
						imgList.add(img);
					}
				}
				SurveyFeedback surveyFeedback = new SurveyFeedback();
				Store store = mapStore.get(storeNo);
				if(null==store){
					store = mapStore.get(storeNo.toUpperCase());
				}
				if(store == null ){
					String errStr = "未知的门店";
					errStrList.add(errStr);
					errDataList.add(values.get(i));
					log.info("storeNo:"+values.get(i)[2]+","+",storeName:"+values.get(i)[3]);
					continue;
				}else{
					surveyFeedback.setPopId(store.getStoreId());
					
					surveyFeedback.setFeedbackId(UUID.randomUUID().toString());
					surveyFeedback.setClientId(clientId);
					surveyFeedback.setClientUserId(clientUserId);
					surveyFeedback.setFeedbackNo(feedbackNo);
					surveyFeedback.setFeedbackDate(DateUtil.toSimpleDate(feedbackDate));
					surveyFeedback.setLastUpdateTime(DateUtil.toSimpleDate(feedbackDate));
					surveyFeedback.setSurveyId(1);
					surveyFeedback.setRemark(batchId);      //批次Id
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail();
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(surveyFeedback.getFeedbackId());
					surveyFeedbackDetail.setSubSurveyId(2);
					surveyFeedbackDetail.setObjectId(99);             //门头照
					surveyFeedbackDetail.setSurveyParameterId(5);  
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbacks.add(surveyFeedback);
					subSurveyFeedbacks.add(surveyFeedback);
					if(imgList.size()==0){
						surveyFeedbackDetail.setValue("");
					}else{
						surveyFeedbackDetail.setValue(StringUtils.join(imgList,","));
					}
					surveyFeedbackDetail.setCol1(batchId);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetails.add(surveyFeedbackDetail);
					subSurveyFeedbackDetails.add(surveyFeedbackDetail);
				}
			}
			if(subSurveyFeedbacks != null && subSurveyFeedbacks.size()!=0){
//				surveyFeedbackDao.batchInsertSurveyFeedback(subSurveyFeedbacks); 
				SqlUtil.printMainSql(subSurveyFeedbacks, batchId);
				
			}
			if(subSurveyFeedbackDetails != null && subSurveyFeedbackDetails.size()!=0){
				//surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails);
				SqlUtil.printSql(subSurveyFeedbackDetails,batchId);
			}
			
		}
		
		for(int a = 0;a < num2 ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num2-1)){
				if(rowCount2%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount2%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values2 = reader.getSubSheetData(1,beginRowIndex,endRowIndex);   //sheet2
			if(month!=null && surveyFeedCacheList==null){
				surveyFeedCacheMap = new HashMap<String, SurveyFeedback>();
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("clientId",clientId);
				queryMap.put("feedbackDate", month);
				surveyFeedCacheList = surveyFeedbackDao.selectSurveyFeedbackByDate(queryMap);
				for(SurveyFeedback surveyFeedback : surveyFeedCacheList){
					surveyFeedCacheMap.put(surveyFeedback.getFeedbackNo(), surveyFeedback);
				}
			}
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails2 = new ArrayList<SurveyFeedbackDetail>();
//			Map<String, Object> paramSurveyNo = new HashMap<String, Object>();
//			paramSurveyNo.put("clientId", clientId);
			for (int i = 1; i < values2.size(); i++) {
				String[] row = values2.get(i);
				String feedbackNo = row[0];
				String storeNo = row[1];
				String storeName = row[2];
				String objectNo = row[3];
				
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
//				paramSurveyNo.put("objectNo", objectNo);
				//TODO
//				List<SurveyObject> surveyObjects = surveyObjectDao.selectSurveyObjectByObjectNo(paramSurveyNo);
				SurveyObject surveyObject = surveyObjectMap.get(objectNo);
				boolean flag = true;
				if(surveyObject == null){
					String errStr = "未知的产品编号";
					errStrList2.add(errStr);
					errDataList2.add(values2.get(i));
					flag = false;
					log.info("未知的产品编号::"+storeNo);
					continue;
				}else{
//					surveyObject = surveyObjects.get(0);
					SurveyFeedback info = surveyFeedCacheMap.get(feedbackNo);
					if(info!=null){
						//是否有售卖
						if(StringUtil.isNotEmptyString(row[7])){
							SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail();
							surveyFeedbackDetail.setFeedbackId(info.getFeedbackId());
							surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
							if(surveyObject!=null){
								surveyFeedbackDetail.setObjectId(surveyObject.getObjectId());
							}
							if(!flag){
								surveyFeedbackDetail.setIsDelete((byte)1);
							}else{
								surveyFeedbackDetail.setIsDelete((byte)0);
							}
							surveyFeedbackDetail.setClientId(clientId);
							surveyFeedbackDetail.setSubSurveyId(1);
							surveyFeedbackDetail.setSurveyParameterId(1);
							surveyFeedbackDetail.setValue(row[7]);
							surveyFeedbackDetail.setCol1(batchId);
							subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
							surveyFeedbackDetails2.add(surveyFeedbackDetail);
						}
						
						//SKU数
						if(StringUtil.isNotEmptyString(row[8])){
							SurveyFeedbackDetail surveyFeedbackDetailSku = new SurveyFeedbackDetail();
							surveyFeedbackDetailSku.setDetailId(UUID.randomUUID().toString());
							surveyFeedbackDetailSku.setFeedbackId(info.getFeedbackId());
							if(surveyObject!=null){
								surveyFeedbackDetailSku.setObjectId(surveyObject.getObjectId());
							}
							if(!flag){
								surveyFeedbackDetailSku.setIsDelete((byte)1);
							}else{
								surveyFeedbackDetailSku.setIsDelete((byte)0);
							}
							surveyFeedbackDetailSku.setCol1(batchId);
							surveyFeedbackDetailSku.setClientId(clientId);
							surveyFeedbackDetailSku.setSubSurveyId(1);
							surveyFeedbackDetailSku.setSurveyParameterId(2);
							surveyFeedbackDetailSku.setValue(row[8]);
							subSurveyFeedbackDetails2.add(surveyFeedbackDetailSku);
							surveyFeedbackDetails2.add(surveyFeedbackDetailSku);
						}
						
						//陈列面数
						if(StringUtil.isNotEmptyString(row[9])){
							SurveyFeedbackDetail surveyFeedbackDetailDisplayCount = new SurveyFeedbackDetail();
							surveyFeedbackDetailDisplayCount.setDetailId(UUID.randomUUID().toString());
							surveyFeedbackDetailDisplayCount.setFeedbackId(info.getFeedbackId());
							if(surveyObject!=null){
								surveyFeedbackDetailDisplayCount.setObjectId(surveyObject.getObjectId());
							}
							if(!flag){
								surveyFeedbackDetailDisplayCount.setIsDelete((byte)1);
							}else{
								surveyFeedbackDetailDisplayCount.setIsDelete((byte)0);
							}
							surveyFeedbackDetailDisplayCount.setCol1(batchId);
							surveyFeedbackDetailDisplayCount.setClientId(clientId);
							surveyFeedbackDetailDisplayCount.setSubSurveyId(1);
							surveyFeedbackDetailDisplayCount.setSurveyParameterId(3);
							surveyFeedbackDetailDisplayCount.setValue(row[9]);
							subSurveyFeedbackDetails2.add(surveyFeedbackDetailDisplayCount);
							surveyFeedbackDetails2.add(surveyFeedbackDetailDisplayCount);
						}else{
							//如果一下三种陈列面为空，则补0
							//所有高露洁牙膏,所有高露洁牙刷,所有高露洁漱口水
							if("a035".equals(objectNo) || "b028".equals(objectNo) || "c011".equals(objectNo)){
								SurveyFeedbackDetail surveyFeedbackDetailDisplayCount = new SurveyFeedbackDetail();
								surveyFeedbackDetailDisplayCount.setDetailId(UUID.randomUUID().toString());
								surveyFeedbackDetailDisplayCount.setFeedbackId(info.getFeedbackId());
								if(surveyObject!=null){
									surveyFeedbackDetailDisplayCount.setObjectId(surveyObject.getObjectId());
								}
								if(!flag){
									surveyFeedbackDetailDisplayCount.setIsDelete((byte)1);
								}else{
									surveyFeedbackDetailDisplayCount.setIsDelete((byte)0);
								}
								surveyFeedbackDetailDisplayCount.setCol1(batchId);
								surveyFeedbackDetailDisplayCount.setClientId(clientId);
								surveyFeedbackDetailDisplayCount.setSubSurveyId(1);
								surveyFeedbackDetailDisplayCount.setSurveyParameterId(3);
								surveyFeedbackDetailDisplayCount.setValue("0");
								subSurveyFeedbackDetails2.add(surveyFeedbackDetailDisplayCount);
								surveyFeedbackDetails2.add(surveyFeedbackDetailDisplayCount);
							}
						}
						
						//价格
						if(StringUtil.isNotEmptyString(row[10])){
							SurveyFeedbackDetail surveyFeedbackDetailPrice = new SurveyFeedbackDetail();
							surveyFeedbackDetailPrice.setDetailId(UUID.randomUUID().toString());
							surveyFeedbackDetailPrice.setFeedbackId(info.getFeedbackId());
							if(surveyObject!=null){
								surveyFeedbackDetailPrice.setObjectId(surveyObject.getObjectId());
							}
							if(!flag){
								surveyFeedbackDetailPrice.setIsDelete((byte)1);
							}else{
								surveyFeedbackDetailPrice.setIsDelete((byte)0);
							}
							surveyFeedbackDetailPrice.setCol1(batchId);
							surveyFeedbackDetailPrice.setClientId(clientId);
							surveyFeedbackDetailPrice.setSubSurveyId(1);
							surveyFeedbackDetailPrice.setSurveyParameterId(4);
							surveyFeedbackDetailPrice.setValue(row[10]);
							subSurveyFeedbackDetails2.add(surveyFeedbackDetailPrice);
							surveyFeedbackDetails2.add(surveyFeedbackDetailPrice);
						}
					}else{
						String errStr = "没有找到主数据";
						errStrList2.add(errStr);
						errDataList2.add(values2.get(i));
						flag = false;
						log.info("--没有找到主数据::"+feedbackNo);
						continue;
					}
				}
			}
			if(subSurveyFeedbackDetails2 != null && subSurveyFeedbackDetails2.size() != 0){
				long sTime = System.currentTimeMillis();
				//surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails2);
				SqlUtil.printSql(subSurveyFeedbackDetails2,batchId);
				long eTime = System.currentTimeMillis();
				log.info("Insert SurveyFeedbackDetail time:"+(eTime-sTime)+",index="+a);
			}
		}
		
		Map<String, Object> resultMessage = new HashMap<String, Object>();
//		resultMessage.put("errorCount", errDataList.size() - 1);
//		resultMessage.put("successCount", surveyFeedbacks.size());
		
		if (errStrList != null && errStrList.size()>0) {
//			String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			XSSFWorkbook wb = new XSSFWorkbook();
			/*String excelName = null;
			if(clientId.equals(8)){
				excelName = "errWorkLogExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			}else{
				excelName = "errWorkTimeSettingsExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			}*/
			FileOutputStream fos =  null;
			try {
				String name = URLEncoder.encode("导入结果查看.xlsx",ReportGlobal.CHARTSET);
				response.addHeader("Content-Disposition", "attachment;filename="+name);
				response.setContentType("application/vnd.ms-excel");
				//sheet1  数据
				XSSFSheet wTSSheet = wb.createSheet("错误数据");
				XSSFRow row = wTSSheet.createRow((int) 0);
				XSSFCellStyle style = wb.createCellStyle();
				XSSFCell cell = row.createCell(0);
				cell = row.createCell(0);
				cell.setCellValue("错误信息");
				cell.setCellStyle(style);
				for (int i = 0;i<ImportConstants.STORE_IMG_TITLE.length;i++) {
					cell = row.createCell(i+1);
					cell.setCellStyle(style);
					cell.setCellValue(ImportConstants.STORE_IMG_TITLE[i].toString());
				}
				for (int i = 1; i < errDataList.size(); i++) {
					row = wTSSheet.createRow((int) i);
					String[] date = errDataList.get(i);
					for (int j = 0; j < date.length; j++) {
						row.createCell(0).setCellValue(errStrList.get(i - 1));
						row.createCell(j + 1).setCellValue(date[j] == null ? "" : date[j]);
					}
				}
				//sheet2  数据
				XSSFSheet wTSSheet2 = wb.createSheet("错误数据2");
				XSSFRow row2 = wTSSheet2.createRow((int) 0);
				
				XSSFCell cell2 = row2.createCell(0);
				cell2 = row2.createCell(0);
				cell2.setCellValue("错误信息");
				cell2.setCellStyle(style);
				for (int i = 0;i<ImportConstants.STORE_SURVEY_TITLE.length;i++) {
					cell2 = row2.createCell(i+1);
					cell2.setCellStyle(style);
					cell2.setCellValue(ImportConstants.STORE_SURVEY_TITLE[i].toString());
				}
				
				for (int i = 1; i < errDataList2.size(); i++) {
					row2 = wTSSheet2.createRow((int) i);
					String[] date2 = errDataList2.get(i);
					for (int j = 0; j < date2.length; j++) {
						row2.createCell(0).setCellValue(errStrList2.get(i - 1));
						row2.createCell(j + 1).setCellValue(date2[j] == null ? "" : date2[j]);
					}
				}
				
				String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
				File fileMkdir = new File(errDataExcelPath+clientId);
				if (!fileMkdir.exists()) {
					fileMkdir.mkdirs();
				}
				File file = new File(fileMkdir.getPath() + File.separator + fileName);
				fos = new FileOutputStream(file);
				wb.write(fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < errDataList.size(); i++) {
				log.info("ErrorInfo===>"+errDataList.get(i)[0]+","+errStrList.get(i));
			}
			
			throw new RuntimeException("数据验证失败！");
		}else{
			resultMessage.put("errDataExcelPath", "");
		}
		return resultMessage;
	}

	@Override
	public Object saveImportColgateDisPlayHistoryData(MultipartFile fileField,HttpServletRequest req, HttpServletResponse resp, Integer clientId,Integer clientUserId,String excelName,String month) {
		String batchId = UUID.randomUUID().toString();
		log.info("batchId====>"+batchId);
		
		ExcelReader reader = new ExcelReader(fileField);
		List<SurveyFeedback> surveyFeedbacks = new ArrayList<SurveyFeedback>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails = new ArrayList<SurveyFeedbackDetail>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails2 = new ArrayList<SurveyFeedbackDetail>();
		/**将一个sheet里面数据分成多个批次导入：每次导入5000条数据*/
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		List<String> errStrList2 = new ArrayList<String>();
		List<String[]> errDataList2 = new ArrayList<String[]>(); 
		Integer eachCount = 2000;
		Integer rowCount = reader.getRowNum(0)+1;    //sheet1
		Integer rowCount2 = reader.getRowNum(1)+1;    //sheet2
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		int num2 = rowCount2%eachCount==0?rowCount2/eachCount:rowCount2/eachCount + 1;
		List<SurveyFeedback> surveyFeedCacheList = null;
		Map<String, SurveyFeedback> surveyFeedCacheMap = null;
		
		/**查询出所属clientId所有的surveyObjects对象，放入map中 key为col3+groupId**/
		Map<String, SurveyObject> surveyObjectMap = new LinkedHashMap<String, SurveyObject>();
		List<SurveyObject> allSurveyObjects = surveyObjectDao.selectAllSurveyObjectsByClientId(clientId);
		if (allSurveyObjects !=null && allSurveyObjects.size() >0) {
			for ( SurveyObject surveyObject : allSurveyObjects ) {
				surveyObjectMap.put(surveyObject.getCol3()+surveyObject.getObjectGroupId(), surveyObject);
			}
		}
		
//		Set<String> keys = surveyObjectMap.keySet();
//		for ( String key : keys ) {
//			System.out.println(key); 
//		}
		
		log.info("num2==>"+num2);
		boolean globalFlag = true;
		for(int a = 0;a < num ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num-1)){
				if(rowCount%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<SurveyFeedback> subSurveyFeedbacks = new ArrayList<SurveyFeedback>();
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails = new ArrayList<SurveyFeedbackDetail>();
			List<String[]> values = reader.getSubSheetData(0,beginRowIndex,endRowIndex);
			for (int i = 1; i < values.size(); i++) {
				String[] row = values.get(i);
				String feedbackNo = row[0];
				String feedbackDate = row[1];
				String storeNo = row[2];
				String storeName = row[3];
				SurveyFeedback info = surveyFeedbackDao.selectSurveyFeedbackByNo(feedbackNo);
				if(info!=null){
					continue;
				}
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				String imgs = row[12].replaceAll("http", ",http");
				imgs = StringUtil.removeStrComma(imgs);     //去掉首尾逗号
				List<String> imgList = new ArrayList<String>();
				for(String img : imgs.split(",")){
					if(StringUtils.isNotEmpty(img)){
						img = img.substring(img.indexOf("web")+4,img.length());
						imgList.add(img);
					}
				}
				SurveyFeedback surveyFeedback = new SurveyFeedback();
				List<Store> stores = storeDao.findStoreByNameAndNo(clientId,storeNo,storeName);
				if(stores == null || stores.size() == 0){
					String errStr = "未知的门店";
					errStrList.add(errStr);
					errDataList.add(values.get(i));
					globalFlag = false;
					continue;
				}else if(stores.size()>1){
					String errStr = "存在有多个一样的门店";
					errStrList.add(errStr);
					errDataList.add(values.get(i));
					globalFlag = false;
					continue;
				}else{
					Store store = stores.get(0);
					surveyFeedback.setPopId(store.getStoreId());
					
					surveyFeedback.setFeedbackId(UUID.randomUUID().toString());
					surveyFeedback.setClientId(clientId);
					surveyFeedback.setClientUserId(clientUserId);
					surveyFeedback.setFeedbackNo(feedbackNo);
					surveyFeedback.setFeedbackDate(DateUtil.toSimpleDate(feedbackDate));
					surveyFeedback.setLastUpdateTime(DateUtil.toSimpleDate(feedbackDate));
					surveyFeedback.setSurveyId(1);
					surveyFeedback.setRemark(batchId);      //批次Id
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail();
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(surveyFeedback.getFeedbackId());
					surveyFeedbackDetail.setSubSurveyId(2);
					surveyFeedbackDetail.setObjectId(99);             //门头照
					surveyFeedbackDetail.setSurveyParameterId(5);  
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbacks.add(surveyFeedback);
					subSurveyFeedbacks.add(surveyFeedback);
					if(imgList.size()==0){
						surveyFeedbackDetail.setValue("");
					}else{
						surveyFeedbackDetail.setValue(StringUtils.join(imgList,","));
					}
					surveyFeedbackDetail.setCol1(batchId);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetails.add(surveyFeedbackDetail);
					subSurveyFeedbackDetails.add(surveyFeedbackDetail);
				}
			}
			
			if(subSurveyFeedbacks != null && subSurveyFeedbacks.size()!=0){
				long sTime = System.currentTimeMillis();
				surveyFeedbackDao.batchInsertSurveyFeedback(subSurveyFeedbacks);
				long eTime = System.currentTimeMillis();
				log.info("Insert InsertSurveyFeedback time:"+(eTime-sTime)+",item:"+a);
			}
			if(subSurveyFeedbackDetails != null && subSurveyFeedbackDetails.size()!=0){
				//surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails);
				SqlUtil.printSql(subSurveyFeedbackDetails,batchId);
			}
			
		}
		
		List<SurveyObjectGroup> surveyObjectGroups = surveyObjectGroupDao.selectSurveyObjectBySubSurveyId(14);
		Map<String, Integer> groupObjectNameMap = new HashMap<String, Integer>();
		for(SurveyObjectGroup surveyObjectGroup : surveyObjectGroups){
			groupObjectNameMap.put(surveyObjectGroup.getGroupName(), surveyObjectGroup.getObjectGroupId());
		}
//		Set<String> keys_1 = groupObjectNameMap.keySet();
//		for ( String key : keys_1 ) {
//			System.out.println(key); 
//		}
		if(month!=null && surveyFeedCacheList==null){
			surveyFeedCacheMap = new HashMap<String, SurveyFeedback>();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("clientId",clientId);
			queryMap.put("feedbackDate", month);
			surveyFeedCacheList = surveyFeedbackDao.selectSurveyFeedbackByDate(queryMap);
			for(SurveyFeedback surveyFeedback : surveyFeedCacheList){
				surveyFeedCacheMap.put(surveyFeedback.getFeedbackNo(), surveyFeedback);
			}
		}
		
		for(int a = 0;a < num2 ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num2-1)){
				if(rowCount2%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount2%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values2 = reader.getSubSheetData(1,beginRowIndex,endRowIndex);   //sheet2
//			if(month!=null && surveyFeedCacheList==null){
//				surveyFeedCacheMap = new HashMap<String, SurveyFeedback>();
//				Map<String, Object> queryMap = new HashMap<String, Object>();
//				queryMap.put("clientId",clientId);
//				queryMap.put("feedbackDate", month);
//				surveyFeedCacheList = surveyFeedbackDao.selectSurveyFeedbackByDate(queryMap);
//				for(SurveyFeedback surveyFeedback : surveyFeedCacheList){
//					surveyFeedCacheMap.put(surveyFeedback.getFeedbackNo(), surveyFeedback);
//				}
//			}
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails2 = new ArrayList<SurveyFeedbackDetail>();
//			Map<String, Object> paramSurvey = new HashMap<String, Object>();
//			paramSurvey.put("clientId", clientId);
			for (int i = 1; i < values2.size(); i++) {
				String[] row = values2.get(i);
				String storeNo = row[2];
				String feedbackNo_0 = row[0];
				String feedbackNo_1 = row[1];
				String feedbackNo = StringUtil.isEmptyString(feedbackNo_0)?feedbackNo_1:feedbackNo_0;
				String storeName = row[3];
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				String objectGroupName = row[4];
				if(objectGroupName.equals("大堆")){
					objectGroupName = "大堆（面积≥2)";
				}
				if(objectGroupName.equals("小堆")){
					objectGroupName = "小堆(面积＜2)";
				}
				String objectName = getFullObjectName(row[5]);
				SurveyFeedback info = surveyFeedCacheMap.get(feedbackNo);
				String feedbackId = null;
				if(info!=null){
					feedbackId = info.getFeedbackId();
				}else{
					String errStr = "没有找到主数据";
					errStrList2.add(errStr);
					errDataList2.add(values2.get(i));
					globalFlag = false;
					continue;
				}
				boolean flag = true;
//				paramSurvey.put("objectGroupId", groupObjectNameMap.get(objectGroupName));
//				paramSurvey.put("objectName", objectName);
				//TODO
//				List<SurveyObject> surveyObjects = surveyObjectDao.selectSurveyObjectByNameAndGroupId(paramSurvey);
				
				String key = objectName+groupObjectNameMap.get(objectGroupName);
				SurveyObject surveyObject = surveyObjectMap.get(key.trim());
				
//				if(surveyObjects == null || surveyObjects.size() == 0){
//					String errStr = "未知的品牌 :"+objectName;
//					flag = false;
//				}else if(surveyObjects.size()>1){
//					String errStr = "存在有多个一样的品牌:"+objectName;
//					flag = false;
//				}else{
//					surveyObject = surveyObjects.get(0);
//				}
				if(surveyObject==null){
					String errStr = "未知的品牌 :"+objectName;
//					log.info(errStr);
					flag = false;
//					continue;
				}else{
					//非高露洁品牌需要写入陈列数量
					if(StringUtil.isNotEmptyString(row[6]) && row[5].indexOf("高露洁") == -1){
						SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
						surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetail.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetail.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetail.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetail.setIsDelete((byte)0);
						}
						surveyFeedbackDetail.setClientId(clientId);
						surveyFeedbackDetail.setSubSurveyId(14);
						surveyFeedbackDetail.setSurveyParameterId(38);
						surveyFeedbackDetail.setValue(row[6]);       
						surveyFeedbackDetail.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
						surveyFeedbackDetails2.add(surveyFeedbackDetail);
					}
					
					//牙刷面积
					if(StringUtil.isNotEmptyString(row[8])){
						SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
						surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetail.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetail.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetail.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetail.setIsDelete((byte)0);
						}
						surveyFeedbackDetail.setClientId(clientId);
						surveyFeedbackDetail.setSubSurveyId(14);
						surveyFeedbackDetail.setSurveyParameterId(40);
						surveyFeedbackDetail.setValue(row[8]);       
						surveyFeedbackDetail.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
						surveyFeedbackDetails2.add(surveyFeedbackDetail);
					}
					
					//360牙刷
					if(StringUtil.isNotEmptyString(row[9])){
						SurveyFeedbackDetail surveyFeedbackDetail360 = new SurveyFeedbackDetail(); 
						surveyFeedbackDetail360.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetail360.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetail360.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetail360.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetail360.setIsDelete((byte)0);
						}
						surveyFeedbackDetail360.setClientId(clientId);
						surveyFeedbackDetail360.setSubSurveyId(14);
						surveyFeedbackDetail360.setSurveyParameterId(41);
						surveyFeedbackDetail360.setValue(row[9]);       
						surveyFeedbackDetail360.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetail360);
						surveyFeedbackDetails2.add(surveyFeedbackDetail360);
					}
					//纤柔牙刷
					if(StringUtil.isNotEmptyString(row[10])){
						SurveyFeedbackDetail surveyFeedbackDetailDelicate = new SurveyFeedbackDetail();
						surveyFeedbackDetailDelicate.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailDelicate.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailDelicate.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailDelicate.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailDelicate.setIsDelete((byte)0);
						}
						surveyFeedbackDetailDelicate.setClientId(clientId);
						surveyFeedbackDetailDelicate.setSubSurveyId(14);
						surveyFeedbackDetailDelicate.setSurveyParameterId(42);
						surveyFeedbackDetailDelicate.setValue(row[10]);       
						surveyFeedbackDetailDelicate.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailDelicate);
						surveyFeedbackDetails2.add(surveyFeedbackDetailDelicate);
					}
					//牙膏面积
					if(StringUtil.isNotEmptyString(row[11])){
						SurveyFeedbackDetail surveyFeedbackDetailToothpaste = new SurveyFeedbackDetail();
						surveyFeedbackDetailToothpaste.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailToothpaste.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailToothpaste.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailToothpaste.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailToothpaste.setIsDelete((byte)0);
						}
						surveyFeedbackDetailToothpaste.setClientId(clientId);
						surveyFeedbackDetailToothpaste.setSubSurveyId(14);
						surveyFeedbackDetailToothpaste.setSurveyParameterId(43);
						surveyFeedbackDetailToothpaste.setValue(row[11]);       
						surveyFeedbackDetailToothpaste.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailToothpaste);
						surveyFeedbackDetails2.add(surveyFeedbackDetailToothpaste);
					}
					//光感白
					if(StringUtil.isNotEmptyString(row[12])){
						SurveyFeedbackDetail surveyFeedbackDetailBright = new SurveyFeedbackDetail();
						surveyFeedbackDetailBright.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailBright.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailBright.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailBright.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailBright.setIsDelete((byte)0);
						}
						surveyFeedbackDetailBright.setClientId(clientId);
						surveyFeedbackDetailBright.setSubSurveyId(14);
						surveyFeedbackDetailBright.setSurveyParameterId(44);
						surveyFeedbackDetailBright.setValue(row[12]);       
						surveyFeedbackDetailBright.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailBright);
						surveyFeedbackDetails2.add(surveyFeedbackDetailBright);
					}
					
					//360卓效护龈
					if(StringUtil.isNotEmptyString(row[13])){
						SurveyFeedbackDetail surveyFeedbackDetailGum = new SurveyFeedbackDetail();
						surveyFeedbackDetailGum.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailGum.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailGum.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailGum.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailGum.setIsDelete((byte)0);
						}
						surveyFeedbackDetailGum.setClientId(clientId);
						surveyFeedbackDetailGum.setSubSurveyId(14);
						surveyFeedbackDetailGum.setSurveyParameterId(45);
						surveyFeedbackDetailGum.setValue(row[13]);       
						surveyFeedbackDetailGum.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailGum);
						surveyFeedbackDetails2.add(surveyFeedbackDetailGum);
					}
					//360牙膏
					if(StringUtil.isNotEmptyString(row[14])){
						SurveyFeedbackDetail surveyFeedbackDetail360toothpaste = new SurveyFeedbackDetail();
						surveyFeedbackDetail360toothpaste.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetail360toothpaste.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetail360toothpaste.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetail360toothpaste.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetail360toothpaste.setIsDelete((byte)0);
						}
						surveyFeedbackDetail360toothpaste.setClientId(clientId);
						surveyFeedbackDetail360toothpaste.setSubSurveyId(14);
						surveyFeedbackDetail360toothpaste.setSurveyParameterId(46);
						surveyFeedbackDetail360toothpaste.setValue(row[14]);       
						surveyFeedbackDetail360toothpaste.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetail360toothpaste);
						surveyFeedbackDetails2.add(surveyFeedbackDetail360toothpaste);
					}
					//360备长炭
					if(StringUtil.isNotEmptyString(row[15])){
						SurveyFeedbackDetail surveyFeedbackDetailCharcoal = new SurveyFeedbackDetail();
						surveyFeedbackDetailCharcoal.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailCharcoal.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailCharcoal.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailCharcoal.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailCharcoal.setIsDelete((byte)0);
						}
						surveyFeedbackDetailCharcoal.setClientId(clientId);
						surveyFeedbackDetailCharcoal.setSubSurveyId(14);
						surveyFeedbackDetailCharcoal.setSurveyParameterId(47);
						surveyFeedbackDetailCharcoal.setValue(row[15]);       
						surveyFeedbackDetailCharcoal.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailCharcoal);
						surveyFeedbackDetails2.add(surveyFeedbackDetailCharcoal);
					}
					
					//防蛀
					if(StringUtil.isNotEmptyString(row[16])){
						SurveyFeedbackDetail surveyFeedbackDetailMothproof = new SurveyFeedbackDetail();
						surveyFeedbackDetailMothproof.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailMothproof.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailMothproof.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailMothproof.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailMothproof.setIsDelete((byte)0);
						}
						surveyFeedbackDetailMothproof.setClientId(clientId);
						surveyFeedbackDetailMothproof.setSubSurveyId(14);
						surveyFeedbackDetailMothproof.setSurveyParameterId(48);
						surveyFeedbackDetailMothproof.setValue(row[16]);       
						surveyFeedbackDetailMothproof.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailMothproof);
						surveyFeedbackDetails2.add(surveyFeedbackDetailMothproof);
					}
					
					//冰爽
					if(StringUtil.isNotEmptyString(row[17])){
						SurveyFeedbackDetail surveyFeedbackDetailCoolPassion = new SurveyFeedbackDetail();
						surveyFeedbackDetailCoolPassion.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailCoolPassion.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailCoolPassion.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailCoolPassion.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailCoolPassion.setIsDelete((byte)0);
						}
						surveyFeedbackDetailCoolPassion.setClientId(clientId);
						surveyFeedbackDetailCoolPassion.setSubSurveyId(14);
						surveyFeedbackDetailCoolPassion.setSurveyParameterId(49);
						surveyFeedbackDetailCoolPassion.setValue(row[17]);       
						surveyFeedbackDetailCoolPassion.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailCoolPassion);
						surveyFeedbackDetails2.add(surveyFeedbackDetailCoolPassion);
					}
					
					//抗敏
					if(StringUtil.isNotEmptyString(row[18])){
						SurveyFeedbackDetail surveyFeedbackDetailAntisensitizer = new SurveyFeedbackDetail();
						surveyFeedbackDetailAntisensitizer.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailAntisensitizer.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailAntisensitizer.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailAntisensitizer.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailAntisensitizer.setIsDelete((byte)0);
						}
						surveyFeedbackDetailAntisensitizer.setClientId(clientId);
						surveyFeedbackDetailAntisensitizer.setSubSurveyId(14);
						surveyFeedbackDetailAntisensitizer.setSurveyParameterId(50);
						surveyFeedbackDetailAntisensitizer.setValue(row[18]);       
						surveyFeedbackDetailAntisensitizer.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailAntisensitizer);
						surveyFeedbackDetails2.add(surveyFeedbackDetailAntisensitizer);
					}
					
					//低值
					if(StringUtil.isNotEmptyString(row[19])){
						SurveyFeedbackDetail surveyFeedbackDetailLowValue  = new SurveyFeedbackDetail();
						surveyFeedbackDetailLowValue.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailLowValue.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailLowValue.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailLowValue.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailLowValue.setIsDelete((byte)0);
						}
						surveyFeedbackDetailLowValue.setClientId(clientId);
						surveyFeedbackDetailLowValue.setSubSurveyId(14);
						surveyFeedbackDetailLowValue.setSurveyParameterId(51);
						surveyFeedbackDetailLowValue.setValue(row[19]);       
						surveyFeedbackDetailLowValue.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailLowValue);
						surveyFeedbackDetails2.add(surveyFeedbackDetailLowValue);
					}
					
					//其他
					if(StringUtil.isNotEmptyString(row[20])){
					SurveyFeedbackDetail surveyFeedbackDetailOther  = new SurveyFeedbackDetail();
						surveyFeedbackDetailOther.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailOther.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailOther.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailOther.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailOther.setIsDelete((byte)0);
						}
						surveyFeedbackDetailOther.setClientId(clientId);
						surveyFeedbackDetailOther.setSubSurveyId(14);
						surveyFeedbackDetailOther.setSurveyParameterId(52);
						surveyFeedbackDetailOther.setValue(row[20]);       
						surveyFeedbackDetailOther.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailOther);
						surveyFeedbackDetails2.add(surveyFeedbackDetailOther);
					}
					
					//牙膏牙刷是否紧邻
					if(StringUtil.isNotEmptyString(row[21])){
						SurveyFeedbackDetail surveyFeedbackDetailNeighbour  = new SurveyFeedbackDetail();
						surveyFeedbackDetailNeighbour.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailNeighbour.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailNeighbour.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailNeighbour.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailNeighbour.setIsDelete((byte)0);
						}
						surveyFeedbackDetailNeighbour.setClientId(clientId);
						surveyFeedbackDetailNeighbour.setSubSurveyId(14);
						surveyFeedbackDetailNeighbour.setSurveyParameterId(53);
						surveyFeedbackDetailNeighbour.setValue(row[21]);       
						surveyFeedbackDetailNeighbour.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailNeighbour);
						surveyFeedbackDetails2.add(surveyFeedbackDetailNeighbour);
					}
					
					//漱口水面积
					if(StringUtil.isNotEmptyString(row[22])){
						SurveyFeedbackDetail surveyFeedbackDetailMouthWash  = new SurveyFeedbackDetail();
						surveyFeedbackDetailMouthWash.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailMouthWash.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailMouthWash.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailMouthWash.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailMouthWash.setIsDelete((byte)0);
						}
						surveyFeedbackDetailMouthWash.setClientId(clientId);
						surveyFeedbackDetailMouthWash.setSubSurveyId(14);
						surveyFeedbackDetailMouthWash.setSurveyParameterId(54);
						surveyFeedbackDetailMouthWash.setValue(row[22]);       
						surveyFeedbackDetailMouthWash.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailMouthWash);
						surveyFeedbackDetails2.add(surveyFeedbackDetailMouthWash);
					}
					
					//是否有一面挂满挂条
					if(StringUtil.isNotEmptyString(row[23])){
						SurveyFeedbackDetail surveyFeedbackDetailHangingBar  = new SurveyFeedbackDetail();
						surveyFeedbackDetailHangingBar.setDetailId(UUID.randomUUID().toString());
						surveyFeedbackDetailHangingBar.setFeedbackId(feedbackId);
						if(surveyObject!=null){
							surveyFeedbackDetailHangingBar.setObjectId(surveyObject.getObjectId());
						}
						if(!flag){
							surveyFeedbackDetailHangingBar.setIsDelete((byte)1);
						}else{
							surveyFeedbackDetailHangingBar.setIsDelete((byte)0);
						}
						surveyFeedbackDetailHangingBar.setClientId(clientId);
						surveyFeedbackDetailHangingBar.setSubSurveyId(14);
						surveyFeedbackDetailHangingBar.setSurveyParameterId(55);
						surveyFeedbackDetailHangingBar.setValue(row[23]);       
						surveyFeedbackDetailHangingBar.setCol1(batchId);
						subSurveyFeedbackDetails2.add(surveyFeedbackDetailHangingBar);
						surveyFeedbackDetails2.add(surveyFeedbackDetailHangingBar);
					}
				}
			}
			if(subSurveyFeedbackDetails2 != null && subSurveyFeedbackDetails2.size() != 0){
				long sTime = System.currentTimeMillis();
				//surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails2);
				SqlUtil.printSql(subSurveyFeedbackDetails2,batchId);
				long eTime = System.currentTimeMillis();
				log.info("Insert DisPlay Data time:"+(eTime-sTime)+",item:"+a);
				log.info("subSurveyFeedbackDetails2.size()::"+subSurveyFeedbackDetails2.size());
			}
		}
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errDataList.size() - 1);
		resultMessage.put("successCount", surveyFeedbacks.size());
		
		if (errStrList != null && !errStrList.isEmpty() && errStrList.size()>0) {
//			String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			
			XSSFWorkbook wb = new XSSFWorkbook();
			/*String excelName = null;
			if(clientId.equals(8)){
				excelName = "errWorkLogExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			}else{
				excelName = "errWorkTimeSettingsExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			}*/
			FileOutputStream fos =  null;
			try {
				String name = URLEncoder.encode("导入结果查看.xlsx",ReportGlobal.CHARTSET);
				resp.addHeader("Content-Disposition", "attachment;filename="+name);
				resp.setContentType("application/vnd.ms-excel");
				//sheet1  数据
				XSSFSheet wTSSheet = wb.createSheet("错误数据");
				XSSFRow row = wTSSheet.createRow((int) 0);
				XSSFCellStyle style = wb.createCellStyle();
				XSSFCell cell = row.createCell(0);
				cell = row.createCell(0);
				cell.setCellValue("错误信息");
				cell.setCellStyle(style);
				for (int i = 1; i < errDataList.size(); i++) {
					row = wTSSheet.createRow((int) i);
					String[] date = errDataList.get(i);
					for (int j = 0; j < date.length; j++) {
						row.createCell(0).setCellValue(errStrList.get(i - 1));
						row.createCell(j + 1).setCellValue(date[j] == null ? "" : date[j]);
					}
				}
				//sheet2  数据
				XSSFSheet wTSSheet2 = wb.createSheet("错误数据2");
				XSSFRow row2 = wTSSheet2.createRow((int) 0);
				
				XSSFCell cell2 = row2.createCell(0);
				cell2 = row2.createCell(0);
				cell2.setCellValue("错误信息");
				cell2.setCellStyle(style);
				
				for (int i = 1; i < errDataList2.size(); i++) {
					row2 = wTSSheet2.createRow((int) i);
					String[] date2 = errDataList2.get(i);
					for (int j = 0; j < date2.length; j++) {
						row2.createCell(0).setCellValue(errStrList2.get(i - 1));
						row2.createCell(j + 1).setCellValue(date2[j] == null ? "" : date2[j]);
					}
				}
				
				String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
				File fileMkdir = new File(errDataExcelPath+clientId);
				if (!fileMkdir.exists()) {
					fileMkdir.mkdirs();
				}
				File file = new File(fileMkdir.getPath() + File.separator + excelName);
				fos = new FileOutputStream(file);
				wb.write(fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			resultMessage.put("errDataExcelPath", excelName);
			throw new RuntimeException("数据验证失败！");
		}else{
			resultMessage.put("errDataExcelPath", "");
		}
		return resultMessage;
	}
	
	public String getFullObjectName(String objectName){
		if(objectName.equals("高露洁")&&!objectName.contains("2")&&!objectName.contains("3")&&!objectName.contains("4")&&!objectName.contains("5")){
			return objectName+" 1";
		}
		return objectName;
	}

	@Override
	public Object ImportSupplementaryHistoryData(MultipartFile fileField,HttpServletRequest req, HttpServletResponse resp, Integer clientId,Integer clientUserId,String excelName,String month) {
		String batchId = UUID.randomUUID().toString();
		log.info("batchId===>"+batchId);
		
		ExcelReader reader = new ExcelReader(fileField);
		List<SurveyFeedback> surveyFeedbacks = new ArrayList<SurveyFeedback>();
//		List<SurveyFeedbackDetail> surveyFeedbackDetails = new ArrayList<SurveyFeedbackDetail>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails2 = new ArrayList<SurveyFeedbackDetail>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails3 = new ArrayList<SurveyFeedbackDetail>();
		List<SurveyFeedbackDetail> surveyFeedbackDetails4 = new ArrayList<SurveyFeedbackDetail>();
//		List<SurveyFeedbackDetail> surveyFeedbackDetails5 = new ArrayList<SurveyFeedbackDetail>();
//		List<SurveyFeedbackDetail> surveyFeedbackDetails6 = new ArrayList<SurveyFeedbackDetail>();
		/**将一个sheet里面数据分成多个批次导入：每次导入5000条数据*/
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		List<String> errStrList2 = new ArrayList<String>();
		List<String[]> errDataList2 = new ArrayList<String[]>();
		List<String> errStrList3 = new ArrayList<String>();
		List<String[]> errDataList3 = new ArrayList<String[]>();
		List<String> errStrList4 = new ArrayList<String>();
		List<String[]> errDataList4 = new ArrayList<String[]>();
//		List<String> errStrList5 = new ArrayList<String>();
//		List<String[]> errDataList5 = new ArrayList<String[]>();
		List<String> errStrList6 = new ArrayList<String>();
		List<String[]> errDataList6 = new ArrayList<String[]>();
		 
		//数据对象
		Integer eachCount = 2000;
//		Integer rowCount = reader.getRowNum(0)+1;    //sheet1
		Integer rowCount2 = reader.getRowNum(1)+1;    //sheet2
		Integer rowCount3 = reader.getRowNum(2)+1;    //sheet3
		Integer rowCount4 = reader.getRowNum(3)+1;    //sheet4
		Integer rowCount6 = reader.getRowNum(5)+1;    //sheet4
//		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		int num2 = rowCount2%eachCount==0?rowCount2/eachCount:rowCount2/eachCount + 1;
		int num3 = rowCount3%eachCount==0?rowCount3/eachCount:rowCount3/eachCount + 1;
		int num4 = rowCount3%eachCount==0?rowCount4/eachCount:rowCount4/eachCount + 1;
		int num6 = rowCount6%eachCount==0?rowCount6/eachCount:rowCount6/eachCount + 1;
		Map<String,Object> paramsSubSurveyId9 = new HashMap<String, Object>();
		paramsSubSurveyId9.put("clientId", clientId);
		paramsSubSurveyId9.put("subSurveyId", 9);
		List<SurveyObject> subSurveyId9Objs = surveyObjectDao.selectSurveyObjectBySubSurveyId(paramsSubSurveyId9);
		Map<String,Integer> subSurveyId9IdMap = new HashMap<String, Integer>();
		for(SurveyObject surveyObject : subSurveyId9Objs){
			subSurveyId9IdMap.put(surveyObject.getObjectName(), surveyObject.getObjectId());
		}
		Map<String,Object> paramsSubSurveyId7 = new HashMap<String, Object>();
		paramsSubSurveyId7.put("clientId", clientId);
		paramsSubSurveyId7.put("subSurveyId", 7);
		List<SurveyObject> subSurveyId7Objs = surveyObjectDao.selectSurveyObjectBySubSurveyId(paramsSubSurveyId7);
		Map<String,Integer> subSurveyId7Map = new HashMap<String, Integer>();
		for(SurveyObject surveyObject : subSurveyId7Objs){
			subSurveyId7Map.put(surveyObject.getObjectName(), surveyObject.getObjectId());
		}
		Map<String,Object> paramsSubSurveyId10 = new HashMap<String, Object>();
		paramsSubSurveyId10.put("clientId", clientId);
		paramsSubSurveyId10.put("subSurveyId", 10);
		List<SurveyObject> subSurveyId10Objs = surveyObjectDao.selectSurveyObjectBySubSurveyId(paramsSubSurveyId10);
		Map<String,Integer> subSurveyId10Map = new HashMap<String, Integer>();
		for(SurveyObject surveyObject : subSurveyId10Objs){
			subSurveyId10Map.put(surveyObject.getObjectName(), surveyObject.getObjectId());
		}
		Map<String, Object> surveyFeedbackMap = new HashMap<String, Object>();
		surveyFeedbackMap.put("clientId", clientId);
		surveyFeedbackMap.put("feedbackDate", month);
		Map<String, SurveyFeedback> surveyFeedCacheMap = new HashMap<String, SurveyFeedback>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("clientId",clientId);
		queryMap.put("feedbackDate", month);
		List<SurveyFeedback> surveyFeedCacheList = surveyFeedbackDao.selectSurveyFeedbackByStoreNoAndDate(queryMap);
		for(SurveyFeedback surveyFeedback : surveyFeedCacheList){
			surveyFeedCacheMap.put(surveyFeedback.getStoreNo().toUpperCase(), surveyFeedback);
		}
		
		//sheet2
		log.info("sheet2 start ");
		for(int a = 0;a < num2 ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num2-1)){
				if(rowCount2%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount2%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values2 = reader.getSubSheetData(1,beginRowIndex,endRowIndex);   //sheet2
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails2 = new ArrayList<SurveyFeedbackDetail>();
			//feedNoAndStoreNo
			boolean isfirst = true;
			for (int i = 1; i < values2.size(); i++) {
				String[] row = values2.get(i);
				String storeNo = row[0];
				surveyFeedbackMap.put("storeNo", storeNo);
				String storeName = row[1];
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				SurveyFeedback surveyFeedback = surveyFeedCacheMap.get(storeNo.toUpperCase());
				if(null==surveyFeedback){
					String errStr = "没有找到主数据"+storeNo;
					log.info(errStr);
					errStrList2.add(errStr);
					errDataList2.add(values2.get(i));
					continue;
				}
//				String feedbackNo = surveyFeedback.getFeedbackNo();
				String valueStr = row[5];
				String feedbackId = surveyFeedback.getFeedbackId();
				if(feedbackId==null){
					continue;
				} 
				
				Integer oid = subSurveyId7Map.get(row[3]);
				if(row[3].equals("视平线")){
					if(isfirst){
						oid = 150;
					}else{
						oid = 151;
					}
					isfirst = !isfirst;
				}
				
				//牙膏区域系列
				if(valueStr.contains("360系列")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(9);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				if(valueStr.contains("冰爽系列")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(12);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				if(valueStr.contains("全面防蛀系列")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(13);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				if(valueStr.contains("低值系列")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(14);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				if(valueStr.contains("抗敏系列")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(15);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				if(valueStr.contains("其他系列")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(16);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				if(valueStr.contains("冰爽劲白")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(60);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				if(valueStr.contains("光感白")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(10);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				if(valueStr.contains("卓效护龈")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(oid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(7);
					surveyFeedbackDetail.setSurveyParameterId(11);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				String lightValueStr = row[6];    //光感白
				if(StringUtils.isNotEmpty(lightValueStr)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(17);
					surveyFeedbackDetail.setValue(lightValueStr);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				String str360Value = row[7];     //360
				if(StringUtils.isNotEmpty(str360Value)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(18);
					surveyFeedbackDetail.setValue(str360Value);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				String strMothproof = row[8];         //全面防蛀
				if(StringUtils.isNotEmpty(strMothproof)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(19);
					surveyFeedbackDetail.setValue(strMothproof);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				String coolStr = row[9];              //冰爽
				if(StringUtils.isNotEmpty(coolStr)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(20);
					surveyFeedbackDetail.setValue(coolStr);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				String  lowValueStr = row[10];     //低值
				if(StringUtils.isNotEmpty(lowValueStr)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(21);
					surveyFeedbackDetail.setValue(lowValueStr);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				
				String antisensitizerStr = row[11];        //基础抗敏
				if(StringUtils.isNotEmpty(antisensitizerStr)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(22);
					surveyFeedbackDetail.setValue(antisensitizerStr);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
				

				String safeStr = row[12];        //卓效护龈
				if(StringUtils.isNotEmpty(safeStr)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(160);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(8);
					surveyFeedbackDetail.setSurveyParameterId(62);
					surveyFeedbackDetail.setValue(safeStr);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails2.add(surveyFeedbackDetail);
					surveyFeedbackDetails2.add(surveyFeedbackDetail);
				}
			}
			//入库
			if(subSurveyFeedbackDetails2 != null && subSurveyFeedbackDetails2.size() != 0){
//				surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails2);
				SqlUtil.printSql(subSurveyFeedbackDetails2,batchId);
			}
		}
		
		//sheet3   牙刷
		log.info("sheet3 start ");
		for(int a = 0;a < num3 ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num3-1)){
				if(rowCount3%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount3%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values3 = reader.getSubSheetData(2,beginRowIndex,endRowIndex);   //sheet2
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails3 = new ArrayList<SurveyFeedbackDetail>();
			boolean isfirst = true;
			for (int i = 1; i < values3.size(); i++) {
				String[] row = values3.get(i);
				String storeNo = row[0];
				surveyFeedbackMap.put("storeNo", storeNo);
				String storeName = row[1];
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				SurveyFeedback surveyFeedback = surveyFeedCacheMap.get(storeNo.toUpperCase());
				if(null==surveyFeedback){
					String errStr = "没有找到主数据"+storeNo;
					log.info(errStr);
					errStrList2.add(errStr);
					errDataList2.add(values3.get(i));
					continue;
				}
				String valueStr = row[4];
				String feedbackId = surveyFeedback.getFeedbackId();
				if(feedbackId==null){
					continue;
				} 
				
				Integer boid = subSurveyId10Map.get(row[3]);
				if(row[3].equals("视平线")){
					if(isfirst){
						boid = 167;
					}else{
						boid = 168;
					}
					isfirst = !isfirst;
				}
				
				//纤柔牙刷系列
				if(valueStr.contains("高露洁纤柔牙刷")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(boid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(10);
					surveyFeedbackDetail.setSurveyParameterId(24);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//360牙刷
				if(valueStr.contains("高露洁360牙刷")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(boid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(10);
					surveyFeedbackDetail.setSurveyParameterId(25);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//低值牙刷
				if(valueStr.contains("高露洁低值牙刷")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(boid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(10);
					surveyFeedbackDetail.setSurveyParameterId(26);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//中值牙刷
				if(valueStr.contains("高露洁中值牙刷")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(boid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(10);
					surveyFeedbackDetail.setSurveyParameterId(28);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//高值牙刷
				if(valueStr.contains("高露洁高值牙刷")){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(boid);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(10);
					surveyFeedbackDetail.setSurveyParameterId(29);
					surveyFeedbackDetail.setValue("1");       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				
				//高值纤柔
				String qianRouStr = row[5];
				if(StringUtils.isNotEmpty(qianRouStr)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(177);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(11);
					surveyFeedbackDetail.setSurveyParameterId(30);
					surveyFeedbackDetail.setValue(qianRouStr);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//360牙刷
				String yaShua360 = row[6];
				if(StringUtils.isNotEmpty(yaShua360)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(177);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(11);
					surveyFeedbackDetail.setSurveyParameterId(32);
					surveyFeedbackDetail.setValue(yaShua360);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//高值牙刷
				String highValueYashua = row[7];
				if(StringUtils.isNotEmpty(highValueYashua)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(177);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(11);
					surveyFeedbackDetail.setSurveyParameterId(34);
					surveyFeedbackDetail.setValue(highValueYashua);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//中值牙刷
				String midValueYashua = row[8];
				if(StringUtils.isNotEmpty(midValueYashua)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(177);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(11);
					surveyFeedbackDetail.setSurveyParameterId(31);
					surveyFeedbackDetail.setValue(midValueYashua);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
				
				//低值牙刷
				String lowValueYashua = row[9];
				if(StringUtils.isNotEmpty(lowValueYashua)){
					SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
					surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
					surveyFeedbackDetail.setFeedbackId(feedbackId);
					surveyFeedbackDetail.setObjectId(177);
					surveyFeedbackDetail.setIsDelete((byte)0);
					surveyFeedbackDetail.setClientId(clientId);
					surveyFeedbackDetail.setSubSurveyId(11);
					surveyFeedbackDetail.setSurveyParameterId(33);
					surveyFeedbackDetail.setValue(lowValueYashua);       
					surveyFeedbackDetail.setCol1(batchId);
					subSurveyFeedbackDetails3.add(surveyFeedbackDetail);
					surveyFeedbackDetails3.add(surveyFeedbackDetail);
				}
			}
			//入库
			if(subSurveyFeedbackDetails3 != null && subSurveyFeedbackDetails3.size() != 0){
//				surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails3);
				SqlUtil.printSql(subSurveyFeedbackDetails3,batchId);
			}
		}
		
		log.info("sheet4 start ");
		for(int a = 0;a < num4 ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num4-1)){
				if(rowCount4%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount4%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values4 = reader.getSubSheetData(3,beginRowIndex,endRowIndex);   //sheet2
			List<SurveyFeedbackDetail> subSurveyFeedbackDetails4 = new ArrayList<SurveyFeedbackDetail>();
			for (int i = 1; i < values4.size(); i++) {
				String[] row = values4.get(i);
				String storeNo = row[0];
				surveyFeedbackMap.put("storeNo", storeNo);
				String storeName = row[1];
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				SurveyFeedback surveyFeedback = surveyFeedCacheMap.get(storeNo.toUpperCase());
				if(null == surveyFeedback){
					String errStr = "没有找到主数据"+storeNo;
					log.info(errStr);
					errStrList2.add(errStr);
					errDataList2.add(values4.get(i));
					continue;
				}
				String valueStr = row[4];
				String feedbackId = surveyFeedback.getFeedbackId();
				if(feedbackId==null){
					continue;
				} 
				SurveyFeedbackDetail surveyFeedbackDetail = new SurveyFeedbackDetail(); 
				surveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
				surveyFeedbackDetail.setFeedbackId(feedbackId);
				surveyFeedbackDetail.setObjectId(subSurveyId9IdMap.get(row[3]));
				surveyFeedbackDetail.setIsDelete((byte)0);
				surveyFeedbackDetail.setClientId(clientId);
				surveyFeedbackDetail.setSubSurveyId(9);
				surveyFeedbackDetail.setSurveyParameterId(23);
				surveyFeedbackDetail.setValue(valueStr);       
				surveyFeedbackDetail.setCol1(batchId);
				subSurveyFeedbackDetails4.add(surveyFeedbackDetail);
				surveyFeedbackDetails4.add(surveyFeedbackDetail);
			}
			//入库
			if(subSurveyFeedbackDetails4 != null && subSurveyFeedbackDetails4.size() != 0){
//				surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(subSurveyFeedbackDetails4);
				SqlUtil.printSql(subSurveyFeedbackDetails4,batchId);
			}
		}
		
		//sheet6
		log.info("sheet6 start ");
		for(int a = 0;a < num6 ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num6-1)){
				if(rowCount6%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount6%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values6 = reader.getSubSheetData(5,beginRowIndex,endRowIndex);   //sheet2
			List<SurveyFeedbackDetail> details = new ArrayList<SurveyFeedbackDetail>();
			
			for (int i = 3; i < values6.size(); i++) {
				String[] values = values6.get(i);
				String storeNo = values[2];
				String storeName = values[3];
				if(storeNo.equals("门店编号")){
					continue;
				}
				if(storeName.equals("门店名称")){
					continue;
				}
				surveyFeedbackMap.put("storeNo", storeNo);
				SurveyFeedback surveyFeedback = surveyFeedCacheMap.get(storeNo.toUpperCase());
				if(null==surveyFeedback){
					String errStr = "没有找到主数据"+storeNo;
					errStrList2.add(errStr);
					errDataList2.add(values6.get(i));
					continue;
				}
				//主货架装饰
				SurveyFeedbackDetail d1 = createZHJ(139, values[5], surveyFeedback.getFeedbackId(),batchId); //主货架装饰条
				SurveyFeedbackDetail d2 = createZHJ(140, values[6], surveyFeedback.getFeedbackId(),batchId);//牙刷尝试小架
				SurveyFeedbackDetail d3 = createZHJ(141, values[7], surveyFeedback.getFeedbackId(),batchId);//漱口水货架托盘
				SurveyFeedbackDetail d4 = createZHJ(142, values[8], surveyFeedback.getFeedbackId(),batchId);////抗敏牙膏货架托盘
				HistoryDataImportUtil.add(details, d1);
				HistoryDataImportUtil.add(details, d2);
				HistoryDataImportUtil.add(details, d3);
				HistoryDataImportUtil.add(details, d4);
				
				//是否货架中间陈列
				SurveyFeedbackDetail a1 = createZJCL(143, values[9], surveyFeedback.getFeedbackId(),batchId);//高露洁牙膏
				SurveyFeedbackDetail a2 = createZJCL(144, values[10], surveyFeedback.getFeedbackId(),batchId);//高露洁牙刷
				SurveyFeedbackDetail a3 = createZJCL(145, values[11], surveyFeedback.getFeedbackId(),batchId);//高露洁贝齿漱口水
				HistoryDataImportUtil.add(details, a1);
				HistoryDataImportUtil.add(details, a2);
				HistoryDataImportUtil.add(details, a3);
				
				//是否有陈列
				SurveyFeedbackDetail c1 = create(178, 12, 35, values[12], surveyFeedback.getFeedbackId(),batchId); //牙刷陈列中心
				SurveyFeedbackDetail c2 = create(179, 12, 35, values[13], surveyFeedback.getFeedbackId(),batchId); //牙刷落地架
				HistoryDataImportUtil.add(details, c1);
				HistoryDataImportUtil.add(details, c2);
				
				//陈列是否达标
				SurveyFeedbackDetail e1 = create(178, 12, 36, values[17], surveyFeedback.getFeedbackId(),batchId); //牙刷陈列中心
				SurveyFeedbackDetail e2 = create(179, 12, 36, values[18], surveyFeedback.getFeedbackId(),batchId); //牙刷落地架
				HistoryDataImportUtil.add(details, e1);
				HistoryDataImportUtil.add(details, e2);
				
				//促销员检查
				SurveyFeedbackDetail f1 = create(180, 13, 37, values[22], surveyFeedback.getFeedbackId(),batchId);//本店有无高露洁促销员
				SurveyFeedbackDetail f2 = create(181, 13, 37, values[23], surveyFeedback.getFeedbackId(),batchId);//促销员仪容仪表标准
				SurveyFeedbackDetail f3 = create(182, 13, 37, values[24], surveyFeedback.getFeedbackId(),batchId);//本店有无舒客促销员
				SurveyFeedbackDetail f4 = create(183, 15, 59, values[25], surveyFeedback.getFeedbackId(),batchId);//有几个舒客促销员
				HistoryDataImportUtil.add(details, f1);
				HistoryDataImportUtil.add(details, f2);
				HistoryDataImportUtil.add(details, f3);
				HistoryDataImportUtil.add(details, f4);
				
				//收银台数量
				SurveyFeedbackDetail g1 = create(146, 6, 8, values[26], surveyFeedback.getFeedbackId(),batchId);
				HistoryDataImportUtil.add(details, g1);
			}
			
			//入库
			if(details != null && details.size() != 0){
//				surveyFeedbackDetailDao.batchInsertSurveyFeedbackDetail(details);
				SqlUtil.printSql(details,batchId);
			}
		}
		
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errDataList.size() - 1);
		resultMessage.put("successCount", surveyFeedbacks.size());
		if (errStrList != null && !errStrList.isEmpty() && errStrList.size()>0) {
			XSSFWorkbook wb = new XSSFWorkbook();
			FileOutputStream fos =  null;
			try {
				String name = URLEncoder.encode("导入结果查看.xlsx",ReportGlobal.CHARTSET);
				resp.addHeader("Content-Disposition", "attachment;filename="+name);
				resp.setContentType("application/vnd.ms-excel");
				//sheet1  数据
				XSSFSheet wTSSheet = wb.createSheet("错误数据");
				XSSFRow row = wTSSheet.createRow((int) 0);
				XSSFCellStyle style = wb.createCellStyle();
				XSSFCell cell = row.createCell(0);
				cell = row.createCell(0);
				cell.setCellValue("错误信息");
				cell.setCellStyle(style);
				for (int i = 0;i<ImportConstants.STORE_IMG_TITLE.length;i++) {
					cell = row.createCell(i+1);
					cell.setCellStyle(style);
					cell.setCellValue(ImportConstants.STORE_IMG_TITLE[i].toString());
				}
				for (int i = 1; i < errDataList.size(); i++) {
					row = wTSSheet.createRow((int) i);
					String[] date = errDataList.get(i);
					for (int j = 0; j < date.length; j++) {
						row.createCell(0).setCellValue(errStrList.get(i - 1));
						row.createCell(j + 1).setCellValue(date[j] == null ? "" : date[j]);
					}
				}
				//sheet2  数据
				XSSFSheet wTSSheet2 = wb.createSheet("错误数据2");
				XSSFRow row2 = wTSSheet2.createRow((int) 0);
				
				XSSFCell cell2 = row2.createCell(0);
				cell2 = row2.createCell(0);
				cell2.setCellValue("错误信息");
				cell2.setCellStyle(style);
				
				for (int i = 1; i < errDataList2.size(); i++) {
					row2 = wTSSheet2.createRow((int) i);
					String[] date2 = errDataList2.get(i);
					for (int j = 0; j < date2.length; j++) {
						row2.createCell(0).setCellValue(errStrList2.get(i - 1));
						row2.createCell(j + 1).setCellValue(date2[j] == null ? "" : date2[j]);
					}
				}
				
				//sheet3  数据
				XSSFSheet wTSSheet3 = wb.createSheet("错误数据3");
				XSSFRow row3 = wTSSheet3.createRow((int) 0);
				
				XSSFCell cell3 = row3.createCell(0);
				cell3 = row3.createCell(0);
				cell3.setCellValue("错误信息");
				cell3.setCellStyle(style);
				
				for (int i = 1; i < errDataList3.size(); i++) {
					row3 = wTSSheet3.createRow((int) i);
					String[] date2 = errDataList3.get(i);
					for (int j = 0; j < date2.length; j++) {
						row2.createCell(0).setCellValue(errStrList3.get(i - 1));
						row2.createCell(j + 1).setCellValue(date2[j] == null ? "" : date2[j]);
					}
				}
				
				//sheet4  数据
				XSSFSheet wTSSheet4 = wb.createSheet("错误数据4");
				XSSFRow row4 = wTSSheet3.createRow((int) 0);
				
				XSSFCell cell4 = row4.createCell(0);
				cell4 = row4.createCell(0);
				cell4.setCellValue("错误信息");
				cell4.setCellStyle(style);
				for (int i = 1; i < errDataList4.size(); i++) {
					row4 = wTSSheet4.createRow((int) i);
					String[] date2 = errDataList4.get(i);
					for (int j = 0; j < date2.length; j++) {
						row4.createCell(0).setCellValue(errStrList4.get(i - 1));
						row4.createCell(j + 1).setCellValue(date2[j] == null ? "" : date2[j]);
					}
				}
				
				//sheet4  数据
				XSSFSheet wTSSheet6 = wb.createSheet("错误数据6");
				XSSFRow row6 = wTSSheet6.createRow((int) 0);
				
				XSSFCell cell6 = row6.createCell(0);
				cell6 = row6.createCell(0);
				cell6.setCellValue("错误信息");
				cell6.setCellStyle(style);
				for (int i = 1; i < errDataList6.size(); i++) {
					row6 = wTSSheet6.createRow((int) i);
					String[] date2 = errDataList6.get(i);
					for (int j = 0; j < date2.length; j++) {
						row6.createCell(0).setCellValue(errStrList6.get(i - 1));
						row6.createCell(j + 1).setCellValue(date2[j] == null ? "" : date2[j]);
					}
				}
				
				String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
				File fileMkdir = new File(errDataExcelPath+clientId);
				if (!fileMkdir.exists()) {
					fileMkdir.mkdirs();
				}
				File file = new File(fileMkdir.getPath() + File.separator + excelName);
				fos = new FileOutputStream(file);
				wb.write(fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultMessage.put("errDataExcelPath", excelName);
			throw new RuntimeException("数据验证失败！");
		}else{
			resultMessage.put("errDataExcelPath", "");
		}
		return resultMessage;
	}
	
	public static SurveyFeedbackDetail create(Integer objectId,Integer subSurveyId,Integer parameterId,String value,String feedbackId,String batchId){
		if(StringUtil.isEmptyString(value)){
			return null;
		}else{
			SurveyFeedbackDetail d1 = new SurveyFeedbackDetail();
			d1.setClientId(15);
			d1.setDetailId(UUID.randomUUID().toString());
			d1.setValue(value);
			d1.setFeedbackId(feedbackId);
			d1.setSubSurveyId(subSurveyId);
			d1.setObjectId(objectId); 
			d1.setSurveyParameterId(parameterId); 
			d1.setIsDelete((byte)0);
			d1.setCol1(batchId);
			return d1;
		}
	}
	
	public static SurveyFeedbackDetail createZHJ(Integer objectId,String value,String feedbackId,String batchId){
		if(StringUtil.isEmptyString(value)){
			return null;
		}else{
			SurveyFeedbackDetail d1 = new SurveyFeedbackDetail();
			d1.setClientId(15);
			d1.setDetailId(UUID.randomUUID().toString());
			d1.setValue(value);
			d1.setFeedbackId(feedbackId);
			d1.setSubSurveyId(4);
			d1.setObjectId(objectId); 
			d1.setIsDelete((byte)0);
			d1.setSurveyParameterId(6); 
			d1.setCol1(batchId);
			return d1;
		}
	}
	
	public static SurveyFeedbackDetail createZJCL(Integer objectId,String value,String feedbackId,String batchId){
		if(StringUtil.isEmptyString(value)){
			return null;
		}else{
			SurveyFeedbackDetail d1 = new SurveyFeedbackDetail();
			d1.setClientId(15);
			d1.setDetailId(UUID.randomUUID().toString());
			d1.setValue(value);
			d1.setFeedbackId(feedbackId);
			d1.setCol1(batchId);
			d1.setSubSurveyId(5);
			d1.setObjectId(objectId); 
			d1.setIsDelete((byte)0);
			d1.setSurveyParameterId(7); 
			return d1;
		}
	}
}
