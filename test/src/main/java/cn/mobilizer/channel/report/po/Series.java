package cn.mobilizer.channel.report.po;

import java.io.Serializable;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.report.vo.ArraysUtils;

public class Series implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String type;
	private String data;
	private String place;
	private double maxData;
	private String stack;
	
	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public double getMaxData() {
		if (StringUtil.isEmptyString(data)) {
			return 0;
		} else {
			maxData = ArraysUtils.getMaxValue(data.split(","));
			return maxData;
		}
	}

	public void setMaxData(double maxData) {
		this.maxData = maxData;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
