package cn.mobilizer.channel.sales.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.sales.po.DimYear;

public interface DimService {

	public List<DimYear> getYear(Map<String, Object> params);
	
}
