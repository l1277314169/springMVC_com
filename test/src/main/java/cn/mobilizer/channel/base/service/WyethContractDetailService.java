/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.exception.BusinessException;

public interface WyethContractDetailService {

	List<WyethContractDetail> queryWyethContractDetailByContractId(
			String contractId)throws BusinessException;

	List<WyethContractDetail> findContractDetailByParams(
			Map<String, Object> params)throws BusinessException;

}
