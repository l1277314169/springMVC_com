/**
 * 
 */
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

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.OptionsConstants;
import cn.mobilizer.channel.base.service.ChainService;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;

/**
 * @author honger.liu
 * Chain管理Controller
 * 2014-11-20 17:54:20 
 */ 
@Controller
@RequestMapping(value = "/chain")
public class ChainController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1997444411661968749L;
	@Autowired
	private ChainService chainService;
	@Autowired
	private ClientService clientService;

	/**
	 * 异步加载树的子结点
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object listNodes(@RequestParam(value = "id",defaultValue="-1") Integer id,Integer channelId) {
		int clientId = getClientId ();
		List<TreeNodeVO> list = chainService.getTreeNodes(clientId,id,channelId);
		return list;
	}
	
	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(){
		return "base/importClientUser";
	}
	
	 /**
	  * 连锁管理-查询列表
	  * @param model
	  * @param page
	  * @param searchUserName
	  * @param searchUserNo
	  * @param searchClientStructureId
	  * @param req
	  * @return
	  * @throws BusinessException
	  */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String searchName, String clientStructureId, Integer parentId, HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("searchName", searchName);
		parameters.put("parentId", parentId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = chainService.queryChainCount(parameters);
	
		int pagenum = page == null ? 1 : page;
		Page<Chain> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
//		parameters.put("_orderby", "LAST_UPDATE_TIME");
//		parameters.put("_order", "DESC");
		List<Chain> list = chainService.queryChainList(parameters);
		pageParam.setItems(list);
		
		/**获取部门**/
//		TODO-获取部门tree
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("searchName", searchName);
		model.addAttribute("parentId", parentId);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/chainList";
	}
	
	
	/**
	 * 连锁管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddChain")
	public String showAddChain(Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			/**
			 * 获取sysPosition的options数据，暂时每次新增都从数据库中查一下，以后考虑放入缓存中
			 * optionName = sys_positionType 
			 */
//			List<Chain> chain = chainService.findChainName();
//			model.addAttribute("chain" , chain);
			model.addAttribute("clientId" , clientId);
			
			return "base/showAddChain";
		}
		return "/dialog/error";	
	}
	/**
	 * 连锁管理-编辑页面
	 * @param chainId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditChain/{chainId}")
	public String editChain(@PathVariable("chainId")Integer chainId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			Map<String, Object> parameters = new HashMap<String, Object>();
			Chain chain = chainService.findByPrimaryKey(chainId);
			parameters.put("clientId", clientId);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			if(chain.getGrade()==null){
				parameters.put("grade",null);
			}else{
				parameters.put("grade", chain.getGrade()-1);
			}
			List<Chain> chainList = chainService.findChainName(parameters);
			model.addAttribute("chain" , chain);
			model.addAttribute("chainList" , chainList);
			model.addAttribute("clientId" , clientId);
			return "base/showEditChain";
		}
		return "/dialog/error";	
	}
	/**
	 * 查看
	 * @param chainId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showCheckChain/{chainId}")
	public String checkChain(@PathVariable("chainId")Integer chainId, Model model){
		Chain chain = chainService.findChainRelateChannel(chainId);
		if(chain != null){
			if(chain.getParentId() != null){
				Chain parent = chainService.findByPrimaryKey(chain.getParentId());
				model.addAttribute("parent" , parent);
			}
		}
		model.addAttribute("chain" , chain);
		return "base/showCheckChain";
	}
	
	/**
	 * 新增
	 * @param chain
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addChain", produces="application/json")
	@ResponseBody
	public Object addChain(Chain chain) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<addChain>");
		}
		chainService.addChain(chain);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	
	
	/**
	 * 修改
	 * @param chain
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateChain", produces="application/json")
	@ResponseBody
	public Object updateChain(Chain chain) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<updateChain>");
		}
		chainService.update(chain);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 删除
	 * @param chainId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteChain/{chainId}", produces="application/json")
	@ResponseBody	
	public Object deleteChain(@PathVariable("chainId")Integer chainId) throws BusinessException  {
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteChain>");
		}
//		clientPositionService.deleteClinetPostion(clientPositionId);
		chainService.deleteChain(chainId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	/**
	 * 上级连锁
	 * @param gardeId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findForListChain",produces="application/json")
	@ResponseBody	
	public List<Chain> addGrade(Integer gradeId,Integer chainId){
		if (log.isDebugEnabled()) {
			log.debug("start method<addgrade>");
		}
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(gradeId != null){
			parameters.put("grade", gradeId-1);
		}
		parameters.put("chainId", chainId);
		parameters.put("clientId", clientId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		List<Chain> parent = chainService.findChainName(parameters);
		return parent;
	}
	
}
