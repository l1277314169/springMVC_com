package cn.mobilizer.channel.posm.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.dao.PromotionMaterialXDao;
import cn.mobilizer.channel.posm.po.PromotionMaterial;
import cn.mobilizer.channel.posm.service.PromotionMaterialXService;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class PromotionMaterialXServiceImpl implements PromotionMaterialXService {

	private static final Log LOG = LogFactory
			.getLog(PromotionMaterialXServiceImpl.class);

	@Autowired
	private PromotionMaterialXDao promotionMaterialXDao;

	@Override
	public void addPromotionMaterial(PromotionMaterial promotionMaterial) {
		promotionMaterialXDao.addPromotionMaterial(promotionMaterial);
	}

	@Override
	public void updatePromotionMaterial(PromotionMaterial promotionMaterial) {
		promotionMaterialXDao.updatePromotionMaterial(promotionMaterial);
	}

	@Override
	public void delPromotionMaterial(Integer materialId) {
		try {
			PromotionMaterial material = new PromotionMaterial();
			material.setMaterialId(materialId);
			material.setIsDelete((byte) 1);
			promotionMaterialXDao.updatePromotionMaterial(material);
		} catch (BusinessException e) {
			LOG.error("method delPromotionMaterial error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
	}

	@Override
	public PromotionMaterial queryPromotionMaterialById(Integer materialId) {
		PromotionMaterial promotionMaterial = promotionMaterialXDao
				.queryPromotionMaterialById(materialId);
		return promotionMaterial;
	}

	@Override
	public Integer queryPromotionMaterialByCount(Map<String, Object> param) {
		int total = promotionMaterialXDao.queryPromotionMaterialByCount(param);
		return total;
	}

	@Override
	public List<PromotionMaterial> queryPromotionMaterialList(
			Map<String, Object> param) {
		List<PromotionMaterial> list = promotionMaterialXDao
				.queryPromotionMaterialList(param);
		return list;
	}

	@Override
	public List<PromotionMaterial> queryPromotionMaterialByNames() {
		List<PromotionMaterial> list = promotionMaterialXDao
				.queryPromotionMaterialByNames();
		return list;
	}

	@Override
	public void batchSavePromotionMaterial(
			List<PromotionMaterial> promotionMaterial) throws BusinessException {
		promotionMaterialXDao.batchSavePromotionMaterial(promotionMaterial);

	}

	/*
	 * @Override public PromotionMaterial queryBymaterialId(Integer materialId)
	 * { PromotionMaterial
	 * promotionMaterial=promotionMaterialXDao.queryBymaterialId(materialId);
	 * return promotionMaterial; }
	 */

	@Override
	public List<PromotionMaterial> batchOutPromotionMaterial(
			Map<String, Object> param) {
		List<PromotionMaterial> list1 = promotionMaterialXDao
				.batchOutPromotionMaterial(param);
		return list1;
	}

	@Override
	public List<PromotionMaterial> queryPmListByClinteIdAndLikeName(
			Integer clinteId, String name) {
		return promotionMaterialXDao
				.queryPromotionMaterialsByClientIdAndLikeName(clinteId, name);
	}

}
