package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface ClientPositionService extends BasicService<ClientPosition>{

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryClientPositionCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPosition> queryClientPositionList(Map<String, Object> searchParams) throws BusinessException;
	
	public int addClientPosition(ClientPosition clientPosition) throws BusinessException;
	
	public int deleteClinetPostion(Integer clientPositionId) throws BusinessException;
	
	public ClientPosition findClientPositionById(Integer id, Integer clientId) throws BusinessException;

	public int updateClientPosition(ClientPosition clientPosition) throws BusinessException;
	
	/**
	 * 通过clientId查询职位信息
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPosition> findClientPositionsByClientId(Integer clientId) throws BusinessException;
	
	/**
	 * 通过有手机登录权限的人反推所有职位的信息
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPosition> findClientPositionByMobilePer(Integer clientId) throws BusinessException;
	
	/**
	 * 查询用户名是否存在
	 * @return
	 * @throws BusinessException
	 */
	public boolean isExistUserName(String positionName,Integer clientId) throws BusinessException;
	
	/**
	 * 查询用户名是否存在(用户修改)
	 * @param positionName
	 * @param currentUserId
	 * @return
	 * @throws Exception
	 */
	public boolean isExistUserName(String positionName,String clientPositionId,Integer clientId) throws BusinessException;
	
	/**
	 * 根据用户组查询职位组
	 */
	public ClientPosition findPositionByStoreIdAndUserIds(Map<String,Object> params) throws BusinessException;
	/**
	 * 长促和促销队
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPosition> findClientPositionByName(Map<String,Object> params)throws BusinessException;
	
	/***
	 * 查询职位展示树
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPosition> getZtreeClientPosition(Map<String,Object> params)throws BusinessException;
	
	/**
	 * 角色为手机-业务代表和手机-业务代表主管下的职位
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPosition> getClientPositionBusiness(Integer clientId)throws BusinessException;
    
	public Map<String, ClientPosition> selectAllClientPosition(Integer clientId);
	

}
