package cn.mobilizer.channel.sync.po;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SyncTableDefined implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4624762633655884306L;
	private String tableName;
	//[{"is_primary_key":false,"Name":"city_id","Type":1,"Length":NULL},{"is_primary_key":false,"Name":"district_no","Type":4,"Length":10},{"is_primary_key":false,"Name":"district_en","Type":4,"Length":50},{"is_primary_key":false,"Name":"is_delete","Type":1,"Length":NULL},{"is_primary_key":true,"Name":"district_id","Type":1,"Length":NULL},{"is_primary_key":false,"Name":"district","Type":4,"Length":50},{"is_primary_key":false,"Name":"create_time","Type":3,"Length":NULL}]
	private String columns;
	private String sqlTemplate;
	private Boolean uploadFlag;//不上传数据表标志
	private Boolean downloadFlag;//不下载数据标志
	
	public Boolean getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(Boolean downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	public Boolean getUploadFlag() {
		return uploadFlag;
	}
	public void setUploadFlag(Boolean uploadFlag) {
		this.uploadFlag = uploadFlag;
	}
	private List<TableColumnDefined> columnList;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getSqlTemplate() {
		return sqlTemplate;
	}
	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}
	//[{"IsPrimaryKey":false,"Name":"unit_name","Type":4,"Length":50}
	@JsonIgnore
	public List<TableColumnDefined> getColumnList(){
		columnList = new ArrayList<TableColumnDefined>();
		ObjectMapper mapper = new ObjectMapper();
		try {
//			List<TableColumnDefined> tlist = mapper.readValue(columns, new TypeReference<List<TableColumnDefined>>() {});
//			System.out.println(tlist.size());
			List<LinkedHashMap<String, Object>> mapList= mapper.readValue(columns,List.class);
			for (LinkedHashMap<String, Object> linkedHashMap : mapList) {
				TableColumnDefined tcd = new TableColumnDefined();
				tcd.setName(linkedHashMap.get("Name").toString());
				tcd.setType((Integer)linkedHashMap.get("Type"));
				tcd.setLength((Integer)linkedHashMap.get("Length"));
				tcd.setPrimaryKey((Boolean)linkedHashMap.get("IsPrimaryKey"));
				columnList.add(tcd);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return columnList;
	}
	public void setColumnList(List<TableColumnDefined> columnList) {
		this.columnList = columnList;
	}
	@JsonIgnore
	public String getColumnStr() {
		getColumnList();
		String[] names = new String[getColumnList().size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = columnList.get(i).getName();
		}
		return StringUtils.join(names,",");
	}
	@JsonIgnore
	public String getKeyColumn(){
		String keyColumnName = null;
		List<TableColumnDefined> list = getColumnList();
		for (TableColumnDefined tableColumnDefined : list) {
			if(tableColumnDefined.isPrimaryKey()){
				keyColumnName = tableColumnDefined.getName();
				break;
			}
		}
		return keyColumnName;
	}
	
}
