package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseVo implements Serializable {

	private static final long serialVersionUID = -6079142963292828625L;

	@JsonProperty("DashboardId")
	private Integer DashboardId;
	@JsonProperty("StoresAudited")
	private Integer StoresAudited;

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

}
