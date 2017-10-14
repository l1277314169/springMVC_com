package cn.mobilizer.channel.sync.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.log.po.DataErrorLog;
import cn.mobilizer.channel.log.po.UserActivityLog;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.log.service.DataErrorLogService;
import cn.mobilizer.channel.sync.exception.SyncDataException;
import cn.mobilizer.channel.sync.po.Constants;
import cn.mobilizer.channel.sync.po.SyncRequestEntity;
import cn.mobilizer.channel.sync.po.SyncResultEntity;
import cn.mobilizer.channel.sync.po.SyncTableData;
import cn.mobilizer.channel.sync.po.SyncTableDefined;
import cn.mobilizer.channel.sync.service.SyncService;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.PropertiesHelper;
import cn.mobilizer.channel.util.SyncDataHelper;
import cn.mobilizer.channel.util.ZipCompress;
@RestController
@RequestMapping(value = "/api/sync")
public class SyncDataController {
	private static final Log LOG = LogFactory.getLog(SyncDataController.class);
	@Autowired
	private SyncService syncService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private DataErrorLogService dataErrorLogService;
	
	private Map<Integer, List<SyncTableDefined>> dbmap = new HashMap<Integer, List<SyncTableDefined>>();
	
	//数据同步接口
	@RequestMapping(value = "/syncdata", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public SyncResultEntity syncData(SyncRequestEntity sre){
		LOG.info("userId:"+sre.getClientUserId()+"同步开始。");
		SyncResultEntity srentity = new SyncResultEntity();
		int optionCode = sre.getOptionCode();
		boolean hasUploadData = false;//是否包含上传数据
		boolean needTableDefined = false;//是否需要下发表定义
		boolean needTableData = false;//是否需要下发数据
		switch (optionCode) {
		case Constants.OPTION_CODE_GET://只获取差异数据
			needTableData = true;
			break;
		case Constants.OPTION_CODE_UPLOAD ://只上传数据
			hasUploadData = true;
			break;
		case Constants.OPTION_CODE_UPLOAD_GET://上传数据并下载差异数据
			needTableData = true;
			hasUploadData = true;
			break;

		default:
			break;
		}
		UserActivityLog log = new UserActivityLog();
		log.setLogContent(optionCode+"-"+ sre.getDataTag()+"-"+sre.getDbVersion()+"-"+sre.getLastSyncTime());
		log.setClientId(sre.getClientId());
		log.setClientUserId(sre.getClientUserId());
		log.setLogType(UserActivityLog.LOG_TYPE_SYNC);
		if(!StringUtils.isEmpty(sre.getAppInfo())){
			log.setLogContent(log.getLogContent()+"-"+sre.getAppInfo());
			String[] arg = sre.getAppInfo().split("#");
			if(arg != null && arg.length > 0)
				log.setAppVersion(arg[0]);
			if(arg != null && arg.length > 1){
				log.setPlatform(arg[1]);
			}
		}
		//初始化标志为失败，如果成功会修改为成功
		log.setResponseCode("Failed");
		//记录请求信息，执行过程做update
		activityLogService.insert(log);

		//1980初始化请求，需要获取表定义
		if(Constants.DEFAULT_SYNC_TIME.equals(sre.getLastSyncTime())){
			needTableDefined = true;
		}
		
		//处理上传数据，数据结构同服务器下载数据
		if(hasUploadData){
			boolean importSucess = true;
			String msg = "导入成功！";
			String errorLog = "";
			if(!StringUtils.isEmpty(sre.getUploadDataStr())){
				try {
					List<SyncTableData> syncTabledDatalist = SyncDataHelper.parseTableData(sre.getUploadDataStr(), sre.getZtype());
					syncService.handleTableData(syncTabledDatalist);
				} catch (JsonParseException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "数据解析出错！";
					errorLog = e1.getMessage();
				} catch (JsonMappingException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "数据解析出错！";
					errorLog = e1.getMessage();
				} catch (IOException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "读取数据失败！";
				} catch (SyncDataException e) {
					e.printStackTrace();
					importSucess = false;
					msg = e.getMessage();
					errorLog = e.getMessage();
				} catch (Exception e) {
					e.printStackTrace();
					importSucess = false;
					msg = "数据解析或者同步失败";
					errorLog = e.getMessage();
				}
				
				//如果解析出错，就写入备份数据
				String rootPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.DATA_FILE_PATH);
				String Path = rootPath + "/" + sre.getClientId()+"/"+sre.getClientUserId();
				String erroOnly = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ONLY_ERROR_DATA);
				if(!importSucess || !Boolean.parseBoolean(erroOnly)){
					try {
						File file = new File(Path);
						if(!file.exists()){
							file.mkdirs();
						}
						File dataFile = new File(Path +  "/" +sre.getDataTag());
//						FileUtils.write(dataFile, ZipCompress.decompress(sre.getUploadDataStr()));
						FileUtils.write(dataFile, SyncDataHelper.decompress(sre.getUploadDataStr(), sre.getZtype()));
					} catch (IOException e) {
						LOG.error("文件备份出错"+e);
					}
				}
				
			}
			
			if(!importSucess){
				List<DataErrorLog> elist = dataErrorLogService.findByClientUserIdAndFileName(sre.getClientUserId(), sre.getDataTag());
				if(elist != null && elist.size() > 0){
					DataErrorLog elog = elist.get(0);
					//重复三次视为数据文件数据异常，记录到数据库，文件标记为处理完成
					if(elog.getRepeatTimes() >= 3){
						log.setLogContent(log.getLogContent() + ",数据处理失败3次，忽略此数据包。");
						activityLogService.update(log);
					}else{
						//repeatTime +1
						int repeatTime = elog.getRepeatTimes() + 1;
						elog.setRepeatTimes(repeatTime);
						dataErrorLogService.update(elog);
						//小于3次，返回失败
						log.setResponseCode(log.getResponseCode() + "-" + msg);
						activityLogService.update(log);
						srentity.setResultCode(-1);
						srentity.setResultMSG(msg);
						LOG.info("userId:"+sre.getClientUserId()+"同步结束-数据处理失败。");
						return srentity;
					}
				}else{
					//没有记录，新增错误记录
					DataErrorLog elog = new DataErrorLog();
					elog.setClientUserId(sre.getClientUserId());
					elog.setFileName(sre.getDataTag());
					elog.setRepeatTimes(1);
					elog.setErrorLog("["+msg+"]"+errorLog);
					dataErrorLogService.insert(elog);
					//返回失败
					log.setResponseCode(log.getResponseCode() + "-" + msg);
					activityLogService.update(log);
					srentity.setResultCode(-1);
					srentity.setResultMSG(msg);
					LOG.info("userId:"+sre.getClientUserId()+"同步结束-数据处理失败。");
					return srentity;
				}
			}
		}
		log.setLogContent(log.getLogContent()+"-Import Data Success");
		activityLogService.update(log);

		//获取数据库初始化定义，写入到手机的table_defined表中
		//服务器需要做缓存，以减小数据库读写压力
		int maxdbVersion = Integer.parseInt(PropertiesHelper.getInstance().getProperty(PropertiesHelper.CACHE_DB_NUMBER));
		List<SyncTableDefined> syncTableDefinedlist = null;
		if(sre.getDbVersion() <= maxdbVersion){
			syncTableDefinedlist = dbmap.get(sre.getDbVersion());
			if(syncTableDefinedlist == null){
				List<SyncTableDefined> nlist = syncService.getTableDefined(sre.getClientId(), sre.getClientUserId(), sre.getDbVersion());
				dbmap.put(sre.getDbVersion(), nlist);
				syncTableDefinedlist = nlist;
			}
		}
		if(syncTableDefinedlist == null)
			syncTableDefinedlist = syncService.getTableDefined(sre.getClientId(), sre.getClientUserId(), sre.getDbVersion());	
		
		if(needTableDefined){
			srentity.setTableDefinedList(syncTableDefinedlist,sre.getZtype());
		}
		if(needTableData){
			String currentTime = DateTimeUtils.getFormatTime(DateTimeUtils.yyyyMMddHHmmss);
			srentity.setCurrentTime(currentTime);
			List<SyncTableData> stds = syncService.getTableData(syncTableDefinedlist, sre.getClientId(), sre.getClientUserId(), sre.getLastSyncTime(), currentTime);
			srentity.setTableDataList(stds,sre.getZtype());
		}
		
		
		log.setLogContent(log.getLogContent()+"-Export Data Success");
		log.setResponseCode("Success");
		activityLogService.update(log);
		srentity.setResultCode(0);
		srentity.setResultMSG("Success");
		LOG.info("userId:"+sre.getClientUserId()+"同步结束-成功。");
		return srentity;
	}
	
	@RequestMapping(value = "/syncalldata", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public void syncAllData(){
		syncService.getAllTableData(2, 18, "1980-01-01", DateTimeUtils.getFormatTime(DateTimeUtils.yyyyMMddHHmmss));
	}
	
	@RequestMapping(value = "/synccheckdata", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public SyncResultEntity syncCheckData(SyncRequestEntity sre){
		
		return null;
	}
	
	//数据同步接口
	@RequestMapping(value = "/syncdata/{syncCode}", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public SyncResultEntity newSyncData(@PathVariable("syncCode")String syncCode, SyncRequestEntity sre){
		LOG.info("userId:"+sre.getClientUserId()+"接口同步开始。syncCode:"+syncCode);
		SyncResultEntity srentity = new SyncResultEntity();
		srentity.setSyncCode(syncCode);
		int optionCode = sre.getOptionCode();
		boolean hasUploadData = false;//是否包含上传数据
		boolean needTableDefined = false;//是否需要下发表定义
		boolean needTableData = false;//是否需要下发数据
		switch (optionCode) {
		case Constants.OPTION_CODE_GET://只获取差异数据
			needTableData = true;
			break;
		case Constants.OPTION_CODE_UPLOAD ://只上传数据
			hasUploadData = true;
			break;
		case Constants.OPTION_CODE_UPLOAD_GET://上传数据并下载差异数据
			needTableData = true;
			hasUploadData = true;
			break;

		default:
			break;
		}
		UserActivityLog log = new UserActivityLog();
		log.setLogContent(optionCode+"-"+ sre.getDataTag()+"-"+sre.getDbVersion()+"-"+sre.getLastSyncTime());
		log.setClientId(sre.getClientId());
		log.setClientUserId(sre.getClientUserId());
		log.setLogType(UserActivityLog.LOG_TYPE_SYNC);
		if(!StringUtils.isEmpty(sre.getAppInfo())){
			log.setLogContent(log.getLogContent()+"-"+sre.getAppInfo());
			String[] arg = sre.getAppInfo().split("#");
			if(arg != null && arg.length > 0)
				log.setAppVersion(arg[0]);
			if(arg != null && arg.length > 1){
				log.setPlatform(arg[1]);
			}
		}
		//初始化标志为失败，如果成功会修改为成功
		log.setResponseCode("Failed");
		//记录请求信息，执行过程做update
		activityLogService.insert(log);

		//1980初始化请求，需要获取表定义
		if(Constants.DEFAULT_SYNC_TIME.equals(sre.getLastSyncTime())){
			needTableDefined = true;
		}
		
		//处理上传数据，数据结构同服务器下载数据
		if(hasUploadData){
			boolean importSucess = true;
			String msg = "导入成功！";
			String errorLog = "";
			if(!StringUtils.isEmpty(sre.getUploadDataStr())){
				try {
					List<SyncTableData> syncTabledDatalist = SyncDataHelper.parseTableData(sre.getUploadDataStr(), sre.getZtype());
					syncService.handleTableData(syncTabledDatalist);
				} catch (JsonParseException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "数据解析出错！";
					errorLog = e1.getMessage();
				} catch (JsonMappingException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "数据解析出错！";
					errorLog = e1.getMessage();
				} catch (IOException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "读取数据失败！";
				} catch (SyncDataException e) {
					e.printStackTrace();
					importSucess = false;
					msg = e.getMessage();
					errorLog = e.getMessage();
				} catch (Exception e) {
					e.printStackTrace();
					importSucess = false;
					msg = "数据解析或者同步失败";
					errorLog = e.getMessage();
				}
				
				//如果解析出错，就写入备份数据
				String rootPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.DATA_FILE_PATH);
				String Path = rootPath + "/" + sre.getClientId()+"/"+sre.getClientUserId();
				String erroOnly = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ONLY_ERROR_DATA);
				if(!importSucess || !Boolean.parseBoolean(erroOnly)){
					try {
						File file = new File(Path);
						if(!file.exists()){
							file.mkdirs();
						}
						File dataFile = new File(Path +  "/" +sre.getDataTag());
//							FileUtils.write(dataFile, ZipCompress.decompress(sre.getUploadDataStr()));
						FileUtils.write(dataFile, SyncDataHelper.decompress(sre.getUploadDataStr(), sre.getZtype()));
					} catch (IOException e) {
						LOG.error("文件备份出错"+e);
					}
				}
				
			}
			
			if(!importSucess){
				List<DataErrorLog> elist = dataErrorLogService.findByClientUserIdAndFileName(sre.getClientUserId(), sre.getDataTag());
				if(elist != null && elist.size() > 0){
					DataErrorLog elog = elist.get(0);
					//重复三次视为数据文件数据异常，记录到数据库，文件标记为处理完成
					if(elog.getRepeatTimes() >= 3){
						log.setLogContent(log.getLogContent() + ",数据处理失败3次，忽略此数据包。");
						activityLogService.update(log);
					}else{
						//repeatTime +1
						int repeatTime = elog.getRepeatTimes() + 1;
						elog.setRepeatTimes(repeatTime);
						dataErrorLogService.update(elog);
						//小于3次，返回失败
						log.setResponseCode(log.getResponseCode() + "-" + msg);
						activityLogService.update(log);
						srentity.setResultCode(-1);
						srentity.setResultMSG(msg);
						LOG.info("userId:"+sre.getClientUserId()+"接口同步结束-数据处理失败。");
						return srentity;
					}
				}else{
					//没有记录，新增错误记录
					DataErrorLog elog = new DataErrorLog();
					elog.setClientUserId(sre.getClientUserId());
					elog.setFileName(sre.getDataTag());
					elog.setRepeatTimes(1);
					elog.setErrorLog("["+msg+"]"+errorLog);
					dataErrorLogService.insert(elog);
					//返回失败
					log.setResponseCode(log.getResponseCode() + "-" + msg);
					activityLogService.update(log);
					srentity.setResultCode(-1);
					srentity.setResultMSG(msg);
					LOG.info("userId:"+sre.getClientUserId()+"接口同步结束-数据处理失败。");
					return srentity;
				}
			}
		}
		log.setLogContent(log.getLogContent()+"-Import Data Success");
		activityLogService.update(log);

		//获取数据库初始化定义，写入到手机的table_defined表中
		//服务器需要做缓存，以减小数据库读写压力
		int maxdbVersion = Integer.parseInt(PropertiesHelper.getInstance().getProperty(PropertiesHelper.CACHE_DB_NUMBER));
		List<SyncTableDefined> syncTableDefinedlist = null;
		if(sre.getDbVersion() <= maxdbVersion){
			syncTableDefinedlist = dbmap.get(sre.getDbVersion());
			if(syncTableDefinedlist == null){
				List<SyncTableDefined> nlist = syncService.getTableDefined(sre.getClientId(), sre.getClientUserId(), sre.getDbVersion());
				dbmap.put(sre.getDbVersion(), nlist);
				syncTableDefinedlist = nlist;
			}
		}
		if(syncTableDefinedlist == null)
			syncTableDefinedlist = syncService.getTableDefined(sre.getClientId(), sre.getClientUserId(), sre.getDbVersion());	
		
		if(needTableDefined){
			srentity.setTableDefinedList(syncTableDefinedlist,sre.getZtype());
		}
		if(needTableData){
			String currentTime = DateTimeUtils.getFormatTime(DateTimeUtils.yyyyMMddHHmmss);
			srentity.setCurrentTime(currentTime);
			List<SyncTableData> stds = syncService.getTableData(syncTableDefinedlist, sre.getClientId(), sre.getClientUserId(), sre.getLastSyncTime(), currentTime);
			srentity.setTableDataList(stds,sre.getZtype());
		}
		
		
		log.setLogContent(log.getLogContent()+"-Export Data Success");
		log.setResponseCode("Success");
		activityLogService.update(log);
		srentity.setResultCode(200);//新接口中状态码改为200
		srentity.setResultMSG("Success");
		LOG.info("userId:"+sre.getClientUserId()+"接口同步结束-成功。");
		return srentity;
	}
	
	//数据同步接口
	@RequestMapping(value = "/syncupdata/{syncCode}", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public SyncResultEntity syncupdata(@PathVariable("syncCode")String syncCode, SyncRequestEntity sre){
		LOG.info("userId:"+sre.getClientUserId()+"接口同步开始。syncCode:"+syncCode);
		SyncResultEntity srentity = new SyncResultEntity();
		srentity.setSyncCode(syncCode);
//		int optionCode = sre.getOptionCode();
		int optionCode = Constants.OPTION_CODE_UPLOAD ;
		boolean hasUploadData = true;//是否包含上传数据
		
		UserActivityLog log = new UserActivityLog();
		log.setLogContent(optionCode+"-"+ sre.getDataTag()+"-"+sre.getDbVersion()+"-"+sre.getLastSyncTime());
		log.setClientId(sre.getClientId());
		log.setClientUserId(sre.getClientUserId());
		log.setLogType(UserActivityLog.LOG_TYPE_SYNC);
		if(!StringUtils.isEmpty(sre.getAppInfo())){
			log.setLogContent(log.getLogContent()+"-"+sre.getAppInfo());
			String[] arg = sre.getAppInfo().split("#");
			if(arg != null && arg.length > 0)
				log.setAppVersion(arg[0]);
			if(arg != null && arg.length > 1){
				log.setPlatform(arg[1]);
			}
		}
		//初始化标志为失败，如果成功会修改为成功
		log.setResponseCode("Failed");
		//记录请求信息，执行过程做update
		activityLogService.insert(log);

		//1980初始化请求，需要获取表定义
//		if(Constants.DEFAULT_SYNC_TIME.equals(sre.getLastSyncTime())){
//			needTableDefined = true;
//		}
		
		//处理上传数据，数据结构同服务器下载数据
		if(hasUploadData){
			boolean importSucess = true;
			String msg = "导入成功！";
			String errorLog = "";
			if(!StringUtils.isEmpty(sre.getUploadDataStr())){
				try {
					List<SyncTableData> syncTabledDatalist = SyncDataHelper.parseTableData(sre.getUploadDataStr(), sre.getZtype());
					syncService.handleTableData(syncTabledDatalist);
				} catch (JsonParseException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "数据解析出错！";
					errorLog = e1.getMessage();
				} catch (JsonMappingException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "数据解析出错！";
					errorLog = e1.getMessage();
				} catch (IOException e1) {
					e1.printStackTrace();
					importSucess = false;
					msg = "读取数据失败！";
				} catch (SyncDataException e) {
					e.printStackTrace();
					importSucess = false;
					msg = e.getMessage();
					errorLog = e.getMessage();
				} catch (Exception e) {
					e.printStackTrace();
					importSucess = false;
					msg = "数据解析或者同步失败";
					errorLog = e.getMessage();
				}
				
				//如果解析出错，就写入备份数据
				String rootPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.DATA_FILE_PATH);
				String Path = rootPath + "/" + sre.getClientId()+"/"+sre.getClientUserId();
				String erroOnly = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ONLY_ERROR_DATA);
				if(!importSucess || !Boolean.parseBoolean(erroOnly)){
					try {
						File file = new File(Path);
						if(!file.exists()){
							file.mkdirs();
						}
						File dataFile = new File(Path +  "/" +sre.getDataTag());
//								FileUtils.write(dataFile, ZipCompress.decompress(sre.getUploadDataStr()));
						FileUtils.write(dataFile, SyncDataHelper.decompress(sre.getUploadDataStr(), sre.getZtype()));
					} catch (IOException e) {
						LOG.error("文件备份出错"+e);
					}
				}
				
			}
			
			if(!importSucess){
				List<DataErrorLog> elist = dataErrorLogService.findByClientUserIdAndFileName(sre.getClientUserId(), sre.getDataTag());
				if(elist != null && elist.size() > 0){
					DataErrorLog elog = elist.get(0);
					//重复三次视为数据文件数据异常，记录到数据库，文件标记为处理完成
					if(elog.getRepeatTimes() >= 3){
						log.setLogContent(log.getLogContent() + ",数据处理失败3次，忽略此数据包。");
						activityLogService.update(log);
					}else{
						//repeatTime +1
						int repeatTime = elog.getRepeatTimes() + 1;
						elog.setRepeatTimes(repeatTime);
						dataErrorLogService.update(elog);
						//小于3次，返回失败
						log.setResponseCode(log.getResponseCode() + "-" + msg);
						activityLogService.update(log);
						srentity.setResultCode(-1);
						srentity.setResultMSG(msg);
						LOG.info("userId:"+sre.getClientUserId()+"接口同步结束-数据处理失败。");
						return srentity;
					}
				}else{
					//没有记录，新增错误记录
					DataErrorLog elog = new DataErrorLog();
					elog.setClientUserId(sre.getClientUserId());
					elog.setFileName(sre.getDataTag());
					elog.setRepeatTimes(1);
					elog.setErrorLog("["+msg+"]"+errorLog);
					dataErrorLogService.insert(elog);
					//返回失败
					log.setResponseCode(log.getResponseCode() + "-" + msg);
					activityLogService.update(log);
					srentity.setResultCode(-1);
					srentity.setResultMSG(msg);
					LOG.info("userId:"+sre.getClientUserId()+"接口同步结束-数据处理失败。");
					return srentity;
				}
			}
		}
		log.setLogContent(log.getLogContent()+"-Import Data Success");
		activityLogService.update(log);

		log.setLogContent(log.getLogContent()+"-Export Data Success");
		log.setResponseCode("Success");
		activityLogService.update(log);
		srentity.setResultCode(200);//新接口中状态码改为200
		srentity.setResultMSG("Success");
		LOG.info("userId:"+sre.getClientUserId()+"接口同步结束-成功。");
		return srentity;
	}
}
