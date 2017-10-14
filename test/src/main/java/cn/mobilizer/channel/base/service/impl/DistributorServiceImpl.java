package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.dao.DistributorDao;
import cn.mobilizer.channel.base.dao.DistributorUserMappingDao;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Distributor;
import cn.mobilizer.channel.base.po.DistributorUserMapping;
import cn.mobilizer.channel.base.service.DistributorService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class DistributorServiceImpl implements DistributorService {
	private static final Log LOG = LogFactory.getLog(ClientUserServiceImpl.class);
	
	@Autowired
	private DistributorDao distributorDao;
	@Autowired
	private DistributorUserMappingDao distributorUserMappingDao;
	@Autowired
	private ClientUserDao clientUserDao;

	@Override
	public List<Distributor> queryDistributorList(Map<String, Object> param) {
		List<Distributor> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = distributorDao.queryDistributorList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryDistributorList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		
		return list;
	}


	@Override
	public Integer queryDistributorCount(Map<String, Object> param) {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = distributorDao.queryDistributorCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryDistributorCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}



	@Override
	public Distributor findDistributorByprimaryKey(Integer distributorId) {
		if(distributorId!=null){
			return distributorDao.findByPrimaryKey(distributorId);
		}else{
			return null;
		}	
	}

	@Override
	public int updateDistributor(Distributor distributor) {
		int result = 0;
		try {
			result = distributorDao.update(distributor);
			DistributorUserMapping distributorUserMapping=new DistributorUserMapping();
			distributorUserMapping.setMappingId(distributor.getMappingId());
			distributorUserMapping.setClientUserId(distributor.getClientUserId());
			distributorUserMappingDao.update(distributorUserMapping);
		} catch (BusinessException e) {
			LOG.error("method updateDistributor error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method updateDistributor error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return result;
	}

	@Override
	public int addDistributor(Distributor distributor) {
		try {
			int distributorId=distributorDao.insert(distributor);
			DistributorUserMapping distributorUserMapping=new DistributorUserMapping();
			distributorUserMapping.setClientId(distributor.getClientId());
			distributorUserMapping.setClientUserId(distributor.getClientUserId());
			distributorUserMapping.setDistributorId(distributorId);
			distributorUserMappingDao.insert(distributorUserMapping);
			return distributorId;
		} catch (BusinessException e) {
			LOG.error("method addDistributor error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addDistributor error, ", e); 
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}


	@Override
	public int deleteDistributor(Integer distributorId,Integer clientId) {
		int result = 0;
		Distributor distributor = new Distributor();
		try {
//			result = clientPositionDao.delete(clientPositionId);
			distributor.setDistributorId (distributorId);
			distributor.setIsDelete (Constants.IS_DELETE_TRUE);
			result = distributorDao.update(distributor);
			//删除人店关系
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("distributorId", distributorId);
			distributorUserMappingDao.isdeleteDistributorUserMapping(parameters);
		} catch (BusinessException e) {
			LOG.error("method deleteDistributor error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteDistributor error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}


	
	

	@Override
	public List<Distributor> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Distributor getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<TreeNodeVO> getTreeNodes(int clientId, Integer id, String subStructureId)throws BusinessException {
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("id", id);
			paramMap.put("subStructureId", subStructureId);
			ls = distributorDao.getTreeNodesByPId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}


	@Override
	public ClientUser getParentClientUser(Integer clientUserId, Integer clientId)
			throws BusinessException {
		ClientUser clientUser = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("clientId", clientId);
			clientUser = clientUserDao.getDistributorLogName(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getParentClientUser error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return clientUser;
	}




}
