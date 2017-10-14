/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.WyethContractDetailDao;
import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.base.service.WyethContractDetailService;
import cn.mobilizer.channel.exception.BusinessException;
@Service
public class WyethContractDetailServiceImpl implements WyethContractDetailService {
	@Autowired
	private WyethContractDetailDao wyethContractDetailDao;
	@Override
	public List<WyethContractDetail> queryWyethContractDetailByContractId(
			String contractId) {
		
		return wyethContractDetailDao.queryWyethContractDetailByContractId(contractId);
	}
	@Override
	public List<WyethContractDetail> findContractDetailByParams(
			Map<String, Object> params) throws BusinessException {
		
		return wyethContractDetailDao.findContractDetailByParams(params);
	}

}
