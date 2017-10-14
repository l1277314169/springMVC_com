package cn.mobilizer.channel.survey.vo;

import java.io.Serializable;

public class SurveyImageVo implements Serializable {

	private static final long serialVersionUID = -7314930446066347165L;

	private String city;
	private String storeNo;
	private String value;
	private String[] images;
	
	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		this.images = this.value.split(",");
	}

}
