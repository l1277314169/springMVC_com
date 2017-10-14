package cn.mobilizer.channel.sync.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SyncTableData implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3641672110817568349L;
	private String tableName;
	private String rowNames;//定义返回字段
	private String keyRowName;
	private List<String> dataRows = new ArrayList<String>();
	public String getRowNames() {
		return rowNames;
	}
	public void setRowNames(String rowNames) {
		this.rowNames = rowNames;
	}
	public List<String> getDataRows() {
		return dataRows;
	}
	public void setDataRows(List<String> dataRows) {
		this.dataRows = dataRows;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getKeyRowName() {
		return keyRowName;
	}
	public void setKeyRowName(String keyRowName) {
		this.keyRowName = keyRowName;
	}

}
