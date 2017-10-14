package cn.mobilizer.channel.posm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.dao.PromotionMaterialStockXDao;
import cn.mobilizer.channel.posm.po.PromotionMaterialStock;
import cn.mobilizer.channel.posm.service.PromotionMaterialStockService;
import cn.mobilizer.channel.posm.vo.PromotionMaterialStockVo;
@Service
public class PromotionMaterialStockServiceImpl implements PromotionMaterialStockService{
	@Autowired
	private PromotionMaterialStockXDao promotionMaterialStockXDao;
	@Override
	public Integer findpromotionMaterialStockVoCountByparam(Map<String, Object> params)
			throws BusinessException {
		
		return promotionMaterialStockXDao.findpromotionMaterialStockVoCountByparam(params);
	}
	@Override
	public List<PromotionMaterialStockVo> findpromotionMaterialStockVoByparam(
			Map<String, Object> params) throws BusinessException {
		return promotionMaterialStockXDao.findpromotionMaterialStockVoByparam(params);
	}
	
	@Override
	public PromotionMaterialStock getPmsByClientIdAndMaterialIdAndWarehouseId(
			Integer materialId, Integer warehouseId, Integer clientId) {
		if(null != materialId || null != warehouseId || null != clientId){
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != materialId){
				map.put("materialId", materialId);
			}
			if(null != warehouseId){
				map.put("warehouseId", warehouseId);
			}
			if(null != clientId){
				map.put("clientId", clientId);
			}
			return promotionMaterialStockXDao.queryPromotionMaterialStock(map);
		}
		return null;
	}
	
	
	@Override
	public boolean isQuantity(Integer materialId, Integer warehouseId,Integer clientId, Integer quantity) {
		if(null != quantity && quantity > 0){
			PromotionMaterialStock materialStock = getPmsByClientIdAndMaterialIdAndWarehouseId(materialId,warehouseId,clientId);
			if(null != materialStock){
				if(null != materialStock.getQuantity() && materialStock.getQuantity() > 0){
					if((materialStock.getQuantity() - quantity) >= 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	@Override
	public boolean findPromotionMaterialStock(PromotionMaterialStock materialStock) {
		if(null != materialStock){
			if(null != materialStock.getWarehouseId()){
				if(null != materialStock.getMaterialId()){
					if(null != materialStock.getQuantity() && materialStock.getQuantity() >= 0){
						if(promotionMaterialStockXDao.updateByPrimaryKeySelective(materialStock) > 0){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	@Override
	public boolean savePromotionMaterialStock(PromotionMaterialStock materialStock) {
		if(null != materialStock){
			if(null != materialStock.getWarehouseId()){
				if(null != materialStock.getMaterialId()){
					if(null != materialStock.getQuantity() && materialStock.getQuantity() >= 0){
						if(promotionMaterialStockXDao.insertSelective(materialStock) > 0){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public String updataPmsQuantity(Integer imputWarehouseId,Integer outWarehouseId, Integer materialId,String storeId, Integer quantity,Integer userId, Integer clientId, String remark) {
		String ret = "false";
		if(null != imputWarehouseId && null != outWarehouseId &&
				null != materialId && !StringUtil.isBlank(storeId) && null !=quantity &&
				null != userId && null != clientId){
			PromotionMaterialStock materialStock = getPmsByClientIdAndMaterialIdAndWarehouseId(materialId,outWarehouseId,clientId);
			if(null != materialStock){
				if(materialStock.getQuantity() - quantity > 0){
					if(StringUtil.isBlank(storeId) && null != imputWarehouseId){
						PromotionMaterialStock stock = getPmsByClientIdAndMaterialIdAndWarehouseId(materialId,imputWarehouseId,clientId);
						materialStock.setQuantity(materialStock.getQuantity() - quantity);
						if(null != stock){// 修改库存		修改库存
							stock.setQuantity(stock.getQuantity() + quantity);
							if((true == findPromotionMaterialStock(stock))&&(true == findPromotionMaterialStock(materialStock))){
								ret = "true";
							}else{
								throw new RuntimeException();
							}
						}else{//修改库存//添加库存
							stock = new PromotionMaterialStock();
							stock.setQuantity(quantity);
							stock.setMaterialId(materialId);
							stock.setClientId(clientId);
							stock.setCreateBy(userId);
							stock.setWarehouseId(imputWarehouseId);
							stock.setRemark(remark);
							if((true == savePromotionMaterialStock(stock))&&(true == findPromotionMaterialStock(materialStock))){
								ret = "true";
							}else{
								throw new RuntimeException();
							}
						}
					}else{
						materialStock.setQuantity(materialStock.getQuantity() - quantity);
						if(findPromotionMaterialStock(materialStock)){
							ret = "true";
						}
					}
				}else{
					ret = "库存不足";//库存不足
				}
			}else{
				ret = "没有库存";//没有库存
			}
		}
		return ret;
	}

}
