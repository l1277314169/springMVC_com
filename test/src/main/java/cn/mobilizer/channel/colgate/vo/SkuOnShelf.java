package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkuOnShelf extends BaseVo implements Serializable {

	private static final long serialVersionUID = -7834399892513224441L;

	@JsonProperty(value = "BrandSKUOS")
	private BrandSKUOS brandSKUOS;

	public BrandSKUOS getBrandSKUOS() {
		return brandSKUOS;
	}

	public void setBrandSKUOS(BrandSKUOS brandSKUOS) {
		this.brandSKUOS = brandSKUOS;
	}

}
