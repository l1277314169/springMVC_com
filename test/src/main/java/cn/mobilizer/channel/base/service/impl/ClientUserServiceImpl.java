/**
 * 
 */
package cn.mobilizer.channel.base.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.autho.ShiroConstants;
import cn.mobilizer.channel.base.dao.CityDao;
import cn.mobilizer.channel.base.dao.ClientPositionDao;
import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.dao.ClientUserRelationDao;
import cn.mobilizer.channel.base.dao.DistrictDao;
import cn.mobilizer.channel.base.dao.ProvinceDao;
import cn.mobilizer.channel.base.dao.StoreUserMappingDao;
import cn.mobilizer.channel.base.exception.ImprotException;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.base.vo.Users;
import cn.mobilizer.channel.comm.security.utils.Digests;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.Encodes;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.utils.json.JsonTool;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.image.po.ExecTimeLog;
import cn.mobilizer.channel.image.service.ExecTimeLogService;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.posm.po.Warehouse;
import cn.mobilizer.channel.systemManager.dao.ClientRolesDao;
import cn.mobilizer.channel.systemManager.dao.ClientRolesUserMappingDao;
import cn.mobilizer.channel.systemManager.po.ClientRoles;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.HttpClientUtils;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PasswordHelper;
import cn.mobilizer.channel.util.PropertiesHelper;

/**
 * 用户业务处理
 */

@Service
public class ClientUserServiceImpl implements ClientUserService {
	

	@Autowired
	private ExecTimeLogService execTimeLogService;
	@Autowired
	private ActivityLogService activityLogService;
	
	private static String eHRURL = null;
//	private static String imageTask_enabled = null;
	
	static{
		eHRURL = PropertiesHelper.getInstance().getProperty(PropertiesHelper.EHR_URL);
		
	}
	
	
	private static final Log LOG = LogFactory.getLog(ClientUserServiceImpl.class);
	private static final int SALT_SIZE = 8;

	@Autowired
	private ClientUserDao clientUserDao;
	@Autowired
	private ClientPositionDao clientPositionDao;
	@Autowired
	private ClientUserRelationDao clientUserRelationDao;
	@Autowired
	private ClientRolesDao clientRolesDao;
	@Autowired
	private ClientRolesUserMappingDao clientRolesUserMappingDao;
	@Autowired
	private StoreUserMappingDao storeUserMappingDao;
	@Autowired
	private DataSource dataSourceBase;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private ClientStructureDao clientStructureDao;
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(ClientUser clientUser) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		clientUser.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(clientUser.getPlainPassword().getBytes(), salt, ShiroConstants.HASH_INTERATIONS);
		clientUser.setLoginPwd(Encodes.encodeHex(hashPassword));
	}

	@Override
	public ClientUser findByLoginName(String loginName, int clientId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		paramMap.put("clientId", clientId);
		return clientUserDao.findByLoginName(paramMap);
	}

	@Override
	public ClientUser findByLoginNameAndClientName(String loginName, String clientName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		paramMap.put("clientName", clientName);
		return clientUserDao.findByLoginNameAndClientName(paramMap);
	}

	/**
	 * <p>
	 * Description: 登录校验
	 * </p>
	 * 
	 * @param username
	 *            用户名
	 * @param userPwd
	 *            密码
	 * @return ClientUser
	 */
	@Override
	public ClientUser findByLoginNameAndLoginPWD(String loginName, String loginPWD) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		paramMap.put("loginPwd", loginPWD);
		return clientUserDao.findByLoginNameAndLoginPWD(paramMap);
	}

	@Override
	public ClientUser findFullUserInfoByPrimaryKey(Integer clientUserid) {
		try {
			return clientUserDao.findFullUserInfoByPrimaryKey(clientUserid);
		} catch (BusinessException e) {
			LOG.error("method findFullUserInfoByPrimaryKey error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public ClientUser findByPrimaryKey(Integer clientUserid) {
		try {
			return clientUserDao.findByPrimaryKey(clientUserid);
		} catch (BusinessException e) {
			LOG.error("method findByPrimaryKey error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public ClientUser selectByPrimaryKey(Integer clientUserid) {
		try {
			return clientUserDao.selectByPrimaryKey(clientUserid);
		} catch (BusinessException e) {
			LOG.error("method selectByPrimaryKey error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	public String getRoles(String roleIds){
		StringBuffer sb = new StringBuffer();
		if(StringUtil.isEmptyString(roleIds)){
			return null;
		} else {
			ClientRoles cr = clientRolesDao.getWebAppRoles(roleIds);
			boolean isWebUser = false, isMobileUser = false;
			//非空判断
			if(cr != null){
				if(!StringUtils.isEmpty(cr.getWebstr()))
					isWebUser=cr.getWebstr().contains("1");
				if(!StringUtils.isEmpty(cr.getAppstr()))
					isMobileUser=cr.getAppstr().contains("1");
			}
			
			if (isWebUser) {
				sb.append("user");
			}
			if (isMobileUser) {
				if (!StringUtil.isEmptyString(sb.toString())) {
					sb.append(",mobile");
				}else{
					sb.append("mobile");
					
				}
			}
			return sb.toString();
		}
	}
	
	@Override
	public int update(ClientUser clientUser, String oldRoleNames, Integer oldParentId) {
		int result = 0;
		try {
			/**修改了部门需要解除原门店关系*/
			ClientUser userInfo = clientUserDao.findByPrimaryKey(clientUser.getClientUserId());
			if(userInfo.getClientStructureId().intValue()!=clientUser.getClientStructureId().intValue()){
				storeUserMappingDao.deleteByClientUserId(clientUser.getClientUserId());
			}
			String str = getRoles(clientUser.getRoleNames());
			if(!StringUtil.isEmptyString(str)){
				clientUser.setRoles(str);
			}
			result = clientUserDao.update(clientUser);
			
			/**修改角色-人员映射关系表**/
			if(!StringUtils.equals(oldRoleNames, clientUser.getRoleNames())){
				String[] roles=clientUser.getRoleNames().split(",");
				clientRolesUserMappingDao.updateIsdelete(clientUser.getClientUserId());
				if (roles != null && roles.length > 0 && clientUser != null) {
					for (int i = 0; i < roles.length; i++) {
						ClientRolesUserMapping clientRolesUserMapping = clientRolesUserMappingDao.selectClientRolesUserMapping(Integer.parseInt(roles[i]), clientUser.getClientUserId());
						if (clientRolesUserMapping != null) {
							clientRolesUserMapping.setIsDelete(Constants.IS_DELETE_FALSE);
							clientRolesUserMappingDao.update(clientRolesUserMapping);
						} else {
							ClientRolesUserMapping clientRolesUserMapping_s = new ClientRolesUserMapping();
							clientRolesUserMapping_s.setClientId(clientUser.getClientId());
							clientRolesUserMapping_s.setClientUserId(clientUser.getClientUserId());
							clientRolesUserMapping_s.setRoleId(Integer.parseInt(roles[i]));
							clientRolesUserMappingDao.insert(clientRolesUserMapping_s);
						}
					}
				}
			}
			/**修改上级**/
			Integer parentId = clientUser.getParentId();
			Integer ClientUserId = clientUser.getClientUserId();
			if(null != parentId){
				if(! parentId.equals(oldParentId)) {
					clientUserRelationDao.updateIsdelete(ClientUserId);
					Map<String, Object> parames = new HashMap<String, Object>();
					parames.put("ClientUserId", ClientUserId);
					parames.put("parentId", parentId);
					ClientUserRelation clientUserRelation = clientUserRelationDao.selectClientUserRelation(parames);
					if (clientUserRelation != null) {
						clientUserRelation.setIsDelete(Constants.IS_DELETE_FALSE);
						clientUserRelationDao.updateClientUserRelation(clientUserRelation);
					} else {
						ClientUserRelation clientUserRelation_s = new ClientUserRelation();
						clientUserRelation_s.setClientUserId(clientUser.getClientUserId());
						clientUserRelation_s.setParentId(clientUser.getParentId());
						clientUserRelation_s.setClientId(clientUser.getClientId());
						clientUserRelationDao.insertSelective(clientUserRelation_s);
					}
				}
			} else {
				clientUserRelationDao.updateIsdelete(ClientUserId);
			}
		} catch (BusinessException e) {
			LOG.error("method update error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method update error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return result;

	}

	@Override
	public Integer queryClientUserCount(Map<String, Object> param) throws BusinessException {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = clientUserDao.queryClientUserCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryClientUserCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<ClientUser> queryClientUserList(Map<String, Object> param) throws BusinessException {
		List<ClientUser> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = clientUserDao.queryClientUserList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryClientUserList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	public int addClientUser(ClientUser clientUser) throws BusinessException {
		try {
			Integer parentId = null;
			String[] roles = clientUser.getRoleNames().split(",");
						
			String str = getRoles(clientUser.getRoleNames());
			if(!StringUtil.isEmptyString(str)){
				clientUser.setRoles(str);
			}
			
			int clientUserId = clientUserDao.insert(clientUser);
			if (clientUser.getParentId() != null) {
				parentId = clientUser.getParentId();
				ClientUserRelation clientUserRelation = new ClientUserRelation();
				clientUserRelation.setClientUserId(clientUserId);
				clientUserRelation.setParentId(parentId);
				clientUserRelation.setClientId(clientUser.getClientId());
				clientUserRelationDao.insertSelective(clientUserRelation);
			}
			if (roles != null && roles.length >0) {
				for (int i = 0; i < roles.length; i++) {
						ClientRolesUserMapping clientRolesUserMapping = new ClientRolesUserMapping();
						clientRolesUserMapping.setClientId(clientUser.getClientId());
						clientRolesUserMapping.setClientUserId(clientUserId);
						clientRolesUserMapping.setRoleId(Integer.parseInt(roles[i]));
						clientRolesUserMappingDao.insert(clientRolesUserMapping);
				}
			}
			return clientUserId;
		} catch (BusinessException e) {
			LOG.error("method addClientUser error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addClientUser error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}

	}

	public int deleteClientUser(Integer clientUserId,Integer clientId) throws BusinessException {
		int result = 0;
		ClientUser ClientUser = new ClientUser();
		try {
			/** 删除映射表中上级=clientUserId的记录 **/
			clientUserRelationDao.updateUpdownIsdelete(clientUserId);
			
			/**删除角色人员映射中的角色=clientUserId的记录**/
				clientRolesUserMappingDao.updateIsdelete(clientUserId);
			//删除clientUserId下的门店
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("clientId", clientId);
				parameters.put("clientUserId", clientUserId);
				storeUserMappingDao.isdeleteByClientUserId(parameters);
			//删除本身
			ClientUser.setClientUserId(clientUserId);
			ClientUser.setIsDelete(Constants.IS_DELETE_TRUE);
			result = clientUserDao.update(ClientUser);
		} catch (BusinessException e) {
			LOG.error("method deleteClinetUser error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteCatePropGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public ImportVO saveAll(List<ClientUser> users) throws ImprotException{
		ImportVO ivo = new ImportVO();
		for (ClientUser clientUser : users) {
			clientUserDao.insert(clientUser);
		}
		//处理中间映射表(一个人角色可以为多个)
		for (ClientUser clientUser : users) {
			if (!StringUtils.isEmpty(clientUser.getUplevelName())) {
				Map<String, Object> paras = new HashMap<String, Object>();
				paras.put("name", clientUser.getUplevelName());
				paras.put("clientStructureId", clientUser.getClientStructureId());
				List<ClientUser> list = clientUserDao.getListByParames(paras);
				if (list != null && list.size() > 0) {
					ClientUserRelation cur = new ClientUserRelation();
					cur.setClientId(clientUser.getClientId());
					cur.setClientUserId(clientUser.getClientUserId());
					cur.setParentId(list.get(0).getClientUserId());
					clientUserRelationDao.insertSelective(cur);
				}else{
					throw new ImprotException("上级主管："+clientUser.getUplevelName() + "不存在");
				}
			}
		}

		return ivo;
	}

	@Override
	public List<ClientUser> getObjectList(Map<String, Object> parames) {
		return clientUserDao.getListByParames(parames);
	}

	@Override
	public ClientUser getObject(Map<String, Object> parames) {
		return clientUserDao.getOneByParames(parames);
	}

	@Override
	public List<ClientUser> getClientUserWithoutSelf(Integer clientUserId, Integer clientId) throws BusinessException {
		List<ClientUser> list = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("clientId", clientId);
			list = clientUserDao.getClientUserWithoutSelf(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getClientUserWithoutSelf error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientUser> getClientUserWithoutSelf(Integer clientUserId, Integer clientId, String name, String clientUserIdChill, String childId,String subordinates,String deptIds) throws BusinessException {
		List<ClientUser> list = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("clientId", clientId);
			paramMap.put("name", name);
			paramMap.put("childId", childId);
			paramMap.put("subordinates", subordinates);
			paramMap.put("clientUserIdChill", clientUserIdChill);
			paramMap.put("subStructureId", deptIds);
			list = clientUserDao.getClientUserWithoutSelf(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getClientUserWithoutSelf error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientUser> getClientUserPosition(Integer clientUserId, Integer clientId, String name,String deptIds, String subordinates, String clientPositionIds) throws BusinessException {
		List<ClientUser> list = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientId", clientId);
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("name", name);
			paramMap.put("subStructureId", deptIds);
			paramMap.put("subordinates", subordinates);
			paramMap.put("clientPositionIds", clientPositionIds);
			list = clientUserDao.getClientUserPosition(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getClientUserPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public ClientUser getParentClientUser(Integer clientUserId, Integer clientId) throws BusinessException {
		ClientUser clientUser = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("clientId", clientId);
			clientUser = clientUserDao.getParentClientUser(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getParentClientUser error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return clientUser;
	}

	@Override
	public ClientUser findLogName(String LogName,Integer clientId) throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("loginName", LogName);
		parames.put("clientId", clientId);
		return clientUserDao.getLogName(parames);
	}

	@Override
	public ClientUser getClientUser(Integer clientUserId, Integer clientId) throws BusinessException {
		ClientUser clientUser = null;
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			clientUser = clientUserDao.getClientUser(clientUserId);
			parames.put("clientUserId", clientUserId);
			parames.put("clientId", clientId);
			parames.put("clientPositionId", clientUser.getClientPositionId());
			clientUser = clientUserDao.getClientUserPositionById(parames);
		} catch (BusinessException e) {
			LOG.error("method getClientUser error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return clientUser;
	}

	@Override
	public ClientUser findByName(String loginName, Integer clientId) {
		
		return clientUserDao.findByName(loginName, clientId);
	}

	@Override
	public String exeAppSql() {
		File file = new File("/Users/Kris/Downloads/bat2.sql");
		if(!file.exists())
			return null;
		int i = 0, j = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader bfr = new BufferedReader(isr);
			String str = null;
			while ((str = bfr.readLine()) != null) {
				JdbcTemplate jdbcTemp = new JdbcTemplate(dataSourceBase); 
				try {
					jdbcTemp.update(str);
					i++;
				} catch (Exception e) {
//					System.out.println(str);
					LOG.info(str);
					LOG.info(e);
					j++;
				}
			}
			bfr.close();
			fis.close();
		} catch (Exception e) {
		}
		return i+"--"+j;
	}
	
	

	@Override
	public List<ClientUser> queryChildByClietnUserParentId(Map<String, Object> parameters) throws BusinessException {
		return clientUserDao.findclientUserByParentId(parameters);
	}
	
	public Integer changePasswd(ClientUser clientUser){
		return clientUserDao.update(clientUser);
	}

	@Override
	public int updatePassword(ClientUser clientUser) throws BusinessException {
		
		return clientUserDao.update(clientUser);
	}

	@Override
	public List<ClientUser> queryForListForReport(Map<String, Object> parameters) throws BusinessException {
		List<ClientUser> list = null;
		try {
			list = clientUserDao.queryForListForReport(parameters);
		} catch (BusinessException e) {
			LOG.error("method queryForListForReport error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<ClientUser> exportClientUserByParams(Map<String, Object> parames) {
		List<ClientUser> list = null;
		try {
			list = clientUserDao.exportClientUserByParams(parames);
		} catch (BusinessException e) {
			LOG.error("method queryForListForReport error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public String relevanceAllsClientUser(Map<String, Object> parameters) throws BusinessException {
	
		return clientUserDao.findAllClientUser(parameters);
	}

	@Override
	public String replaceAllsClientUser(Map<String, Object> parameters) throws BusinessException {
		
		return clientUserDao.findAllsClientUser(parameters);
	}
	@Override
	public List<ClientUser> findMessageClientUser(Map<String, Object> params) throws BusinessException {
		
		return clientUserDao.findMessageDetailsRelateUser(params);
	}

	@Override
	public int selectUserStoreRelation(Map<String, Object> params) throws BusinessException {
		
		return clientUserDao.selectUserStoreRelation(params);
	}

	@Override
	public List<ClientUser> getAllClientUser(Integer clientId)
			throws BusinessException {
		return clientUserDao.findClientUserList(clientId);
	}

	@Override
	public String selectUserNoByStoreUser(Integer clientId,String storeId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeId", storeId);
		return clientUserDao.selectUserNoByStoreUser(params);
	}

	@Override
	public ClientUser selectClientUserByUserNo(Integer clientId, String userNo) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("userNo", userNo);
		return clientUserDao.selectClientUserByUserNo(params);
	}

	@Override
	public List<ClientUser> selectClientUserByName(Integer clientId,String name) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("clientId", clientId);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		return clientUserDao.selectClientUserByName(params);
	}
	

	@Override
	public ResultMessage addImportWorkLog(MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp,
			Integer clientId) {
		Map<String,Object> parmater = new HashMap<String,Object>();
		parmater.put("clientId", clientId);
		List<ClientUser> clientUserlist = new ArrayList<ClientUser>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		Map<String,ClientUser> logNameMap = clientUserDao.findClientUserBylogName(parmater);//登录名
		Map<String,ClientUser> clientNameMap = clientUserDao.findClientUserBylName(parmater);//上级
		Map<String,ClientPosition> positionMap = clientPositionDao.getClientPositionMapByName(parmater);//职位
		Map<String,Province> provicesMap = provinceDao.getProvinceMapByName(parmater);//省份
		Map<String,ClientStructure> clientStructureMap = clientStructureDao.getClientStructureMapByName(parmater);//部门
		Map<String,ClientRoles> clientRolesMap = clientRolesDao.getClientRolesMapByName(parmater);//角色
		
		
		Set<String> setLoginName = new HashSet<String>(); 
		
		
		List<String[]> errorData = new ArrayList<String[]>();
		List<String> errorStr = new ArrayList<String>();
		
		List<String[]> tureData = new ArrayList<String[]>();
		//表头
		String[] titles = values.get(0);
		errorData.add(titles);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.WORK_LOG_CLIENT_USER_TITLE, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		
		out:for (int i = 1; i < values.size(); i++) {
			String[] rows = values.get(i);
			ClientUser clientUser = new ClientUser();
			clientUser.setUserNo(rows[0]);
			String name = rows[1];
			clientUser.setName(name);
			String  logName = rows[2];
			String 	clientStructure = rows[3];
			String clientPosition = rows[4];
			
			String province = rows[11];
			String city = rows[12];
			
			String parnetName = rows[14];
			
			String clientRoles = rows[17];
		
			
			if(name == null || name.equals("")){
				errorData.add(rows);
				errorStr.add("姓名不能为空");
				continue;
			}else if(logName == null || logName.equals("")){
				errorData.add(rows);
				errorStr.add("用户名不能为空");
				continue;
			}else if(clientStructure == null || clientStructure.equals("")){
				errorData.add(rows);
				errorStr.add("部门不能为空");
				continue;
			}else if(clientPosition == null || clientPosition.equals("")){
				errorData.add(rows);
				errorStr.add("职位不能为空");
				continue;
			}else if(province == null || province.equals("")){
				errorData.add(rows);
				errorStr.add("省份不能为空");
				continue;
			}else if(city == null || city.equals("")){
				errorData.add(rows);
				errorStr.add("城市不能为空");
				continue;
			}else if(parnetName == null || parnetName.equals("")){
				errorData.add(rows);
				errorStr.add("上级不能为空");
				continue;
			}else if(clientRoles == null || clientRoles.equals("")){
				errorData.add(rows);
				errorStr.add("角色不能为空");
				continue;
			}else if(logNameMap.get(logName) != null){
				errorData.add(rows);
				errorStr.add("用户名已存在");
				continue;
			}else if(positionMap.get(clientPosition) == null){
				errorData.add(rows);
				errorStr.add("不存在的职位");
				continue;
			}else if(clientStructureMap.get(clientStructure) == null){
				errorData.add(rows);
				errorStr.add("不存在的部门");
				continue;
			}else if(!StringUtil.isEmptyString(rows[5]) && !rows[5].equals(Constants.ZZ) && !rows[5].equals(Constants.LZ)){
				errorData.add(rows);
				errorStr.add("未能识别在职状态");
				continue;
			}else if(!StringUtil.isEmptyString(rows[6]) && !StringUtil.isPhone(rows[6])){
				errorData.add(rows);
				errorStr.add("电话号码格式有误");
				continue;
			}else if(!StringUtil.isEmptyString(rows[7]) && !rows[7].equals(Constants.QY) && !rows[7].equals(Constants.JY) && !rows[7].equals(Constants.WSY)){
				errorData.add(rows);
				errorStr.add("未能识别在账号状态");
				continue;
			}else if(!StringUtil.isEmptyString(rows[8]) && !StringUtil.isIdentity(rows[8])){
				errorData.add(rows);
				errorStr.add("证件有误");
				continue;
			}else if(!StringUtil.isEmptyString(rows[9]) && !Constants.WOMAN.equals(rows[9]) && !Constants.MAN.equals(rows[9])){
				errorData.add(rows);
				errorStr.add("性别未能识别");
				continue;
			}else if(!StringUtil.isEmptyString(rows[10]) && !StringUtil.isMobile(rows[10])){
				errorData.add(rows);
				errorStr.add("手机号码格式有误");
				continue;
			}else if(provicesMap.get(province) == null){
				errorData.add(rows);
				errorStr.add("未知的省份");
				continue;
			}else if(!StringUtil.isEmptyString(rows[14]) && clientNameMap.get(rows[14]) == null){
				errorData.add(rows);
				errorStr.add("上级不存在");
				continue;
			}else if(!StringUtil.isEmptyString(rows[16]) && !StringUtil.checkPost(rows[16])){
				errorData.add(rows);
				errorStr.add("邮编有误");
				continue;
			}
			String[] clientRolesStr = clientRoles.split(",");
			for (String crs : clientRolesStr) {
				if(clientRolesMap.get(crs) == null){
					errorData.add(rows);
					errorStr.add("不存在的角色"+crs);
					continue out;
				}
			}
			
			if(provicesMap.get(rows[11]) != null){
				parmater.put("id", provicesMap.get(rows[11]).getProvinceId());
				Map<String,City> citysMap = cityDao.queryCityMapByProvinceId(parmater);//城市
				if(citysMap.get(rows[12]) != null){
					parmater.put("cityId", citysMap.get(rows[12]).getCityId());
					Map<String,District> districtsMap = districtDao.queryDistrictMapByCityId(parmater);//区县
					if(!StringUtil.isEmptyString(rows[13]) && districtsMap.get(rows[13]) == null){
						errorData.add(rows);
						errorStr.add("未知的区县");
						continue;
					}
					clientUser.setCityId(citysMap.get(rows[12]).getCityId());
					if(!StringUtil.isEmptyString(rows[13])){
						clientUser.setDistrictId(districtsMap.get(rows[13]).getDistrictId());
					}else{
						clientUser.setDistrictId(null);
					}
				}else{
					errorData.add(rows);
					errorStr.add("未知的城市");
					continue;
				}
			}
			if(setLoginName.contains(logName)){
				errorData.add(rows);
				errorStr.add("用户名重复");
				continue;
			}else{
				tureData.add(rows);
			}
			setLoginName.add(logName);
			
			clientUser.setLoginName(logName);
			clientUser.setClientStructureId(clientStructureMap.get(clientStructure).getClientStructureId());
			clientUser.setClientPositionId(positionMap.get(clientPosition).getClientPositionId());
			if(!StringUtil.isEmptyString(rows[5]) && rows[5].equals(Constants.LZ)){
				clientUser.setStatus(ChannelEnum.CLIENT_USER_STATUS.LZ.getKey());
			}else if(!StringUtil.isEmptyString(rows[5])  && rows[5].equals(Constants.ZZ)){
				clientUser.setStatus(ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey());
			}else{
				clientUser.setStatus(null);
			}
			clientUser.setTelephoneNo(rows[6]);
			if(!StringUtil.isEmptyString(rows[7]) && rows[7].equals(Constants.JY)){
				clientUser.setIsActivation(ChannelEnum.CLIENT_USER_ISACTIVATION.JY.getKey());
			}else if(!StringUtil.isEmptyString(rows[7]) && rows[7].equals(Constants.QY)){
				clientUser.setIsActivation(ChannelEnum.CLIENT_USER_ISACTIVATION.QY.getKey());
			}else if(!StringUtil.isEmptyString(rows[7]) && rows[7].equals(Constants.WSY)){
				clientUser.setIsActivation(ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey());
			}else{
				clientUser.setIsActivation(null);
			}
			clientUser.setIdcard(rows[8]);
			if(!StringUtil.isEmptyString(rows[9]) && rows[9].equals(Constants.MAN)){
				clientUser.setSex(Constants.MANIN);
			}else if(!StringUtil.isEmptyString(rows[9]) && rows[9].equals(Constants.WOMAN)){
				clientUser.setSex(Constants.WOMANIN);
			}else{
				clientUser.setSex(null);
			}
			clientUser.setMobileNo(rows[10]);
			clientUser.setProvinceId(provicesMap.get(rows[11]).getProvinceId());
			
			clientUser.setAddr(rows[15]);
			clientUser.setRemark(rows[18]);
			clientUser.setClientId(clientId);
			clientUserlist.add(clientUser);
		}
		boolean flag = false;
		/*批量新增数据**/
		if(clientUserlist != null && !clientUserlist.isEmpty()){
			try {
				clientUserDao.insertList(clientUserlist);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		/**查询clientUserId*/
		List<ClientUserRelation> clientUserRelationlist = new ArrayList<ClientUserRelation>();
		List<ClientRolesUserMapping> clientRolesUserMappinglist = new ArrayList<ClientRolesUserMapping>();
	if(setLoginName != null && !setLoginName.isEmpty()){
		List<ClientUser> clientUserBylogNamelist = clientUserDao.getClientUserBylogName(setLoginName,clientId);
		if(clientUserBylogNamelist != null && !clientUserBylogNamelist.isEmpty() && flag){
			for (ClientUser cu : clientUserBylogNamelist) {
				ClientUserRelation cr = new ClientUserRelation();
				for (int k = 0; k< tureData.size(); k++) {
					String[] loginRows = tureData.get(k);
					if(loginRows[17] != null && !loginRows[17].equals("")){
						String[] logNameStr = loginRows[17].split(",");
						if(cu.getLoginName().equals(loginRows[2])){
							for (int j = 0; j < logNameStr.length; j++) {
								ClientRolesUserMapping cum = new ClientRolesUserMapping();
								cum.setRoleId(clientRolesMap.get(logNameStr[j]).getRoleId());
								cum.setClientUserId(cu.getClientUserId());
								cum.setClientId(clientId);
								clientRolesUserMappinglist.add(cum);
							}
							cr.setClientUserId(cu.getClientUserId());
							cr.setParentId(clientNameMap.get(loginRows[14]).getClientUserId());
							cr.setClientId(clientId);
							clientUserRelationlist.add(cr);
						}
					}
				}
			}
		}
	}
		/**新增人员角色关联关系*/
		if(clientRolesUserMappinglist != null && !clientRolesUserMappinglist.isEmpty()){
			clientRolesUserMappingDao.insertList(clientRolesUserMappinglist);
		}
		/**人员上下级关联*/
		if(clientUserRelationlist != null && !clientUserRelationlist.isEmpty()){
			clientUserRelationDao.insertList(clientUserRelationlist);
		}
		
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errorData.size() - 1);
		resultMessage.put("successCount", clientUserlist.size());
		
		if (errorStr != null && !errorStr.isEmpty()) {
			String excelName = "errWorkLogExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			reader.importResult(resp,errorData,errorStr,clientId,excelName);
			resultMessage.put("errDataExcelPath", excelName);
		}
		ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
		rm.setAttributes(resultMessage);
		return rm;
	}

	@Override
	public Map<String, ClientUser> findClientUserBylogName(Integer clientId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		return clientUserDao.findClientUserBylogName(params);
	}

	@Override
	public ResultMessage addImportFerreroLog(MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp,
			Integer clientId) {
		//设置方法过时
		Map<String,Object> parmater = new HashMap<String,Object>();
		parmater.put("clientId", clientId);
		Map<String,ClientRoles> roleMap = clientRolesDao.getRoleNameMyClientID(parmater); //获取角色集合
		Map<String,ClientStructure> csMap = clientStructureDao.getStructureByClientId(parmater);//部门
		Map<String,ClientPosition> cpMap = clientPositionDao.getClientPositionByClientId(parmater);//职位
		Map<String,ClientUser> userMap = clientUserDao.getClientUserByClientId(parmater);//上级
		Map<String, ClientUser> map =new HashMap<String, ClientUser>();
//		Set<String> setLoginName = new HashSet<String>(); 
		
		
		List<String[]> errorData = new ArrayList<String[]>();
		List<String> errorStr = new ArrayList<String>();
		

//		Map<String[], String> error = new HashMap<String[], String>();
		
//		7、直属上级需要在系统中存在。    职位状态 0/ 1
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		
		//列明确认
		ResultMessage rm = affirmTitle(values.get(0) ,ImportConstants.FACT_CLIENT_USER_TITLE);
		if(null != rm){
			return rm ;
		}
		errorData.add(values.get(0));
		
		for (int i = 1; i < values.size(); i++) {
			String[] rows = values.get(i);
			String userName = rows[0];
			String identity = rows[1];
			String name = rows[2];
			String entryTime = rows[3];
			String clientRoles = rows[4];
			String cs = rows[5];
			String cp = rows[6];
			String status = rows[7];
			String parentName = rows[8];
			
			if(-1 != entryTime.indexOf("/")){
				entryTime = entryTime.replace('/', '-');
			}
			
			
//			用户名	身份证	姓名	入职时间	角色*		部门*		职位*		在职状态*		直属上级*
			String clientRolesStr =verify( roleMap,csMap,cpMap,userMap,map, userName, identity,name,
										entryTime, clientRoles,  cs,  cp,  status, parentName);
			if(null != clientRolesStr ){
				errorData.add(rows);
				errorStr.add(clientRolesStr);
//				error.put(rows, clientRolesStr);
				continue;
			}
			ClientUser clientUser = new ClientUser();
			clientUser.setLoginName(userName);		// 用户名
			clientUser.setIdcard(identity);			// 身份证
			clientUser.setName(name);			// 姓名
			clientUser.setCol1(entryTime);			// 入职时间
			clientUser.setRoles(clientRoles); 		// 角色
			clientUser.setStructureName(cs);	// 部门名称
			clientUser.setUplevelName(parentName);		// 直属上级名字
			for(ClientUser user : userMap.values()){
				if(parentName.equals(user.getName())){
					clientUser.setParentId(user.getClientUserId()); //直属上级编号
					continue;
				}
			}
			byte status1 = (byte) ("在职".equals(rows[7].trim())?1:0); 			//在职状态
			clientUser.setClientId(clientId);
			clientUser.setStatus(status1);
			if(null != cpMap.get(cp)){
				clientUser.setClientPositionId(cpMap.get(cp).getClientPositionId());		//职位
			}else{
				errorData.add(rows);
				errorStr.add("不存在的职位");
				continue;
			}
			if(null != csMap.get(cs)){
				clientUser.setClientStructureId(csMap.get(cs).getClientStructureId());		//部门
			}else{
				errorData.add(rows);
				errorStr.add("不存在的部门");
				continue;
			}
			map.put(clientUser.getLoginName(), clientUser);
//			setLoginName.add(userName);
		}
		//批量添加用户
		boolean flag = false;
		/*批量新增数据**/
		if(map != null && !map.isEmpty()){
			try {
				clientUserDao.insertList(new ArrayList<ClientUser>(map.values()));
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		/**查询clientUserId*/
		List<ClientUserRelation> clientUserRelationlist = new ArrayList<ClientUserRelation>();
		List<ClientRolesUserMapping> clientRolesUserMappinglist = new ArrayList<ClientRolesUserMapping>();
		if(map.keySet() != null && !map.keySet().isEmpty()){
			List<ClientUser> clientUserBylogNamelist = clientUserDao.getClientUserBylogName(map.keySet(),clientId);
			if(clientUserBylogNamelist != null && !clientUserBylogNamelist.isEmpty() && flag){
				for (ClientUser cu : clientUserBylogNamelist) {
					ClientUserRelation cr = new ClientUserRelation();
//					for (String[] loginRows :error.keySet()) {
					for (ClientUser user : map.values()) {
//						String[] loginRows = error.keySet().get(k);
						if(user.getRoles() != null && !user.getRoles().equals("")){
							String[] logNameStr = user.getRoles().split(",");
							if(cu.getLoginName().equals(user.getLoginName())){
								for (int j = 0; j < logNameStr.length; j++) {
									ClientRolesUserMapping cum = new ClientRolesUserMapping();
									cum.setRoleId(roleMap.get(logNameStr[j]).getRoleId());
									cum.setClientUserId(cu.getClientUserId());
									cum.setClientId(clientId);
									clientRolesUserMappinglist.add(cum);
								}
								cr.setClientUserId(cu.getClientUserId());
								for(ClientUser ur : userMap.values()){
									if(user.getUplevelName().equals(ur.getName())){
										cr.setParentId(ur.getClientUserId());
										continue;
									}
								}
								if(null != cr.getParentId() && cr.getClientId() != null && cr.getClientUserId() != null){
									cr.setClientId(clientId);
									clientUserRelationlist.add(cr);
								}
							}
						}
					}
				}
			}
		}
		/**新增人员角色关联关系*/
		if(clientRolesUserMappinglist != null && !clientRolesUserMappinglist.isEmpty()){
			clientRolesUserMappingDao.insertList(clientRolesUserMappinglist);
		}
		/**人员上下级关联*/
		if(clientUserRelationlist != null && !clientUserRelationlist.isEmpty()){
			clientUserRelationDao.insertList(clientUserRelationlist);
		}
		
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errorData.size() - 1);
		resultMessage.put("successCount", map.size());
		
		if (errorStr != null && ! errorStr.isEmpty()) {
			String excelName = "errWorkLogExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			reader.importResult(resp,errorData,errorStr,clientId,excelName);
			resultMessage.put("errDataExcelPath", excelName);
		}
		rm = ResultMessage.IMPORT_SUCCESS_RESULT;
		rm.setAttributes(resultMessage);
		return rm;
	}
	
	/**
	 * 数据校验，校验一些基本信息
	 * @param roleMap		角色集合
	 * @param csMap			部门集合
	 * @param cpMap			职位集合
	 * @param userMap		用户集合
	 * @param map 			已有用户集合
	 * @param userName		用户名
	 * @param identity		身份证
	 * @param name			姓名
	 * @param entryTime		入职时间
	 * @param clientRoles	角色
	 * @param cs			部门
	 * @param cp			职位
	 * @param status		在职状体
	 * @param parentName	直接上属姓名
	 * @return
	 */
	private String verify(Map<String, ClientRoles> roleMap,
			Map<String, ClientStructure> csMap,
			Map<String, ClientPosition> cpMap, Map<String, ClientUser> userMap,
			Map<String, ClientUser> map,
			String userName, String identity, String name, String entryTime,
			String clientRoles, String cs, String cp, String status,
			String parentName) {
		String ret = "";
		if(null == userName || userName.trim().length() == 0){
			return "用户名不能为空";
		}else if(!StringUtil.isSpecial(userName)){
			return "用户名不能有特殊字符";
		}
		if(null != userMap.get(userName) || null !=map.get(userName)){
			return "用户名已存在";
		}
		if(null == identity || !StringUtil.isIdentity(identity)){
			return "证件有误";
		}
		if(null == name || name.trim().length() == 0){
			return "姓名不能为空";
		}else if(!StringUtil.isSpecial(name)){
			return "姓名不能有特殊字符";
		}
		try {
			DateUtil.toSimpleDate(entryTime);
		} catch (Exception e) {
			return "时间格式错误";
		}
		String[] clientRolesStr = clientRoles.split(",");
		for (String crs : clientRolesStr) {
			if(!StringUtil.isSpecial(crs)){
				return "角色"+ crs+"存在特殊字符";
			}
			if(roleMap.get(crs) == null){
				return "不存在的角色"+crs;
			}
		}
		if(null == csMap.get(cs)){
			return "不存在的部门";
		}else if(!StringUtil.isSpecial(cs)){
			return "部门存在特殊字符";
		}
		if(null == cpMap.get(cp)){
			return "不存在的职位";
		}
		if(!"在职".equals(status) &&  !"离职".equals(status)){
			return "不存在的在职状态";
		}else if(!StringUtil.isSpecial(status)){
			return "在职状态存在特殊字符";
		}
		if(!StringUtil.isSpecial(parentName)){
			return "直接上属存在特殊字";
		}
		for(ClientUser user : userMap.values()){
			if(parentName.equals(user.getName())){
				return null;
			}else {
				ret = "直接上属不存在";
			}
		}
		return ret.trim().length() > 0?ret :null;
	}
	
	/**
	 * Excel 列明确认
	 * @param titles		Excel列明
	 * @param titleTemplate	模板列明
	 * @return
	 */
	private ResultMessage affirmTitle(String[] titles ,String[] titleTemplate ){
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(titleTemplate, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		return null;
	}

	@Override
	public Map<String, ClientUser> getCMByLoginName(Integer clientId)
			throws BusinessException {
		Map<String,Object> parmare = new HashMap<String,Object>();
		parmare.put("clientId", clientId);
		return clientUserDao.getCMClientPosition(parmare);
	}

	@Override
	public List<ClientUser> getClientUserPositionBystoreId(Integer clientUserId, Integer clientId, String name,String deptIds, String subordinates, String popId)
			throws BusinessException {
		List<ClientUser> list = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientId", clientId);
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("name", name);
			paramMap.put("subStructureId", deptIds);
			paramMap.put("subordinates", subordinates);
			paramMap.put("storeId", popId);
			list = clientUserDao.getClientUserPositionBystoreId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getClientUserPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	/**
	 * eHR系统人员信息更新	
	 * @return
	 */
@SuppressWarnings("unchecked")
public String updateClientUser(Integer clientId,Map<String, ClientUser> clinetUsers,Map<String, City> citys,ExecTimeLog execTimeLog,Map<String, ClientStructure> structuresMap){
	String time =null;
	try {
		LOG.info("UsersTask start ");
		//获取系统当前时间
		String lastUpdateTime = DateTimeUtils.formatTime(execTimeLog.getLastExecuted(), DateTimeUtils.yyyyMMddHHmmss);
		String xml = "<?xml version='1.0' encoding='utf-8'?><soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'><soap:Body><GetEmpInfo xmlns='http://tempuri.org/'><lastUpdateTime>"+lastUpdateTime+"</lastUpdateTime></GetEmpInfo></soap:Body></soap:Envelope>";
		String s = HttpClientUtils.doPost(eHRURL, xml);
		LOG.info("ehr user sysn result:"+s);
		int indexOf = s.indexOf("{");
		int lastIndexOf = s.lastIndexOf("}");
		String substring = s.substring(indexOf,lastIndexOf+1);
		JSONObject jsonObject = JSONObject.fromObject(substring);
		String msg = (String) jsonObject.get("Msg");
		time = (String) jsonObject.get("DateTime");
		String batchId = UUID.randomUUID().toString();
		if(StringUtil.isEmptyString(msg)){
			JSONArray jsonArray = jsonObject.getJSONArray("Users");
			String jsonString = jsonArray.toString();
			List<Users> us = (List<Users>) JsonTool.transToList(jsonString, Users.class);
			//FileUtils.write(new File("/Users/Kris/Json.txt"), jsonString);
			List<ClientUser> updateclList=new ArrayList<ClientUser>();
			List<ClientUser> insertclList=new ArrayList<ClientUser>();
			int errorRecord = 0;
			//StringBuffer sb = new StringBuffer();
			List<District> districts = districtDao.queryAll();
			for (Users users : us) {
				ClientUser c = new ClientUser();
				c.setCol1(batchId);
				c.setName(users.getName());
				c.setUserNo(users.getCode());
				c.setMobileNo(users.getMobile());
				c.setClientPositionId(159);
				c.setClientId(clientId);
				if("在职".equals(users.getStatus())){
					c.setStatus((byte)1);
				}else{
					c.setStatus((byte)0);	
				}
				c.setIdcard(users.getIDCard());
				String tCity = users.getCity()+"市";
				//match city then district
				if(citys.containsKey(tCity)){
					City city = citys.get(tCity);
					if(null!=city){
						c.setCityId(city.getCityId());
						c.setProvinceId(city.getProvinceId());
					}
				}else{
					boolean discontain = false;
					int disid= -1;
					for (District district : districts) {
						if(district.getDistrict().equals(tCity)){
							discontain = true;
							disid = district.getDistrictId();
							break;
						}
					}
					if(discontain){
						c.setDistrictId(disid);
					}else{
						//sb.append(users.getName()+","+users.getCity()+"-\n");
						errorRecord++;
					}
				}
				if(structuresMap.containsKey(tCity)){
					Integer id = structuresMap.get(tCity).getClientStructureId();
					c.setClientStructureId(id);
				}else{
					c.setClientStructureId(1447); //没有关联到部门的统一关联到EHR同步部门下
				}
				
				c.setLoginName(users.getMobile());
				c.setJoinDate(users.getJoinDateTime());
				c.setRoles("mobile");
				c.setLeaveDate(users.getLeaveDateTime());
				c.setPlainPassword("8888");
				PasswordHelper.entryptPassword(c);
				
				c.setIsActivation((byte) 1);
				
				String v = users.getMobile()+","+users.getName();
				if(clinetUsers.containsKey(v)){
					c.setClientUserId(clinetUsers.get(v).getClientUserId());
					updateclList.add(c);
				}else{
					/*ClientRolesUserMapping cum = new ClientRolesUserMapping();
					cum.setClientId(8);
					cum.setClientUserId(c.getClientUserId());
					cum.setRoleId(71);*/
					
					insertclList.add(c);
//					insertRoles.add(cum);
				}
			}
			LOG.info("sysn result: insert COUNT "+insertclList.size() +",update COUNT " + updateclList.size() +", ERROR COUNT " + errorRecord);
			//LOG.info(sb.toString());
			if(!insertclList.isEmpty()){
				clientUserDao.insertList(insertclList);
				LOG.info("insert items "+insertclList.size());
			}
			if(!updateclList.isEmpty()){
				for (ClientUser clientUser : updateclList) {
					clientUserDao.update(clientUser);
				}
				LOG.info("update items： "+updateclList.size());
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("col",batchId);
			List<Integer> insertRoles = clientUserDao.getClientUserByCol1(params);
			if(!insertRoles.isEmpty()){
				clientRolesUserMappingDao.insertGZRZList(insertRoles);
				LOG.info("insert roles items: "+insertRoles.size());
			}
			LOG.info("UsersTask end ");
		}
		} catch (Exception e) {
		e.printStackTrace();
		LOG.info(e.getMessage());
	}
	return time;
}

	@Override
	public Map<String, ClientUser> queryAllClinetUser(Map<String, Object> parames) {
		return clientUserDao.queryAllClinetUser(parames);
	}

	
	
	@Override
	public Map<String, Object> importClientUser(List<String[]> values,  Map<String,ClientUser> mapUser,
			Map<String, ClientStructure> mapDept, Map<String, City> mapCity,
			Map<String, Province> mapProvince,
			Map<String, ClientRoles> mapClientRoles,
			Map<String, ClientPosition> mapClientPosition, Integer clientId)
			throws BusinessException {
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> parmater = new HashMap<String,Object>();
		parmater.put("clientId", clientId);
		Map<String, ClientUser> mapUserLogin = clientUserDao.queryAllClinetUserbyLogin(parmater);
		Map<String, ClientUser> map =new HashMap<String, ClientUser>();
		if(null==values || values.isEmpty() || values.size()<=1){
			returnMap.put("errorMsg","导入数据不能为空");
			return returnMap;
		}
		String[] titles = values.get(0); //获取导入数据表头
		for (String title : titles) {
			if(StringUtils.isEmpty(title)){
				returnMap.put("errorMsg","列名不能为空");
				return returnMap;
			}else if(!MobiStringUtils.contains(ImportConstants.PERSONNEL_TITLE,title)){
				returnMap.put("errorMsg","存在不能识别的列名：" +title);
				return returnMap;
			}
		}
		errDataList.add(titles);
		List<ClientUser> insertList = new ArrayList<ClientUser>();
		//数据校验
		for (int i = 1; i < values.size(); i++) {
			String[] vals = values.get(i);
			 
			String name = vals[0];
			String telephoneNo = vals[1];
			String email = vals[2];
			String loginName = vals[3];
			String cs = vals[4];
			String cs1 = vals[5];
			String province = vals[6];
			String city = vals[7];
			String cp = vals[8];
			String clientRoles = vals[9];
			String parentName = vals[10];	
		 Map<String,Object> parmaterprovince = new HashMap<String,Object>();
		 parmaterprovince.put("province", province);
		 Map<String,City> mapCityByprovince= cityDao.selectCityByProvince(parmaterprovince);
		 Map<String,Object> parmatercs = new HashMap<String,Object>();
		 parmatercs.put("structurename", cs);
		 parmatercs.put("clientId", clientId);
		Map<String,ClientStructure> mapDeptbyparentId=clientStructureDao.getStructureByparentId(parmatercs);
		 /*ClientUser clientUserbyname = mapUser.get(parentName);
		 String a =clientUserRelationDao.getSubordinatesWithOutSelf(clientUserbyname.getId().toString());
		 Map<String,Object> parmatercp = new HashMap<String,Object>();
		 parmaterprovince.put("province", cp);*/
		if(StringUtil.isEmptyString(name)){
			 errStrList.add("用户名不能为空");
			 errDataList.add(vals);
		     continue;
		}else if(!StringUtil.isSpecial(name)){
			 errStrList.add("用户名不能有特殊字符");
			 errDataList.add(vals);
			 continue;
		}
		if(StringUtil.isEmptyString(city)){
			 errStrList.add("城市不能为空");
			 errDataList.add(vals);
		     continue;
		}else if(!mapCityByprovince.containsKey(city)){
			 errStrList.add("城市不存在");
			 errDataList.add(vals);
		     continue;
		}
		if(StringUtil.isEmptyString(province)){
			 errStrList.add("省份不能为空");
			 errDataList.add(vals);
		     continue;
		}else if(!mapProvince.containsKey(province)){
			 errStrList.add("省份不存在");
			 errDataList.add(vals);
		     continue;
		}
		if(StringUtil.isEmptyString(telephoneNo)){
			 errStrList.add("手机不能为空");
			 errDataList.add(vals);
		     continue;
		}else if(!StringUtil.isMobile(telephoneNo)){
			 errStrList.add("手机格式有误");
			 errDataList.add(vals);
		     continue;
		}else if(!StringUtil.isNumber(telephoneNo)){
			 errStrList.add("手机格式有误");
			 errDataList.add(vals);
		     continue;
		}
		if(!StringUtil.validEmail(email)){
			 errStrList.add("邮箱有误");
			 errDataList.add(vals);
		     continue;
		} 
		if(StringUtil.isEmptyString(loginName)){
			 errStrList.add("登陆名不能为空");
			 errDataList.add(vals);
			 continue;
		}else if(!StringUtil.isSpecial(loginName)){
			 errStrList.add("登陆名不能有特殊字符");
			 errDataList.add(vals);
			 continue;
		}else if(mapUserLogin.containsKey(loginName)){
			 errStrList.add("登陆名已存在");
			 errDataList.add(vals);
			 continue;
		}
		String[] clientRolesStr = clientRoles.split(",");
		
		boolean flag = false;
		for (String crs : clientRolesStr) {
			if(!mapClientRoles.containsKey(crs)){
				 errStrList.add("不存在的角色"+crs);
				 errDataList.add(vals);
				 flag = true;
			}
		}
		
		if(flag){
			continue;
		}
		
		if(!mapDept.containsKey(cs)){
			 errStrList.add("不存在的部门");
			 errDataList.add(vals);
			 continue;
		}else if(!StringUtil.isSpecial(cs)){
			 errStrList.add("部门存在特殊字符");
			 errDataList.add(vals);
			 continue;
		}else if(StringUtil.isEmptyString(cs)){
			 errStrList.add("部门不能为空");
			 errDataList.add(vals);
			 continue;
		}
		if(!mapDeptbyparentId.containsKey(cs1)){
			 errStrList.add("不存在的部门");
			 errDataList.add(vals);
			 continue;
		}else if(!StringUtil.isSpecial(cs1)){
			 errStrList.add("部门存在特殊字符");
			 errDataList.add(vals);
			 continue;
		}else if(StringUtil.isEmptyString(cs1)){
			 errStrList.add("部门不能为空");
			 errDataList.add(vals);
			 continue;
		}
		if(!mapClientPosition.containsKey(cp)){
			 errStrList.add("不存在的职位");
			 errDataList.add(vals);
			 continue;
		}
		if(!StringUtil.isSpecial(parentName)){
			 errStrList.add("直接上属存在特殊字");
			 errDataList.add(vals);
			 continue;
		}else if(!mapUser.containsKey(parentName)){
			 errStrList.add("直接上属不存在");
			 errDataList.add(vals);
			 continue;
		}
			ClientStructure clientStructurefen = mapDept.get(cs1);
			City mcity = mapCity.get(city);
			Province mprovince=mapProvince.get(province);
			ClientRoles mclientRoles=mapClientRoles.get(clientRoles);
			ClientPosition mclientPosition=mapClientPosition.get(cp);
			 Integer[] intg = new Integer[]{mclientRoles.getId()};
			ClientUser clientUser = new ClientUser();
			clientUser.setClientId(clientId);
			clientUser.setName(name);		// 用户名
			clientUser.setMobileNo(telephoneNo);
			clientUser.setEmail(email);
			clientUser.setLoginName(loginName);    // 登录名
			clientUser.setRoles(clientRoles); 		// 角色
			clientUser.setLoginName(loginName);    // 登录名
		    clientUser.setRolesId(intg); 		// 角色
			clientUser.setCityId(mcity.getCityId());
			clientUser.setProvinceId(mprovince.getProvinceId()); 
			clientUser.setClientPositionId(mclientPosition.getClientPositionId());
			clientUser.setClientStructureId(clientStructurefen.getClientStructureId());
			clientUser.setStructureName(cs);	    // 部门名称
			clientUser.setUplevelName(parentName);		// 直属上级名字
			for(ClientUser user : mapUser.values()){
				if(parentName.equals(user.getName())){
					clientUser.setParentId(user.getClientUserId()); //直属上级编号
					continue;
				}
			}
			map.put(clientUser.getLoginName(), clientUser);
//			setLoginName.add(userName);

				insertList.add(clientUser);
		 
			 
		}
		//批量添加用户
		boolean flag = false;
		/*批量新增数据**/
		if(map != null && !map.isEmpty()){
			try {
				if (null != insertList && !insertList.isEmpty()) {
					clientUserDao.insterclientuserlist(insertList);
					flag = true;
				flag = true;
			}
			}catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		/**查询clientUserId*/
		List<ClientUserRelation> clientUserRelationlist = new ArrayList<ClientUserRelation>();
		List<ClientRolesUserMapping> clientRolesUserMappinglist = new ArrayList<ClientRolesUserMapping>();
		if(map.keySet() != null && !map.keySet().isEmpty()){
			List<ClientUser> clientUserBylogNamelist = clientUserDao.getClientUserBylogName(map.keySet(),clientId);
			if(clientUserBylogNamelist != null && !clientUserBylogNamelist.isEmpty() && flag){
				for (ClientUser cu : clientUserBylogNamelist) {
					ClientUserRelation cr = new ClientUserRelation();
//					for (String[] loginRows :error.keySet()) {
					for (ClientUser user : map.values()) {
//						String[] loginRows = error.keySet().get(k);
						if(user.getRoles() != null && !user.getRoles().equals("")){
							String[] logNameStr = user.getRoles().split(",");
							if(cu.getLoginName().equals(user.getLoginName())){
								for (int j = 0; j < logNameStr.length; j++) {
									ClientRolesUserMapping cum = new ClientRolesUserMapping();
									cum.setRoleId(mapClientRoles.get(logNameStr[j]).getRoleId());
									cum.setClientUserId(cu.getClientUserId());
									cum.setClientId(clientId);
									clientRolesUserMappinglist.add(cum);
								}
								cr.setClientUserId(cu.getClientUserId());
								for(ClientUser ur : mapUser.values()){
									if(user.getUplevelName().equals(ur.getName())){
										cr.setParentId(ur.getClientUserId());
										continue;
									}
								}
								
							 	if(null != cr.getParentId() && cr.getClientUserId() != null){
									cr.setClientId(clientId);
									clientUserRelationlist.add(cr);
								} 
							}
						}
					}
				}
			}
		}
		/**新增人员角色关联关系*/
		if(clientRolesUserMappinglist != null && !clientRolesUserMappinglist.isEmpty()){
			clientRolesUserMappingDao.insertList(clientRolesUserMappinglist);
		}
		/**人员上下级关联*/
		if(clientUserRelationlist != null && !clientUserRelationlist.isEmpty()){
			clientUserRelationDao.insertList(clientUserRelationlist);
		}
		
		Integer successCount = insertList.size() ;
		returnMap.put("successCount", successCount);
		returnMap.put("errorCount", errStrList.size());

		returnMap.put("errStrList", errStrList);
		returnMap.put("errDataList", errDataList);
		return returnMap;
	}
	
	
	@Override
	public Map<String, ClientUser> getClientUserByClientId(Integer id) throws BusinessException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", id);
		return clientUserDao.getClientUserByClientId(parameters);
	}

 

	@Override
	public Map<String, ClientUser> getClientUserByClientName(Integer id)
			throws BusinessException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", id);
		return clientUserDao.getClientUserByClientName(parameters);
	}

	@Override
	public List<ClientUser> getClientUserName(Integer clientId,String positionName) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("clientId", clientId);
		params.put("positionName",positionName);
		return clientUserDao.getClientUserName(params);
	}


 
	
  
}

