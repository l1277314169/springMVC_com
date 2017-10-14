package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class ClientPositionDao extends MyBatisDao {
	
	public ClientPositionDao() {
		super("CLIENT_POSITION");
	}
	/**根据参数获取列表，可以适用所有基础数据查询**/
	public List<ClientPosition> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	/**根据参数获取列表，可以适用所有基础数据查询**/
	public ClientPosition getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	/**
	 * 
	 * @param param
	 * @return Integer 总记录数
	 */
	public Integer queryClientPositionCount(Map<String, Object> params) {
		return super.get("queryTotalCount", params);
	}
	
	public List<ClientPosition> queryClientPositionList(Map<String, Object> params){
		return super.queryForList("findListByParams", params);
	}
	
	public List<ClientPosition> getAllClientPosition(Map<String, Object> params){
		return super.getList ("getAllClientPosition",params);
	}
	
	public int insert(ClientPosition clientPosition){
		return super.insert ("insertSelective", clientPosition);
	}
	
	public int delete(Integer clientPositionId){
		return super.delete ("deleteByPrimaryKey", clientPositionId);
	}
	
	public int update(ClientPosition clientPosition){
		return super.update ("updateByPrimaryKeySelective", clientPosition);
	}
	
	public ClientPosition findClientPositionById(int id){
		return super.get ("selectByPrimaryKeys", id);
	}

	public List<ClientPosition> findClientPositionByMobilePer(Map<String, Object> params){
		return super.queryForList("findClientPositionByMobilePer", params);
	}
	
	public Integer getUserNameItems(Map<String, Object> params){
		return super.get("getUserNameItems",params);
	}
	
	public ClientPosition findPositionByStoreIdAndUserIds(Map<String,Object> params){
		return super.get("findPositionByStoreIdAndUserIds", params);
	}
	
	public List<ClientPosition> findClientPositionByName(Map<String,Object> params){
		return super.queryForList("findClientPositionByName", params);
	}
	
	/**
	 * 根据 client_id 查询 所有部门
	 * @param params 参数 client_id
	 * @return	
	 * <pre>
	 * 返回MAP ， key： positionName (职位名称)
	 * 	返回信息中 有职位详细信息。（包含整条数据）
	 * 
	 * </pre>
	 */
	public Map<String,ClientPosition> getClientPositionMapByName(Map<String,Object> params){
		return super.queryForMap("getAllClientPosition", params, "positionName");
	}

	
	
	/**
	 * 根据 client_id 查询 所有部门
	 * @param params 参数 client_id
	 * @return	
	 * <pre>
	 * 		返回MAP ， key： positionName(职位名称)
	 * 		查询返回  职位名称集合 ， 不包含其他数据
	 * 		
	 * 		key 职位名称
	 * 		value 职位编号 ， 职位名称
	 * </pre>
	 */
	public Map<String,ClientPosition> getClientPositionByClientId(Map<String,Object> params){
		return super.queryForMap("getClientPositionByClientId", params, "positionName");
	}

	
	public  List<ClientPosition> getTreeNodesByPId(Map<String,Object> params){
		return super.queryForList("getTreeNodesByPId", params);
	}
	
	public List<ClientPosition> getClientPositionBusiness(Map<String,Object> params){
		return super.queryForList("getClientPositionBusiness", params);
	}

}
