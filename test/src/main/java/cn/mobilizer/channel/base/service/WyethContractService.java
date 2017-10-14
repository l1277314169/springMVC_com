/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.WyethContract;
import cn.mobilizer.channel.base.vo.ContractContent;
import cn.mobilizer.channel.base.vo.ContractFeedbackAppVo;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.base.vo.ContractVo;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.export.po.DataInfo;

public interface WyethContractService {

	public Object addContract(HttpServletRequest request,MultipartFile fileField, Integer clientId,
			HttpServletResponse response,Integer currentUserId) throws BusinessException;

	public List<ContractContent> findContractsByparam(Map<String, Object> params,boolean flag) throws BusinessException;

	public Integer findContractCountByparam(Map<String, Object> params,boolean flag) throws BusinessException;

	public WyethContract queryWyethContractById(String contractId)throws BusinessException;


	public void deleteContract(String contractId,Integer currentUserId, Integer clientId)throws BusinessException;

	public void uploadContractImages(String contractPic,String contractId, Integer currentUserId,
			Integer clientId)throws BusinessException;

	
	public List<ContractFeedbackVo> findCheckedWyethContract(Map<String, Object> params,boolean flag) throws BusinessException;
	
	public Integer findCheckedWyethContractCount(Map<String, Object> params,boolean flag) throws BusinessException;
	
	public List<DataInfo> selectExportData(Map<String, Object> params,boolean flag)throws BusinessException;

	public ContractContent findContractPicByparam(Map<String, Object> params)throws BusinessException;

	public ContractContent getContractsByparam(Map<String, Object> params)throws BusinessException;

	public void updateContract(ContractContent cc)throws BusinessException;

	public ContractFeedbackVo findCheckedWyethContractById(
			Map<String, Object> params)throws BusinessException;
	
	public void batchDelContract(String contractIds) throws BusinessException;


	public List<ContractVo> getContractList(Map<String, Object> params ,Boolean flag) throws BusinessException;

	
	
	public List<DataInfo> selectExportHTData(Map<String, Object> params,Boolean flag) throws BusinessException;
	
	public List<ContractFeedbackAppVo> findApiCheckedWyethContract(Map<String, Object> params,Boolean flag) throws BusinessException;

	public Integer getCountContractList(Map<String, Object> params,Boolean flag)throws BusinessException;

	
	public List<DataInfo> selectExportHSHXData(Map<String,Object> params,Boolean flag)throws BusinessException;

	
	public Integer findApiCheckedWyethContractCount(Map<String, Object> params)throws BusinessException;

	public Integer findApiCheckedWyethContractCount(Map<String, Object> params,Boolean flag)throws BusinessException;


	public Integer getCheckContract(Map<String, Object> params)throws BusinessException;

	public ContractContent findContractByPrimaryKey(Map<String, Object> params)throws BusinessException;



}
