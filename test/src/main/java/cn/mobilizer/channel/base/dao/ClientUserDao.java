/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.posm.po.Warehouse;

@Repository
public class ClientUserDao extends MyBatisDao {
	
	public ClientUserDao() {
		super("CLIENT_USER");
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public ClientUser findByLoginName(Map<String, Object> params){
		List<ClientUser> ls = super.queryForList ("selectByParams", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public ClientUser findByLoginNameAndLoginPWD(Map<String, Object> params){
		List<ClientUser> ls = super.queryForList ("selectByParams", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 		根据 params 参数到数据库中查询 client_user 表
	 * 			有返回 用户 没有返回Null
	 *  	返回查询结果集中的第一个
	 *  
	 *  params
	 *  	LoginName
	 *  	ClientName
	 * </pre>
	 * @param params 
	 * @return
	 */
	public ClientUser findByLoginNameAndClientName(Map<String, Object> params){
		List<ClientUser> ls = super.queryForList ("selectByLoginNameAndClientName", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	public ClientUser selectByPrimaryKey(Integer clientUserId){
		return super.get("selectByPrimaryKey",clientUserId);
	}
	
	public ClientUser findFullUserInfoByPrimaryKey(Integer clientUserId){
		return super.get("findFullUserInfoByPrimaryKey",clientUserId);
	}
	
	public ClientUser findByPrimaryKey(Integer clientUserId){
		return super.get("findByPrimaryKey",clientUserId);
	}
	public int update(ClientUser clientUser){
		return super.update("updateByPrimaryKeySelective", clientUser); 
	}

	public Integer queryClientUserCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<ClientUser> queryClientUserList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	
	
	public int delete(Integer clientUserId){
		return super.delete ("deleteByPrimaryKey", clientUserId);
	}
	
	public int insert(ClientUser clientUser){
		super.insert ("insertSelectiveclient", clientUser);
		return clientUser.getClientUserId ();
	}
	
	public boolean saveAll(List<ClientUser> list){
		boolean re = true;
		for (ClientUser user : list) {
			int i = super.insert("insertSelectiveclient", user);
			if(i == 0){
				re = false;
				break;
			}
		}
		return re;
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<ClientUser> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public ClientUser getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	
	
	
	public List<ClientUser> getClientUserWithoutSelf(Map<String, Object> parames){
		return super.queryForList("getClientUserPosition", parames);
	}
	
	public ClientUser getParentClientUser(Map<String, Object> parames){
		return super.get("getParentClientUserClientPosition", parames);
	}
	
	public ClientUser getDistributorLogName(Map<String, Object> parames){
		return super.get("getParentClientUserToDistributor", parames);
		
	}

	
	public List<ClientUser> getClientUserPosition(Map<String, Object> paramMap) {
		return super.getList("getClientUserPosition", paramMap);
	}
	
	public ClientUser getLogName(Map<String, Object> paramMap){
		return super.get("selectLogName", paramMap);
		
	}
	
	public ClientUser getParentClientUserToDistributor(Map<String, Object> parames){
		return super.get("getParentClientUser", parames);
	}

	public ClientUser getClientUser(Integer clientUserId) {
		return super.get("selectByPrimaryKey", clientUserId);
	}

	public ClientUser getClientUserPositionById(Map<String, Object> parames) {
		return super.get("getClientUserPositionById", parames);
	}
	
	public ClientUser findByName(String loginName, Integer clientId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("clientId", clientId);
		return super.get("findByName", params);
	}
	
	public List<ClientUser> findSubordinatesWhoHasApproveCallPlanning(Integer clientUserId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientUserId", clientUserId);
		return super.getList("findSubordinatesWhoHasApproveCallPlanning", params);
	}
	public  List<ClientUser> findclientUserByParentId(Map<String, Object> parameters){
		return super.queryForList("selectClientUserAndPosition", parameters);
		
	}

	public List<ClientUser> queryForListForReport(Map<String, Object> parameters) {
		return super.getList("findListByParams",parameters);
	}
	
	public List<ClientUser> exportClientUserByParams(Map<String, Object> parameters){
		return super.getList("exportClientUserByParams",parameters);
	}
	
	public String findAllClientUser(Map<String, Object> parameters){
		return super.get("findAllClientUser",parameters);
	}
	
	public String findAllsClientUser(Map<String, Object> parameters){
		return super.get("replaceAllsClientUser", parameters);
		
	}
	public List<ClientUser> findMessageDetails(Map<String,Object> params){
		return super.queryForList("findListByParamsMessage", params);
	}
	
	public List<ClientUser> findMessageDetailsRelateUser(Map<String,Object> params){
		return super.queryForList("findMessageDetailsRelateUser", params);
	}
	
	public Integer selectUserStoreRelation(Map<String, Object> params){
		return super.get("selectUserStoreRelation",params);
	}
	
	public List<ClientUser> findClientUserList(Integer clientId){
		return super.queryForList("findClientUserList", clientId);
	}
	
	public String selectUserNoByStoreUser(Map<String,Object> params){
		return super.get("selectUserNoByStoreUser", params);
	}
	
	public ClientUser selectClientUserByUserNo(Map<String,Object> params){
		return super.get("selectClientUserByUserNo",params);
	}
	
	public List<ClientUser> selectClientUserByName(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	
	public Map<String,ClientUser> findClientUserByKeylogName(Map<String, Object> parames){
		return super.queryForMap("findClientUserByKeylogName", parames, "loginName");
	}
	
	/**
	 * 根据 client_ID  查询员工
	 * @param parames	
	 * @return	返回所有员工信息 key ：client_ID  
	 * 		查询员工
	 */
	public Map<String,ClientUser> findClientUserBylogName(Map<String, Object> parames){
		return super.queryForMap("findClientUserBylogName", parames, "loginName");
	}
	
	
	/**
	 * 根据 client_ID  查询员工
	 * @param parames	
	 * @return	返回所有员工信息 key ：name  
	 * 		查询员工 (员工完整数据，员工的所有信息)
	 */
	public Map<String,ClientUser> findClientUserBylName(Map<String, Object> parames){
		return super.queryForMap("findClientUserBylogName", parames, "name");
	}
	
	/**
	 * 根据 client_ID  查询员工
	 * @param parames	
	 * @return	返回所有员工信息 key ：login_name  
	 * 		查询员工 ，查询员工姓名
	 * <pre>
	 * 		Key ， 用户名
	 * 		Value , 用户自动编号
	 * 
	 * </pre>
	 */
	public Map<String,ClientUser> getClientUserByClientId(Map<String, Object> parames){
		return super.queryForMap("getClientUserByClientId", parames, "loginName");
	}
	
	public Map<String,ClientUser> getClientUserByClientName(Map<String, Object> parames){
		return super.queryForMap("getClientUserByClientId", parames, "name");
	}
	
	
	public int insertList(List<ClientUser> list){
		return super.insert("insertList", list);
	}
	
	public List<ClientUser> getClientUserBylogName(Set<String> set,Integer clientId){
		List<String> list =new ArrayList<String>(set);
		Map<String,Object> clientUserMap = new HashMap<String,Object>();
		clientUserMap.put("list", list);
		clientUserMap.put("clientId", clientId);
		return super.getList("getClientUserBylogName", clientUserMap);
	}
	
	public Map<String,ClientUser> getCMClientPosition(Map<String, Object> parames){
		return super.queryForMap("getCMClientPosition", parames, "loginName");
	}
	
	public List<ClientUser> getClientUserPositionBystoreId(Map<String, Object> paramMap) {
		return super.getList("getClientUserPositionBystoreId", paramMap);
	}
    public Map<String, ClientUser> queryAllClinetUser(Map<String, Object> parames){
		
		return super.queryForMap("queryAllClinetUser", parames, "keyName"); 
	}
    public Map<String, ClientUser> queryAllClinetUserbyLogin(Map<String, Object> parames){
		
		return super.queryForMap("queryAllClinetUserByLogin", parames, "loginName"); 
	}
    public Integer querycount(Map<String, Object> parames){
    	
    	return super.get("selectclientuserId", parames);  	
    }
    
    public void insterclientuserlist(List<ClientUser> ClientUserList) {
		for (ClientUser clientUser : ClientUserList) {
			super.insert("insertSelectiveclient", clientUser);
		}

	}
    
    public void updateclientuserlist(List<ClientUser> ClientUserList){
    	for (ClientUser clientUser : ClientUserList) {
			super.update("updateByPrimaryKeySelective", clientUser);
		}
	}
    
    public List<Integer> getClientUserByCol1(Map<String, Object> params){
    	
    	return super.getList("getClientUserByCol1",params);
    }
    
    /**
     * 根据职位查找所有的人员
     * @param params
     * @return
     */
    public List<ClientUser> getClientUserName(Map<String, Object> params)
    {
    	return super.getList("selectLoginName",params);
    }
    
}
