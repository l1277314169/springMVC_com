package cn.mobilizer.channel.sales.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.FactMonthlySales;
import cn.mobilizer.channel.sales.po.FactMonthlySales4Ferrero;

@Repository
public class FactMonthlySalesDao extends MyBatisDao{
	
	public FactMonthlySalesDao() {
		super("FACT_MONTHLY_SALES");
	}
	
	public String insert(FactMonthlySales factMonthlySales){
		super.insert ("insert", factMonthlySales);
		return factMonthlySales.getDataId();
	}
	
	public String insertSelective(FactMonthlySales factMonthlySales){
		super.insert("insertSelective", factMonthlySales);
		return factMonthlySales.getDataId();
	}
	
	public int updateByPrimaryKeySelective(FactMonthlySales factMonthlySales){
		return super.update("updateByPrimaryKeySelective", factMonthlySales); 
	}
	
	public FactMonthlySales selectByPrimaryKey(Integer dataId){
		return super.get("selectByPrimaryKey",dataId);
	}

	public int update(FactMonthlySales factMonthlySales){
		return super.update("updateByPrimaryKey", factMonthlySales); 
	}
	
	public List<FactMonthlySales> selectByParams(Map<String, Object> param){
		return super.queryForList("selectByParams", param); 		
	}
	
	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
	public List<FactMonthlySales> getSkuIdNullEntityByParam(Map<String, Object> param){
		return super.queryForList("getSkuIdNullEntityByParam", param); 		
	}
	
	public List<?> getEntityListByProcedure(Map<String, Object> params){
		List<?> list = super.getList("getEntityListByProcedure",params);
		return list;
	}
	
	public FactMonthlySales getEntityByDataId(Map<String,Object> param){
		return super.get("getEntityByDataId", param);
	}
	
}