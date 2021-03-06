package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrandSKUOS implements Serializable {

	private static final long serialVersionUID = -1006216792741917639L;
	
	@JsonProperty(value = "IsSubCategorised")
	private boolean IsSubCategorised = false;
	@JsonProperty(value = "LabelColor")
	private String LabelColor = "#43434e";
	@JsonProperty(value = "LabelFontSize")
	private Integer LabelFontSize = 12;
	@JsonProperty(value = "LabelHeader")
	private String LabelHeader = "Brand";
	@JsonProperty(value = "MaxValue")
	private Double MaxValue;
	@JsonProperty(value = "NumOfExtraInfo")
	private Integer NumOfExtraInfo = 0;
	@JsonProperty(value = "ValueHeader")
	private String ValueHeader = "Avg SKU# on Shelf";
	@JsonProperty(value = "Data")
	private List<DataVo> Data = new ArrayList<DataVo>();

	public List<DataVo> getData() {
		return Data;
	}

	public void setData(List<DataVo> data) {
		Data = data;
	}

	public boolean isIsSubCategorised() {
		return IsSubCategorised;
	}

	public void setIsSubCategorised(boolean isSubCategorised) {
		IsSubCategorised = isSubCategorised;
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

	public Double getMaxValue() {
		return MaxValue;
	}

	public void setMaxValue(Double maxValue) {
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

}
