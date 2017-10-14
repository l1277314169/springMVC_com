package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SkuTypeDao;
import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.base.po.Unit;
import cn.mobilizer.channel.base.service.SkuTypeService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class SkuTypeServiceImpl implements SkuTypeService {
	
	private static final Log LOG = LogFactory.getLog(SkuTypeServiceImpl.class);
	@Autowired
	private SkuTypeDao skuTypeDao;
	@Override
	public List<SkuType> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SkuType getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<SkuType> selectByParams(Integer clientId,Integer categoryId)throws BusinessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId", clientId);
		param.put("categoryId", categoryId);
		return skuTypeDao.selectByParams(param);
	}
	@Override
	public List<SkuType> selectById(Integer clientId) throws BusinessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId", clientId);
	 
		return skuTypeDao.selectById(param);
		 
	}
	@Override
	public SkuType selectByskutypeId(Integer skutypeid) throws BusinessException {
		SkuType skuType = null;
		try {
			skuType = skuTypeDao.selectByskutypeId(skutypeid);
		} catch (BusinessException e) {
			LOG.error("method getSkuType error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return skuType;
	}

	 
}
