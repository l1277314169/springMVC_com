package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientPositionDao;
import cn.mobilizer.channel.base.dao.OptionsDao;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

/**
 * 
 * @author yeeda.tian
 * 职位业务处理层
 */
@Service
public class ClientPositionServiceImpl implements ClientPositionService {
	private static final Log LOG = LogFactory.getLog(ClientPositionServiceImpl.class);
	
	@Autowired
	private ClientPositionDao clientPositionDao;
	@Autowired
	private OptionsDao optionsDao;
	
//	public ClientPosition getClientPosition(Long id) {
//		return clientPositionDao.findOne(id);
//	}
	
	@Override
	public Integer queryClientPositionCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = clientPositionDao.queryClientPositionCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryClientPositionCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}
	
	@Override
	public List<ClientPosition> queryClientPositionList(Map<String, Object> params) throws BusinessException{
		List<ClientPosition> list = null;
		try {
			list = clientPositionDao.queryClientPositionList(params);
		} catch (BusinessException e) {
			LOG.error("method queryClientPositionList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return list;
	}

	@Override
	public int addClientPosition(ClientPosition clientPosition) throws BusinessException{
		try {
			return clientPositionDao.insert(clientPosition);
		} catch (BusinessException e) {
			LOG.error("method addClientPosition error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addClientPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	@Override
	public int deleteClinetPostion(Integer clientPositionId) throws BusinessException{
		int result = 0;
		ClientPosition clientPosition = new ClientPosition();
		try {
//			result = clientPositionDao.delete(clientPositionId);
			clientPosition.setClientPositionId (clientPositionId);
			clientPosition.setIsDelete (Constants.IS_DELETE_TRUE);
			result = clientPositionDao.update(clientPosition);
		} catch (BusinessException e) {
			LOG.error("method deleteClinetPostion error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteCatePropGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public ClientPosition findClientPositionById(Integer id, Integer clientId) throws BusinessException{
		ClientPosition clientPosition = null;
		try {
			clientPosition = clientPositionDao.findClientPositionById(id);
			if(clientPosition != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("optionName",clientPosition.getClinetPostionSysPosition());
				params.put("isDelete", Constants.IS_DELETE_FALSE);
				List<Options> optionsList = optionsDao.findOptionsByOptionName (params);
				clientPosition.setOptionsList (optionsList);
			}
		} catch (BusinessException e) {
			LOG.error("method findClientPositionById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return clientPosition;
	}

	@Override
	public int updateClientPosition(ClientPosition clientPosition) throws BusinessException{
		int result = 0;
		try {
			result = clientPositionDao.update(clientPosition);
		} catch (BusinessException e) {
			LOG.error("method updateClientPosition error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method updateClientPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return result;
	}
	
	@Override
	public List<ClientPosition> findClientPositionsByClientId(Integer clientId) throws BusinessException{
		List<ClientPosition> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			params.put("_orderby", "CLIENT_POSITION_ID");
			params.put("_order", "DESC");
			list = clientPositionDao.queryClientPositionList(params);
		} catch (BusinessException e) {
			LOG.error("method findClientPositionsByClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientPosition> getObjectList(Map<String, Object> parames) {
		return clientPositionDao.getListByParames(parames);
	}

	@Override
	public ClientPosition getObject(Map<String, Object> parames) {
		return clientPositionDao.getOneByParames(parames);
	}

	@Override
	public List<ClientPosition> findClientPositionByMobilePer(Integer clientId) throws BusinessException{
		List<ClientPosition> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			list = clientPositionDao.findClientPositionByMobilePer(params);
		} catch (BusinessException e) {
			LOG.error("method findClientPositionByMobilePer error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public boolean isExistUserName(String positionName,Integer clientId) throws BusinessException {
		boolean flag = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("position_name", positionName);
		params.put("clientId", clientId);
		int items = clientPositionDao.getUserNameItems(params);
		if(items>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isExistUserName(String positionName, String clientPositionId,Integer clientId)
			throws BusinessException {
		boolean flag = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("position_name", positionName);
		params.put("clientPositionId", clientPositionId);
		params.put("clientId", clientId);
		
		int items = clientPositionDao.getUserNameItems(params);
		if(items>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public ClientPosition findPositionByStoreIdAndUserIds(
			Map<String, Object> params) throws BusinessException {		 
		return clientPositionDao.findPositionByStoreIdAndUserIds(params);
	}

	@Override
	public List<ClientPosition> findClientPositionByName(Map<String, Object> params)
			throws BusinessException {
		return clientPositionDao.findClientPositionByName(params);
	}

	@Override
	public List<ClientPosition> getZtreeClientPosition(
			Map<String, Object> params) throws BusinessException {
		return clientPositionDao.getTreeNodesByPId(params);
	}

	@Override
	public List<ClientPosition> getClientPositionBusiness(Integer clientId)
			throws BusinessException {
		List<ClientPosition> clientPositionBusiness = null;
		try {
			Map<String, Object> parmar = new HashMap<String, Object>();
			parmar.put("clientId", clientId);
			parmar.put("roleNames", Constants.MOBI_BUSINESS);
			clientPositionBusiness = clientPositionDao.getClientPositionBusiness(parmar);
		} catch (BusinessException e) {
			LOG.error("method getClientPositionBusiness error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return clientPositionBusiness;
	}

	@Override
	public Map<String, ClientPosition> selectAllClientPosition(Integer clientId) {
		Map<String, Object> parmar = new HashMap<String, Object>();
		parmar.put("clientId", clientId);
		return clientPositionDao.getClientPositionMapByName(parmar);
	}

}
