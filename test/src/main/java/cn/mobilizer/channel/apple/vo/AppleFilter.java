package cn.mobilizer.channel.apple.vo;

import java.io.Serializable;

public class AppleFilter implements Serializable {

	private static final long serialVersionUID = -1735078671344781411L;

	private String filterStructureIds; //权限过滤条件
	private String deptIds;
	private Integer yearId;
	private Integer channel;
	private Integer province;
	private String city;
	private String amName;
	private String rmName;
	
 	public String getAmName() {
		return amName;
	}

	public void setAmName(String amName) {
		this.amName = amName;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getFilterStructureIds() {
		return filterStructureIds;
	}

	public void setFilterStructureIds(String filterStructureIds) {
		this.filterStructureIds = filterStructureIds;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public Integer getYearId() {
		return yearId;
	}

	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
