package cn.mobilizer.channel.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.exception.ImprotException;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.image.po.ExecTimeLog;
import cn.mobilizer.channel.systemManager.po.ClientRoles;
/**
 * @author Administrator
 *
 */
public interface ClientUserService extends BasicService<ClientUser>{
	
	/**
	 * 
	 * @param loginName
	 * @param clientName
	 * @return
	 */
	public ClientUser findByLoginNameAndClientName(String loginName, String clientName);
	
	/**
	 * 
	 * @param loginName
	 * @return
	 */
	public ClientUser findByLoginName(String loginName, int clientId);
	
	/**
	 * 
	 * @param loginName
	 * @param loginPWD
	 * @return
	 */
	public ClientUser findByLoginNameAndLoginPWD(String loginName, String loginPWD);
	
	/**
	 * 此查询关联了职位、组织机构、上下级的数据
	 * @param clientUserid
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser findFullUserInfoByPrimaryKey(Integer clientUserid) throws BusinessException;
	
	/**
	 * 此查询关联了职位、组织架构
	 * @param clientUserid
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser findByPrimaryKey(Integer clientUserid) throws BusinessException;
	
	/**
	 * 
	 * @param clientUserid
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser selectByPrimaryKey(Integer clientUserid) throws BusinessException;
	
	/**
	 * @param clientUser
	 * @return
	 */
	public int addClientUser(ClientUser clientUser);
	/**
	 * @param clientUser
	 * @return
	 */
	public int update(ClientUser clientUser, String oldRoleNames, Integer oldParentId);
	/**
	 * @param clientUserId
	 * @return
	 */
	public int deleteClientUser(Integer clientUserId,Integer clientId);
	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryClientUserCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUser> queryClientUserList(Map<String, Object> searchParams) throws BusinessException;
	
	/**人员导入--(中间映射表在人员添加完毕后同时处理)
	 * @author Nany
	 * 2015年1月26日下午12:02:24
	 * @param users
	 * @return
	 * @throws ImprotException
	 */
	public ImportVO saveAll(List<ClientUser> users) throws ImprotException;
	
	/**
	 * 查询除开自己之外所在客户的所有用户	 
	 * @param clientUserId 用户id-id为空则查询所有的
	 * @param clientId 客户id
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUser> getClientUserWithoutSelf(Integer clientUserId, Integer clientId) throws BusinessException;
	
	/**
	 * 查询除开自己之外所在客户的所有用户-该方法重写后增加了模糊匹配用户名和部门名称
	 * @param clientUserId 用户id-id为空则查询所有的
	 * @param clientId 客户id
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUser> getClientUserWithoutSelf(Integer clientUserId, Integer clientId, String name, String clientUserIdChill, String childId,String subordinates,String deptIds) throws BusinessException;
	
	/**
	 * 根据clientUserId获取该用户的领导
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser getParentClientUser(Integer clientUserId, Integer clientId) throws BusinessException;

	/**获取人员--职位关系数据
	 * @author Nany
	 * 2014年12月19日下午6:02:05
	 * @param clientId
	 * @return
	 */
	public List<ClientUser> getClientUserPosition(Integer clientUserId,Integer clientId, String name,String deptIds, String subordinates, String clientPositionIds) throws BusinessException;
	
	/**
	 * 根据门店Id获取人员--职位关系数据
	 * @param clientUserId
	 * @param clientId
	 * @param name
	 * @param deptIds
	 * @param subordinates
	 * @param popId
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUser> getClientUserPositionBystoreId(Integer clientUserId,Integer clientId, String name,String deptIds, String subordinates,String popId) throws BusinessException;
	
	/**
	 * 
	 */
	public ClientUser findLogName(String LogName,Integer clientId)throws BusinessException;
	/**
	 * 
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser getClientUser(Integer clientUserId, Integer clientId)throws BusinessException;
	/**
	 * 
	 * @param name
	 * @param clientId
	 * @return
	 */
	public ClientUser findByName(String name, Integer clientId);
	
	public String exeAppSql();
	/**
	 * 
	 * @param str
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUser> queryChildByClietnUserParentId(Map<String, Object> parameters)throws BusinessException;
	/**
	 * 
	 * @param clientUser
	 * @return
	 */
	public Integer changePasswd(ClientUser clientUser);
	/**
	 * 	
	 * @param clientUser
	 * @return
	 * @throws BusinessException
	 */
	public int updatePassword(ClientUser clientUser)throws BusinessException;

	public List<ClientUser> queryForListForReport(Map<String, Object> parameters)throws BusinessException;

	/**
	 * 关联所有人员
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public String relevanceAllsClientUser(Map<String, Object> parameters) throws BusinessException;
	/**
	 * 替换所有人的上级
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public String replaceAllsClientUser(Map<String, Object> parameters) throws BusinessException;
	/**
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUser> findMessageClientUser(Map<String, Object> params)throws BusinessException;
	
	
	public int selectUserStoreRelation(Map<String, Object> params) throws BusinessException;
	
	public List<ClientUser> getAllClientUser(Integer clientId) throws BusinessException;
	
	/**
	 * 根据门店关系获取人员工号
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public String selectUserNoByStoreUser(Integer clientId,String storeId) throws BusinessException;
	
	/**
	 * 根据人员编号查询
	 * @param clientId
	 * @param userNo
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser selectClientUserByUserNo(Integer clientId,String userNo) throws BusinessException;
	
	/**
	 * 根据name关键字模糊查询人员
	 */
	public List<ClientUser> selectClientUserByName(Integer clientId,String name) throws BusinessException;
	
	/**
	 * 工作日志导入
	 * @param file
	 * @param request
	 * @param resp
	 * @param clientId
	 * @return
	 */
	public ResultMessage addImportWorkLog(MultipartFile file, HttpServletRequest request,HttpServletResponse resp,Integer clientId);
	
	
	/**
	 * 费罗列导入
	 * @param file		
	 * @param request
	 * @param resp
	 * @param clientId
	 * @return
	 */
	public ResultMessage addImportFerreroLog(MultipartFile file, HttpServletRequest request,HttpServletResponse resp,Integer clientId);
	
	
	/**
	 * 获取用户信息根据登陆账号
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,ClientUser> findClientUserBylogName(Integer clientId) throws BusinessException;
	
	/**
	 * 得到人员的职位是否是CM
	 * @param clientId
	 * @return 
	 * @throws BusinessException
	 */
	public Map<String,ClientUser> getCMByLoginName(Integer clientId)throws BusinessException;
	
	public String updateClientUser(Integer clientIdn,Map<String, ClientUser> clinetUsers,Map<String, City> citys,ExecTimeLog execTimeLog,Map<String, ClientStructure> structuresMap) throws BusinessException;
	
	public Map<String, ClientUser>queryAllClinetUser(Map<String, Object> parames);
	
	public Map<String,Object> importClientUser(List<String[]> values,Map<String,ClientUser> mapUser , Map<String, ClientStructure> mapDept,Map<String, City> mapCity,
			Map<String, Province> mapProvince, Map<String, ClientRoles> mapClientRoles,Map<String,ClientPosition> mapClientPosition,
			Integer clientId)throws BusinessException;

	 public Map<String, ClientUser> getClientUserByClientId(Integer id) throws BusinessException;
	
	 public Map<String, ClientUser> getClientUserByClientName(Integer id) throws BusinessException;
	
	 public List<ClientUser> exportClientUserByParams(Map<String, Object> parames);
	 
	 public List<ClientUser> getClientUserName(Integer clientId,String positionName);
}
