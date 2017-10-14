package cn.mobilizer.channel.sync.po;

import java.io.Serializable;


public class SyncReceiveEntity implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2150908960935844954L;
	private int resultCode;
	private String resultMSG;
	private String tableDefinedStr;
	private String tableDataStr;
	private String currentTime;

	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMSG() {
		return resultMSG;
	}
	public void setResultMSG(String resultMSG) {
		this.resultMSG = resultMSG;
	}

	public String getTableDefinedStr() {
		return tableDefinedStr;
	}
	public void setTableDefinedStr(String tableDefinedStr) {
		this.tableDefinedStr = tableDefinedStr;
	}

	public String getTableDataStr() {
		return tableDataStr;
	}
	public void setTableDataStr(String tableValueStr) {
		this.tableDataStr = tableValueStr;
	}

	
}
