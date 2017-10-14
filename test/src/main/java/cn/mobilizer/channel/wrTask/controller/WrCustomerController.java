package cn.mobilizer.channel.wrTask.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.wrTask.po.WrCustomer;
import cn.mobilizer.channel.wrTask.po.WrCustomerBrandMapping;
import cn.mobilizer.channel.wrTask.service.WrCustomerBrandMappingService;
import cn.mobilizer.channel.wrTask.service.WrCustomerService;

@Controller
@RequestMapping(value ="/wrCustomer")
public class WrCustomerController extends BaseActionSupport {
	
	@Autowired
	private WrCustomerService wrCustomerService;
	@Autowired
	private WrCustomerBrandMappingService wrCustomerBrandMapping;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private WrCustomerBrandMappingService wrCustomerBrandMappingService;
	
	@RequestMapping(value="/query")
	public String query(Model model,Integer page,String customerName,HttpServletRequest req)throws BusinessException
	{
		int clientId = getClientId ();
		Map<String,Object> paramster=new HashMap<String,Object>();
		//paramster.put("clientId", clientId);
		paramster.put("customerName", customerName);
		paramster.put("isDelete",Constants.IS_DELETE_FALSE);
		int count=wrCustomerService.selectByParamCount(paramster);
		
		int pagenum= page ==null ?1:page;
		Page<WrCustomer> pageParam=Page.page(count,Page.DEFAULT_PAGE_SIZE,pagenum);
		pageParam.buildUrl(req);
		paramster.put("_start",pageParam.getStartRows());
		paramster.put("_size",pageParam.getPageSize());
		//paramster.put("_orderby","knowledgeId");
		//paramster.put("_order","DESC");
		List<WrCustomer> list=wrCustomerService.selectByParams(paramster);
		
		pageParam.setItems(list);
		model.addAttribute("pageParam",pageParam);
		model.addAttribute("page",pageParam.getPage().toString());
		model.addAttribute("customerName",customerName);
		return "/wrTask/WrCustomerList";
	}
	
	@RequestMapping(value="/showAddWrCustomer")
	public String showAddWrCustomer()
	{
		return "/wrTask/showAddWrCustomer";
	}
	/**
	 * 校验name的唯一值
	 * @param customerName
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value="/validateWrCustomer/{customerName}")
	public void validateWrCustomer(@PathVariable("customerName")String customerName,HttpServletResponse res)throws IOException
	{
		
		String flag="error";
		Map<String,Object> param=new HashMap<String,Object>();
		List<WrCustomer> list=wrCustomerService.selectByParams(param);
		List<String> nameList=new ArrayList<String>();	
			for (WrCustomer wrCustomer : list) {
				String name=wrCustomer.getCustomerName();
				nameList.add(name);
			}
			if(nameList.contains(customerName))
			{
				flag="success";
			}
		PrintWriter out=res.getWriter();
		out.print(flag);
		out.close();
	}
	/**
	 * 添加客户
	 * @param wrCustomer
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/addWrCustomer",produces="application/json")
	@ResponseBody
	public Object addWrCustomer(WrCustomer wrCustomer,Integer[] brandId)throws BusinessException
	{
		if(log.isDebugEnabled())
		{
			log.debug("start method<addWrCustomer>");
		}
		int clientId = getClientId();
		wrCustomer.setClientId(clientId);
		Integer customerId=wrCustomerService.insertSelective(wrCustomer);
		if(customerId !=null)
		{
		for(int i=0;i<brandId.length;i++)
		{
			int id=brandId[i];
			WrCustomerBrandMapping record=new WrCustomerBrandMapping();
			record.setBrandId(id);
			record.setClientId(clientId);
			record.setCustomerId(customerId);
			wrCustomerBrandMappingService.insertSelective(record);
			}
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	/**
	 * 删除客户信息
	 * @param customerId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/showDeleteWrCustomer/{customerId}",produces="application/json")
	@ResponseBody
	public Object showDeleteWrCustomer(@PathVariable("customerId")Integer customerId)throws BusinessException
	{
		if(log.isDebugEnabled())
		{
			log.debug("delete method<deleteWrCustomer>");
		}
		wrCustomerService.deleteByPrimaryKey(customerId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	/**
	 * 编辑客户信息
	 * @param customerId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/showEditWrCustomer/{customerId}",produces="application/json")
	public Object showEditWrCustomer(@PathVariable("customerId")Integer customerId,Model model)throws BusinessException
	{
		int clientId=getClientId();
		WrCustomer wrCustomer=wrCustomerService.selectByPrimaryKey(customerId,clientId);
		model.addAttribute("wrCustomer",wrCustomer);
		return "/wrTask/showEditWrCustomer";
		}
	
	@ResponseBody
	@RequestMapping(value="/editWrCustomer")
	public Object editWrCustomer(WrCustomer wrCustomer,String brandIds)throws BusinessException
	{
		int clientId = getClientId ();
		WrCustomer wrCustomer2=wrCustomerService.selectByPrimaryKey(wrCustomer.getCustomerId(),clientId);
		String oldBrandIds=wrCustomer2.getBrandIds();
		if(log.isDebugEnabled())
		{
			log.debug("edit method<editWrCustomer>");
		}
		wrCustomer.setClientId(clientId);
		int customerId=wrCustomerService.updateByPrimaryKeySelective(wrCustomer);
		wrCustomerBrandMapping.updateCustomerBrandMapping(brandIds,oldBrandIds, customerId, clientId);
		return ResultMessage.UPDATE_SUCCESS_RESULT;	
	}
	
	/**
	 * 查看客户
	 * @param customerId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/showCheckWrCustomer/{customerId}")
	public Object showCheckWrCustomer(@PathVariable("customerId")Integer customerId,Model model)throws BusinessException
	{
		int client=getClientId();
		WrCustomer wrCustomer=wrCustomerService.selectByPrimaryKey(customerId,client);
		model.addAttribute("wrCustomer",wrCustomer);
		return "/wrTask/showCheckWrCustomer";
	}
	
	/**
	 * 品牌的设置
	 * @param res
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/getwrCustomerAjax")
    public List<Brand> getwrCustomerAjax()throws BusinessException
    {
		int clientId=getClientId();
    	Map<String,Object> parames=new HashMap<String,Object>();
    	parames.put("clientId", clientId);
    	List<Brand> list=brandService.getBrandList(parames);
    	return list;
    }
}
