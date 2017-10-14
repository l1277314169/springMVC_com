package cn.mobilizer.channel.export.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.mobilizer.channel.report.po.Column;
import cn.mobilizer.channel.report.po.Filter;
import cn.mobilizer.channel.report.po.Func;
import cn.mobilizer.channel.report.vo.ReportGlobal;

public class DumpDataSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());

	private int settingId; // 自动编号
	private String name; // 名称
	private String filters; // 查询条件(JSON格式)
	private String parameters; // 参数
	private String columnLists; // 返回字段
	private String sqlTemplate; // SQL脚本
	private String funcList; //功能
	private String remark; // 备注
	private int clientId; // 客户编号(关联client表)
	private Timestamp createTime; // 创建时间
	private Timestamp lastUpdateTime; // 最后更新时间
	private int isDelete; // 删除标识(0-正常,1-删除)

	private List<Filter> filterList;
	private List<Filter> parametersList;
	private List<Func> funcs;
	private List<Column> columnList;
	private boolean hasSearch; //是否有查询功能，如果有查询功能，返回的结果集跟没有查询功能的结果集解析器不一样
	
	public boolean isHasSearch() {
		return hasSearch;
	}

	public void setHasSearch(boolean hasSearch) {
		this.hasSearch = hasSearch;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	private boolean hasDept; //是否包含部门,如果包含部门
	
	public void setHasDept(boolean hasDept) {
		this.hasDept = hasDept;
	}

	public boolean isHasDept() {
		for (Filter rep : getFilterList()) {
			if(rep.getType()==ReportGlobal.Filters.SELECT_DEPT){
				this.hasDept = true;
				break;
			}
		}		
		return hasDept;
	}
	
	public List<Func> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<Func> funcs) {
		this.funcs = funcs;
	}
	
	public String getFuncList() {
		return funcList;
	}

	public void setFuncList(String funcList) {
		this.funcList = funcList;
		this.funcs = new Func().transJSON(funcList);
		for (Func func : this.funcs) {
			if(func.getId().equals(ReportGlobal.HAS_SEARCH)){
				this.hasSearch = true;
				break;
			}
		}
	}

	public String getMyBatisSql(Map<String, Object> filterMap){
		Set<String> keys = filterMap.keySet();
		String sql = getSqlTemplate();
		for (String key : keys) {
			Filter reFilter = this.getParamByName(key);
			if(null==reFilter){
				continue;
			}
			Object val = filterMap.get(key);
			if(reFilter.getType()==ReportGlobal.ColumnType.NUMBER){
				val = val==null?"0":val;
				if(val.toString().equals("")){
					val = "0";
				}
				sql = sql.replaceAll(key,val+"");
			}else{
				val = val==null?"":val;
				sql = sql.replaceAll(key, "'"+val+"'");
			}
		}
		log.info("call sql: "+sql);
		return sql;
	}
	
	public String getFilterName(int type){
		String name = null;
		for (Filter rep : getFilterList()) {
			if(rep.getType() == type){
				name = rep.getArg();
				break;
			}
		}
		return name;
	}
	
	/**
	 * 根据字段名称获取字段信息
	 * @param colName
	 * @return
	 */
	public Filter getParamByName(String colName) {
		Filter rep = null;
		for (Filter c : getParametersList()) {
			if (c.getArg().equals(colName)) {
				rep = c;
				break;
			}
		}
		return rep;
	}

	public List<Filter> getParametersList() {
		return parametersList;
	}

	public void setParametersList(List<Filter> parametersList) {
		this.parametersList = parametersList;
	}

	public List<Filter> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}

	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
		this.filterList = new Filter().transJSON(this.filters);
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
		this.parametersList = new Filter().transJSON(this.parameters);
	}

	public String getColumnLists() {
		return columnLists;
	}

	public void setColumnLists(String columnLists) {
		this.columnLists = columnLists;
		this.columnList = new Column().transJSON(this.columnLists);
	}

	public String getSqlTemplate() {
		return sqlTemplate;
	}

	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}
