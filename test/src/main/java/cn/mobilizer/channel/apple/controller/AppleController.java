package cn.mobilizer.channel.apple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.apple.service.AppleService;
import cn.mobilizer.channel.apple.vo.AppleFilter;
import cn.mobilizer.channel.apple.vo.AppleOverViewVo;
import cn.mobilizer.channel.apple.vo.YearVo;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.export.controller.ActionSupport;
import cn.mobilizer.channel.util.DateTimeUtils;

@Controller
@RequestMapping(value = "/apple")
public class AppleController extends ActionSupport{

	private static final long serialVersionUID = 4947667718226433350L;
	
	@Autowired
	private AppleService appleService;
	
	@RequestMapping(value = "appleOverView")
	public String overView(Model model,AppleFilter filterVo){
		if(null == filterVo.getYearId()){
			filterVo.setYearId(DateTimeUtils.getCurrentYear());
		}
		if(null==filterVo.getChannel()){
			filterVo.setChannel(0);
		}
		if(null==filterVo.getProvince()){
			filterVo.setProvince(0);
		}
		
		filterVo.setDeptIds(super.getClientStructureId()+"");
		
		List<YearVo> years = new YearVo().initYearVo();
		String filterStructureIds = super.getDeptIds(getClientStructureId()+"");
		filterVo.setFilterStructureIds(filterStructureIds);
		model.addAttribute("years",years);
		model.addAttribute("filterVo",filterVo);
		return "/apple/appleOverView";
	}
	
	@RequestMapping(value = "loadOverViewContent")
	@ResponseBody
	public Object loadOverViewContent(AppleFilter filterVo){
		List<AppleOverViewVo> voList = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			voList = appleService.loadOverView(filterVo);
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return voList;
	}
	
	
	@RequestMapping(value = "appleReports")
	public String appleReports(){
		
		return "/apple/appleReports";
	}
	
	@RequestMapping(value = "getYearVo")
	@ResponseBody
	public List<YearVo> getYearVo(){
		List<YearVo> years = new YearVo().initYearVo();
		return years;
	}
	
	@RequestMapping(value = "showDashboard")
	public String showDashboard(Model model,AppleFilter filterVo){
		if(null == filterVo.getYearId()){
			filterVo.setYearId(DateTimeUtils.getCurrentYear());
		}
		if(null==filterVo.getChannel()){
			filterVo.setChannel(0);
		}
		if(null==filterVo.getProvince()){
			filterVo.setProvince(0);
		}
		filterVo.setDeptIds(super.getClientStructureId()+"");
		List<YearVo> years = new YearVo().initYearVo();
		String filterStructureIds = super.getDeptIds(getClientStructureId()+"");
		filterVo.setFilterStructureIds(filterStructureIds);
		model.addAttribute("years",years);
		model.addAttribute("filterVo",filterVo);
		return "/apple/appleDashboard";
	}
	
	
	@RequestMapping(value = "/loadAppleDashboardVo")
	@ResponseBody
	public Object loadAppleDashboardVo(AppleFilter filterVo){
		Object result = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			result = appleService.loadAppleACSC(filterVo);
		} finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return result;
	}
	
	
}
