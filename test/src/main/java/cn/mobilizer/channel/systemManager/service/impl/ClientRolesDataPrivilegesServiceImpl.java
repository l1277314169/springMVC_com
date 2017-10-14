package cn.mobilizer.channel.systemManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.utils.MemcachedUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.MemcachedEnum;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.ClientRolesDataPrivilegesDao;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.po.VisitingTask;

@Service
public class ClientRolesDataPrivilegesServiceImpl implements ClientRolesDataPrivilegesService{
	
	private static final Log LOG = LogFactory.getLog(ClientRolesDataPrivilegesServiceImpl.class);
			
	@Autowired
	private ClientRolesDataPrivilegesDao clientRolesDataPrivilegesDao;
	
	@Autowired
	private ClientUserDao clientUserDao;
	
	@Autowired
	private ClientStructureDao clientStructureDao;
	
	public List<String> getUserPermissionsByClientUserId(Integer clientUserId) throws BusinessException{
		List<String> list = new ArrayList<String>();
		try {
			list = clientRolesDataPrivilegesDao.getUserPermissionsByClientUserId(clientUserId);
		} catch (BusinessException e) {
			LOG.error ("method getUserPermissionsByClientUserId error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public String getUserPermissionsByClientUserId2String(Integer clientUserId) throws BusinessException{
		String str = "";
		String key = MemcachedEnum.ClientUserPerData.getKey()+clientUserId;
		int sec = MemcachedEnum.ClientUserPerData.getSec();
		try {
			str = MemcachedUtil.getInstance().get(key) == null? "": (String) MemcachedUtil.getInstance().get(key);
			if(StringUtil.isNotEmptyString(str)){
				return str;
			} else {
				ClientUser cu = clientUserDao.selectByPrimaryKey(clientUserId);
				/**获取用户数据权限信息**/
				List<String> permissionsData = clientRolesDataPrivilegesDao.getUserPermissionsByClientUserId(clientUserId);
				
				/**本部门数据权限**/
				String subStructrueIds  = "" ;
				/**判断是否有为0的数据权限，为0表示本部门权限**/
				for ( String string : permissionsData ) {
					if(string.equals(Constants.SELF_STRUCTURE_ID.toString())){
						subStructrueIds = clientStructureDao.getSubStructure(cu.getClientStructureId());
						break;
					}
				}
				
				/**合并重新赋值**/
				permissionsData = mergeDataPermissions(permissionsData, subStructrueIds);
				for(Iterator<String> it = permissionsData.iterator();it.hasNext();){
					if(!it.hasNext()) {
						str += it.next ();
					} else {
						str += it.next () + ",";
					}
				}
				MemcachedUtil.getInstance().set(key, sec, str);
			}
//			str = clientRolesDataPrivilegesDao.getUserPermissionsByClientUserId2String(clientUserId);
		} catch (BusinessException e) {
			LOG.error ("method getUserPermissionsByClientUserId error, ", e);
			throw new BusinessException (ErrorCodeMsg.ERR_QUERY);
		}
		return str;
	}
	
	/**
	 * 合并权限
	 * @param arr1
	 * @param arr2 ","号隔开的字符串
	 * @return 返回Lsit<String>
	 */
	protected List<String> mergeDataPermissions(List<String> arr1,String arr2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		List<String> list = new ArrayList<String>();
		if(arr1 !=null && arr1.size() >0 ) {
			for ( String str : arr1 ) {
				map.put(str, Boolean.TRUE);
			}
		}
		if(arr2 != null && arr2 != "") {
			String tmp_str[] =  arr2.split(",");
			for ( String str : tmp_str ) {
				map.put(str, Boolean.TRUE);
			}
		}
		for ( Entry<String, Boolean> e : map.entrySet() ) {
			list.add(e.getKey());
		}
		
		return list;
	}
}
