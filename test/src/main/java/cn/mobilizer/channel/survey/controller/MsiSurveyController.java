/**
 * 
 */
package cn.mobilizer.channel.survey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.MsiSurvey;
import cn.mobilizer.channel.survey.service.MsiSurveyService;


/**
 * 暗访任务Controller
 * @author yeeda.tian 2015-6-10
 */
@Controller
@RequestMapping(value = "/msiSurvey")
public class MsiSurveyController extends BaseActionSupport {

	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2439604741212061572L;

	@Autowired
	private MsiSurveyService msiSurveyService;
	
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, HttpServletRequest req) throws BusinessException{
		String reFtl = "/survey/msiSurveyList";
		
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		int count = msiSurveyService.queryMsiSurveyCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<MsiSurvey> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<MsiSurvey> list = msiSurveyService.queryMsiSurveyList(parameters);
		pageParam.setItems(list);
		
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("count", count);
		return reFtl;
	}
}
