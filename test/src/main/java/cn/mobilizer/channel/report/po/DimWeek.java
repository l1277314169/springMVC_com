package cn.mobilizer.channel.report.po;

import java.io.Serializable;

public class DimWeek implements Serializable {

	
	private static final long serialVersionUID = -6654884159998483955L;

	private Integer weekId;
	
	private String weekDesc;
	
	private String weekLongDesc;
	

	public String getWeekLongDesc() {
		return weekLongDesc;
	}

	public void setWeekLongDesc(String weekLongDesc) {
		this.weekLongDesc = weekLongDesc;
	}

	public Integer getWeekId() {
		return weekId;
	}

	public void setWeekId(Integer weekId) {
		this.weekId = weekId;
	}

	public String getWeekDesc() {
		return weekDesc;
	}

	public void setWeekDesc(String weekDesc) {
		this.weekDesc = weekDesc;
	}
}
