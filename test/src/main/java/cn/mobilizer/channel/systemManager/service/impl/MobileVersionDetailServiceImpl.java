package cn.mobilizer.channel.systemManager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.MobileVersionDetailDao;
import cn.mobilizer.channel.systemManager.po.MobileVersionDetail;
import cn.mobilizer.channel.systemManager.service.MobileVersionDetailService;

@Service
public class MobileVersionDetailServiceImpl implements MobileVersionDetailService {
	@Autowired
	private MobileVersionDetailDao mobileVersionDetailDao;

	@Override
	public int addMobileVersionDetail(MobileVersionDetail mobileVersionDetail) throws BusinessException {
		return mobileVersionDetailDao.insert(mobileVersionDetail);
	}

	public int updateMobileVersionDetail(MobileVersionDetail mobileVersionDetail) throws BusinessException {
		return mobileVersionDetailDao.update(mobileVersionDetail);
	}

	@Override
	public MobileVersionDetail findMobileVersionDetailByMobileVersionId(Integer mobileVersionId) throws BusinessException {

		return mobileVersionDetailDao.queryMobileVersionDetailList(mobileVersionId);
	}

	public List<MobileVersionDetail> findMobileVersionDetailForClientUser(Integer mobileVersionId, Integer clientUserId)throws BusinessException{
		return mobileVersionDetailDao.findMobileVersionDetailForClientUser(mobileVersionId, clientUserId);
	}
}
