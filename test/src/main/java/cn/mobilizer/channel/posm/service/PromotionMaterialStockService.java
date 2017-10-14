package cn.mobilizer.channel.posm.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.po.PromotionMaterialStock;
import cn.mobilizer.channel.posm.vo.PromotionMaterialStockVo;

public interface PromotionMaterialStockService {

	public Integer findpromotionMaterialStockVoCountByparam(
			Map<String, Object> params) throws BusinessException;

	public List<PromotionMaterialStockVo> findpromotionMaterialStockVoByparam(
			Map<String, Object> params) throws BusinessException;
	
	
	/**
	 * 
	 * @param materialId		物料编号
	 * @param warehouseId		仓库编号
	 * @param ClientId			客户编号
	 * @return	
	 * @author：wei.peng
	 * @date 2015年10月9日
	 * 
	 */
	public PromotionMaterialStock getPmsByClientIdAndMaterialIdAndWarehouseId(Integer materialId,Integer warehouseId,Integer clientId);

	/**
	 * 判断库存是否足够
	 * @param materialId		物料编号	
	 * @param warehouseId		仓库编号
	 * @param clientId			客户编号
	 * @param quantity			验证的数量(数量必须为正数 大于0)
	 * @return	
	 * <pre>
	 * 返回说明
	 * 		true 库存足够
	 * 		false 库存不足
	 * </pre>
	 * @author：wei.peng
	 * @date 2015年10月10日
	 * 
	 */
	public boolean isQuantity(Integer materialId,Integer warehouseId,Integer clientId,Integer quantity);
	/**
	 * 修改库存
	 * @param materialStock
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public boolean findPromotionMaterialStock(PromotionMaterialStock materialStock);
	/**
	 * 添加库存
	 * @param materialStock
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public boolean savePromotionMaterialStock(PromotionMaterialStock materialStock);
	
	/**
	 * 
	 * @param imputWarehouseId		导入仓库编号
	 * @param outWarehouseId		导出仓库编号
	 * @param materialId			物料编号
	 * @param storeId				门店编号
	 * @param quantity				数量
	 * @param userId				用户编号
	 * @param clientId				客户编号
	 * @param remark				备注
	 * @return		
	 * <pre>
	 * 返回说明
	 * 	"true"  		修改成功
	 * 	"false"			修改失败
	 * "库存不足"		库存不足
	 * "没有库存"		没有库存
	 * 
	 * 业务逻辑
	 * 	门店编号跟导入仓库编号只能存在一个，如果门店不为空则从导出仓库出物料
	 *  导入仓库编号不为空，从导出仓库编号中导出无物料，导入到导入仓库中
	 * 
	 * </pre>
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public String updataPmsQuantity(Integer imputWarehouseId,Integer outWarehouseId,Integer materialId,String storeId,Integer quantity,Integer userId,Integer clientId,String remark);
}
