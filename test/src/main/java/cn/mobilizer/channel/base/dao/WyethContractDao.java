/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.WyethContract;
import cn.mobilizer.channel.base.vo.ContractContent;
import cn.mobilizer.channel.base.vo.ContractFeedbackAppVo;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.base.vo.ContractVo;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class WyethContractDao extends MyBatisDao {
	public WyethContractDao() {
		super("WYETH_CONTRACT");
	}
	public void batchAddSales(List<WyethContract> sales) {
		super.insert("batchAddSales", sales);
	}
	
	public Map<String, WyethContract> queryWyethContractByMonthId(Map<String, Object> params){
		 return super.queryForMap("queryWyethContractByMonthId", params,"storeNo");
	}
	public Map<String, WyethContract> queryWyethContract(Map<String, Object> params){
		 return super.queryForMap("queryWyethContract", params,"storeName");
	}
	public Map<String, WyethContract>queryAll(Map<String, Object> params){
		return super.queryForMap("queryAll", params, "keyName");
	}
	
	public List<ContractContent> findContractsByparam(Map<String, Object> params) {
		return super.getList("findContractsByparam",params);
	}
	
	public List<ContractContent> findContractsByparam2(Map<String, Object> params) {
		return super.getList("findContractsByparam2",params);
	}
	
	public Integer findContractCountByparam(Map<String, Object> params){
		return super.get("findContractCountByparam",params);
	}
	
	public Integer findContractCountByparam2(Map<String, Object> params){
		return super.get("findContractCountByparam2",params);
	}
	
	public WyethContract queryWyethContractById(String contractId) {
		
		return super.get("selectByPrimaryKey", contractId);
	}

	public Map<String, WyethContract> queryAllMap(Map<String, Object> params) {
		return super.queryForMap("queryAllMap", params, "keyName");
	}
	public void deleteContract(Map<String, Object> params) {
		
		super.update("updateByPrimaryKeySelective", params);
	}
	public void uploadContractImages(Map<String, Object> params) {
		super.update("updateByPrimaryKeySelective", params);
		
	}
	public Map<String, WyethContract> queryAllKeyName(Map<String, Object> params) {
		return super.queryForMap("queryAllKeyName", params, "keyName");
	}

	
	public List<ContractFeedbackVo> findCheckedWyethContract(Map<String, Object> params){
		return super.getList("findCheckedWyethContract", params);
	}
	
	public List<ContractFeedbackVo> findCheckedWyethContract2(Map<String, Object> params){
		return super.getList("findCheckedWyethContract2", params);
	}
	
	public Integer findCheckedWyethContractCount(Map<String, Object> params){
		return super.get("findCheckedWyethContractCount",params);
	}
	
	public Integer findCheckedWyethContractCount2(Map<String, Object> params){
		return super.get("findCheckedWyethContractCount2",params);
	}
	
	public List<TreeMap<String, String>> selectExportData(Map<String, Object> params){
		return super.getList("selectExportData",params);
	}
	
	public List<TreeMap<String, String>> selectExportData2(Map<String, Object> params){
		return super.getList("selectExportData2",params);
	}
	
	public ContractContent findContractPicByparam(Map<String, Object> params) {
		return super.get("findContractPicByparam", params);
	}
	public ContractContent getContractsByparam(Map<String, Object> params) {
		
		return super.get("getContractsByparam", params);
	}
	public void updateContract(ContractContent cc) {
		super.update("updateByPrimaryKeySelective", cc);
		
	}
	public ContractFeedbackVo findCheckedWyethContractById(
			Map<String, Object> params) {
		
		return super.get("findCheckedWyethContractById", params);
	}
	
	public void batchDelContract(String contractIds){
		super.update("batchDelContract", contractIds);
	}

	public List<ContractVo> getContractList(Map<String, Object> params) {
		
		return super.queryForList("getContractList", params);
	}
	public List<ContractVo> getContractList2(Map<String, Object> params) {
		
		return  super.queryForList("getContractList2", params);
	}
	
	public List<TreeMap<String, String>> selectExportHTData(Map<String, Object> params){
		return super.getList("selectExportHTData",params);
	}
	public List<TreeMap<String,String>> selectExportHTData2(Map<String,Object> params)
	{
		return super.getList("selectExportHTData2", params);
	}
	public Integer getCountContractList(Map<String, Object> params) {
		
		return super.get("getCountContractList", params);
	}
	public Integer getCountContractList2(Map<String, Object> params) {
		
		return super.get("getCountContractList2", params);
	}

	public List<ContractFeedbackAppVo> findApiCheckedWyethContract(Map<String, Object> params){
		return super.getList("findApiCheckedWyethContract",params);
	}

	
	public List<TreeMap<String, String>> selectExportHTSXData(Map<String, Object> params){
		return super.getList("selectExportData3",params);
	}
    public List<TreeMap<String,String>> selectExportHTSXData2(Map<String,Object> params)
    {
    	return super.getList("selectExportData4",params);
    }
	
	public Integer findApiCheckedWyethContractCount(Map<String, Object> params){
		return super.get("findApiCheckedWyethContractCount", params);
	}
	
	public Integer findApiCheckedWyethContractCount2(Map<String, Object> params){
		return super.get("findApiCheckedWyethContractCount2", params);
	}
	
	public List<ContractFeedbackAppVo> findApiCheckedWyethContract2(Map<String, Object> params){
		return super.getList("findApiCheckedWyethContract2", params);
	}
	public Integer getCheckContract(Map<String, Object> params) {
		
		return super.get("getCheckContract", params);
	}
	public ContractContent findContractByPrimaryKey(Map<String, Object> params) {
		
		return super.get("getContractsByparam", params);
	}

}
