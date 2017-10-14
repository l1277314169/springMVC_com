package cn.mobilizer.channel.systemManager.service;

import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.MobileVersionDetail;

public interface MobileVersionDetailService {
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public int addMobileVersionDetail(MobileVersionDetail mobileVersionDetail)throws BusinessException;
	/**
	 * 
	 * @param mobileVersionDetail
	 * @return
	 * @throws BusinessException
	 */
	public int updateMobileVersionDetail(MobileVersionDetail mobileVersionDetail)throws BusinessException;
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public MobileVersionDetail findMobileVersionDetailByMobileVersionId(Integer MobileVersionId)throws BusinessException;
	
	public List<MobileVersionDetail> findMobileVersionDetailForClientUser(Integer MobileVersionId, Integer clientUserId)throws BusinessException;
}
