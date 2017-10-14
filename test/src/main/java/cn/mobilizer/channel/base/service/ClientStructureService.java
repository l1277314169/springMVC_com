package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface ClientStructureService extends BasicService<ClientStructure>{

	/**
	 * 查询组织结构树信息
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<TreeNodeVO> getTreeNodes(Integer clientId, Integer id) throws BusinessException;
	
	/**
	 * 从数据权限中-查询某个用户的-组织架构树
	 * @param clientId
	 * @param clientUserId
	 * @param dataPermissions
	 * @return
	 * @throws BusinessException
	 */
	public List<TreeNodeVO> getTreeNodeWithPer(Integer clientId, Integer clientUserId,String dataPermissions) throws BusinessException;
	
	/**
	 * 
	 * @param clientId
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public String getTreeNodes4String(Integer clientId, Integer pId) throws BusinessException;

	public List<Object> getTreeNodes4StringList(Integer clientId,Integer pId) throws BusinessException;
	
	/**
	 * 
	 * @param clientStructure
	 * @return
	 * @throws BusinessException
	 */
	public int updateClientStructure(ClientStructure clientStructure) throws BusinessException;
	
	public int addClientStructure(ClientStructure clientStructure) throws BusinessException;
	
	public int deleteClientStructure(Integer id) throws BusinessException;
	
	public List<ClientStructure> getTopClientStructure(Integer clientId);
	
	public ClientStructure getClientStructureByName(Integer clientId, String structureName);
	
	/**
	 * 通过id查询ClientStructure实例
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public ClientStructure getClientStructureById(Integer id) throws BusinessException;
	/**
	 * 查找重复的部门name
	 * @param name
	 * @return
	 * @throws BusinessException
	 */
	public ClientStructure getRepeatClientStructureName(Map<String, Object> paramMap)throws BusinessException;
	
	/**
	 * 从组织架构管理中-查询某个用户的所在组织架构及其下级的组织架构树-树结构
	 * @param clientId
	 * @param clientUserId
	 * @param dataPermissions
	 * @return
	 * @throws BusinessException
	 */
	public List<TreeNodeVO> getTreeNodeWithSle(Integer clientId, Integer clientUserId,String subClientStructureIds) throws BusinessException;
	
	/**
	 * 获取门店信息 key为门店名称
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, ClientStructure> getClientStructureMapByName(Integer clientId) throws BusinessException;
	
	public  List<ClientStructure> query(Integer clientId);
}
