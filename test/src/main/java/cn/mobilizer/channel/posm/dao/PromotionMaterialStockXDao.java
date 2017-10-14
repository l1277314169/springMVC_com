package cn.mobilizer.channel.posm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.posm.po.PromotionMaterial;
import cn.mobilizer.channel.posm.po.PromotionMaterialStock;
import cn.mobilizer.channel.posm.vo.PromotionMaterialStockVo;

@Repository
public class PromotionMaterialStockXDao extends MyBatisDao {

	public PromotionMaterialStockXDao(){
		super("PROMOTIONMATERIALSTOCKX");
	}
	public Integer findpromotionMaterialStockVoCountByparam(
			Map<String, Object> params) {
		return super.get("findpromotionMaterialStockVoCountByparam", params);
	}

	public List<PromotionMaterialStockVo> findpromotionMaterialStockVoByparam(
			Map<String, Object> params) {
		return super.getList("findpromotionMaterialStockVoByparam", params);
	}
	
	/**
	 * 根据客户编号获取所有物料库存
	 * @param clienId
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月14日
	 */
	public List<PromotionMaterialStock> selectMaterialStockByClientId(Integer clienId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clienId);
		return super.getList("selectMaterialStockByClientId", params);
	}
	
	
	public PromotionMaterialStock  getMaterialStockByMaterialIdAndWarehouseId(Integer materialId,Integer warehouseId , Integer clientId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clientId", clientId);
		map.put("materialId", materialId);
		map.put("warehouseId", warehouseId);
		List<PromotionMaterialStock> list = super.getList("getMaterialStockByMaterialIdAndWarehouseId", map);
		return null != list && list.size() > 0 ?list.get(0):null;
	}

	
	public Integer insertMaterialStocks(List<PromotionMaterialStock> list ){
		return super.insert("insertMaterialStocks", list);
	}
	
	/**
	 * 使用需谨慎
	 * @param list
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月14日
	 */
	@Deprecated
	public Integer updataMaterialStocksByKey(List<PromotionMaterialStock> list ){
		return super.update("updataMaterialStocksByKey", list);
	}

	
	public PromotionMaterialStock queryPromotionMaterialStock(Map<String,Object> map){
		return super.get("queryPromotionMaterialStock", map);
	}
	
	public Integer insertSelective(PromotionMaterialStock object){
		return super.insert("insertSelective", object);
	}
	
	public Integer updateByPrimaryKeySelective(PromotionMaterialStock object){
		return super.update("updateByPrimaryKeySelective", object);
	}
	
	
}
