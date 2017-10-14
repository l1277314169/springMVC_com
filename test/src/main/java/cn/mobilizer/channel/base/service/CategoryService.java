package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface CategoryService {

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryCategoryCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Category> queryCategoryList(Map<String, Object> searchParams) throws BusinessException;

	/**品类管理--新增品类--保存
	 * @author Nany
	 * 2014年12月4日下午4:35:26
	 * @param category
	 */
	public void addCategory(Category category);

	/**品类管理--编辑
	 * @author Nany
	 * 2014年12月4日下午5:01:54
	 * @param categoryId
	 * @return
	 */
	public Category getCategory(Integer categoryId);

	/**品类管理--编辑品类--保存
	 * @author Nany
	 * 2014年12月4日下午6:29:05
	 * @param category
	 */
	public void updateCategory(Category category);

	/**品类管理--删除(非物理删除)
	 * @author Nany
	 * 2014年12月4日下午6:54:08
	 * @param categoryId
	 */
	public void deleteCategory(Integer categoryId);

	/**品类管理——编辑，查询出该客户下的品类
	 * @author Nany
	 * 2014年12月8日上午11:32:30
	 * @param categoryId
	 * @param clientId
	 * @return
	 */
	public List<Category> getCategoryListWithOutId(Integer categoryId,
			int clientId);

	/**异步加载树的子结点
	 * @author Nany
	 * 2014年12月10日下午3:30:57
	 * @param clientId
	 * @param id
	 * @return
	 */
	public List<TreeNodeVO> getTreeNodes(int clientId, Integer id);

	public Category getCategoryByClientIdAndId(Integer clientId,Integer categoryId);
	
	
	public Map<String, Category> getCategoryMap(Integer clientId) throws BusinessException;
}
