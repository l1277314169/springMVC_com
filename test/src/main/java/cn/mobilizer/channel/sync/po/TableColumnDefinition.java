package cn.mobilizer.channel.sync.po;

import java.io.Serializable;

public class TableColumnDefinition implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6474884637461632093L;
	private String tableColumnDefinitionID;
	private String tableDefinitionID;
	private String columnName;
	private String columnDataType;
	private String dataMaxLength;
	private String sequence;
	private String isPrimaryKey;
	private String version;
	public String getTableColumnDefinitionID() {
		return tableColumnDefinitionID;
	}
	public void setTableColumnDefinitionID(String tableColumnDefinitionID) {
		this.tableColumnDefinitionID = tableColumnDefinitionID;
	}
	public String getTableDefinitionID() {
		return tableDefinitionID;
	}
	public void setTableDefinitionID(String tableDefinitionID) {
		this.tableDefinitionID = tableDefinitionID;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnDataType() {
		return columnDataType;
	}
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	public String getDataMaxLength() {
		return dataMaxLength;
	}
	public void setDataMaxLength(String dataMaxLength) {
		this.dataMaxLength = dataMaxLength;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("tableColumnDefinitionID="+tableColumnDefinitionID);
		sb.append(",tableDefinitionID="+tableDefinitionID);
		sb.append(",columnName="+columnName);
		sb.append(",columnDataType="+columnDataType);
		sb.append(",dataMaxLength="+dataMaxLength);
		sb.append(",sequence="+sequence);
		sb.append(",isPrimaryKey="+isPrimaryKey);
		sb.append(",version="+version);
		return sb.toString();
	}

}
