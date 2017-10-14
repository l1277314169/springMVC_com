package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistributionVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = -1361661950829816642L;
	
	@JsonProperty(value = "SKUDistribution")
	private SKUDistribution SKUDistribution;

	public SKUDistribution getSKUDistribution() {
		return SKUDistribution;
	}

	public void setSKUDistribution(SKUDistribution sKUDistribution) {
		SKUDistribution = sKUDistribution;
	}

}
