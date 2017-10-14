package cn.mobilizer.channel.comm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.apple.vo.YearVo;
import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.ARG_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISITING_TASK_TYPE;
import cn.mobilizer.channel.comm.vo.Enum2Bean;
import cn.mobilizer.channel.comm.vo.MonthVo;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.sales.po.DimYear;
import cn.mobilizer.channel.sales.service.DimService;
import cn.mobilizer.channel.wrTask.po.WrCustomer;
import cn.mobilizer.channel.wrTask.po.WrTask;
import cn.mobilizer.channel.wrTask.service.WrCustomerService;
import cn.mobilizer.channel.wrTask.service.WrTaskService;

@RequestMapping(value = "comms")
@Controller
public class CommsController extends BaseActionSupport{
	
	private static final long serialVersionUID = 1429701128651145500L;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private WrTaskService wrTaskService;
	@Autowired
	private DimService dimService;
	@Autowired
	private WrCustomerService wrCustomerService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ClientUserService clientUserService;
	
	/**
	 * 拜访类型
	 * @return
	 */
	@RequestMapping(value = "/getVisitType")
	@ResponseBody
	public List<Enum2Bean> getVisitType(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		for (VISITING_TASK_TYPE s :VISITING_TASK_TYPE.values()) {
			Enum2Bean e = new Enum2Bean();
			e.setId(s.getKey().intValue());
			e.setName(s.getCnName());
			ls.add(e);
		}
		return ls;
	}
	
	/**
	 *品类编号
	 * @return
	 */
	@RequestMapping(value = "/getArgType")
	@ResponseBody
	public List<Enum2Bean> getArgType(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		
		for (ARG_TYPE s :ARG_TYPE.values()) {
			Enum2Bean e = new Enum2Bean();
			e.setId(s.getKey().intValue());
			e.setName(s.getCnName());
			ls.add(e);
		}
		return ls;
	} 
	
	
	
	/**
	 * 拜访类型
	 * @return
	 */
	@RequestMapping(value = "/getProject")
	@ResponseBody
	public List<Enum2Bean> getProject(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		
		Enum2Bean e = new Enum2Bean();
		e.setId(1);
		e.setName("高露洁");
		ls.add(e);
		
		return ls;
	}
	
	
	/**
	 *KPI 该方法用于测试
	 * @return
	 */
	@RequestMapping(value = "/getKPI")
	@ResponseBody
	public List<Enum2Bean> getKPI(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		Enum2Bean e = new Enum2Bean();
		e.setId(1);
		e.setName("门店覆盖率");
		
		Enum2Bean e1 = new Enum2Bean();
		e1.setId(2);
		e1.setName("必备单品覆盖率");
		
		Enum2Bean e3 = new Enum2Bean();
		e3.setId(3);
		e3.setName("主货架份额");
		
		Enum2Bean e4 = new Enum2Bean();
		e4.setId(4);
		e4.setName("销量");
		
		ls.add(e4);
		ls.add(e3);
		ls.add(e1);
		ls.add(e);
		
		return ls;
	} 
	
	@RequestMapping(value = "/testView")
	public String testDashBoard(){
		
		return "/report/test_view";
	}
	
	/**
	 * 初始化月份控件
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "initializeMonthControl",produces="application/json")
	@ResponseBody
	public List<MonthVo> initializeMonthControl(String beginDate) throws BusinessException{
		List<MonthVo> monthVos = new ArrayList<MonthVo>();
		try {
			String month = DateUtil.formatDate(DateUtil.getDateByStr(beginDate, "yyyy-MM"), "yyyy-MM");
			MonthVo monthVo = new MonthVo();
			monthVo.setText(month); 
			monthVo.setValue(month);
			monthVos.add(monthVo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//return channelCommService.initializeMonthControl();
		return monthVos;
	}
	
	@RequestMapping(value = "getAccount",produces="application/json")
	@ResponseBody
	public List<Options> getAccount(){
		Map<String, Object> paramrter = new HashMap<String, Object>();
		paramrter.put("clientId", super.getClientId());
		paramrter.put("optionName", "store_type");
		List<Options> options = optionsService.selectOptionsList(paramrter);
		return options;
	}
	
	
	@RequestMapping(value = "getOptions",produces="application/json")
	@ResponseBody
	public List<Options> getOptions(String optionName){
		Map<String, Object> paramrter = new HashMap<String, Object>();
		paramrter.put("clientId", super.getClientId());
		paramrter.put("optionName", optionName);
		List<Options> options = optionsService.selectOptionsList(paramrter);
		return options;
	}
	
	/**
	 * 任务编号
	 * @return
	 */
	@RequestMapping(value = "getWrTaskList",produces="application/json")
	@ResponseBody
	public List<WrTask> getWrTaskList(){
		return wrTaskService.getWrTaskList(super.getClientId());
	}
	
	/**
	 * 获取所有年份
	 * @return
	 */
	@RequestMapping(value = "getYear",produces="application/json")
	@ResponseBody
	public List<DimYear> getYear(){
		List<DimYear> years = null;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", super.getClientId());
			years =  dimService.getYear(params);
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return years;
	}
	
	
	@RequestMapping(value = "getCustomers",produces="application/json")
	@ResponseBody
	public List<WrCustomer> getCustomers(){
		List<WrCustomer> lists = wrCustomerService.selectList(super.getClientId());
		return lists;
	}
	
	@RequestMapping(value = "getBrands",produces="application/json")
	@ResponseBody
	public List<Brand> getBrands(Integer customerId){
		List<Brand> brans = brandService.selectBrandListBycustomerId(super.getClientId(),customerId);
		return brans;
	}
	
	@RequestMapping(value = "getYearVo")
	@ResponseBody
	public List<YearVo> getYearVo(Integer year){
		List<YearVo> years = new YearVo().initYearVo(year);
		return years;
	}
	
	@RequestMapping(value="getClientUser",produces="application/json")
	@ResponseBody
	public List<ClientUser> getClientUser(String positionName)
	{
	   int clientId=getClientId();
	   List<ClientUser> list=clientUserService.getClientUserName(clientId, positionName);
	   return list;
	}
	
}
