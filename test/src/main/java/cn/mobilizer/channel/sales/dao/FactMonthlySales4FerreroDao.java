package cn.mobilizer.channel.sales.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.FactMonthlySales4Ferrero;

@Repository
public class FactMonthlySales4FerreroDao extends MyBatisDao {

	public FactMonthlySales4FerreroDao() {
		super("FACT_MONTHLY_SALES4_FERRERO");
	}

	public Map<String, FactMonthlySales4Ferrero> getSalesByDayId(Map<String, Object> params) {

		return super.queryForMap("getSalesByDayId", params, "storeAndSku");
	}

	public void batchAddSales(List<FactMonthlySales4Ferrero> sales) {
		super.insert("batchAddSales", sales);
	}

	public void batchUpdateSales(List<FactMonthlySales4Ferrero> sales) {
		for (FactMonthlySales4Ferrero factMonthlySales4Ferrero : sales) {
			super.update("updateByPrimaryKeySelective", factMonthlySales4Ferrero);
		}
	}
	
}
