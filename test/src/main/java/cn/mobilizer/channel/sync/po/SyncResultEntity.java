package cn.mobilizer.channel.sync.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.mobilizer.channel.util.SyncDataHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SyncResultEntity implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1799439872213787749L;
	private int resultCode;
	private String resultMSG;
	private List<SyncTableDefined> tableDefinedList = new ArrayList<SyncTableDefined>();
	private List<SyncTableData> tableDataList = new ArrayList<SyncTableData>();
	private String tableDefinedStr;
	private String tableDataStr;
	private String currentTime;
	private String syncCode;//同步验证码
	
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

	@JsonIgnore
	public List<SyncTableDefined> getTableDefinedList() {
		return tableDefinedList;
	}
	public void setTableDefinedList(List<SyncTableDefined> tableDefinedList, String ztype) {
		tableDefinedStr = SyncDataHelper.serializeTableDefined(tableDefinedList, ztype);
		this.tableDefinedList = tableDefinedList;
	}

	@JsonIgnore
	public List<SyncTableData> getTableDataList() {
		return tableDataList;
	}
	public void setTableDataList(List<SyncTableData> tableDataList, String ztype) {
		tableDataStr = SyncDataHelper.serializeTableData(tableDataList, ztype);
		this.tableDataList = tableDataList;
	}
	
	public String getSyncCode(){
		return syncCode;
	}
	
	public void setSyncCode(String syncCode){
		this.syncCode = syncCode;
	}
	
}
