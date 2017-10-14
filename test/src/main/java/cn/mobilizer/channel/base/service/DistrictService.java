package cn.mobilizer.channel.base.service;

import java.util.List;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.District;

public interface DistrictService {
	public List<District> getAll(); 
	public List<District> queryDistrictByCityId(Object params);
	public District getDistrictById(Integer DistrictId);
}
