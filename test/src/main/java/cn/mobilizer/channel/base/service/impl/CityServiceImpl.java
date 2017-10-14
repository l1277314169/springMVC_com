package cn.mobilizer.channel.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.CityDao;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.service.CityService;
@Service
public class CityServiceImpl implements CityService {
	@Autowired
	private CityDao cityDao;

	@Override
	public List<City> getAll() {
		return cityDao.queryAll();
	}

	@Override
	public List<City> queryCityByProvinceId(Object params) {
		
		return cityDao.queryCityByProvinceId(params);
	}

	@Override
	public City getCityById(Integer cityId) {
		
		return cityDao.getCityById(cityId);
	}

	@Override
	public Map<String, City> selectAllCity(Map<String, Object> params) {
		return cityDao.selectAllCity(params);
	}

}
