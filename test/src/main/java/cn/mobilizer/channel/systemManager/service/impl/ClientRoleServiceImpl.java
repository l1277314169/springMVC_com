package cn.mobilizer.channel.systemManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.systemManager.dao.ClientRolesDao;
import cn.mobilizer.channel.systemManager.dao.ClientRolesDataPrivilegesDao;
import cn.mobilizer.channel.systemManager.dao.ClientRolesModuleMappingDao;
import cn.mobilizer.channel.systemManager.dao.ClientRolesPrivilegesDao;
import cn.mobilizer.channel.systemManager.dao.ClientRolesUserMappingDao;
import cn.mobilizer.channel.systemManager.po.ClientRoles;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.base.service.impl.BrandServiceImpl;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class ClientRoleServiceImpl implements ClientRolesService {
	private static final Log LOG = LogFactory.getLog(BrandServiceImpl.class);
	@Autowired
	private ClientRolesDao clientRolesDao;
	@Autowired
	private ClientRolesUserMappingDao clientRolesUserMappingDao;
	@Autowired
	private ClientRolesPrivilegesDao clientRolesPrivilegesDao;
	@Autowired
	private ClientRolesModuleMappingDao clientRolesModuleMappingDao;
	@Autowired
	private ClientRolesDataPrivilegesDao clientRolesDataPrivilegesDao;
	
	@Override
	public List<ClientRoles> getObjectList(Map<String, Object> parames)throws BusinessException{
		List<ClientRoles> list = null;
		try {
			if(parames != null && parames.size()>0){
				list =  clientRolesDao.getClientRoleList(parames);
			}
		} catch (Exception e) {
			LOG.error("method getObjectList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public ClientRoles getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryClientRoleCount(Map<String, Object> params)throws BusinessException{
		int count = 0;
		try {
			if(params != null && params.size()>0){
				count = clientRolesDao.queryClientRoleCount(params);
			}
		} catch (BusinessException e) {
			LOG.error("method queryClientRoleCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
		
		
	}

	@Override
	public void addRoles(ClientRoles clientRole)throws BusinessException {
		try {
			clientRolesDao.addRole(clientRole);
		} catch (BusinessException e) {
			LOG.error("method addRoles error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
		
	}

	@Override
	public ClientRoles getClientRole(Integer roleId)throws BusinessException {
		ClientRoles clientRole = null;
		try {
				clientRole = clientRolesDao.getClientRole(roleId);
		} catch (BusinessException e) {
			LOG.error("method getClientRole error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		
		return clientRole;
	}

	@Override
	public void updateRole(ClientRoles clientRole)throws BusinessException{
		try {
			clientRolesDao.updateRole(clientRole);
//			Integer roleId = clientRole.getRoleId();
//			Integer clientId = clientRole.getClientId();
//			Map<String,Object> params = new HashMap<String, Object>();
//			params.put("roleId", roleId);
//			params.put("clientId", clientId);
//			List<ClientRolesUserMapping> list = clientRolesUserMappingDao.getClientRolesUserMappingByRoleId(params);
//			if(list != null && list.size()>0){
//				for (ClientRolesUserMapping clientRolesUserMapping : list) {
//					clientRolesUserMapping.setIsDelete(Constants.IS_DELETE_TRUE);
//					clientRolesUserMappingDao.update(clientRolesUserMapping);
//				}
//			}
		} catch (BusinessException e) {
			LOG.error("method updateRole error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		
	}
	
	@Override
	public void deleteRole(ClientRoles clientRole)throws BusinessException{
		try {
			clientRolesDao.updateRole(clientRole);
			Integer roleId = clientRole.getRoleId();
			Integer clientId = clientRole.getClientId();
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("clientId", clientId);
			List<ClientRolesUserMapping> list = clientRolesUserMappingDao.getClientRolesUserMappingByRoleId(params);
			if(list != null && list.size()>0){
				for (ClientRolesUserMapping clientRolesUserMapping : list) {
					clientRolesUserMapping.setIsDelete(Constants.IS_DELETE_TRUE);
					clientRolesUserMappingDao.update(clientRolesUserMapping);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method updateRole error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		
	}

	@Override
	public List<String> getUserRolesByClientUserId(Integer clientUserId) throws BusinessException{
		List<String> list = null;
		try {
			list = clientRolesDao.getUserRolesByClientUserId(clientUserId);
		} catch (BusinessException e) {
			LOG.error ("method getUserRolesByClientUserId error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientRoles> findSystemRolesByClientId(Integer clientId) {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("clientId", clientId);
		return clientRolesDao.getClientRoleList1(parames);
	}

	@Override
	public ClientRoles getClientRoleByRoleName(int clientId, String roleName)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		ClientRoles clientRole = null;
		try {
			params.put("clientId", clientId);
			params.put("roleName", roleName);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			clientRole = clientRolesDao.getClientRoleByRoleName(params);
		} catch (BusinessException e) {
			LOG.error ("method getClientRoleByRoleNameEn error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return clientRole;
	}

	@Override
	public void updateRolePrivileges(Integer roleId,Integer clientId,String checkedWebOld,String checkedWebNew,String checkedMobileOld,
			String checkedMobileNew,String checkedDataOld,String checkedDataNew) throws BusinessException{
		try {
			String[] oldWeb = (checkedWebOld != null && checkedWebOld != "")? checkedWebOld.split(","):null;
			String[] newWeb = (checkedWebNew != null && checkedWebNew != "")? checkedWebNew.split(","):null;
			String[] oldMobile = (checkedMobileOld != null && checkedMobileOld != "")? checkedMobileOld.split(","):null;
			String[] newMobile = (checkedMobileNew != null && checkedMobileNew != "")? checkedMobileNew.split(","):null;
			String[] oldData = (checkedDataOld != null && checkedDataOld != "")? checkedDataOld.split(","):null;
			String[] newData = (checkedDataNew != null && checkedDataNew != "")? checkedDataNew.split(","):null;
			
			updateRoleWebPrivileges(roleId,clientId,oldWeb,newWeb);
			updateRoleMobilePrivileges(roleId,clientId,oldMobile,newMobile);
			updateRoleDataPrivileges(roleId,clientId,oldData,newData);
			
		} catch (BusinessException e) {
			LOG.error("method updateRolePrivileges error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}
	
	/**
	 * 修改某个角色的web权限
	 * @param roleId
	 * @param oldPrivileges
	 * @param newPrivileges
	 */
	public void updateRoleWebPrivileges(Integer roleId,Integer clientId,String[] oldPrivileges,String[] newPrivileges){
		try {
			if(oldPrivileges ==null){//如果以前的权限为空,那么全部为新增
				if(newPrivileges != null){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("roleId", roleId);
					params.put("clientId", clientId);
					params.put("insPrivileges", newPrivileges);
					clientRolesPrivilegesDao.insertByRoleAndPrivileges(params);
				}
			} else {
				if(newPrivileges == null){//如果以前的权限不为空，当前权限为空，那么全部删除
					clientRolesPrivilegesDao.deleteByRoleId(roleId);
				} else {//如果以前和当前的权限都不为空
					/**获取old中存在而new中不存在的权限，删除**/
					String delPrivileges = ArrayUtil.arraySubtract2Str(oldPrivileges,newPrivileges);
					if(delPrivileges != null && delPrivileges != ""){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("roleId", roleId);
						params.put("clientId", clientId);
						params.put("delPrivileges", delPrivileges);
						clientRolesPrivilegesDao.deleteByRoleAndPrivileges(params);
					}
					/**获取new中存在而old中不存在的权限，新增**/
					String[] insPrivileges = ArrayUtil.arraySubtract(newPrivileges,oldPrivileges);
					if(insPrivileges !=null && insPrivileges.length > 0 ){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("roleId", roleId);
						params.put("clientId", clientId);
						params.put("insPrivileges", insPrivileges);
						clientRolesPrivilegesDao.insertByRoleAndPrivileges(params);
					}
				}
			}
		} catch (BusinessException e) {
			LOG.error("method updateRoleMobilePrivileges error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}
	
	/**
	 * 修改某个角色的Mobile权限
	 * @param roleId
	 * @param oldPrivileges
	 * @param newPrivileges
	 */
	public void updateRoleMobilePrivileges(Integer roleId,Integer clientId,String[] oldPrivileges,String[] newPrivileges){
		try {
			if(oldPrivileges ==null){//如果以前的权限为空,那么全部为新增
				if(newPrivileges != null){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("roleId", roleId);
					params.put("clientId", clientId);
					params.put("insPrivileges", newPrivileges);
					clientRolesModuleMappingDao.insertByRoleAndPrivileges(params);
				}
			} else {
				if(newPrivileges == null){//如果以前的权限不为空，当前权限为空，那么全部删除
					clientRolesModuleMappingDao.deleteByRoleId(roleId);
				} else {//如果以前和当前的权限都不为空
					/**获取old中存在而new中不存在的权限，删除**/
					String delPrivileges = ArrayUtil.arraySubtract2Str(oldPrivileges,newPrivileges);
					if(delPrivileges != null && delPrivileges != ""){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("roleId", roleId);
						params.put("clientId", clientId);
						params.put("delPrivileges", delPrivileges);
						clientRolesModuleMappingDao.deleteByRoleAndPrivileges(params);
					}
					/**获取new中存在而old中不存在的权限，新增**/
					String[] insPrivileges = ArrayUtil.arraySubtract(newPrivileges,oldPrivileges);
					if(insPrivileges !=null && insPrivileges.length > 0 ){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("roleId", roleId);
						params.put("clientId", clientId);
						params.put("insPrivileges", insPrivileges);
						clientRolesModuleMappingDao.insertByRoleAndPrivileges(params);
					}
				}
			}
		} catch (BusinessException e) {
			LOG.error("method updateRolePrivileges error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}
	
	/**
	 * 修改某个角色的数据权限
	 * @param roleId
	 * @param oldPrivileges
	 * @param newPrivileges
	 */
	public void updateRoleDataPrivileges(Integer roleId,Integer clientId,String[] oldPrivileges,String[] newPrivileges){
		try {
			if(oldPrivileges ==null){//如果以前的权限为空,那么全部为新增
				if(newPrivileges != null){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("roleId", roleId);
					params.put("clientId", clientId);
					params.put("insPrivileges", newPrivileges);
					clientRolesDataPrivilegesDao.insertByRoleAndPrivileges(params);
				}
			} else {
				if(newPrivileges == null){//如果以前的权限不为空，当前权限为空，那么全部删除
					clientRolesDataPrivilegesDao.deleteByRoleId(roleId);
				} else {//如果以前和当前的权限都不为空
					/**获取old中存在而new中不存在的权限，删除**/
					String delPrivileges = ArrayUtil.arraySubtract2Str(oldPrivileges,newPrivileges);
					if(delPrivileges != null && delPrivileges != ""){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("roleId", roleId);
						params.put("clientId", clientId);
						params.put("delPrivileges", delPrivileges);
						clientRolesDataPrivilegesDao.deleteByRoleAndPrivileges(params);
					}
					/**获取new中存在而old中不存在的权限，新增**/
					String[] insPrivileges = ArrayUtil.arraySubtract(newPrivileges,oldPrivileges);
					if(insPrivileges !=null && insPrivileges.length > 0 ){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("roleId", roleId);
						params.put("clientId", clientId);
						params.put("insPrivileges", insPrivileges);
						clientRolesDataPrivilegesDao.insertByRoleAndPrivileges(params);
					}
				}
			}
		} catch (BusinessException e) {
			LOG.error("method updateRolePrivileges error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	@Override
	public List<ClientRoles> queryRolesByselectTwo(Map<String, Object> parameters) throws BusinessException {
		
		return clientRolesDao.findRolesbyselectTwo(parameters);
	}

	@Override
	public ClientRoles queryRolesById(Integer id) throws BusinessException {
		
		return clientRolesDao.getClientRolesById(id);
	}

	@Override
	public Byte getUserRoleLevel(Integer clientUserId,Integer clientId) throws BusinessException{
		Byte level = null;
		List<ClientRoles> list = null;
		try {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("clientUserId", clientUserId);
			list = clientRolesDao.findClientRolesByUserId(parames);
			if(list !=null && list.size() > 0){
				ClientRoles clientRoles = list.get(0);
				level = clientRoles.getRoleLevel() !=null ? clientRoles.getRoleLevel() : 0;
			}
		} catch (BusinessException e) {
			LOG.error ("method getUserRoleLevel error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return level;
	}

	@Override
	public List<ClientRoles> findSystemRolesByClientIdAndRoleLevel(Integer clientId,Byte level) throws BusinessException{
		List<ClientRoles> list = null;
		try {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("level", level);
			list = clientRolesDao.findSystemRolesByClientIdAndRoleLevel(parames);
		} catch (BusinessException e) {
			LOG.error ("method findSystemRolesByClientIdAndRoleLevel error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientRoles> getRolesIsBusiness(Integer clientId)
			throws BusinessException {
		 List<ClientRoles> businessRoles = null;
		try {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("roleNames", Constants.MOBI_BUSINESS);
			businessRoles = clientRolesDao.getBusinessRoles(parames);
		} catch (BusinessException e) {
			LOG.error ("method getRolesIsBusiness error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return businessRoles;
	}

	@Override
	public Map<String, ClientRoles> selectAllRoles(Integer clientId) {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("clientId", clientId);
		return clientRolesDao.getClientRolesMapByid(parames);
	}

}
