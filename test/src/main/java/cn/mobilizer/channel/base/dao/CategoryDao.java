/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class CategoryDao extends MyBatisDao {
	
	public CategoryDao() {
		super("CATEGORY");
	}
	

	public Integer queryCategoryCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<Category> queryCategoryList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}


	/**品类管理--新增品类--保存
	 * @author Nany
	 * 2014年12月4日下午4:36:40
	 * @param category
	 */
	public void addCategory(Category category) {
		 super.insert("insertSelective", category);
		
	}


	/**品类管理--编辑
	 * @author Nany
	 * 2014年12月4日下午5:03:03
	 * @param categoryId
	 */
	public Category getCategory(Integer categoryId) {
		Category category = super.get("selectByPrimaryKey",categoryId);
		return category;
		
	}


	/**品类管理--编辑品类--保存
	 * @author Nany
	 * 2014年12月4日下午6:30:08
	 * @param category
	 */
	public void updateCategory(Category category) {
		super.update("updateByPrimaryKeySelective", category);
		
	}


	/**品类管理--删除
	 * @author Nany
	 * 2014年12月4日下午6:55:22
	 * @param categoryId
	 */
	public void delteCategory(Integer categoryId) {
		Category category = super.get("selectByPrimaryKey",categoryId);
		byte isDetele = 1;
		category.setIsDelete(isDetele);
		super.update("updateByPrimaryKeySelective", category);
	}


	/**品类管理——编辑，查询出该客户下的品类
	 * @author Nany
	 * 2014年12月8日上午11:35:02
	 * @param categoryId
	 * @param clientId
	 * @return
	 */
	public List<Category> getCategoryListWithOutId(Map<String, Object> params) {
		
		return super.queryForList("findListWithOutId", params);
	}


	/**异步加载树的子结点
	 * @author Nany
	 * 2014年12月10日下午3:33:59
	 * @param paramMap
	 * @return
	 */
	public List<TreeNodeVO> getTreeNodes(Map<String, Object> paramMap) {
	   return super.getList("getTreeNodesByPId", paramMap);
	
	}
	
	public List<Category>  queryParentCategory(Map<String, Object> paramMap){
		return super.getList("queryParentCategory", paramMap);
	} 
	
	public Category getCategoryByClientIdAndId(Map<String, Object> params){
		return super.get("getCategoryByClientIdAndId", params);
	}
	
	public Map<String, Category> getCategoryMap(Map<String, Object> params){
		return super.queryForMap("getCategoryMap", params, "categoryName");
	}
	
}
