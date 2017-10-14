package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuGroup;
import cn.mobilizer.channel.base.po.SkuGroupMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class SkuGroupDao extends MyBatisDao {
	public SkuGroupDao(){
		
		super("SKU_GROUP");
	}

	/**查询产品分组
	 * @author Nany
	 * 2014年12月15日下午12:21:50
	 * @param parames
	 * @return
	 */
	public List<SkuGroup> getSkuGroupList(Map<String, Object> parames) {
		
		return super.getList("findListByParams", parames);
	}

	public SkuGroup getSkuGroup(int skuGroupId) {
		return super.get("selectByPrimaryKey", skuGroupId);
	}	
}
