/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class WyethContractFeedbackDao extends MyBatisDao {
	public WyethContractFeedbackDao() {
		super("WYETH_CONTRACT_FEEDBACK");
	}

	public void batchAddSales(List<WyethContractFeedback> sales) {
		super.insert("batchAddSales", sales);
	}

	public void addList(List<WyethContractFeedback> wyethContractFeedbacks) {
		for (WyethContractFeedback wyethContractFeedback : wyethContractFeedbacks) {
			super.insert("insertSelective", wyethContractFeedback);
		}
	}

	public Map<String, WyethContractFeedback> queryWyethContractFeedbackList(
			Map<String, Object> params) {
		return super.queryForMap("queryWyethContractFeedbackList", params,
				"contractId");
	}

	public Map<String, WyethContractFeedback> queryWyethContractFeedbackListByStatusAndMonthId(
			Map<String, Object> params) {
		return super.queryForMap(
				"queryWyethContractFeedbackListByStatusAndMonthId", params,
				"contractId");
	}
	
	
	public List<ContractFeedbackVo> getContractFeedbackVo(Map<String, Object> params){
		return super.queryForList("getContractFeedbackVo",params);
	}
	public WyethContractFeedback queryBycontractId(String contractId) {
		
		return super.get("queryBycontractId", contractId);
	}


	public void confirmContractFeedback(Map<String, Object> params) {
		super.update("updateByPrimaryKeySelective", params);
		
	}

	public void deleteWyethContractFeedback(Map<String, Object> params) {
		super.update("updateByContractIdKeySelective", params);
	}

	

	public void updateByPrimaryKeySelective(WyethContractFeedback wyethContractFeedback){
		super.update("updateByPrimaryKeySelective", wyethContractFeedback);
	}

	
	public WyethContractFeedback findBycontractId(String contractId){
		return super.get("findBycontractId", contractId);
	}

	public void uploadInvoiceImages(Map<String, Object> params) {
		super.update("updateByPrimaryKeySelective", params);
		
	}

	public WyethContractFeedback findInvoicePicByParmas(
			Map<String, Object> params) {
	
		return super.get("findInvoicePicByParmas", params);
	}

	public void updateFeeback(WyethContractFeedback contractFeedback) {
	super.update("updateByPrimaryKeySelective", contractFeedback);
		
	}
	
	public void batchChecked(String feedbackIds){
		super.update("batchChecked", feedbackIds);
	}
	
	public void batchDel(String feedbackIds){
		super.update("batchDel", feedbackIds);
	}
}
