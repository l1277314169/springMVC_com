package cn.mobilizer.channel.wrTask.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrCustomer;

@Repository
public class WrCustomerDao extends MyBatisDao{
	
	public WrCustomerDao() {
		super("WR_CUSTOMER");
	}
	/**
	 * 删除客户
	 * @param customerId
	 * @return
	 */
    public void deleteByPrimaryKey(Integer customerId){
    	super.delete("deleteByPrimaryKey", customerId);
    }
   
    /**
     * 添加客户
     * @param record
     * @return
     */
    public int insert(WrCustomer record){
    	super.insert("insert", record);
    	 return record.getCustomerId();
    }

    public int insertSelective(WrCustomer record){
    	super.insert("insertSelective", record);
    	return record.getCustomerId();
    }
    /**
     * 根据id查找客户
     * @param customerId
     * @return
     */
    public WrCustomer selectByPrimaryKey(Integer customerId,Integer clientId){
    	Map<String,Object> param=new HashMap<String,Object>();
    	param.put("customerId",customerId);
    	param.put("clientId", clientId);
    	return super.get("selectByPrimaryKey",param);
    }

    /**
     * 修改客户信息
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(WrCustomer record){
    	super.update("updateByPrimaryKeySelective", record);
    	return record.getCustomerId();
    }

    public int updateByPrimaryKey(WrCustomer record){
    	super.update("updateByPrimaryKey", record);
    	return record.getCustomerId();
    }
    /**
     * 根据id查找所有的客户
     * @param clientId
     * @return
     */
    public List<WrCustomer> selectList(Integer clientId){
    	return super.getList("selectList",clientId);
    }
	/**
	 * 
	 * @param params
	 * @return 总页数
	 */
    public Integer queryTotal(Map<String,Object> params)
    {
    	return super.get("queryTotalCount", params);
    }
    
    public List<WrCustomer> queryWrCustomerListByParam(Map<String,Object> params)
    {
    	return super.getList("findWrCustomerListByParam", params);
    }

  /*  public void  batchWrCustomer(Map<String,Object> params)
    {
    	super.update("isdeleteWrCustomer",params);
    }*/
}