package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataVo implements Serializable {

	private static final long serialVersionUID = -875011232159443149L;
	
	@JsonProperty(value = "Category")
	private String Category;
	@JsonProperty(value = "Color")
	private String Color = "#fff200";
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
		if(label.length()>50){
			label = label.substring(0,50);
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
