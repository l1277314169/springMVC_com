package cn.mobilizer.channel.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ChannelDao;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class ChannelServiceImpl implements ChannelService {
	private static final Log LOG = LogFactory.getLog(ChannelServiceImpl.class);
	
	@Autowired
	private ChannelDao channelDao;
	
	@Override
	public List<Channel> findChannelsByClientId(Integer clientId) throws BusinessException{
		List<Channel> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			params.put("_orderby", "CHANNEL_ID");
			params.put("_order", "DESC");
			list = channelDao.queryChannelList(params);
		} catch (BusinessException e) {
			LOG.error("method findChannelsByClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return list;
	}
	
	@Override
	public List<TreeNodeVO> getTreeNodes(Integer clientId,Integer id) throws BusinessException{
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("id", id);
			ls = channelDao.getTreeNodesByPId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	
	}

	@Override
	public List<Channel> getObjectList(Map<String, Object> parames) {
		return channelDao.getListByParames(parames);
	}

	@Override
	public Channel getObject(Map<String, Object> parames) {
		return channelDao.getOneByParames(parames);
	}

	@Override
	public Integer queryChannelCount(Map<String, Object> param)
			throws BusinessException {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = channelDao.queryChannelCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryChannelCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Channel> queryChannelList(Map<String, Object> param)
			throws BusinessException {
		List<Channel> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = channelDao.queryChannelList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryChannelList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	


	@Override
	public int addChannel(Channel channel) {
		try {
			return channelDao.insert(channel);
		} catch (BusinessException e) {
			LOG.error("method addChannel error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addChannel error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	
	
	@Override
	public int update(Channel channel) {
		return channelDao.update(channel);
	}
	
	
	public int deleteChannel(Integer channelId) throws BusinessException {
		int result = 0;
		Channel channel = new Channel();
		try {
//			result = clientUserDao.delete(clientUserId);
			channel.setChannelId(channelId);
			channel.setIsDelete (Constants.IS_DELETE_TRUE);
			result = channelDao.update(channel);
		} catch (BusinessException e) {
			LOG.error("method deleteChannel error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteChannel error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public Channel findByPrimaryKey(Integer channelId) {
		
		return channelDao.findChannelById(channelId);
	}

	@Override
	public List<Channel> findChannelName(Object Params) throws BusinessException {
		List<Channel> list = channelDao.findChannelList(Params);
		if(list !=null && list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public String findChannelChild(Integer channelId) throws BusinessException {
		return channelDao.fnGetChannelChild(channelId);
	}

	@Override
	public List<Channel> findChannelByChannelName(Map<String, Object> params)
			throws BusinessException {
		return channelDao.findChannelByChannelName(params);
	}

	@Override
	public List<Channel> findChannelByChannelNameAndParentId(Map<String, Object> params) throws BusinessException {
		return channelDao.findChannelByChannelNameAndParentId(params);
	}

	@Override
	public List<Channel> findFirstChannelByChannelName(Map<String, Object> params) throws BusinessException {
		return channelDao.findFirstChannelByChannelName(params);
	}

	@Override
	public Map<String, Channel> queryChannelByclientId(Integer id) throws BusinessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clientId", id);
		return channelDao.getChannelMap(paramMap);
	}
	
}
