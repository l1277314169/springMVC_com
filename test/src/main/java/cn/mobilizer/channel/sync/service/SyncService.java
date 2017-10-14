package cn.mobilizer.channel.sync.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.service.impl.SkuGroupMappigServiceImpl;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.sync.exception.SyncDataException;
import cn.mobilizer.channel.sync.po.Constants;
import cn.mobilizer.channel.sync.po.SyncTableData;
import cn.mobilizer.channel.sync.po.SyncTableDefined;
import cn.mobilizer.channel.sync.po.TableColumnDefined;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.SyncDataHelper;

@Service
public class SyncService {
	private static final Log LOG = LogFactory.getLog(SyncService.class);
	@Autowired
	private DataSource dataSourceBase;
	//获取表结构定义
	@SuppressWarnings("unchecked")
	public List<SyncTableDefined> getTableDefined(int clientId, int clientUserId, int DBVersion) {
		GetTableDefinedProcedure sp = new GetTableDefinedProcedure(dataSourceBase);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_client_id", clientId);
		map.put("arg_user_id", clientUserId);
		map.put("arg_db_version", DBVersion);
		Map<String, Object> results = sp.execute(map);
		List<SyncTableDefined> tdlist = (List<SyncTableDefined>)results.get("tableDefined"); 
		return tdlist;
	}
	//通过存储过程获取每个表中的内容
	public List<SyncTableData> getTableData(List<SyncTableDefined> tdlist,int clientId, int clientUserID, String startTime, String endTime) throws BusinessException{
		List<SyncTableData> stdList = new ArrayList<SyncTableData>();
		try {
			Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String,Object>>>();
			//先取数据，再解析数据
			long QstartTime = System.currentTimeMillis();
			for (SyncTableDefined tableDefined : tdlist) {
				if(!tableDefined.getDownloadFlag())
					continue;
				List<Map<String, Object>> result = callTableValue(tableDefined.getSqlTemplate(),clientId,clientUserID,startTime,endTime);
				resultMap.put(tableDefined.getTableName(), result);
			}
			long QendTime = System.currentTimeMillis();
			LOG.info("userId:"+ clientUserID +",获取表数据 Query Time = " + (QendTime - QstartTime));
			long DstartTime = System.currentTimeMillis();
			for (SyncTableDefined stde : tdlist) {
				List<String> datas = new ArrayList<String>();
				List<Map<String, Object>> result = resultMap.get(stde.getTableName());
				if(result != null){
					SyncTableData std = new SyncTableData();
					std.setTableName(stde.getTableName());
					std.setRowNames(stde.getColumnStr());
					std.setKeyRowName(stde.getKeyColumn());
					for (Map<String, Object> map : result) {
						List<TableColumnDefined> tcd = stde.getColumnList();
						Object[] valuesArray = new Object[tcd.size()];
						int i = 0 ;
						for (TableColumnDefined tableColumnDefined : tcd) {
							// 字符型转为base64的字符串,防止特殊字符影响内容结构,字符串转换为[base64字符串],日期类型加'2014-09-22 23:14:09.0'
							//valuesArray[i] = map.get(tableColumnDefined.getName());
							if (map.get(tableColumnDefined.getName()) != null && !StringUtils.isEmpty(map.get(tableColumnDefined.getName()).toString()) && "null" != map.get(tableColumnDefined.getName())) {
								if (Constants.COLUMN_TYPE_CHAR == tableColumnDefined.getType() || Constants.COLUMN_TYPE_GUID == tableColumnDefined.getType()) {
									valuesArray[i] = SyncDataHelper.encodeStringValue(map.get(tableColumnDefined.getName()).toString());
								}else if(Constants.COLUMN_TYPE_DATE == tableColumnDefined.getType() ){
									valuesArray[i] = "'" + map.get(tableColumnDefined.getName()) + "'";
								}else
									valuesArray[i] = map.get(tableColumnDefined.getName());
							}
							i++;
						}
						datas.add(StringUtils.join(valuesArray,","));
					}
					if(datas.size() > 0){
						std.setDataRows(datas);
				        stdList.add(std);
					}
				}
			}
			long DendTime = System.currentTimeMillis();
//			LOG.info("Parse Time = " + (DendTime - DstartTime));
		} catch (Exception e) {
			LOG.error("method getTableData error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYNC_TABLEDATA_SEND);
		}
		return stdList;
	}
	
	public void getAllTableData(int clientId, int clientUserID, String startTime, String endTime){
		long start = System.currentTimeMillis();
		List<Map<String, Object>> result = callTableValue("sp_get_data()",clientId,clientUserID,startTime,endTime);
		long end = System.currentTimeMillis();
		LOG.info("userId:"+ clientUserID +",getAllTableData Query Time = " + (end - start));
	}
	
	//单次调用存储过程
	private List<Map<String, Object>> callTableValue(String callName,int clientId, int clientUserID, String startTime, String endTime){
		String paras = callName.substring(callName.indexOf('(')+1 , callName.indexOf(')'));
		String proName = callName.substring(0, callName.indexOf('('));
		String[] parass = paras.split(",");
		String[] calls = new String[paras.split(",").length + 1] ;
		calls[0] = proName;
		for (int i = 0; i < parass.length; i++) {
			calls[i+1] = parass[i];
		}
		GetTableValuesProcedure sp = new GetTableValuesProcedure(dataSourceBase, calls);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < parass.length; i++) {
			if("$ClientID".equals(parass[i])){
				map.put("arg_client_id", clientId);
			}else if("$UserID".equals(parass[i])){
				map.put("arg_user_id", clientUserID);
			}else if("$StartTime".equals(parass[i])){
				map.put("arg_start_time",startTime);
			}else if("$EndTime".equals(parass[i])){
				map.put("arg_end_time", endTime);
			}
		}
		Map<String, Object> results = sp.execute(map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>>  datas = (List<Map<String, Object>>) results.get("tableValues");//取表数据
		return datas;
	}
	
	//sp_get_area_user_mapping_data($ClientID,$UserID,$StartTime,$EndTime);
	private class GetTableValuesProcedure extends StoredProcedure{
		public GetTableValuesProcedure(DataSource dataSource, String[] callName){
			super(dataSource,callName[0]);
			for (int i = 1; i < callName.length; i++) {
				if("$ClientID".equals(callName[i])){
					declareParameter(new SqlParameter("arg_client_id", Types.INTEGER));
				}else if("$UserID".equals(callName[i])){
					declareParameter(new SqlParameter("arg_user_id", Types.INTEGER));
				}else if("$StartTime".equals(callName[i])){
					declareParameter(new SqlParameter("arg_start_time", Types.VARCHAR));
				}else if("$EndTime".equals(callName[i])){
					declareParameter(new SqlParameter("arg_end_time", Types.VARCHAR));
				}
			}
			declareParameter(new SqlReturnResultSet("tableValues", new TableDataRowMapper()));
		}
	}

	private class GetTableDefinedProcedure extends StoredProcedure {
		public GetTableDefinedProcedure(DataSource dataSource) {
			super(dataSource, "sp_get_table_defined");//获取表定义存储过程
			declareParameter(new SqlReturnResultSet("tableDefined", new TableDefinedRowMapper()));
			declareParameter(new SqlParameter("arg_client_id", Types.INTEGER));
			declareParameter(new SqlParameter("arg_user_id", Types.INTEGER));
			declareParameter(new SqlParameter("arg_db_version", Types.INTEGER));
		}
	}
	
	private class TableDefinedRowMapper implements RowMapper<Object> {
		public SyncTableDefined mapRow(ResultSet rs, int rowNum) throws SQLException {
			SyncTableDefined td = new SyncTableDefined();
			td.setTableName(rs.getString("table_name"));
			td.setColumns(rs.getString("table_columns"));
			td.setSqlTemplate(rs.getString("sql_template"));
			td.setUploadFlag(rs.getBoolean("upload_flag"));
			td.setDownloadFlag(rs.getBoolean("download_flag"));
			return td;
		}
	}

	private class TableDataRowMapper implements RowMapper<Object> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, Object> map = new HashMap<String, Object>();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String key = rsmd.getColumnName(i);
				Object obj = rs.getObject(key);
				map.put(key, obj);
			}
			return map;
		}
	}

	//jdbc调用SQL语句
	//select * from province where last_update_time between $starttime and $endtime
	public List<SyncTableData> getTableData(List<SyncTableDefined> tdlist, String startTime, String endTime){
		List<SyncTableData> stdList = new ArrayList<SyncTableData>();
		JdbcTemplate jdbcTemp = new JdbcTemplate(dataSourceBase); 
		for (SyncTableDefined tableDefined : tdlist) {
			SyncTableData std = new SyncTableData();
			std.setTableName(tableDefined.getTableName());
			List<Map<String, Object>> list = jdbcTemp.queryForList(replaceSqlTemp(tableDefined.getSqlTemplate()));
			Map<String, Object> rowsdefined = list.get(0);
        	std.setRowNames(StringUtils.join(rowsdefined.keySet().toArray(),","));
        	List<String> datas = new ArrayList<String>();
	        for (Map<String, Object> map : list) {
	        	Set<String> kes = map.keySet();
	        	Object[] dataArray = new Object[kes.size()];
	        	int i = 0;
	        	for (String string : kes) {
	        		dataArray[i] = map.get(string);
	        		i++;
				}
	        	datas.add(StringUtils.join(dataArray,","));
			}
	        std.setDataRows(datas);
	        stdList.add(std);
		}
		return stdList;
        
	}
	
	public int excuteSQL(String sql){
		JdbcTemplate jdbcTemp = new JdbcTemplate(dataSourceBase); 
		return jdbcTemp.update(sql);
	}
	
	private String replaceSqlTemp(String sqlTemp){
		return sqlTemp.replace("$starttime", "'"+Constants.DEFAULT_SYNC_TIME+"'").replace("$endtime", "'"+DateTimeUtils.getFormatTime(DateTimeUtils.yyyyMMddHHmmss)+"'");
	}

	public boolean handleTableData(List<SyncTableData> stdlist) throws SyncDataException{
		for (SyncTableData syncTableData : stdlist) {
			insertRecords(syncTableData);
		}
		return true;
	}
	
	public void insertRecords(SyncTableData syncTableData) throws SyncDataException{
		List<String> records = syncTableData.getDataRows();
		for (String string : records) {
//			String[] itag = string.split(",");
			//insert
//			if(1 == Integer.parseInt(itag[itag.length - 1])){
//				try {
//					excuteSQL(createInsertSQL(syncTableData, string));
//				} catch (Exception e) {
//					excuteSQL(createUpdateSQL(syncTableData, string));
//				}
//			}else{//update
//				excuteSQL(createUpdateSQL(syncTableData, string));
//			}
			
//			try {
//				int i = excuteSQL(createUpdateSQL(syncTableData, string));
//				if(i <= 0){
//					excuteSQL(createInsertSQL(syncTableData, string));
//				}
//			} catch (Exception e) {
//				throw new SyncDataException("数据导入失败！");
//			}
			
			try {
				excuteSQL(createUpsertSQL(syncTableData, string));
			} catch (Exception e) {
				LOG.error("数据同步失败", e);
				throw new SyncDataException("数据导入失败！");
			}
		}
	}
	//insert into aa(id,name,addr) values('5e594b7b-8c08-11e4-bb4d-9c8e99faf718','cc','ccccccc') on duplicate key update name=values(name),addr=values(addr);
	public String createUpsertSQL(SyncTableData std,String record){
		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(std.getTableName()+" (");
		sb.append(std.getRowNames()+") ");
		sb.append("values ");
		sb.append(createSQLValue(std,record));
		sb.append(" on duplicate key update ");
		sb.append(upsertSQLValue(std.getRowNames()));
//		LOG.info("the sql:"+sb);
		return sb.toString();
	}
	
	private String upsertSQLValue(String rows){
		StringBuffer sb = new StringBuffer();
		String[] ros = rows.split(",");
		String[] svn = new String[ros.length];
		for (int i = 0; i < ros.length; i++) {
			svn[i] = ros[i] + "=values(" + ros[i] +")";
		}
		sb.append(StringUtils.join(svn,","));
		return sb.toString();
	}
	
	private String createInsertSQL(SyncTableData std,String record){
		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(std.getTableName()+" (");
		sb.append(std.getRowNames()+") ");
		sb.append("values ");
		sb.append(createSQLValue(std,record));
		return sb.toString();
	}
	
	private String createUpdateSQL(SyncTableData std,String record){
		StringBuffer sb = new StringBuffer("update ");
		sb.append(std.getTableName());
		sb.append(" set ");
		String[] keys = std.getRowNames().split(",");
		String[] values = record.split(",");
		String keyvalue = "";
		List<String> keyValuesList = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++) {
			if(std.getKeyRowName().equals(keys[i])){
				if(values[i].startsWith("[") && values[i].endsWith("]")){
					String keyvv = "'"+SyncDataHelper.decodeStringValue(values[i])+"'";
					keyvalue = keyvv;
				}else{
					keyvalue = values[i];
				}
			}
			if(!std.getKeyRowName().equals(keys[i]) && !StringUtils.isEmpty(values[i])){
				if(values[i].startsWith("[") && values[i].endsWith("]"))
					values[i] = "'"+SyncDataHelper.decodeStringValue(values[i])+"'";
				//修正手机时间为null时传递数据为'null'导致数据库插入错误
				if("'null'".equals(values[i]))
					values[i] = "null";
				keyValuesList.add(keys[i]+"="+values[i]);
			}
		}
		keyValuesList.add("last_update_time='"+DateTimeUtils.formatTime(new Date(), DateTimeUtils.yyyyMMddHHmmssSSS));
		sb.append(StringUtils.join(keyValuesList.toArray(),","));
		sb.append(" WHERE ");
		sb.append(std.getKeyRowName());
		sb.append("=");
		sb.append(keyvalue);
		return sb.toString();
		
	}
	
	private String createSQLValue(SyncTableData std,String record){
		StringBuffer sb = new StringBuffer();
		Object[] vs = record.split(",");
		Object[] vsn = new Object[vs.length - 1];
		for (int j = 0; j < vs.length - 1; j++) {
			String baseStr = vs[j].toString();
			if(StringUtils.isEmpty(baseStr)){
				vsn[j] = "null";
			}else if(baseStr.startsWith("[") && baseStr.endsWith("]")){
				vsn[j] = "'" + SyncDataHelper.decodeStringValue(baseStr) +"'";
			}else{
				//修正手机时间为null时传递数据为'null'导致数据库插入错误
				if("'null'".equals(vs[j]))
					vsn[j] = "null";
				else
					vsn[j] = vs[j];
			}
		}
		sb.append("("+ StringUtils.join(vsn,",") +")");
		return sb.toString();
	}
}
