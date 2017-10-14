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

import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.exception.BusinessException;

public interface WyethContractFeedbackService {

	public Map<String, Object> addWyethContractFeedback(HttpServletRequest request,MultipartFile fileField,
			Integer clientId, HttpServletResponse response,
			Integer currentUserId);
	
	public List<ContractFeedbackVo> getContractFeedbackVo(Map<String, Object> params) throws BusinessException;

	public WyethContractFeedback queryBycontractId(String contractId)throws BusinessException;

	public void confirmContractFeedback(Map<String, Object>parmas)throws BusinessException;

	
	public void updateByPrimaryKeySelective(WyethContractFeedback wyethContractFeedback) throws BusinessException;

	
	public WyethContractFeedback findBycontractId(String feedbackId) throws BusinessException;

	public void uploadInvoiceImages(String feedbackId, String invoicePic,
			Integer currentUserId, Integer clientId) throws BusinessException;

	public WyethContractFeedback findInvoicePicByParmas(
			Map<String, Object> params)throws BusinessException;

	public void updateFeeback(WyethContractFeedback contractFeedback)throws BusinessException;
	
	public void batchChecked(String feedbackIds) throws BusinessException;
	
	public void batchDel(String feedbackIds) throws BusinessException;

}
