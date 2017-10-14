package cn.mobilizer.channel.report.po;

import java.io.Serializable;

import cn.mobilizer.channel.comm.utils.json.JsonTool;

public class ChartSetting implements Serializable {

	private static final long serialVersionUID = 8832441638732392308L;

	private String leftUnit; // 左边的单位
	private String rightUnit; // 右边的单位

	private String leftName; // 左上角显示的名字
	private String rightName; // 右上角显示的名字

	private String buttomUnit; // 横轴单位

	public ChartSetting transJson(String json) {
		ChartSetting cs = null;
		try {
			cs = (ChartSetting) JsonTool.transToObject(json, ChartSetting.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cs;
	}

	public String getLeftName() {
		return leftName;
	}

	public void setLeftName(String leftName) {
		this.leftName = leftName;
	}

	public String getButtomUnit() {
		return buttomUnit;
	}

	public void setButtomUnit(String buttomUnit) {
		this.buttomUnit = buttomUnit;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public String getLeftUnit() {
		return leftUnit;
	}

	public void setLeftUnit(String leftUnit) {
		this.leftUnit = leftUnit;
	}

	public String getRightUnit() {
		return rightUnit;
	}

	public void setRightUnit(String rightUnit) {
		this.rightUnit = rightUnit;
	}

}
