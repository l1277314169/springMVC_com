package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SkuSeriesDao;
import cn.mobilizer.channel.base.po.SkuSeries;
import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.base.service.SkuSeriesService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class SkuSeriesServiceImpl implements SkuSeriesService {
	
	private static final Log LOG = LogFactory.getLog(SkuSeriesServiceImpl.class);
	@Autowired
	private SkuSeriesDao skuSeriesDao;
	
	@Override
	public List<SkuSeries> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuSeries getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SkuSeries> selectByParams(Integer clientId,Integer brandId)throws BusinessException {	
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId",clientId);
		param.put("brandId",brandId);
		return skuSeriesDao.selectByParams(param);
	}

	@Override
	public List<SkuSeries> selectByclientId(Integer clientId)
			throws BusinessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId",clientId);
		 
		return skuSeriesDao.selectbyid(param);
		 
	}

	@Override
	public SkuSeries selectbyskuseries(Integer skuseriesid)
			throws BusinessException {
		SkuSeries skuseries = null;
		try {
			skuseries = skuSeriesDao.selectbyskuseriesid(skuseriesid);
		} catch (BusinessException e) {
			LOG.error("method getSkuSeries error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return skuseries;
	}
	}


