package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.SkuSeries;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface SkuSeriesService extends BasicService<SkuSeries>{

	public List<SkuSeries> selectByParams(Integer clientId,Integer brandId) throws BusinessException;
	public List<SkuSeries> selectByclientId(Integer clientId) throws BusinessException;
	public SkuSeries selectbyskuseries(Integer skuseriesid) throws BusinessException;
	

	 
}
