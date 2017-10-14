package cn.mobilizer.channel.promotion.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.DistributorDao;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.Distributor;
import cn.mobilizer.channel.base.service.DistributorService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.promotion.dao.PromotionActivityDao;
import cn.mobilizer.channel.promotion.po.PromotionActivity;
import cn.mobilizer.channel.promotion.service.PromotionActivityService;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class PromotionActivityServiceImpl implements PromotionActivityService {
	private static final Log LOG = LogFactory.getLog(PromotionActivityServiceImpl.class);
	
	@Autowired
	private PromotionActivityDao promotionActivityDao;
	


	@Override
	public List<PromotionActivity> queryPromotionActivityList(Map<String, Object> param) {
		List<PromotionActivity> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = promotionActivityDao.queryPromotionActivityList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryPromotionActivityList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}


	@Override
	public Integer queryPromotionActivityCount(Map<String, Object> param) {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = promotionActivityDao.queryPromotionActivityCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryPromotionActivityCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}


	@Override
	public List<PromotionActivity> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PromotionActivity getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PromotionActivity findByLoginNameAndClientName(String loginName,
			String clientName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PromotionActivity findByLoginName(String loginName, int clientId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PromotionActivity findByLoginNameAndLoginPWD(String loginName,
			String loginPWD) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PromotionActivity findByPrimaryKey(Integer clientUserid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int addPromotionActivity(PromotionActivity promotionActivity) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int update(PromotionActivity promotionActivity) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int deletePromotionActivity(Integer clientUserId) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean saveAll(List<PromotionActivity> users) {
		// TODO Auto-generated method stub
		return false;
	}







	
}
