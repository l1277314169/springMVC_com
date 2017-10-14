package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceDataVo implements Serializable {

	private static final long serialVersionUID = -875011232159443149L;

	@JsonProperty(value = "Category")
	private String Category;
	@JsonProperty(value = "Color")
	private String Color = "#ED1D25";
	@JsonProperty(value = "Postfix")
	private String Postfix = "%";
	@JsonProperty(value = "Label")
	private String Label;
	@JsonProperty(value = "Value")
	private Double Value;
	@JsonProperty(value = "SubCategory")
	private String SubCategory;
	@JsonProperty(value = "Prefix")
	private String Prefix = "";
	@JsonProperty(value = "ExtraInfo1")
	private String ExtraInfo1;
	@JsonProperty(value = "ExtraInfo2")
	private String ExtraInfo2;
	@JsonProperty(value = "ExtraInfo3")
	private String ExtraInfo3;

	public String getExtraInfo1() {
		return ExtraInfo1;
	}

	public void setExtraInfo1(String extraInfo1) {
		ExtraInfo1 = extraInfo1;
	}

	public String getExtraInfo2() {
		return ExtraInfo2;
	}

	public void setExtraInfo2(String extraInfo2) {
		ExtraInfo2 = extraInfo2;
	}

	public String getExtraInfo3() {
		return ExtraInfo3;
	}

	public void setExtraInfo3(String extraInfo3) {
		ExtraInfo3 = extraInfo3;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public String getPostfix() {
		return Postfix;
	}

	public void setPostfix(String postfix) {
		Postfix = postfix;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		if (label.length() > 50) {
			label = label.substring(0, 50);
		}
		Label = label;
	}

	public Double getValue() {
		return Value;
	}

	public void setValue(Double value) {
		Value = value;
	}

	public String getSubCategory() {
		return SubCategory;
	}

	public void setSubCategory(String subCategory) {
		SubCategory = subCategory;
	}

	public String getPrefix() {
		return Prefix;
	}

	public void setPrefix(String prefix) {
		Prefix = prefix;
	}

}
