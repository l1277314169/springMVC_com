package cn.mobilizer.channel.base.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SkuPriceVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4195375625271451837L;
	
	private Integer skuId;
	
	private BigDecimal price;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
