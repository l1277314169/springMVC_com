package cn.mobilizer.channel.report.po;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.comm.utils.json.JsonTool;
import cn.mobilizer.channel.report.vo.ReportGlobal;

public class Column implements Serializable {

	private static final long serialVersionUID = 1L;

	private String colName; // 字段名称
	private String desc; // 字段描述《中文》
	private String place; // 如果为图表的话，需要指定位置，
	private String chartType; // 如果为图表需要指定该列以什么样的方式显示 line or bar
	private String tag; // 1:为可导出为图片,其它待确认

	private String drillArg;
	private String drill = ReportGlobal.Drill.OFF; // 是否可以钻取<1:可钻取,非1为不可钻取>
	private String partId; // 如果可以钻取才有值,否则置空
	
	private String foreignName; // 关联外键字段
	private String argName; // 钻取时需要替换的参数
	private String foreignNamex;
	private String argNamex;
	
	private String showName; // 导航栏显示名称

	private int max = -1; // >85%为绿色，如果该列的值超过了上限or下限需要改变样式 主要针对grid
	private int min = -1; // 70%~85%为黄色
	private String legend; //是否为图例列
	private String type; //导出excel字段类型 对应ReportGlobal ExcelType
	
	//导出excel配置
	private String cellColName;
	private String cellStyle; //对应ReportGlobal.CellStyle，如果该字段有值，将cellColName对应的字段导出的excel背景设置为指定的颜色
	private int hide = 0; //隐藏不在界面展示如果为1不显示 ReportGlobal.HIDE
	
	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}
	
	public String getForeignNamex() {
		return foreignNamex;
	}

	public void setForeignNamex(String foreignNamex) {
		this.foreignNamex = foreignNamex;
	}

	public String getArgNamex() {
		return argNamex;
	}

	public void setArgNamex(String argNamex) {
		this.argNamex = argNamex;
	}

	public String getCellColName() {
		return cellColName;
	}

	public void setCellColName(String cellColName) {
		this.cellColName = cellColName;
	}

	public String getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(String cellStyle) {
		this.cellStyle = cellStyle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	private int format; // 显示格式

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getArgName() {
		return argName;
	}

	public void setArgName(String argName) {
		this.argName = argName;
	}

	public String getDrillArg() {
		return drillArg;
	}

	public void setDrillArg(String drillArg) {
		this.drillArg = drillArg;
	}

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDrill() {
		return drill;
	}

	public void setDrill(String drill) {
		this.drill = drill;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	@SuppressWarnings("unchecked")
	public List<Column> transJSON(String json) {
		List<Column> columns = null;
		try {
			columns = (List<Column>) JsonTool.transToList(json, Column.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return columns;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
