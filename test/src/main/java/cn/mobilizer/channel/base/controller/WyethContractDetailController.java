/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.base.po.WyethContract;
import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.base.service.WyethContractDetailService;
import cn.mobilizer.channel.base.service.WyethContractFeedbackService;
import cn.mobilizer.channel.base.service.WyethContractService;

@Controller
@RequestMapping(value = "/wyethContractDetail")
public class WyethContractDetailController {
	@Autowired
	private WyethContractDetailService wyethContractDetailService;
	@Autowired
	private WyethContractService wyethContractService;
	@Autowired
	private WyethContractFeedbackService wyethContractFeedbackService;

	@RequestMapping(value="/query")
	public String queryWyethContractDetailByContractId( Model model ,String contractId){
	List<WyethContractDetail>wcds= wyethContractDetailService.queryWyethContractDetailByContractId(contractId);
	model.addAttribute("wcds", wcds);
	WyethContract wc=wyethContractService.queryWyethContractById(contractId);
	WyethContractFeedback wy=wyethContractFeedbackService.queryBycontractId(contractId);
	model.addAttribute("wc", wc);
	model.addAttribute("wy", wy);
	return "/base/showContract";
		
	}
}
