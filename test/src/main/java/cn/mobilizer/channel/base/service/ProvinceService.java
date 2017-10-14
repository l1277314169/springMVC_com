package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.Province;

public interface ProvinceService {
	public Province getProvinceByName(String name);
	public List<Province> getAll();

	public Province getPrivinceById(Integer PrivinceId);
	public Map<String, Province> selectAllprovince(Map<String, Object> params);
}
