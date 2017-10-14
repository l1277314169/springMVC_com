package cn.mobilizer.channel.sales.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.FactMonthlyCbSales;

@Repository
public class FactMonthlyCbSalesDao extends MyBatisDao {

	public FactMonthlyCbSalesDao(){
		super("FACT_MONTHLY_CB_SALES");
	}
	
	public void batchInsertSales(List<FactMonthlyCbSales> fmcs){
		super.insert("batchInsertSales", fmcs);
	}
	
	public void updateSales(List<FactMonthlyCbSales> fmcs){
		for (FactMonthlyCbSales factMonthlyCbSales : fmcs) {
			super.update("updateByPrimaryKeySelective", factMonthlyCbSales);
		}
	}
	
	
	public List<FactMonthlyCbSales> getSalesByMonth(Map<String, Object> params){
		return super.queryForList("getSalesByMonth",params);
	}
	
}
