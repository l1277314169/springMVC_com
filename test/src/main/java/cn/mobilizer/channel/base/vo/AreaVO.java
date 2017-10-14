package cn.mobilizer.channel.base.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;

public class AreaVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3288207250434549448L;
	
	private List<Province> provinces;
	
	private List<City> cities;
	
	private List<District> districts;

	
	public List<Province> getProvinces(){
		return provinces;
	}

	
	public void setProvinces(List<Province> provinces){
		this.provinces = provinces;
	}

	
	public List<City> getCities(){
		return cities;
	}


	
	public void setCities(List<City> cities){
		this.cities = cities;
	}


	public List<District> getDistricts(){
		return districts;
	}

	
	public void setDistricts(List<District> districts){
		this.districts = districts;
	}

}
