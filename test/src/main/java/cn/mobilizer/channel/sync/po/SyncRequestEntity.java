package cn.mobilizer.channel.sync.po;

import java.io.Serializable;

public class SyncRequestEntity implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1972564633837159462L;
	public static final String ZTYPE_Z = "Z";//Android
	public static final String ZTYPE_GZ = "GZ";//ios
	public static final String ZTYPE_N = "N";
	//int clientId, int clientUserId, String lastSyncTime, String updata
	private int clientId;
	private int clientUserId;
	private String lastSyncTime;
	private int dbVersion;
	private int optionCode;//提交请求名称代码定义在constants文件
	private String dataTag;//数据包标签，用于识别数据包，做重复提交的处理
	private String uploadDataStr;//压缩后的数据流
	private String appInfo;
	private String ztype;//压缩类型
	
	public String getZtype() {
		return ztype;
	}
	public void setZtype(String ztype) {
		this.ztype = ztype;
	}
	public String getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(int clientUserId) {
		this.clientUserId = clientUserId;
	}
	public String getLastSyncTime() {
		return lastSyncTime;
	}
	public void setLastSyncTime(String lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}
	public String getUploadDataStr() {
		return uploadDataStr;
	}
	public void setUploadDataStr(String uploadDataStr) {
		this.uploadDataStr = uploadDataStr;
	}
	public String getDataTag() {
		return dataTag;
	}
	public void setDataTag(String dataTag) {
		this.dataTag = dataTag;
	}
	public int getDbVersion() {
		return dbVersion;
	}
	public void setDbVersion(int dbVersion) {
		this.dbVersion = dbVersion;
	}
	public int getOptionCode() {
		return optionCode;
	}
	public void setOptionCode(int optionCode) {
		this.optionCode = optionCode;
	}
	
}
