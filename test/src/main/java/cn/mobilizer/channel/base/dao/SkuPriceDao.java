/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.SkuPrice;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class SkuPriceDao extends MyBatisDao {
	
	public SkuPriceDao() {
		super("SKU_PRICE");
	}
	
	public List<SkuPrice> querySkuPriceList (Map<String, Object> params) {
		return super.queryForList("selectByParams", params);
	}
	
	public List<TreeNodeVO> getTreeNodesByPId(Map<String, Object> paramMap) {
		return super.getList("getTreeNodesByPId", paramMap);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<SkuPrice> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public SkuPrice getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	
	
	public Integer querySkuPriceCount(Map<String, Object> parames){
		return super.get("queryTotalCount", parames);
	}
	
	public int insert(SkuPrice skuPrice){
		return super.insert ("insertSelective", skuPrice);
	}
	
	public List<SkuPrice> findSkuPriceList(Object Params){
		return super.queryForList("selectByParams",Params);
	}
	
	public int delete(Integer id){
		return super.delete("deleteByPrimaryKey", id);
	}
	
	public int update(SkuPrice skuPrice){
		return super.update("updateByPrimaryKeySelective", skuPrice);
	}
	
	public int updateIsdelete(Map<String, Object> parames){
		return super.update("updateIsdelete",parames);
	}
	
	public SkuPrice  selectSkuPrice(Map<String, Object> parames){
		
		return super.get("selectOnById", parames);
	}
	
	public SkuPrice findSkuPriceId(Map<String, Object> parames){
		return super.get("findSkuPriceId", parames);
	}
	
	public List<SkuPriceVO> findSkuAndPrices(Map<String, Object> parames){
		return super.queryForList("findSkuAndPrices", parames);
	}
	
	public SkuPrice findSkuPriceByParmarter(Map<String, Object> parames){
		return super.get("findSkuPriceByParmarter", parames);
	}
	
	public int updatefindInSet(Map<String, Object> parames){
		return super.insert("updatefininset", parames);
	}
	
}
