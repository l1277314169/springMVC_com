/**   
* @Title: PromotionMaterialStockService.java 
* @Package cn.mobilizer.channel.posm.service 
* @author 仪动信息技术（上海）有限公司
* @date 2015年9月25日 下午2:58:40 
* @version V1.0   
*/
package cn.mobilizer.channel.posm.service;

import java.util.List;


import cn.mobilizer.channel.posm.po.PromotionMaterialStock;

/** 
 * @ClassName: PromotionMaterialStockService 
 * @Description: 物料库存
 * @author pengwei
 * @date 2015年9月25日 下午2:58:40 
 *  
 */

public interface MaterialStockService {
	
	/**
	 * 
	 * @param list			需要修改的数据集
	 * @param clientId		客户编号
	 * @param userId		用户编号
	 * @param insertTxt		添加数据备注信息
	 * @param updataTxt		修改数据备注信息
	 * @return		修改成功返回 修改成功数量，修改失败返回-1
	 * @author：wei.peng
	 * @date 2015年9月25日
	 * <pre>
	 * 	如果有数据则修改，没有数据新增数据
	 * </pre>
	 */
	public boolean updatePromotionMaterialStocks(List<PromotionMaterialStock> list,Integer clientId,Integer userId,String insertTxt,String updataTxt);

}
