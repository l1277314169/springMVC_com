package cn.mobilizer.channel.base.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ProvinceDao;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class ProvinceServiceImpl implements ProvinceService {
	private static final Log LOG = LogFactory.getLog(ProvinceServiceImpl.class);
	
	@Autowired
	private ProvinceDao provinceDao;

	@Override
	public Province getProvinceByName(String name) {

		return provinceDao.getProvinceByName(name);
	}

	@Override
	public List<Province> getAll() {
		return provinceDao.queryAll();
	}

	@Override
	public Province getPrivinceById(Integer privinceId) {
		
		return provinceDao.getProvinceNameById(privinceId);
	}

	 

	@Override
	public Map<String, Province> selectAllprovince(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return provinceDao.getProvinceMapByName(params);
	}

	 
	
	

}
