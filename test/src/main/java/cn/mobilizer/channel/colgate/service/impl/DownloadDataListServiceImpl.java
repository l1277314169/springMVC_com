package cn.mobilizer.channel.colgate.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.colgate.dao.DownloadDataListDao;
import cn.mobilizer.channel.colgate.po.DownloadDataList;
import cn.mobilizer.channel.colgate.service.DownloadDataListService;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.vo.DateVoSupport;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.PropertiesHelper;


@Service
public class DownloadDataListServiceImpl implements DownloadDataListService {

	@Autowired
	private DownloadDataListDao downloadDataListDao;
	
	private final static String[] EN_MONTH = new String[] { "Jan", "Feb","Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov","Dec" };
	private static String imgFolder = null;
	static{
		imgFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
	}
	
	private static Log log = LogFactory.getLog(DownloadDataListServiceImpl.class);

	/**
	 * RawData数据每月生成一份，每天晚上11点需要通过定时任务去更新数据
	 */
	@Override
	public void createMainQuestionnaire(Date date) throws Exception {
		log.info("createMainQuestionnaire start");
		String startDate = DateTimeUtils.formatTime(DateTimeUtils.getFirstDay(date), DateTimeUtils.yyyyMMdd);
		String endDate = DateTimeUtils.formatTime(DateTimeUtils.getLastDay(date), DateTimeUtils.yyyyMMdd);
//		Date date = DateTimeUtils.getCurrentDate();
		String period = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		String year = DateTimeUtils.formatTime(date, DateTimeUtils.yyyy);
		Integer month = Integer.parseInt(DateTimeUtils.formatTime(date, DateTimeUtils.MM));
		Integer clientId = 15;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		StringBuffer rootPath = new StringBuffer();
		rootPath.append(imgFolder).append(File.separator).append(clientId).append(File.separator).append("download").append(File.separator);
		
		//生成Main Questionnaire数据
		log.info("startDate: "+startDate+",endDate="+endDate);
		List<TreeMap<String, String>> MainQuestionnaireList = downloadDataListDao.getSurveyMainQuestionnaire(params);
		List<DataInfo> mainDataList = DateVoSupport.getDataInfoList(MainQuestionnaireList);
		String fileName = "Main_Questionnaire_"+period+".xlsx";
		FileOutputStream out = new FileOutputStream(rootPath+fileName);
		DateVoSupport.writer(mainDataList,out);
		
		params.put("periodId",period);
		//insert DB Main_Questionnaire
		String MQ = "Main_Questionnaire";
		params.put("dataDesc", MQ);
		params.put("clientId", clientId);
		DownloadDataList mDataList = downloadDataListDao.getDownloadListInfo(params);
		DownloadDataList ddl1 = new DownloadDataList();
		ddl1.setClientId(15);
		ddl1.setDataDesc(MQ);
		ddl1.setPeriodId(Integer.parseInt(period));
		ddl1.setLastUpdateTime(DateTimeUtils.getCurrentTime());
		ddl1.setDataCreated(DateTimeUtils.getCurrentTime());
		if(null==mDataList){
			ddl1.setCreateTime(DateTimeUtils.getCurrentTime());
			ddl1.setIsDelete(Constants.IS_DELETE_FALSE);
			ddl1.setFilePath(fileName);
			ddl1.setPeriodDesc(EN_MONTH[month - 1] + " " + year);
			downloadDataListDao.insert(ddl1);
		}else{ //更新最后修改时间
			ddl1.setDataId(mDataList.getDataId());
			downloadDataListDao.updateLastUpdateTime(ddl1);
		}
		log.info("createMainQuestionnaire end ");
	}

	@Override
	public void createSecondaryDisplayQuestionnaire(Date date) throws Exception {
		log.info("createSecondaryDisplayQuestionnaire start");
		String startDate = DateTimeUtils.formatTime(DateTimeUtils.getFirstDay(date), DateTimeUtils.yyyyMMdd);
		String endDate = DateTimeUtils.formatTime(DateTimeUtils.getLastDay(date), DateTimeUtils.yyyyMMdd);
//		Date date = DateTimeUtils.getCurrentDate();
		String period = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		String year = DateTimeUtils.formatTime(date, DateTimeUtils.yyyy);
		Integer month = Integer.parseInt(DateTimeUtils.formatTime(date, DateTimeUtils.MM));
		Integer clientId = 15;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		StringBuffer rootPath = new StringBuffer();
		rootPath.append(imgFolder).append(File.separator).append(clientId).append(File.separator).append("download").append(File.separator);
		
		//生成Secondary Display Questionnaire数据
		List<TreeMap<String, String>> sdqList = downloadDataListDao.getSurveySecondaryDisplayQuestionnaire(params);
		List<DataInfo> sdqDataList = DateVoSupport.getDataInfoList(sdqList);
		String sdqFileName = "Secondary_Display_Questionnaire_"+period+".xlsx";
		FileOutputStream out2 = new FileOutputStream(rootPath+sdqFileName);
		DateVoSupport.writer(sdqDataList,out2);
		
		params.put("periodId",period);
		params.put("clientId", clientId);
		String SDQ = "Secondary_Display_Questionnaire";
		params.put("dataDesc", SDQ);
		DownloadDataList sDataList = downloadDataListDao.getDownloadListInfo(params);
		DownloadDataList ddl2 = new DownloadDataList();
		ddl2.setClientId(15);
		ddl2.setDataDesc(SDQ);
		ddl2.setPeriodId(Integer.parseInt(period));
		ddl2.setLastUpdateTime(DateTimeUtils.getCurrentTime());
		ddl2.setDataCreated(DateTimeUtils.getCurrentTime());
		if(null==sDataList){
			ddl2.setCreateTime(DateTimeUtils.getCurrentTime());
			ddl2.setIsDelete(Constants.IS_DELETE_FALSE);
			ddl2.setFilePath(sdqFileName);
			ddl2.setPeriodDesc(EN_MONTH[month - 1] + " " + year);
			downloadDataListDao.insert(ddl2);
		}else{ //更新最后修改时间
			ddl2.setDataId(sDataList.getDataId());
			downloadDataListDao.updateLastUpdateTime(ddl2);
		}
		log.info("createSecondaryDisplayQuestionnaire end ");
		
	}

	@Override
	public void createSupplementaryQuestionnaire(Date date) throws Exception {
		log.info("createSupplementaryQuestionnaire start");
		String startDate = DateTimeUtils.formatTime(DateTimeUtils.getFirstDay(date), DateTimeUtils.yyyyMMdd);
		String endDate = DateTimeUtils.formatTime(DateTimeUtils.getLastDay(date), DateTimeUtils.yyyyMMdd);
//		Date date = DateTimeUtils.getCurrentDate();
		String period = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		String year = DateTimeUtils.formatTime(date, DateTimeUtils.yyyy);
		Integer month = Integer.parseInt(DateTimeUtils.formatTime(date, DateTimeUtils.MM));
		Integer clientId = 15;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		StringBuffer rootPath = new StringBuffer();
		rootPath.append(imgFolder).append(File.separator).append(clientId).append(File.separator).append("download").append(File.separator);
		
		params.put("periodId", period);
		params.put("clientId", clientId);
		//生成Supplementary Questionnaire数据
		List<TreeMap<String, String>> sqList = downloadDataListDao.getSupplementaryQuestionnaire(params);
		List<DataInfo> sqDataList = DateVoSupport.getDataInfoList(sqList);
		String sqFileName = "Supplementary_Questionnaire_"+period+".xlsx";
		FileOutputStream out3 = new FileOutputStream(rootPath+sqFileName);
		DateVoSupport.writer(sqDataList,out3);
		
		String SQ = "Supplementary_Questionnaire";
		params.put("dataDesc", SQ);
		DownloadDataList SQDataList = downloadDataListDao.getDownloadListInfo(params);
		DownloadDataList ddl3 = new DownloadDataList();
		ddl3.setClientId(15);
		ddl3.setDataDesc(SQ);
		ddl3.setPeriodId(Integer.parseInt(period));
		ddl3.setLastUpdateTime(DateTimeUtils.getCurrentTime());
		ddl3.setDataCreated(DateTimeUtils.getCurrentTime());
		if(null==SQDataList){
			ddl3.setCreateTime(DateTimeUtils.getCurrentTime());
			ddl3.setIsDelete(Constants.IS_DELETE_FALSE);
			ddl3.setFilePath(sqFileName);
			ddl3.setPeriodDesc(EN_MONTH[month - 1] + " " + year);
			downloadDataListDao.insert(ddl3);
		}else{ //更新最后修改时间
			ddl3.setDataId(SQDataList.getDataId());
			downloadDataListDao.updateLastUpdateTime(ddl3);
		}
		log.info("createSupplementaryQuestionnaire end ");
	}
	
	@Override
	public List<DownloadDataList> loadRawDataList(Map<String, Object> params) throws Exception {
		
		return downloadDataListDao.loadRawDataList(params);
	}

	@Override
	public String createTrend(Date date) throws Exception {
		
		log.info("createTrend start");
		String startDate =  DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
//		Date date = DateTimeUtils.getCurrentDate();
		String period = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
//		String year = DateTimeUtils.formatTime(date, DateTimeUtils.yyyy);
//		Integer month = Integer.parseInt(DateTimeUtils.formatTime(date, DateTimeUtils.MM));
		Integer clientId = 15;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		
		StringBuffer rootPath = new StringBuffer();
		rootPath.append(imgFolder).append(File.separator).append(clientId).append(File.separator).append("download").append(File.separator);
		
		//生成Trend数据
		log.info("startDate: "+startDate);
		List<TreeMap<String, String>> trendList = downloadDataListDao.getTrend(params);
		List<DataInfo> mainDataList = DateVoSupport.getDataInfoList(trendList);
		String fileName = "Trend_"+period+".xlsx";
		FileOutputStream out = new FileOutputStream(rootPath+fileName);
		DateVoSupport.writer(mainDataList,out);
		return fileName;
		
	}

	@Override
	public void updateLastUpdateTime(Date date,String fileName) throws Exception {
		String period = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		String year = DateTimeUtils.formatTime(date, DateTimeUtils.yyyy);
		Integer month = Integer.parseInt(DateTimeUtils.formatTime(date, DateTimeUtils.MM));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("periodId",period);
		//insert DB Trend
		String MQ = "Trend";
		params.put("dataDesc", MQ);
		params.put("clientId", 15);
		DownloadDataList mDataList = downloadDataListDao.getDownloadListInfo(params);
		DownloadDataList ddl1 = new DownloadDataList();
		ddl1.setClientId(15);
		ddl1.setDataDesc(MQ);
		ddl1.setPeriodId(Integer.parseInt(period));
		ddl1.setLastUpdateTime(DateTimeUtils.getCurrentTime());
		ddl1.setDataCreated(DateTimeUtils.getCurrentTime());
		if(null==mDataList){
			ddl1.setCreateTime(DateTimeUtils.getCurrentTime());
			ddl1.setIsDelete(Constants.IS_DELETE_FALSE);
			ddl1.setFilePath(fileName);
			ddl1.setPeriodDesc(EN_MONTH[month - 1] + " " + year);
			downloadDataListDao.insert(ddl1);
		}else{ //更新最后修改时间
			ddl1.setDataId(mDataList.getDataId());
			downloadDataListDao.updateLastUpdateTime(ddl1);
		}
		log.info("createTrend end ");
		
	}
	
}
