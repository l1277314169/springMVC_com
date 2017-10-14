/**   
* @Title: PromotionMaterialStockServiceImpl.java 
* @Package cn.mobilizer.channel.posm.service.impl 
* @author 仪动信息技术（上海）有限公司
* @date 2015年9月25日 下午3:01:23 
* @version V1.0   
*/
package cn.mobilizer.channel.posm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.posm.dao.PromotionMaterialStockXDao;
import cn.mobilizer.channel.posm.dao.PromotionMaterialXDao;
import cn.mobilizer.channel.posm.po.PromotionMaterialStock;
import cn.mobilizer.channel.posm.service.MaterialStockService;

/** 
 * @ClassName: PromotionMaterialStockServiceImpl 
 * @Description: 库存信息
 * @author pengwei
 * @date 2015年9月25日 下午3:01:23 
 *  
 */
@Service
public class MaterialStockServiceImpl implements MaterialStockService {
	
	@Autowired
	private PromotionMaterialStockXDao materialStockXDao;
	
	@Override
	public boolean updatePromotionMaterialStocks(List<PromotionMaterialStock> list,Integer clientId,Integer userId,String insertTxt,String updataTxt) {
		List<PromotionMaterialStock> inertList = new ArrayList<PromotionMaterialStock>();
		List<PromotionMaterialStock> updataList = new ArrayList<PromotionMaterialStock>();
		for(PromotionMaterialStock  materialStock:list){
			PromotionMaterialStock promotionMaterialStock = materialStockXDao.getMaterialStockByMaterialIdAndWarehouseId(materialStock.getMaterialId(), materialStock.getWarehouseId(), clientId);
			if(null != promotionMaterialStock){
				//修改信息
				promotionMaterialStock.setRemark(updataTxt);
				promotionMaterialStock.setUsedTime(new Date());
				promotionMaterialStock.setLastUpdateBy(userId);
				promotionMaterialStock.setQuantity(materialStock.getQuantity());
				promotionMaterialStock.setBillType(materialStock.getBillType());
				updataList.add(promotionMaterialStock);
			}else{	//添加
				promotionMaterialStock = new PromotionMaterialStock();
				promotionMaterialStock.setMaterialId(materialStock.getMaterialId());
				promotionMaterialStock.setWarehouseId(materialStock.getWarehouseId());
				promotionMaterialStock.setQuantity(materialStock.getQuantity());
				promotionMaterialStock.setClientId(clientId);
				promotionMaterialStock.setCreateBy(userId);
				promotionMaterialStock.setRemark(insertTxt);
				promotionMaterialStock.setBillType(materialStock.getBillType());
				inertList.add(promotionMaterialStock);
			}
		}
		Integer insert = 0;
		if(null != inertList && inertList.size() > 0){
			insert = materialStockXDao.insertMaterialStocks(inertList);
		}
		Integer updata= 0;
		if(null != updataList && updataList.size() > 0){
			updata = materialStockXDao.updataMaterialStocksByKey(updataList);
		}
		if(insert < 0 || updata < 0){
			throw new RuntimeException();
		}else{
			return true;
		}
	}

}
