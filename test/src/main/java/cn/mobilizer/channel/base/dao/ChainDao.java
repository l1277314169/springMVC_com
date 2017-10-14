/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class ChainDao extends MyBatisDao {
	
	public ChainDao() {
		super("CHAIN");
	}
	
	public List<TreeNodeVO> getTreeNodesByPId(Map<String, Object> paramMap) {
		return super.getList("getTreeNodesByPId", paramMap);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<Chain> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public Chain getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	
	public Integer queryChainCount(Map<String, Object> parames){
		return super.get("queryTotalCount", parames);
	}
	
	public List<Chain> queryChainList (Map<String, Object> params) {
		return super.queryForList("selectByParams", params);
	}
	
	public int insert(Chain chain){
		return super.insert ("insertSelective", chain);
		
	}
	
	public int delete(Integer id){
		return super.delete("deleteByPrimaryKey", id);
	}
	
	public int update(Chain chain){
		
		return super.update("updateByPrimaryKeySelective", chain);
	}
	
	public Chain findChannelById(int id){
		return super.get ("selectByPrimaryKey", id);
	}
	
	
	public List<Chain> findChainList(Object Params){
		return super.queryForListChain("findByParams",Params);
		
	}

	public Chain getChainByChainId(Integer chainId) {
		return super.get("selectByPrimaryKey", chainId);
	}
	
	public Chain findChainAndChannel(Integer chainId){
		return super.get("findChainAndChannel", chainId);
	}
	
	public List<Chain> findChainByChainName(Map<String, Object> parames){
		return super.queryForList("findChainByChainName", parames); 
	}
	
	public Map<String,Chain> getChainMap(Map<String, Object> parames){
		return super.queryForMap("getChainMap", parames, "chainName");
	}
}
