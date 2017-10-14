package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.UnitDao;
import cn.mobilizer.channel.base.po.Unit;
import cn.mobilizer.channel.base.service.UnitService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class UnitServiceImpl implements UnitService {
	
	private static final Log LOG = LogFactory.getLog(BrandServiceImpl.class);
	@Autowired
	private UnitDao unitDao;

	@Override
	public List<Unit> getObjectList(Map<String, Object> parames) {
		return unitDao.queryAll(parames);
	}

	@Override
	public Unit getObject(Map<String, Object> parames) {
		return null;
	}

	@Override
	public List<Unit> getUnitList(int clientId)throws BusinessException{
		List<Unit> unList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			unList = unitDao.getUnitList(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getUnitList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return unList;
	}

	@Override
	public Unit getUnit(Integer unitId)throws BusinessException {
		Unit unit = null;
		try {
			unit = unitDao.getUnitByUnitId(unitId);
		} catch (BusinessException e) {
			LOG.error("method getUnit error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return unit;
	}

}
