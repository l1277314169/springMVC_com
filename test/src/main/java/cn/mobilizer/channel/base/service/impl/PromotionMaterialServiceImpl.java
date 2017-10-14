package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.PromotionMaterialDao;
import cn.mobilizer.channel.base.po.PromotionMaterial;
import cn.mobilizer.channel.base.service.PromotionMaterialService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class PromotionMaterialServiceImpl implements PromotionMaterialService {
	private static final Log LOG = LogFactory.getLog(PromotionMaterialServiceImpl.class);
	
	@Autowired
	private PromotionMaterialDao promotionMaterialDao;
	
	@Override
	public Integer queryPromotionMaterialCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = promotionMaterialDao.queryPromotionMaterialCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryPromotionMaterialCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<PromotionMaterial> queryPromotionMaterialList(Map<String, Object> param) throws BusinessException{
		List<PromotionMaterial> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = promotionMaterialDao.queryPromotionMaterialList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryPromotionMaterialCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public PromotionMaterial selectByPrimaryKey(Integer materialId)throws BusinessException {		 
		return promotionMaterialDao.selectByPrimaryKey(materialId);
	}

	@Override
	public PromotionMaterial getPromotionMaterialByIdAndClientId(Integer clientId, Integer materialId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("materialId", materialId);
		return promotionMaterialDao.getPromotionMaterialByIdAndClientId(params);
	}
	
}
