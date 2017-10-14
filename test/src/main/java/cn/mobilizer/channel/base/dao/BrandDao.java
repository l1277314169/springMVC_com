/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class BrandDao extends MyBatisDao {
	
	public BrandDao() {
		super("BRAND");
	}
	

	public Integer queryBrandCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<Brand> queryBrandList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	public void addBrand(Brand brand) {
		
		super.insert("insertSelective", brand);
	}


	public Brand getBrand(Integer brandId) {
		
		return super.get("selectByPrimaryKey", brandId);
	}

	public void updateBrand(Brand brand) {
		super.update("updateByPrimaryKeySelective", brand);
		
	}

	public void deleteBrand(Integer brandId) {
		Brand brand = super.get("selectByPrimaryKey", brandId);
		byte isDelete = 1;
		brand.setIsDelete(isDelete);
		super.update("updateByPrimaryKeySelective", brand);
	}
	
	public List<Brand> getBrandListWithOutId (Map<String, Object> params) {
		return super.queryForList("findListWithOutId", params);
	}

	public List<TreeNodeVO> getTreeNodesByPId(Map<String, Object> paramMap) {
		return super.getList("getTreeNodesByPId", paramMap);
		
	}


	public List<Brand> getBrandList(Map<String, Object> parames) {
		return super.getList("findListWithOutId", parames);
		
	}
	
	public Brand getBrandByIdAndClientId(Map<String,Object> params){
		return super.get("getBrandByIdAndClientId", params);
	}
	
	public List<Brand> selectBrandListBycustomerId(Map<String, Object> params){
		return super.getList("selectBrandListBycustomerId",params);
	}
}
