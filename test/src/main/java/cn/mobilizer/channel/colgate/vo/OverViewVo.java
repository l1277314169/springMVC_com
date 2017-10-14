package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

public class OverViewVo implements Serializable {

	private static final long serialVersionUID = -9188141354385935360L;

	private Integer displayType;
	private String displayDesc;
	private Integer category;
	private Integer value;

	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	public String getDisplayDesc() {
		return displayDesc;
	}

	public void setDisplayDesc(String displayDesc) {
		this.displayDesc = displayDesc;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
