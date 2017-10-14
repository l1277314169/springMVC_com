package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ChainDao;
import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.service.ChainService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class ChainServiceImpl implements ChainService {
	private static final Log LOG = LogFactory.getLog(ChainServiceImpl.class);
	@Autowired
	private ChainDao chainDao;

	
	@Override
	public List<TreeNodeVO> getTreeNodes(Integer clientId,Integer id,Integer channelId) throws BusinessException{
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("id", id);
			paramMap.put("channelId", channelId);
			ls = chainDao.getTreeNodesByPId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	
	}
	
	
	
	
	
	@Override
	public List<Chain> getObjectList(Map<String, Object> parames) {
		return chainDao.getListByParames(parames);
	}

	@Override
	public Chain getObject(Map<String, Object> parames) {
		return chainDao.getOneByParames(parames);
	}

	@Override
	public Integer queryChainCount(Map<String, Object> param)
			throws BusinessException {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = chainDao.queryChainCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryChainCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Chain> queryChainList(Map<String, Object> param)
			throws BusinessException {
		List<Chain> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = chainDao.queryChainList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryChainList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public int addChain(Chain chain) {
		try {
			return chainDao.insert(chain);
		} catch (BusinessException e) {
			LOG.error("method addChain error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addChain error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	@Override
	public int update(Chain chain) {
		return chainDao.update(chain);
		
	}

	@Override
	public int deleteChain(Integer chainId) {
		int result = 0;
		Chain chain = new Chain();
		try {
//			result = clientUserDao.delete(clientUserId);
			chain.setChainId(chainId);
			chain.setIsDelete (Constants.IS_DELETE_TRUE);
			result = chainDao.update(chain);
		} catch (BusinessException e) {
			LOG.error("method deleteChain error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteChain error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public Chain findByPrimaryKey(Integer chainId) {
		return chainDao.findChannelById(chainId);
	
	}

	@Override
	public List<Chain> findChainName(Object Params) throws BusinessException {
		List<Chain> list = chainDao.findChainList(Params);
		if(list !=null && list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public List<Chain> queryChainList(Integer clientId) throws BusinessException{
		List<Chain> ls = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientId", clientId);
			paramMap.put("isDelete", Constants.IS_DELETE_FALSE);
			ls = chainDao.queryChainList(paramMap);
		} catch (BusinessException e) {
			LOG.error("method queryChainList error, ", e);
			throw e;
		}
		return ls;
	}
	@Override
	public Chain getChainByChainId(Integer chainId, Integer clientId) throws BusinessException {
		Chain chain = null;
		try {
			chain = chainDao.getChainByChainId(chainId);
		} catch (BusinessException e) {
			LOG.error("method getChainByChainId error, ", e);
			throw e;
		}
		return chain;
	}

	@Override
	public Chain findChainRelateChannel(Integer chainId)
			throws BusinessException {
		return chainDao.findChainAndChannel(chainId);
	}

	@Override
	public List<Chain> findChainByChainName(Map<String, Object> params) throws BusinessException {
		return chainDao.findChainByChainName(params);
	}





	@Override
	public Map<String,Chain> queryChainByclientId(Integer id) throws BusinessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clientId", id);
		return chainDao.getChainMap(paramMap);
	}

}
