/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class SkuDao extends MyBatisDao {
	
	public SkuDao() {
		super("SKU");
	}
	

	public Integer querySkuCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<Sku> querySkuList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}
	
	public int insert(Sku sku){
		return super.insert ("insertSelective", sku);
	}


	/**产品管理--新增--保存
	 * @author Nany
	 * 2014年12月10日下午4:57:11
	 * @param sku
	 */
	public int addSku(Sku sku) {
		super.insert("insertSelective", sku);
		return sku.getSkuId();
		
	}


	/**产品管理--编辑，查询数据
	 * @author Nany
	 * 2014年12月12日下午4:35:49
	 * @param parames
	 * @return 
	 */
	public Sku getSku(Map<String, Object> parames) {
		return 	super.get("findListByParams", parames);	
	}


	/**产品管理(编辑/删除)
	 * @author Nany
	 * 2014年12月12日下午6:36:40
	 * @param sku
	 */
	public void updateSku(Sku sku) {
		super.update("updateByPrimaryKeySelective", sku);
		int skuId = sku.getSkuId();
	}
	
	public List<Sku> getSkuByBarcode(Map<String, Object> parames) {
		return super.queryForList("getSkuByBarcode", parames);
	}
	
	public List<Sku> getSkuByName(Map<String, Object> parames) {
		return super.queryForList("getSkuByName", parames);
	}	
	
	public String findAllSkuIds(Map<String, Object> parames){
		return 	super.get("findAllSkuIds", parames);	
	}
	
	public Sku getSkuByIdAndClientId(Map<String,Object> params){
		return super.get("getSkuByIdAndClientId", params);
	}
	
	public List<SkuPriceVO> findSkuIdAndPriceByParamter(Map<String,Object> params){
		return super.queryForList("findSkuIdAndPriceByParamter", params);
	}
	
	public List<Sku> selectBySelectTwo(Map<String,Object> params){
		return super.queryForList("selectBySelectTwo", params);
	}
 	
	public Map<String,Sku> getSkuNoMap(Map<String,Object> params){
		return super.queryForMap("getSkuNoMap", params, "skuNo");
	}
	
	public int insertList(List<Sku> skuList){
		return super.insert("insertList", skuList);
	}
	
	public int updatePrice(Map<String,Object> parmeter){
		return super.update("updatePrice", parmeter);
	}
	
}
