package cn.mobilizer.channel.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class ClientStructureServiceImpl implements ClientStructureService {
	private static final Log LOG = LogFactory.getLog(ClientStructureServiceImpl.class);
	
	@Autowired
	private ClientStructureDao clientStructureDao;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;

	@Override
	public List<TreeNodeVO> getTreeNodes(Integer clientId,Integer id) throws BusinessException{
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("id", id);
			ls = clientStructureDao.getTreeNodesByPId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public String getTreeNodes4String(Integer clientId,Integer pId) throws BusinessException{
		JSONArray jsonArray = new JSONArray();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("pId", pId);
			List<TreeNodeVO> ls = clientStructureDao.getTreeNodesByPId(paramMap);
			if (ls != null && ls.size () >0) {
				for ( TreeNodeVO item : ls ) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", item.getId());
					jsonObject.put("name", item.getName());
					jsonObject.put("pId", item.getpId());
					jsonObject.put("isParent", item.getIsParent ());
					jsonArray.add(jsonObject);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return jsonArray.toString ();
	}
	
	@Override
	public List<Object> getTreeNodes4StringList(Integer clientId,Integer pId) throws BusinessException{
		List<Object> listZTree  = new ArrayList<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("pId", pId);
			List<TreeNodeVO> ls= clientStructureDao.getTreeNodesByPId(paramMap);
			if (ls != null && ls.size () >0) {
				 String str = ""; 
				for ( TreeNodeVO item : ls ) {
					str = "{id:'" +item.getId() + "', pId:'"+item.getpId()+"', name:'"+item.getName()+"' }";
					LOG.info(str); 
					listZTree.add(str);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return listZTree;
	}

	@Override
	public int updateClientStructure(ClientStructure clientStructure) throws BusinessException {
		int result = 0;
		try {
			result = clientStructureDao.updateClientStructure(clientStructure);
		} catch (BusinessException e) {
			LOG.error("method updateClientStructure error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method updateClientStructure error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		
		return result;
	}

	@Override
	public int addClientStructure(ClientStructure clientStructure) throws BusinessException {
		int id = 0;
		try {
			id = clientStructureDao.addClientStructure(clientStructure);
		} catch (BusinessException e) {
			LOG.error("method updateClientStructure error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method updateClientStructure error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return id;
	}

	@Override
	public int deleteClientStructure(Integer id) throws BusinessException {
		ClientStructure clientStructure = new ClientStructure();
		clientStructure.setClientStructureId (id);
		clientStructure.setIsDelete (Constants.IS_DELETE_TRUE);
		return clientStructureDao.deleteClientStructureByid(clientStructure);
	}

	@Override
	public List<ClientStructure> getTopClientStructure(Integer clientId) {
		return clientStructureDao.getTopClientStructure(clientId);
	}
	
	public ClientStructure getClientStructureByName(Integer clientId, String structureName){
		return clientStructureDao.getClientStructureByName(clientId, structureName);
	}

	@Override
	public List<ClientStructure> getObjectList(Map<String, Object> parames) {
		return clientStructureDao.getListByParames(parames);
	}

	@Override
	public ClientStructure getObject(Map<String, Object> parames) {
		
		return null;
	}
	
	@Override
	public ClientStructure getClientStructureById(Integer id) throws BusinessException{
		ClientStructure clientStructure = new ClientStructure();
		try {
			clientStructure = clientStructureDao.getClientStructureById(id);
		} catch (BusinessException e) {
			LOG.error("method getClientStructureById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return clientStructure;
	}

	@Override
	public List<TreeNodeVO> getTreeNodeWithPer(Integer clientId,Integer clientUserId,String dataPermissions) throws BusinessException{
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			/**如果数据权限为空,重新查询一次数据库获取,以后可考虑用内存数据库实现**/
			if(dataPermissions ==null || dataPermissions ==""){
				dataPermissions = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(clientUserId);
			}
			paramMap.put("clientId", clientId);
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("dataPermissions", dataPermissions);
			ls = clientStructureDao.getTreeNodeWithPer(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodeWithPer error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public ClientStructure getRepeatClientStructureName(Map<String, Object> paramMap)
			throws BusinessException {
		return clientStructureDao.findClientStructureByName(paramMap);
	}

	@Override
	public List<TreeNodeVO> getTreeNodeWithSle(Integer clientId, Integer clientUserId, String subClientStructureIds) throws BusinessException {
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("subClientStructureIds", subClientStructureIds);
			ls = clientStructureDao.getTreeNodeWithSle(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodeWithSle error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public Map<String, ClientStructure> getClientStructureMapByName(
			Integer clientId) throws BusinessException {
		Map<String, Object> parmarter = new HashMap<String, Object>();
		parmarter.put("clientId", clientId);
		return clientStructureDao.getClientStructureMapByName(parmarter);
	}

	@Override
	public List<ClientStructure> query(Integer clientId) { 
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId", clientId);
	 
		return clientStructureDao.getListByParames(param);
		}
}
