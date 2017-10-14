/**
 * 授权
 */
package cn.mobilizer.channel.systemManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


















import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.vo.LeftMenuVO;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.ClientMobileModulesDao;
import cn.mobilizer.channel.systemManager.dao.ClientPrivilegesDao;
import cn.mobilizer.channel.systemManager.dao.ClientRolesDataPrivilegesDao;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;
import cn.mobilizer.channel.systemManager.po.ClientRolesDataPrivileges;
import cn.mobilizer.channel.systemManager.service.ClientPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;


@Service
public class ClientPrivilegesServiceImpl implements ClientPrivilegesService {
	
	private static final Log LOG = LogFactory.getLog(ClientPrivilegesServiceImpl.class);
	
	@Autowired
	private ClientPrivilegesDao clientPrivilegesDao;
	@Autowired
	private ClientMobileModulesDao clientMobileModulesDao;
	@Autowired
	private ClientStructureDao clientStructureDao;
	@Autowired
	private ClientRolesDataPrivilegesDao clientRolesDataPrivilegesDao;

	@Override
	public List<String> getUserPermissionsByClientUserId(Integer clientUserId) throws BusinessException{
		List<String> list = null;
		try {
			list = clientPrivilegesDao.getUserPermissionsByClientUserId(clientUserId);
		} catch (BusinessException e) {
			LOG.error ("method getUserPermissionsByClientUserId error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientPrivileges> getUserMenu(Integer clientUserId) throws BusinessException{
		List<ClientPrivileges> list = null;
		try {
			list = clientPrivilegesDao.getUserMenu(clientUserId);
		} catch (BusinessException e) {
			LOG.error ("method getUserMenu error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	@Override
	public Map<String,List<ClientPrivileges>> getMapUserMenu(Integer clientUserId) throws BusinessException{
		Map<String, List<ClientPrivileges>> result = new LinkedHashMap<String, List<ClientPrivileges>>();
		try {
			List<ClientPrivileges> ls = clientPrivilegesDao.getUserMenu(clientUserId);
//			TODO--以后完善
		} catch (BusinessException e) {
			LOG.error ("method getUserMenu error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return result;
	}

	@Override
	public List<LeftMenuVO> getMenusByClientUserId(Integer clientUserId) throws BusinessException{
		List<LeftMenuVO> list = new ArrayList<LeftMenuVO>();
		try {
			List<ClientPrivileges> ls = clientPrivilegesDao.getUserMenu(clientUserId);
			/**
			 * 构造menu
			 * 注意：此方式是写死的，构造的成功依赖于ls的排序，顶级的menu一定要排在前面，最好确保顶层menu的parent_id为空
			 * 当前程序仅支持两极菜单
			 */
			for ( ClientPrivileges clientPrivileges : ls ) {
				if(clientPrivileges.getParentId() == null){
					LeftMenuVO lm = new LeftMenuVO();
					List<LeftMenuVO> childMenus = new ArrayList<LeftMenuVO>();
					BeanUtils.copyProperties(clientPrivileges, lm);
					for ( ClientPrivileges clientPrivileges_inner : ls ) {
						if(clientPrivileges_inner.getParentId() !=null && clientPrivileges_inner.getParentId().intValue() == clientPrivileges.getClientPrivilegeId().intValue()){//如果有子菜单
							LeftMenuVO lm_inner = new LeftMenuVO();
							BeanUtils.copyProperties(clientPrivileges_inner, lm_inner);
							childMenus.add(lm_inner);
						}
					}
					lm.setChildMenus(childMenus);
					list.add(lm);
				}
			}
			return list;
		} catch (BusinessException e) {
			LOG.error ("method getMenusByClientUserId error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public List<TreeNodeVO> getWebTreeNode(Integer clientId,Integer roleId){
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("roleId", roleId);
			ls = clientPrivilegesDao.getWebTreeNode(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getWebTreeNode error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public List<TreeNodeVO> getMobileTreeNode(Integer clientId,Integer roleId){
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("roleId", roleId);
			ls = clientMobileModulesDao.getMobileTreeNode(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getWebTreeNode error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public List<TreeNodeVO> getDataTreeNode(Integer clientId,Integer roleId){
		List<TreeNodeVO> ls = new ArrayList<TreeNodeVO>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientId", clientId);
			paramMap.put("roleId", roleId);
			paramMap.put("selfStructureId", Constants.SELF_STRUCTURE_ID);
			/**查询是否有为"0"的权限，虚拟一个本部门的数据权限-并加进树节点**/
			TreeNodeVO treeNodeVO = new TreeNodeVO();
			treeNodeVO.setId(Constants.SELF_STRUCTURE_ID);
			treeNodeVO.setName(Constants.SELF_STRUCTURE_NAME);
			ClientRolesDataPrivileges crdp = clientRolesDataPrivilegesDao.getSelfDataPrivileges(paramMap);
			if(crdp != null && crdp.getClientStructureId().equals(Constants.SELF_STRUCTURE_ID)){
				treeNodeVO.setChecked(Constants.IS_CHECKED);
			}
			ls.add(treeNodeVO);
			/**查询部门匹配的权限**/
			List<TreeNodeVO> ls_temp = clientStructureDao.getDataTreeNode(paramMap);
			ls.addAll(ls_temp);
		} catch (BusinessException e) {
			LOG.error("method getWebTreeNode error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

}
