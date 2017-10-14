package cn.mobilizer.channel.base.vo;

public class SpecialVO {
	private Integer type;
	private String cvalue;
	private Integer colWidth;
	private String[] picNames;
	public String[] getPicNames() {
		return picNames;
	}
	public void setPicNames(String[] picNames) {
		this.picNames = picNames;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCvalue() {
		return cvalue;
	}
	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
	public Integer getColWidth() {
		return colWidth;
	}
	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}
	
}
