package cn.mobilizer.channel.apple.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppleDistribution implements Serializable {

	private static final long serialVersionUID = 6026854133486295101L;
	
	@JsonProperty(value = "LabelColor")
	private String LabelColor = "#43434e";
	@JsonProperty(value = "LabelFontSize")
	private Integer LabelFontSize = 12;
	@JsonProperty(value = "LabelHeader")
	private String LabelHeader = "ITEMS";
	@JsonProperty(value = "MaxValue")
	private Integer MaxValue = 100;
	@JsonProperty(value = "NumOfExtraInfo")
	private Integer NumOfExtraInfo = 0;
	@JsonProperty(value = "ValueHeader")
	private String ValueHeader = "Qualified Rate";
	@JsonProperty(value = "IsSubCategorised")
	private boolean IsSubCategorised = true;
	@JsonProperty(value = "Data")
	private List<AppleDataVo> Data = new ArrayList<AppleDataVo>();

	public List<AppleDataVo> getData() {
		return Data;
	}

	public void setData(List<AppleDataVo> data) {
		Data = data;
	}

	public String getLabelColor() {
		return LabelColor;
	}

	public void setLabelColor(String labelColor) {
		LabelColor = labelColor;
	}

	public Integer getLabelFontSize() {
		return LabelFontSize;
	}

	public void setLabelFontSize(Integer labelFontSize) {
		LabelFontSize = labelFontSize;
	}

	public String getLabelHeader() {
		return LabelHeader;
	}

	public void setLabelHeader(String labelHeader) {
		LabelHeader = labelHeader;
	}

	public Integer getMaxValue() {
		return MaxValue;
	}

	public void setMaxValue(Integer maxValue) {
		MaxValue = maxValue;
	}

	public Integer getNumOfExtraInfo() {
		return NumOfExtraInfo;
	}

	public void setNumOfExtraInfo(Integer numOfExtraInfo) {
		NumOfExtraInfo = numOfExtraInfo;
	}

	public String getValueHeader() {
		return ValueHeader;
	}

	public void setValueHeader(String valueHeader) {
		ValueHeader = valueHeader;
	}

	public boolean isIsSubCategorised() {
		return IsSubCategorised;
	}

	public void setIsSubCategorised(boolean isSubCategorised) {
		IsSubCategorised = isSubCategorised;
	}

}
