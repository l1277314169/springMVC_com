package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareOfShelf extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("BrandSOS")
	private BrandSOS BrandSOS;

	public BrandSOS getBrandSOS() {
		return BrandSOS;
	}

	public void setBrandSOS(BrandSOS brandSOS) {
		BrandSOS = brandSOS;
	}

}
