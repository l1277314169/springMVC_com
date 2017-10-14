/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class WyethContractDetailDao extends MyBatisDao {
	public WyethContractDetailDao() {
		super("WYETH_CONTRACT_DETAIL");
	}
	public void batchAddSales(List<WyethContractDetail> sales){
		super.insert("batchAddSales", sales);
	}
	
	public List<WyethContractDetail> queryWyethContractDetailByContractId(String contractId){
		
		return super.queryForList("queryWyethContractDetailByContractId", contractId);
	}
	public void deleteWyethContractDetail(Map<String, Object> params) {
		
		super.update("updateByContractIdKeySelective", params);
	}
	public void updateContractDetail(List<WyethContractDetail> updates) {
		for (WyethContractDetail wyethContractDetail : updates) {
			super.update("updateByContractIdKeySelective", wyethContractDetail);
		}
		
		
	}
	public List<WyethContractDetail> findContractDetailByParams(
			Map<String, Object> params) {
		return super.queryForList("findContractDetailByParams", params);
	}
	
}
