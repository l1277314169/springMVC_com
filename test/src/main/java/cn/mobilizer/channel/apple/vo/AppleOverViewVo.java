package cn.mobilizer.channel.apple.vo;

import java.io.Serializable;

public class AppleOverViewVo implements Serializable {

	private static final long serialVersionUID = 2572632884444760678L;
	private Integer displayType;
	private String displayDesc;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
