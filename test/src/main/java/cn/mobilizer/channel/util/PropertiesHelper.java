package cn.mobilizer.channel.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesHelper {
	private static final String PROPERTY_FILE = "const.properties";
	private static PropertiesHelper helper;
	private Properties properties;
	
	public static final String USERS_TASK_ENABLED = "usersTask.enabled";
	public static final String EHR_URL = "eHRurl";
	public static final String DATA_FILE_PATH = "dataFilePath";
	public static final String ONLY_ERROR_DATA = "onlyErrorData";
	public static final String CACHE_DB_NUMBER = "cacheDBNumber";
	public static final String FTP_FOLDER = "ftpFolder";
	public static final String IMG_FOLDER = "imgFolder";
	public static final String DOWNLOAD_IMGZIP_FOLDER = "downLoadImgZipFolder";
	
	public static final String ERROR_FOLDER = "errorFolder";
	public static final String JOB_ENABLED = "job.enabled";
	public static final String PICTUREMOVE_ENABLED = "pictureMove.enabled";
	public static final String KINDEDITOR_SAVEPATH = "kindEditor.savePath";
	public static final String KINDEDITOR_SAVEURL = "kindEditor.saveUrl";
	public static final String API_KEY = "apiKey";
	public static final String SECRET_KEY = "secretKey";
	public static final String KS_PASSWORD = "ksPassword";
	public static final String PLUSPUSH_PATH = "plusPushPath";
	public static final String PUSHMESSAGEJOB_ENABLED = "pushMessageJob.enabled";
	public static final String REMOVEIMAGEZIP_ENABLED = "removeImageZip.enabled";
	public static final String IMPORTIMAGEZIP_ENABLED = "importImageZip.enabled";
	public static final String ERRDATAEXCE_LPATH = "errDataExcelPath";
	public static final String EXPORTDATAEXCEL = "exportDataExcel";
	
	public static final String SCAN_IMAGE_SIZE = "scanImageSize";
	public static final String IMAGETASK_ENABLED = "imageTask.enabled";
	public static final String IMAGEMOVE_ENABLED = "imageMove.enabled";
	public static final String IMAGEMAGICKPATH = "imageMagickPath";
	public static final String FONT = "font";
	public static final String COLGATERAWDATA_ENABLED = "colgateRawData.enabled";
	
	public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
	public static final String EMAIL_ISOPEN = "EMAIL_ISOPEN";
	public static final String EMAIL_HOST = "EMAIL_HOST";
	public static final String EMAIL_PORT = "EMAIL_PORT";
	public static final String EMAIL_USERNAME = "EMAIL_USERNAME";
	public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
	public static final String CHANNELPLUS_DOMAIN = "channelPlus.domain";
	
	private PropertiesHelper(){
		InputStream is = PropertiesHelper.class.getClassLoader().getResourceAsStream(PROPERTY_FILE);
		if (is != null) {
			properties = new Properties();
			// 导入配置文件
			try {
				properties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static PropertiesHelper getInstance(){
		if(helper == null)
			helper = new PropertiesHelper();
		return helper;
	}
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}
	

}
