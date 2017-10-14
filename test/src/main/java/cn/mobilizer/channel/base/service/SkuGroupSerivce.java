package cn.mobilizer.channel.base.service;

import java.util.List;

import cn.mobilizer.channel.base.po.SkuGroup;
import cn.mobilizer.channel.comm.web.BasicService;

/**产品分组
 * @author Nany
 * 2014年12月15日下午12:04:59
 */
public interface SkuGroupSerivce extends BasicService<SkuGroup> {

	/**查询产品分组
	 * @author Nany
	 * 2014年12月15日下午12:09:49
	 * @param clientId
	 * @return
	 */
	List<SkuGroup> getSkuGroupList(int clientId);

	SkuGroup getSkuGroup(int skuGroupId);

}
