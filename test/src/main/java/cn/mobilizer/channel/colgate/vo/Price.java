package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price extends BaseVo implements Serializable {

	private static final long serialVersionUID = -2821364121193504328L;

	@JsonProperty("SKUPrice")
	private SKUPrice SKUPrice;

	public SKUPrice getSKUPrice() {
		return SKUPrice;
	}

	public void setSKUPrice(SKUPrice sKUPrice) {
		SKUPrice = sKUPrice;
	}

}
