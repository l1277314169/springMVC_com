package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.City;

public interface CityService {
	public List<City> getAll();

	public List<City> queryCityByProvinceId(Object params);

	public City getCityById(Integer cityId);
	
	public Map<String, City> selectAllCity(Map<String, Object> params);
}
