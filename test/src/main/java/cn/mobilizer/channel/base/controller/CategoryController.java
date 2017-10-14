package cn.mobilizer.channel.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.util.Constants;

/**品类管理
 * @author Nany
 * 2014年12月4日下午3:18:37
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private CategoryService categoryService;
	/**品类管理，查询列表
	 * @author Nany
	 * 2014年12月4日下午3:21:54
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String searchCategoryName,String searchCategoryNo, HttpServletRequest req) {
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("categoryName", searchCategoryName);
		parameters.put("categoryNo", searchCategoryNo);
		parameters.put("clientId", clientId);
		int count = categoryService.queryCategoryCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<Brand> pageParam = Page.page(count, 12, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		
		List<Category> categoryList = categoryService.queryCategoryList(parameters);
		
		model.addAttribute("pageParam",pageParam );
		model.addAttribute("searchCategoryName", searchCategoryName);
		model.addAttribute("searchCategoryNo", searchCategoryNo);
		model.addAttribute("page", pageParam.getPage().toString());	
		model.addAttribute("categoryList", categoryList);
		
		return"/base/categoryList";
	}
	/**导入品类
	 * @author Nany
	 * 2015年1月12日上午10:16:05
	 * @return
	 */
	@RequestMapping(value="/showImportDialog")
	public String showImportDialog() {
		return "/base/importCategory";
	}
	
	/**品类管理--新增品类
	 * @author Nany
	 * 2014年12月4日下午4:00:38
	 * @return
	 */
	@RequestMapping(value="/showAddCategory")
	public String showAddCategory(Model model) {
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "/base/showAddCategory";
	}
	 
	/**品类管理--新增品类--保存
	 * @author Nany
	 * 2014年12月4日下午4:18:17
	 * @return
	 */
	@RequestMapping(value="/addCategory")
	@ResponseBody
	public Object addCategory(Category category){
		int clientId = getClientId();
		category.setClientId(clientId);
		categoryService.addCategory(category);
		
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**品类管理--编辑品类
	 * @author Nany
	 * 2014年12月4日下午4:56:55
	 * @return
	 */
	@RequestMapping(value="/showEditCategory/{categoryId}",produces="application/json")
	public String showEditCategory(@PathVariable("categoryId")Integer categoryId ,Model model){
		int clientId = getClientId();
		Map<String, Object> parame = new HashMap<String, Object>();
		parame.put("clientId", clientId);
		Category category = categoryService.getCategory(categoryId);
		model.addAttribute("category", category);
		model.addAttribute("clientId", clientId);
		return "/base/showEditCategory";
	}
	
	/**品类管理--编辑品类--保存
	 * @author Nany
	 * 2014年12月4日下午6:24:46
	 * @return
	 */
	@RequestMapping(value="/updateCategory",produces="application/json")
	@ResponseBody
	public Object updateCategory(Category category){
		int clientId = getClientId();
		category.setClientId(clientId);
		categoryService.updateCategory(category);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**品类管理--删除(非物理删除)
	 * @author Nany
	 * 2014年12月4日下午6:52:11
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value="/deleteCategory/{categoryId}",produces="application/json")
	@ResponseBody
	public Object deleteCategory(@PathVariable("categoryId")Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	
	/**异步加载树的子结点
	 * @author Nany
	 * 2014年12月10日下午3:30:38
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getTreeNode(@RequestParam(value = "id",defaultValue="-1") Integer id) {
		int clientId = getClientId();
		List<TreeNodeVO> list = categoryService.getTreeNodes(clientId,id);
		return list;
	}
	
	/**查看品类详细信息
	 * @author Nany
	 * 2015年2月2日下午5:55:31
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showCategoryDetail/{categoryId}")
	public String showCategoryDetail(@PathVariable("categoryId")Integer categoryId,Model model) {
		Category category = categoryService.getCategory(categoryId);
		//上级品类
		Category parentCategory = null;
		if(category.getParentId() != null){
			parentCategory = categoryService.getCategory(category.getParentId());
		}
		
		model.addAttribute("category", category);
		model.addAttribute("parentCategory", parentCategory);
		return "/base/showCategoryDetail";
	}
	/**
	 * 加载上级品类
	 * @return
	 */
	@RequestMapping(value="/loadCategory")
	@ResponseBody
	public Object loadCategory(Integer categoryId){
		int clientId = getClientId();
		List<Category> categoryList = categoryService.getCategoryListWithOutId(categoryId,clientId);
		return categoryList;
	}
}
