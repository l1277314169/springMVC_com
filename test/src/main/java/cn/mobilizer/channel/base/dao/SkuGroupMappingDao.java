package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuGroupMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

/**产品分组，产品映射
 * @author Nany
 * 2014年12月15日下午2:44:14
 */
@Repository
public class SkuGroupMappingDao extends MyBatisDao {

	public SkuGroupMappingDao(){
		super("SKU_GROUP_MAPPING");
	}

	public void addSkuGroup(SkuGroupMapping skuGroupMapping) {
		super.insert("insertSelective", skuGroupMapping);
		
	}

	/**更新产品分组
	 * @author Nany
	 * 2014年12月15日下午3:06:55
	 * @param skuGroupMapping
	 */
	public void updateSkuGroup(SkuGroupMapping skuGroupMapping) {
		super.update("updateByPrimaryKeySelective", skuGroupMapping);	
	}

	/**根据skuId 和clientId查询产品中间表
	 * @author Nany
	 * 2014年12月15日下午3:43:21
	 * @param parames
	 * @return
	 */
	public SkuGroupMapping getObject(Integer skuId) {
		return super.get("selectByPrimaryKey1", skuId);
	}

	/**产品分组选择为空时，将sku_group_mapping相关值删除(逻辑删除)
	 * @author Nany
	 * 2014年12月16日上午10:59:44
	 * @param skuGroupMapping
	 */
	public void updateSkuGroupMapping(SkuGroupMapping skuGroupMapping) {
		super.update("updateBySkuId", skuGroupMapping);
		
	}

	/**编辑产品，之前，将存在的分组关系删除
	 * @author Nany
	 * 2014年12月16日下午6:09:36
	 * @param skuId
	 * @param skuGroupId
	 */
	public List<SkuGroupMapping> getListByparames(Map<String, Object> parames) {
		return super.getList("selectByMap", parames);
		
	}

	/**
	 * @author Nany
	 * 2014年12月17日上午11:20:18
	 * @param parames1
	 * @return
	 */
	public SkuGroupMapping getSkuGroupMappingByParames(Map<String, Object> parames) {
		
		return super.get("selectByMap1", parames);
	}

	/**通过skuId查询产品分组映射表，把该skuId所有记录设置为删除
	 * @author Nany
	 * 2014年12月17日下午1:34:07
	 * @param parames
	 */
	public void setIsDelete(Map<String, Object> parames) {
		super.update("updateIsdelete", parames);
		
	}
}
