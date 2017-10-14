/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class ClientStructureDao extends MyBatisDao {
	
	public ClientStructureDao() {
		super("CLIENT_STRUCTURE");
	}
	
	public List<TreeNodeVO> getTopNodes() {
		return super.getList("getTopNodes");
	}
	
	public List<TreeNodeVO> getTreeNodesByPId(Map<String, Object> paramMap) {
		return super.getList("getTreeNodesByPId", paramMap);
	}
	
	public List<TreeNodeVO> getTreeNodeWithPer(Map<String, Object> paramMap) {
		return super.getList("getTreeNodeWithPer", paramMap);
	}
	
	public List<TreeNodeVO> getTreeNodeWithSle(Map<String, Object> paramMap) {
		return super.getList("getTreeNodeWithSle", paramMap);
	}

	public int updateClientStructure(ClientStructure clientStructure) {
		return super.update ("updateByPrimaryKeySelective", clientStructure);
	}
	
	public int addClientStructure(ClientStructure clientStructure) {
		super.insert("insertSelective", clientStructure);
		int result = clientStructure.getClientStructureId();
		return result;
	}
	
	public int deleteClientStructureByid(ClientStructure clientStructure) {
		return super.update ("updateByPrimaryKeySelective", clientStructure);
	}
	
	public List<ClientStructure> getTopClientStructure(Integer clientId){
		return super.getList("getTopStructure", clientId);
	}
	
	public ClientStructure getClientStructureByName(Integer clientId, String structureName){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("structureName", structureName);
		params.put("clientId", clientId);
		return super.get("selectByParams", params);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<ClientStructure> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public ClientStructure getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}

	
	/**查询所属部门
	*@author Nany
	   2014年11月27日上午11:32:25
	 * @return
	 */
	public List<ClientStructure> query() {
		List<ClientStructure>  clientStructureList = new ArrayList<ClientStructure>();
		clientStructureList = super.getList("selectByParams");
		return clientStructureList;
	}
	
	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	public ClientStructure getClientStructureById(Integer id) {
		return super.get ("selectByPrimaryKeyWhithParent",id);
	}
	
	public String getSubStructure(Integer id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("structureId", id);
		return super.get("selectSubStructrue",id);
	}
	
	public String getSubStructure(String id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("structureId", id);
		return super.get("selectSubStructrue",id);
	}
	
	public String getParentStructure(Integer id){
		return super.get("selectParentStructrue",id);
	}
	
	public List<TreeNodeVO> getDataTreeNode(Map<String, Object> paramMap) {
		return super.getList("getDataTreeNode", paramMap);
	}
	
	public ClientStructure findClientStructureByName(Map<String, Object> paramMap){
		return super.get("findClientStructureByName", paramMap);
	}
	
	/**
	 * 根据 client_id 查询 部门
	 * @param parmarter	：client_id 
	 * @return	
	 * 	<pre>
	 * 		Map ， key ：structureName （组织结构名称） 会查询部门所有信息
	 * </pre>		
	 * 
	 * 		
	 */
	public Map<String,ClientStructure> getClientStructureMapByName(Map<String,Object> parmarter){
		return super.queryForMap("getClientStructureMapByName", parmarter, "structureName");
	}
	
	public String getParentStructureById(Integer id){
		return super.get("getParentStructureById", id);
	}
	
	public List<ClientStructure> getStructureByParentId(Map<String, Object> params){
		return super.queryForList("getStructureByParentId",params);
	}
	
	/**
	 *  根据 client_id 查询 部门
	 * 
	 * @param parmarter ： client_id
	 * @return Map ，Key :structureName (部门名称)
	 * 	<pre>
	 * 		根据 client_id查询部门 ，返回信息 部门名称
	 * <pre>
	 * 		key 部门名称
	 * 		value  部门编号，部门名称
	 * 		
	 * </pre>
	 * 	
	 * </pre>
	 */
	public Map<String,ClientStructure> getStructureByClientId(Map<String,Object> parmarter){
		return super.queryForMap("getStructureByClientId", parmarter, "structureName");
	}
	
	public Map<String,ClientStructure> getStructureByparentId(Map<String,Object> parmarter){
		return super.queryForMap("getStructureBy_parentId", parmarter, "structureName");
	}
	
	public Integer getStructureidByparentname(Map<String,Object> parmarter){
		return super.get("getStructure_parentId", parmarter);
	}
	
}
