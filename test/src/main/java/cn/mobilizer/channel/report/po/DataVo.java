package cn.mobilizer.channel.report.po;

import java.io.Serializable;

import cn.mobilizer.channel.report.vo.NumberFormatUtils;
import cn.mobilizer.channel.report.vo.ReportGlobal;

public class DataVo extends Column implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object value; // 值
	private String key;
	private int index;
	private String style; // 样式
	
	public DataVo() {

	}

	public DataVo(Column column) {
		super.setMax(column.getMax());
		super.setMin(column.getMin());
		super.setArgName(column.getArgName());
		super.setColName(column.getColName());
		super.setDesc(column.getDesc());
		super.setShowName(column.getShowName());
		super.setForeignName(column.getForeignName());
		super.setDrill(column.getDrill());
		super.setPartId(column.getPartId());
		super.setFormat(column.getFormat());
		super.setTag(column.getTag());
		super.setChartType(column.getChartType());
		super.setPlace(column.getPlace());
		super.setLegend(column.getLegend());
		super.setDrillArg(column.getDrillArg());
		super.setType(column.getType());
		super.setCellColName(column.getCellColName());
		super.setCellStyle(column.getCellStyle());
		super.setHide(column.getHide());
		super.setArgNamex(column.getArgNamex());
		super.setForeignNamex(column.getForeignNamex());
	}
	

	public String getStyle() {
		if(super.getMax()!=-1 && super.getMin()!=-1){
			this.value = this.value==null?0:this.value;
			double value = Double.parseDouble(this.value.toString());
			if(value>super.getMax()){
				this.style = ReportGlobal.Style.MAX_STYLE;
			}else if(super.getMin()<=value && value<=super.getMax()){
				this.style = ReportGlobal.Style.MID_STYLE;
			}else if(value<super.getMin()){
				this.style = ReportGlobal.Style.MIN_STYLE;
			}
		}
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		if(super.getFormat()==ReportGlobal.Format.PER){
			this.value = this.value==null?0:this.value;
			double val = Double.parseDouble(this.value.toString());
			this.value =  NumberFormatUtils.formatNumber(val);
		}
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
