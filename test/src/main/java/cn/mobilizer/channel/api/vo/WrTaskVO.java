package cn.mobilizer.channel.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.vo.StoreSingle;

public class WrTaskVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3619907969349726960L;
	
	private Integer cityId;
	
	private String lastUpdateTime;
	
	private List<StoreSingle> storeList;

	public List<StoreSingle> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<StoreSingle> storeList) {
		this.storeList = storeList;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


}
