package cn.mobilizer.channel.systemManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.DistrictService;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;

@Controller
@RequestMapping(value="/commService")
public class CommController  extends BaseActionSupport{

	private static final long	serialVersionUID	= -8540206656587075123L;
	
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private StoreService storeService;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showCoordinatesPage")
	public String  showAddRole(Model model) {
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "/systemManager/showCoordinatesPage";
	}
	
	/**
	 * 异步获取省份的数据
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getProvinceAjax", produces="application/json")
	@ResponseBody	
	public List<Province> getProvinceAjax() throws BusinessException  {
		List<Province> ls = provinceService.getAll();
		return ls;
	}
	
	/**
	 * 异步获取城市数据
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getCityAjax", produces="application/json")
	@ResponseBody
	public List<City> getCityAjax() throws BusinessException{
		
		return cityService.getAll();
	}
	
	
	/**
	 * 根据省份查询市的名称
	 * @param province_id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findCityListByProvinceId/{provinceId}", produces="application/json")
	@ResponseBody
	public List<City> findCityListByProvinceId(@PathVariable("provinceId")Integer provinceId) throws BusinessException{
			return cityService.queryCityByProvinceId(provinceId);
	}
	
	/**
	 * 根据省份查询市的名称
	 * @param province_id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findDistrictListByCityId/{cityId}", produces="application/json")
	@ResponseBody
	public List<District> findDistrictListByCityId(@PathVariable("cityId")Integer cityId) throws BusinessException{
		return districtService.queryDistrictByCityId(cityId);
	}
	
	/**
	 * 获取门店编号
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getStoreNoExists/",produces="application/json")
	@ResponseBody
	public Object getStoreNoExists(String storeNo) throws BusinessException{
		Integer clientId = super.getClientId();
		Integer items = storeService.getStoreItemsByStoreNo(storeNo,clientId);
		if(null==items || items>0){
			return true;
		}else{
			return false;
		}
	}
}
