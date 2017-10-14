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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Knowledge;
import cn.mobilizer.channel.base.po.Knowledgeattachment;
import cn.mobilizer.channel.base.service.KnowledgeService;
import cn.mobilizer.channel.base.service.KnowledgeattachmentService;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;

@Controller
@RequestMapping(value = "/knowledge")
public class KnowledgeController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private KnowledgeattachmentService knowledgeattachmentService;

	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String title, String startDate, String endDate, HttpServletRequest req) throws BusinessException {
		int clientId = getClientId();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// startDate = sdf.format(DateTimeUtils.getFirstDayOfCurrentMonth());
		// endDate=sdf.format(DateTimeUtils.getLastDayOfCurrentMonth());

		/*
		 * if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
		 * Calendar cal =Calendar.getInstance(); cal.set(Calendar.DAY_OF_WEEK,
		 * Calendar.MONDAY); //获取本周一的日期 startDate =
		 * DateTimeUtils.formatTime(cal.getTime(), DateTimeUtils.yyyyMMdd);
		 * endDate =
		 * DateTimeUtils.formatTime(DateTimeUtils.addDays(DateTimeUtils
		 * .getFirstDayOfCurrentWeek() , 7), DateTimeUtils.yyyyMMdd); }
		 */
		Map<String, Object> paramster = new HashMap<String, Object>();
		paramster.put("clientId", clientId);
		paramster.put("isDelete", Constants.IS_DELETE_FALSE);
		paramster.put("title", title);
		paramster.put("startDate", startDate);
		paramster.put("endDate", endDate);
		int count = knowledgeService.queryKnowledgeCount(paramster);

		int pagenum = page == null ? 1 : page;
		Page<Knowledge> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		paramster.put("_start", pageParam.getStartRows());
		paramster.put("_size", pageParam.getPageSize());
		// paramster.put("_orderby","knowledgeId");
		// paramster.put("_order","DESC");
		List<Knowledge> list = knowledgeService.queryKnowledgeList(paramster);
		Map<Integer, String> map = new HashMap<Integer, String>();
		pageParam.setItems(list);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("title", title);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "/base/knowledgeList";
	}

	@RequestMapping(value = "/showAddKnowledge")
	public String showAddKnowledge() {
		return "/base/showAddKnowledge";
	}

	/*
	 * @InitBinder public void initBinder(WebDataBinder binder) {
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * dateFormat.setLenient(false); binder.registerCustomEditor(Date.class,new
	 * CustomDateEditor(dateFormat,true)); //true:允许输入空值，false:不能为空值 }
	 */

	/**
	 * 学习资料管理和附件
	 * 
	 * @param knowledge
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/insertKnowledge", produces = "application/json")
	public Object insertKnowledge(Knowledge knowledge) throws BusinessException {
		// 资料新增信息设置
		if (log.isDebugEnabled()) {
			log.debug("start method<addKnowledge>");
		}
		int clientId = getClientId();
		knowledge.setClientId(clientId);
		// 资料附件
		String fileName2 = null;
		String fileSize = null;
		String[] fileNames;
		String fileName = knowledge.getFileName();
		if (fileName != null && !"".equals(fileName) && fileName.indexOf(",") != -1) {
			fileNames = fileName.split(",");
		} else {
			fileNames = new String[]{fileName};
		}
		for (int i = 0; i < fileNames.length; i++) {
			String fileName1 = fileNames[i];
			String[] fileSizes = fileName1.split("@");
			for (int j = 0; j < fileSizes.length - 1; j++) {
				fileName2 = fileSizes[j];
				fileSize = fileSizes[j + 1];
			}
		}
		Knowledgeattachment knowledgeattachment = new Knowledgeattachment();
		knowledgeattachment.setClientId(clientId);
		knowledgeattachment.setFileName(fileName2);
		knowledgeattachment.setFileSize(fileSize);
		knowledgeattachment.setSequence(knowledge.getOrderby());

		knowledgeService.insertKnowledge(knowledge, knowledgeattachment);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * 添加学习资料
	 * 
	 * @param knowledge
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/addKnowledge", produces = "application/json")
	public Object addKnowledge(Knowledge knowledge) throws BusinessException {
		if (log.isDebugEnabled()) {
			log.debug("start method<addKnowledge>");
		}
		int clientId = getClientId();
		knowledge.setClientId(clientId);
		knowledgeService.addKnowledge(knowledge);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadKnowledge", produces = "application/json")
	public Object uploadKnowledge(String avatar, Model model) throws BusinessException {
		model.addAttribute("avatar", "avatar");
		return ResultMessage.SUCCESS;
	}
	/**
	 * 编辑学习资料
	 * 
	 * @param knowledge
	 * @return
	 * @throws BusinessException
	 */

	@RequestMapping(value = "/showEditKnowledge/{knowledgeId}")
	public String showEditKnowledge(@PathVariable("knowledgeId") Integer knowledgeId, Model model) throws BusinessException {
		Knowledge knowledge = knowledgeService.queryKnowledgeById(knowledgeId);
		model.addAttribute("knowledge", knowledge);
		return "/base/showEditKnowledge";
	}

	/**
	 * 编辑学习资料和附件
	 * 
	 * @param knowledge
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateKnowledge", produces = "application/json")
	@ResponseBody
	public Object updateKnowledge(Knowledge knowledge) throws BusinessException {
		int clientId = getClientId();
		knowledge.setClientId(clientId);
		if (log.isDebugEnabled()) {
			log.debug("edit method<editKnowledge>");
		}
		// 资料附件
		String fileName2 = null;
		String fileSize = null;
		String[] fileNames;
		String fileName = knowledge.getFileName();
		if (fileName != null && !"".equals(fileName) && fileName.indexOf(",") != -1) {
			fileNames = fileName.split(",");
		} else {
			fileNames = new String[]{fileName};
		}
		for (int i = 0; i < fileNames.length; i++) {
			String fileName1 = fileNames[i];
			String[] fileSizes = fileName1.split("@");
			for (int j = 0; j < fileSizes.length - 1; j++) {
				fileName2 = fileSizes[j];
				fileSize = fileSizes[j + 1];
			}
		}
		Knowledgeattachment knowledgeattachment = new Knowledgeattachment();
		knowledgeattachment.setClientId(clientId);
		knowledgeattachment.setFileName(fileName2);
		knowledgeattachment.setFileSize(fileSize);
		knowledgeattachment.setSequence(knowledge.getOrderby());
		knowledgeService.updateKnowledge(knowledge, knowledgeattachment);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}

	@RequestMapping(value = "/editKnowledge", produces = "application/json")
	@ResponseBody
	public Object editKnowledge(Knowledge knowledge) throws BusinessException {
		int clientId = getClientId();
		knowledge.setClientId(clientId);
		if (log.isDebugEnabled()) {
			log.debug("edit method<editKnowledge>");
		}
		knowledgeService.updateKnowledge(knowledge);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}

	/**
	 * 删除学习资料
	 * 
	 * @param knowledgeId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showDeleteKnowledge/{knowledgeId}")
	@ResponseBody
	public Object showDeleteKnowledge(@PathVariable("knowledgeId") Integer knowledgeId) throws BusinessException {
		if (log.isDebugEnabled()) {
			log.debug("delete method<deleteKnowledge>");
		}
		knowledgeService.delKnowledge(knowledgeId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	/**
	 * 根据Id查找学习资料
	 * 
	 * @param knowledgeId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showCheckKnowledge/{knowledgeId}")
	public String showCheckKnowledge(@PathVariable("knowledgeId") Integer knowledgeId, Model model) throws BusinessException {
		Knowledge knowledge = knowledgeService.queryKnowledgeById(knowledgeId);
		model.addAttribute("knowledge", knowledge);
		return "/base/showCheckKnowledge";
	}

	@RequestMapping(value = "/showKnowledgeAttachment/{knowledgeId}")
	public Object showKnowledgeAttachment(@PathVariable("knowledgeId") Integer knowledgeId, Model model) throws BusinessException {
		Knowledge knowledge = knowledgeService.queryKnowledgeById(knowledgeId);
		model.addAttribute("knowledge", knowledge);
		return "/base/showEditKnowledgeAttachment";
	}

}
