package cn.mobilizer.channel.systemManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.MobileVersionDao;
import cn.mobilizer.channel.systemManager.dao.MobileVersionDetailDao;
import cn.mobilizer.channel.systemManager.po.MobileVersion;
import cn.mobilizer.channel.systemManager.po.MobileVersionDetail;
import cn.mobilizer.channel.systemManager.service.MobileVersionService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class MobileVersionServiceImpl implements MobileVersionService {
	private static final Log LOG = LogFactory.getLog(MobileVersionServiceImpl.class);
	@Autowired
	private MobileVersionDao mobileVersionDao;
	@Autowired
	private MobileVersionDetailDao mobileVersionDetailDao;
	@Override
	public MobileVersion findNewMobileVersion(Integer clientId, Integer clientUserId, String version, Integer appCode) {
		return mobileVersionDao.getNewMobileVersion(clientId, clientUserId, version, appCode);
	}

	@Override
	public Integer queryMobileVersionCount(Map<String, Object> searchParams) throws BusinessException {
		int count = 0;
		try {
			if ((searchParams != null) && (searchParams.size() > 0)) {
				count = mobileVersionDao.queryMobileVersionCount(searchParams);
			}
		} catch (BusinessException e) {
			LOG.error("method queryMobileVersionCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<MobileVersion> queryMobileVersionList(Map<String, Object> param) throws BusinessException {
		List<MobileVersion> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = mobileVersionDao.findMobileVersionList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryMobileVersionList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public int addMobileVersion(MobileVersion mobileVersion) {
		try {
			mobileVersionDao.insert(mobileVersion);
			if(mobileVersion.getCheckboxId() != null && mobileVersion.getCheckboxId() != ""){
				String[] clientUserId=mobileVersion.getCheckboxId().split(",");
				for (int i = 0; i < clientUserId.length; i++) {
					String str = clientUserId[i];
					if(StringUtil.isNotEmptyString(str)){
						MobileVersionDetail mobileVersionDetail=new MobileVersionDetail();
						mobileVersionDetail.setClientId(mobileVersion.getClientId());
						mobileVersionDetail.setClientUserId(Integer.parseInt(str));
						mobileVersionDetail.setMobileVersionId(mobileVersion.getMobileVersionId());
						mobileVersionDetailDao.insert(mobileVersionDetail);
					}
				}
			}
			
			return mobileVersion.getMobileVersionId();
			
		} catch (BusinessException e) {
			LOG.error("method addMobileVersion error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addMobileVersion error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	@Override
	public int updateMobileVersion(MobileVersion mobileVersion) {
		int rows=mobileVersionDao.update(mobileVersion);
		mobileVersionDetailDao.updateMobileVersionDetailIsdelete(mobileVersion.getMobileVersionId());
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(mobileVersion.getCheckboxId() != null && mobileVersion.getCheckboxId() != ""){
			String[] clientUserId=mobileVersion.getCheckboxId().split(",");
			for (int i = 0; i < clientUserId.length; i++) {
				String str = clientUserId[i];
				if(StringUtil.isNotEmptyString(str)){
					parameters.put("clientUserId", Integer.parseInt(str));
					parameters.put("mobileVersionId", mobileVersion.getMobileVersionId());
					MobileVersionDetail mobileVersionDetail=mobileVersionDetailDao.findMobileVersionDetail(parameters);
					if(mobileVersionDetail != null){
						mobileVersionDetail.setIsDelete(Constants.IS_DELETE_FALSE);
						mobileVersionDetail.setLastUpdateTime(null);
						mobileVersionDetailDao.updateMobileVersionDetailIsdelete_1(mobileVersionDetail);
					}else{
						MobileVersionDetail mobileVersionDetail_s=new MobileVersionDetail();
						mobileVersionDetail_s.setClientId(mobileVersion.getClientId());
						mobileVersionDetail_s.setClientUserId(Integer.parseInt(clientUserId[i]));
						mobileVersionDetail_s.setMobileVersionId(mobileVersion.getMobileVersionId());
						mobileVersionDetailDao.insert(mobileVersionDetail_s);
					}
				}
			}
		}
		
		return rows;
	}

	@Override
	public int deleteMobileVersion(Integer mobileVersionId) {
		int result = 0;
		MobileVersion mobileVersion = new MobileVersion();
		try {
			// result = clientUserDao.delete(clientUserId);
			mobileVersion.setMobileVersionId(mobileVersionId);
			mobileVersion.setIsDelete(Constants.IS_DELETE_TRUE);
			result = mobileVersionDao.update(mobileVersion);
		} catch (BusinessException e) {
			LOG.error("method deleteMobileVersion error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteMobileVersion error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public MobileVersion findByPrimaryKey(Integer mobileVersionId) {
		return mobileVersionDao.findByPrimaryKey(mobileVersionId);
	}

	@Override
	public String findByParams(Integer clientId, Boolean isEnable, Byte appCode) throws BusinessException {
		String str = null;
		try {
			Map<String,Object> searchParams=new HashMap<String,Object>();
			searchParams.put("clientId", clientId);
			searchParams.put("appCode", appCode);
			searchParams.put("isEnable", isEnable);
			str =  mobileVersionDao.queryUrlByParams(searchParams);
		}catch (BusinessException e) {
			LOG.error("method findByParams error, ", e);
			throw e;
		} 
		return str;
	}


}
