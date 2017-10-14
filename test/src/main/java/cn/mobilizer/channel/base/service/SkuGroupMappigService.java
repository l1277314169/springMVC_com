package cn.mobilizer.channel.base.service;




import cn.mobilizer.channel.base.po.SkuGroupMapping;
import cn.mobilizer.channel.comm.web.BasicService;

public interface SkuGroupMappigService extends BasicService<SkuGroupMapping> {
	
	/**根据skuId和clientId查询中间表
	 * @author Nany
	 * 2014年12月15日下午3:29:51
	 * @param skuId
	 * @param clientId
	 * @return 
	 */
	SkuGroupMapping getObject(Integer skuId);

}
