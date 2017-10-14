/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class StoreGroupDao extends MyBatisDao {
	
	public StoreGroupDao() {
		super("STORE_GROUP");
	}
	
	public List<StoreGroup> queryStoreGroupList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	public StoreGroup getStoreGroupByStoreGroupId(Integer storeGroupId) {
		return super.get("selectByPrimaryKey", storeGroupId);
	}
}
