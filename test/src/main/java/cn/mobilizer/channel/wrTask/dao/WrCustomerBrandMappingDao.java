package cn.mobilizer.channel.wrTask.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrCustomerBrandMapping;

@Repository
public class WrCustomerBrandMappingDao extends MyBatisDao{
	
	public WrCustomerBrandMappingDao() {
		super("WR_CUSTOMER_BRAND_MAPPING");
	}
	
	/**
	 * 删除
	 * @param mappingId
	 * @return
	 */
    public void deleteByPrimaryKey(Integer mappingId){
    	 super.delete("deleteByPrimaryKey", mappingId);
    }

    /**
     * 新增
     * @param record
     * @return
     */
    public void insert(WrCustomerBrandMapping record){
    	 super.insert("insert", record);
    }

    public void insertSelective(WrCustomerBrandMapping record){
    	 super.insert("insertSelective", record);
    }

    /**
     * 根据ID查找
     * @param mappingId
     * @return
     */
    public WrCustomerBrandMapping selectByCustomerId(Integer customerid){
    	return super.get("selectByCustomerId", customerid);
    }

    /**
     * 修改
     * @param record
     */
    public void updateByPrimaryKeySelective(WrCustomerBrandMapping record){
    	 super.get("updateByPrimaryKeySelective", record);
    }

    public void updateByPrimaryKey(WrCustomerBrandMapping record){
    	 super.update("updateByPrimaryKey", record);
    }
    
    
    public WrCustomerBrandMapping getWrCustomerBrandMappingByCusId(Map<String,Object> params){
    	return super.get("getWrCustomerBrandMappingByCusId", params);
    }
    
    /**
     * 分页
     * @param param
     * @return 总数量
     */
    public Integer  queryTotalByParam(Map<String,Object> param)
    {
    	return super.get("queryTotal", param);
    }
    
    public List<WrCustomerBrandMapping> queryWrCustomerBrandMappingList(Map<String,Object> param)
    {
    	return super.getList("queryWrCustomerBrandMappingList", param);
    }
   
    public WrCustomerBrandMapping wrCustomerBrandMapping(Map<String,Object> params){
		return super.get("customerAndBrandMapping", params);
	}
    public int currentMapppingisdelte(Map<String,Object> params){
		return super.update("currentMapppingisdelte", params);
	}

}