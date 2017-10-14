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
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;


/**品牌管理
 * @author Nany
 * 2014年12月3日下午2:03:39
 */
@Controller
@RequestMapping(value = "/brand")
public class BrandController extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private BrandService brandService;
	
	/**品牌管理--查询列表
	*@author Nany
	   2014年12月3日下午2:07:10
	 */
	@RequestMapping(value = "query")
	public String query(Model model, Integer page, String searchBrandName, Byte isOwner,String brandNo,Integer parentId,HttpServletRequest req) {
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("brandName",searchBrandName);
		parameters.put("brandNo", brandNo);
		parameters.put("isOwner", isOwner);
		parameters.put("parentId", parentId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = brandService.queryBrandCount(parameters);
			
		//分页
		int pagenum = page == null ? 1 : page;
		Page<Brand> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		
		List<Brand> brandList = brandService.queryBrandList(parameters);
		for (Brand brand : brandList) {
			if (brand.getParentId() != null) {
				Brand parentBrand = brandService.getBrand(brand.getParentId());
				if(parentBrand != null){
					brand.setParentBrandName(parentBrand.getBrandName());
				}
			}else {
				brand.setParentBrandName("/");
			}
		}
		model.addAttribute("pageParam",pageParam );
		model.addAttribute("searchBrandName", searchBrandName);
		model.addAttribute("brandNo", brandNo);
		model.addAttribute("isOwner", isOwner);
		model.addAttribute("parentId", parentId);
		model.addAttribute("page", pageParam.getPage().toString());	
		model.addAttribute("brandList",brandList);
		
		return "/base/brandList";
	}
	@RequestMapping(value = "showImportDialog")
	public String showImportDialog(){
		return "/base/importBrand";
	}
	
	/**品牌管理--新增
	*@author Nany
	   2014年12月3日下午5:02:15
	 */
	
	@RequestMapping(value = "showAddBrand")
	public String showAddBrand(Model model) {
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "/base/showAddBrand";

	}
	
	
	
	/**品牌管理--新增品牌--保存
	 * @author Nany
	 * 2014年12月3日下午6:49:27
	 * @return
	 */
	@RequestMapping(value ="addBrand")
	@ResponseBody
	public Object addBrand(Brand brand){
		int clientId = getClientId();
		brand.setClientId(clientId);
		brandService.addBrand(brand);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**品牌管理--编辑品牌
	 * @author Nany
	 * 2014年12月4日上午9:34:23
	 * @return
	 */
	@RequestMapping(value="/showEditBrand/{brandId}", produces="application/json")
	public String editBrand(@PathVariable("brandId")Integer brandId ,Model model){
		int clientId = getClientId();
		Brand brand = brandService.getBrand(brandId);
		model.addAttribute("brand", brand);
		model.addAttribute("clientId", clientId);
 		return "/base/showEditBrand";
	}
	
	/**品牌管理--编辑品牌--保存
	 * @author Nany
	 * 2014年12月4日上午11:54:06
	 * @return
	 */
	@RequestMapping(value="/updateBrand",produces="application/json")
	@ResponseBody
	public Object updateBrand(Brand brand)throws BusinessException{
		int clientId = getClientId();
		brand.setClientId(clientId);
		brandService.updateBrand(brand);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**品牌管理--删除(非物理删除，仅仅是改变状态码)
	 * @author Nany
	 * 2014年12月4日下午2:29:08
	 * @return
	 */
	@RequestMapping(value="/deleteBrand/{brandId}",produces="application/json")
	@ResponseBody
	public Object deleteBrand(@PathVariable("brandId")Integer brandId){
		
		brandService.deleteBrand(brandId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	
	/**异步加载树的子结点
	 * @author Nany
	 * 2014年12月10日下午1:20:48
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getTreeNode(@RequestParam(value = "id",defaultValue="-1") Integer id) {
		int clientId = getClientId();
		List<TreeNodeVO> list = brandService.getTreeNodes(clientId,id);
		return list;
	}
	
	@RequestMapping(value="/showBrandDetail/{brandId}")
	public String showBrandDetail(@PathVariable("brandId")Integer brandId,Model model) {
		Brand  brand = brandService.getBrand(brandId);
		//上级品牌
		Brand parentBrand = null;
		if (brand.getParentId() != null) {
			parentBrand = brandService.getBrand(brand.getParentId());
		}
		
		model.addAttribute("brand", brand);
		model.addAttribute("parentBrand", parentBrand);
		return "/base/showBrandDetail";
	}
	/**
	 * 加载上级品牌
	 * @return
	 */
	@RequestMapping(value="/loadParentBrand")
	@ResponseBody
	public Object loadParentBrand(Integer brandId){
		int clientId = getClientId();
		Map<String,Object> parames = new HashMap<String, Object>();
		Integer grade = 1;
		parames.put("clientId", clientId);
		parames.put("isDelete",  Constants.IS_DELETE_FALSE);
		parames.put("grade",  grade);
		parames.put("brandId",  brandId);
		List<Brand> brandList = brandService.getBrandList(parames);
		return brandList;
	}
}
