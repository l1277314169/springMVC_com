package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SKUPrice implements Serializable {

	private static final long serialVersionUID = -7125036179141409224L;
	@JsonProperty(value = "LabelHeader")
	private String LabelHeader = "SKU";
	@JsonProperty(value = "ExtraInfo1Header")
	private String ExtraInfo1Header = "#Stores";
	@JsonProperty(value = "ExtraInfo2Header")
	private String ExtraInfo2Header = "RRSP";
	@JsonProperty(value = "ExtraInfo3Header")
	private String ExtraInfo3Header = "AVG Price";
	@JsonProperty(value = "ValueHeader")
	private String ValueHeader = "% Outside RRSP Range";
	@JsonProperty(value = "NumOfExtraInfo")
	private Integer NumOfExtraInfo = 3;
	@JsonProperty(value = "IsSubCategorised")
	private boolean IsSubCategorised = false;
	@JsonProperty(value = "MaxValue")
	private Integer MaxValue;
	@JsonProperty(value = "LabelColor")
	private String LabelColor = "#43434e";
	@JsonProperty(value = "LabelFontSize")
	private Integer LabelFontSize = 12;
	@JsonProperty(value = "Data")
	private List<PriceDataVo> Data = new ArrayList<PriceDataVo>();

	public String getExtraInfo1Header() {
		return ExtraInfo1Header;
	}

	public void setExtraInfo1Header(String extraInfo1Header) {
		ExtraInfo1Header = extraInfo1Header;
	}

	public String getExtraInfo2Header() {
		return ExtraInfo2Header;
	}

	public void setExtraInfo2Header(String extraInfo2Header) {
		ExtraInfo2Header = extraInfo2Header;
	}

	public String getExtraInfo3Header() {
		return ExtraInfo3Header;
	}

	public void setExtraInfo3Header(String extraInfo3Header) {
		ExtraInfo3Header = extraInfo3Header;
	}

	public String getLabelHeader() {
		return LabelHeader;
	}

	public void setLabelHeader(String labelHeader) {
		LabelHeader = labelHeader;
	}

	public String getValueHeader() {
		return ValueHeader;
	}

	public void setValueHeader(String valueHeader) {
		ValueHeader = valueHeader;
	}

	public Integer getNumOfExtraInfo() {
		return NumOfExtraInfo;
	}

	public void setNumOfExtraInfo(Integer numOfExtraInfo) {
		NumOfExtraInfo = numOfExtraInfo;
	}

	public boolean isIsSubCategorised() {
		return IsSubCategorised;
	}

	public void setIsSubCategorised(boolean isSubCategorised) {
		IsSubCategorised = isSubCategorised;
	}

	public Integer getMaxValue() {
		return MaxValue;
	}

	public void setMaxValue(Integer maxValue) {
		MaxValue = maxValue;
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

	public List<PriceDataVo> getData() {
		return Data;
	}

	public void setData(List<PriceDataVo> data) {
		Data = data;
	}

}
