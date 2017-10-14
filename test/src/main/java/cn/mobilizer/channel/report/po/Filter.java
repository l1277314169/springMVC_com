package cn.mobilizer.channel.report.po;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.utils.json.JsonTool;

public class Filter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String label; // 列名称
	private int type; // 类型
	private String arg;
	private String foreignArgs; //匹配的参数
	private Object defaultVal; //默认值
	private int defaultIndex; //默认位置<某些报表默认查询的为上一月的日期>
	private String enums; //
	private String enumJson;
	
	public String getEnumJson() {
		return enumJson;
	}

	public void setEnumJson(String enumJson) {
		this.enumJson = enumJson;
	}

	public String getEnums() {
		return enums;
	}

	public void setEnums(String enums) {
		this.enums = enums;
		
		StringBuffer buffer = new StringBuffer("[");
		if(!StringUtil.isEmptyString(this.enums)){
			String temp[] = this.enums.split(",");
			for (String str : temp) {
				String s[] = str.split("@");
				buffer.append("{'name':'").append(s[1]).append("','value':'").append(s[0]).append("'},");
			}
			
			if(buffer.toString().endsWith(",")){
				buffer.deleteCharAt(buffer.length()-1);
			}
		}
		buffer.append("]");
		this.setEnumJson(buffer.toString());
	}

	public int getDefaultIndex() {
		return defaultIndex;
	}

	public void setDefaultIndex(int defaultIndex) {
		this.defaultIndex = defaultIndex;
	}

	public Object getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(Object defaultVal) {
		this.defaultVal = defaultVal;
	}

	@SuppressWarnings("unchecked")
	public List<Filter> transJSON(String json){
		List<Filter> filters = null;
		try {
			filters = (List<Filter>) JsonTool.transToList(json, Filter.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filters;
	}
	
	public String getForeignArgs() {
		return foreignArgs;
	}

	public void setForeignArgs(String foreignArgs) {
		this.foreignArgs = foreignArgs;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

}
