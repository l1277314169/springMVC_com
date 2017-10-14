package cn.mobilizer.channel.apple.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppleACSCVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("DashboardId")
	private Integer DashboardId;
	@JsonProperty("StoresAudited")
	private Integer StoresAudited;
	@JsonProperty(value = "SKUDistribution")
	private AppleDistribution appleDistribution;

	public Integer getDashboardId() {
		return DashboardId;
	}

	public void setDashboardId(Integer dashboardId) {
		DashboardId = dashboardId;
	}

	public Integer getStoresAudited() {
		return StoresAudited;
	}

	public void setStoresAudited(Integer storesAudited) {
		StoresAudited = storesAudited;
	}

	public AppleDistribution getAppleDistribution() {
		return appleDistribution;
	}

	public void setAppleDistribution(AppleDistribution appleDistribution) {
		this.appleDistribution = appleDistribution;
	}
}
