/**
 * 
 */
package cn.mobilizer.channel.promotion.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.promotion.po.PromotionActivity;

@Repository
public class PromotionActivityDao extends MyBatisDao {
	
	public PromotionActivityDao() {
		super("PROMOTION_ACTIVITY");
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public PromotionActivity findByLoginName(Map<String, Object> params){
		List<PromotionActivity> ls = super.queryForList ("selectByParams", params);
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
	public PromotionActivity findByLoginNameAndLoginPWD(Map<String, Object> params){
		List<PromotionActivity> ls = super.queryForList ("selectByParams", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	public PromotionActivity findByLoginNameAndClientName(Map<String, Object> params){
		List<PromotionActivity> ls = super.queryForList ("selectByLoginNameAndClientName", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
	}
	
	
	public PromotionActivity findByPrimaryKey(Integer clientUserId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientUserId", clientUserId);
		return super.get("selectByPrimaryKey",params);
	}
	
	public int update(PromotionActivity promotionActivity){
		return super.update("updateByPrimaryKeySelective", promotionActivity); 
	}

	public Integer queryPromotionActivityCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<PromotionActivity> queryPromotionActivityList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	
	
	public int delete(Integer clientUserId){
		return super.delete ("deleteByPrimaryKey", clientUserId);
	}
	
	public int insert(PromotionActivity promotionActivity){
		return super.insert ("insertSelectiveclient", promotionActivity);
	}

	
	public boolean saveAll(List<PromotionActivity> list){
		boolean re = true;
		for (PromotionActivity user : list) {
			int i = super.insert("insertSelective", user);
			if(i == 0){
				re = false;
				break;
			}
		}
		return re;
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<PromotionActivity> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public PromotionActivity getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	
	public List<PromotionActivity> findByPositionName(){
		List<PromotionActivity> qlp = super.queryForListPosition ("findLisetByposition");
		if (qlp != null && qlp.size ()>0) {
			return qlp;
		} else {
			return null;
		}
	}
	



}
