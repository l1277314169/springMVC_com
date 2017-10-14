/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Distributor;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class DistributorDao extends MyBatisDao {
	
	public DistributorDao() {
		super("DISTRIBUTOR");
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public Distributor findByLoginName(Map<String, Object> params){
		List<Distributor> ls = super.queryForList ("selectByParams", params);
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
	public Distributor findByLoginNameAndLoginPWD(Map<String, Object> params){
		List<Distributor> ls = super.queryForList ("selectByParams", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	public Distributor findByLoginNameAndClientName(Map<String, Object> params){
		
		List<Distributor> ls = super.queryForList ("selectByLoginNameAndClientName", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	public Distributor findByPrimaryKey(Integer distributorId){
		return super.get("selectByPrimaryKeys",distributorId);
	}
	
	public int update(Distributor distributor){
		return super.update("updateByPrimaryKeySelective", distributor); 
	}
//
	public Integer queryDistributorCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	//
	public List<Distributor> queryDistributorList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	
	
	public int delete(Integer distributorId){
		return super.delete ("deleteByPrimaryKey", distributorId);
	}
	
	public int insert(Distributor distributor){
		 super.insert ("insertSelective", distributor);
		 return distributor.getDistributorId();
		 
	}

	
	public boolean saveAll(List<Distributor> list){
		boolean re = true;
		for (Distributor user : list) {
			int i = super.insert("insertSelective", user);
			if(i == 0){
				re = false;
				break;
			}
		}
		return re;
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<Distributor> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public Distributor getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	
	public List<ClientPosition> findByPositionName(){
		List<ClientPosition> qlp = super.queryForListPosition ("findLisetByposition");
		if (qlp != null && qlp.size ()>0) {
			return qlp;
		} else {
			return null;
		}
	}

	/**异步加载树的子结点
	 * @author Nany
	 * 2014年12月17日下午5:28:51
	 * @param paramMap
	 * @return
	 */
	public List<TreeNodeVO> getTreeNodesByPId(Map<String, Object> paramMap) {
		return super.getList("getTreeNodesByPId", paramMap);
	}

}
