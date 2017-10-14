package cn.mobilizer.channel.posm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.posm.po.PromotionMaterial;

@Repository
public class PromotionMaterialXDao extends MyBatisDao {

	public PromotionMaterialXDao(){
		super("PROMOTIONMATERIALX");
	}
	/**
	 * 增加
	 * @param promotionMaterial
	 */
	public void addPromotionMaterial(PromotionMaterial promotionMaterial)
	{
		super.insert("insertSelective", promotionMaterial);
	}
	
    /**
     * 修改
     * @param promotionMaterial
     */
	public void updatePromotionMaterial(PromotionMaterial promotionMaterial)
	{
		super.update("updateByPrimaryKeySelective", promotionMaterial);
	}
	
	
	
	/**
	 * 根据id进行查找
	 * @param materialId
	 * @return
	 */
	public PromotionMaterial queryPromotionMaterialById(Integer materialId)
	{
		return super.get("selectByPrimaryKey", materialId);
	}
	/**
	 * 
	 * @param param
	 * @return 总条数
	 */
	public Integer queryPromotionMaterialByCount(Map<String,Object> param)
	{
		return super.get("queryTotalCount",param);
	}
	
	public List<PromotionMaterial> queryPromotionMaterialList(Map<String,Object> param)
	{
		return super.queryForList("findListByParams",param);
	}
	
	


	/**
	 * 
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 * <pre>
	 * 		方法执行说明
	 * 			 select * from promotion_material pm1
	 * 		建议
	 * 			根据需求重新写
	 * </pre>
	 */
	@Deprecated
	public List<PromotionMaterial> queryPromotionMaterialByNames()
	{
		return super.queryForList("findmaterialNameByParams");
	}
	
	/**
	 * 
	 * @param clientId
	 * @param name
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 * <pre>
	 * 		 方法执行SQL 说明
	 * 			select 
		  		<include refid="Base_Column_List" />
		  		from 
		  		promotion_material as pm
		  		where 1 =1 AND pm.is_delete = 0 
		  		<if test="clientId == null" >
		       		and pm.client_id = #{clientId}
			    </if>
			    <if test="name == null" >
			    	and pm.material_name = #{name}
			    </if>
	 * </pre>
	 */
	public List<PromotionMaterial> queryPromotionMaterialsByClientIdAndLikeName(Integer clientId,String name){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("clientId", clientId);
		map.put("name", name);
		List<PromotionMaterial> list = getList("queryPromotionMaterialsByClientIdAndLikeName", map);
		if(null != list && list.size() > 0){
			return list;
		}else {
			return null;
		}
	}
	
	
	public Map<String, PromotionMaterial> queryPromotionMaterials(Map<String, Object> map,String key){
		return super.queryForMap("queryPromotionMaterials",map,key);
	}
	
	/**
	 * 批量导入促销物料
	 * @param promotionMaterial
	 */
	public void batchSavePromotionMaterial(List<PromotionMaterial> promotionMaterial)
	{
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("promotionMaterial", promotionMaterial);
		super.insert("batchSavePromotionMaterial",params);
	}
	
	
	public List<PromotionMaterial> batchOutPromotionMaterial(Map<String,Object> param)
	{
		return super.queryForList("findListByParams1",param);
	}
	
	
}
