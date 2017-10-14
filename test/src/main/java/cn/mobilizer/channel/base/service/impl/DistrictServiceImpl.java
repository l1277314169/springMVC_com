package cn.mobilizer.channel.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.DistrictDao;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.service.DistrictService;
@Service
public class DistrictServiceImpl implements DistrictService {
	@Autowired
	private DistrictDao districtDao;

	@Override
	public List<District> getAll() {
		return districtDao.queryAll();
	}

	@Override
	public List<District> queryDistrictByCityId(Object params) {
		
		return districtDao.queryDistrictByCityId(params);
	}

	@Override
	public District getDistrictById(Integer districtId) {
	
		return districtDao.getDistrictById(districtId);
	}

}
