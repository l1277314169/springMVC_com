package cn.mobilizer.channel.report.po;

import java.io.Serializable;

public class EnumVo implements Serializable {

	private static final long serialVersionUID = 127900904903321451L;

	private String value;
	private String name;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
