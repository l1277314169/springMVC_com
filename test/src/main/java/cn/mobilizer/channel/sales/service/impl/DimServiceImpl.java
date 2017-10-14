package cn.mobilizer.channel.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.sales.dao.DimYearDao;
import cn.mobilizer.channel.sales.po.DimYear;
import cn.mobilizer.channel.sales.service.DimService;


@Service
public class DimServiceImpl implements DimService{

	@Autowired
	private DimYearDao dimYearDao;
	
	public List<DimYear> getYear(Map<String, Object> params){
		
		return dimYearDao.getYear(params);
	}
	
}
