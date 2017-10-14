package cn.mobilizer.channel.base.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;





import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.PopSkuPriceGroupMappingDao;
import cn.mobilizer.channel.base.dao.SkuPriceDao;
import cn.mobilizer.channel.base.dao.SkuPriceGroupDao;
import cn.mobilizer.channel.base.po.PopSkuPriceGroupMapping;
import cn.mobilizer.channel.base.po.SkuPrice;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.base.service.PopSkuPriceGroupMappingService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.visit.po.CallPlanning;

@Service
public class PopSkuPriceGroupMappingServiceImpl implements PopSkuPriceGroupMappingService {
  @Autowired
  private PopSkuPriceGroupMappingDao popSkuPriceGroupMappingDao;
  @Autowired
  private SkuPriceGroupDao skuPriceGroupDao;
  @Autowired
  private SkuPriceDao skupriceDao;
  
	@Override
	public int addPopSkuPriceGroupMapping(PopSkuPriceGroupMapping popSkuPriceGroupMapping) {
		return popSkuPriceGroupMappingDao.insert(popSkuPriceGroupMapping);
	}

	@Override
	public int updateSkuPriceGroupMapping(PopSkuPriceGroupMapping popSkuPriceGroupMapping) {
		/**更新产品价格分组*/
		SkuPriceGroup	skuPriceGroup = new SkuPriceGroup();
		skuPriceGroup.setSkuPriceGroupId(popSkuPriceGroupMapping.getSkuPriceGroupId());
		skuPriceGroup.setGroupName(popSkuPriceGroupMapping.getGroupName());
		Integer rows = skuPriceGroupDao.update(skuPriceGroup);
		/**产品价格分组和终端映射*/
		PopSkuPriceGroupMapping  psgroupMapping = new PopSkuPriceGroupMapping();
		psgroupMapping.setChannelId(popSkuPriceGroupMapping.getChannelId());
		psgroupMapping.setChainId(popSkuPriceGroupMapping.getChainId());
		psgroupMapping.setDistributorId(popSkuPriceGroupMapping.getDistributorId());
		psgroupMapping.setMappingId(popSkuPriceGroupMapping.getMappingId());
		popSkuPriceGroupMappingDao.update(psgroupMapping);
		/**产品价格*/
		JSONArray newSkuPricesJson = (popSkuPriceGroupMapping.getSkuPrices() != null && !popSkuPriceGroupMapping.getSkuPrices().equals("") && !popSkuPriceGroupMapping.getSkuPrices().equals("[]"))?JSONArray.fromObject(popSkuPriceGroupMapping.getSkuPrices()):null;
		List<SkuPrice> newSkuPricesList = newSkuPricesJson == null? null:(List<SkuPrice>)JSONArray.toCollection(newSkuPricesJson, SkuPrice.class);
		JSONArray oldSkuPricesJson = (popSkuPriceGroupMapping.getOldSkuPrices() != null && !popSkuPriceGroupMapping.getOldSkuPrices().equals("") && !popSkuPriceGroupMapping.getOldSkuPrices().equals("[]"))?JSONArray.fromObject(popSkuPriceGroupMapping.getOldSkuPrices()):null;
		List<SkuPrice> oldSkuPricesList = oldSkuPricesJson == null? null:(List<SkuPrice>)JSONArray.toCollection(oldSkuPricesJson, SkuPrice.class);
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("clientId", popSkuPriceGroupMapping.getClientId());
		parameters.put("skuPriceGroupId", popSkuPriceGroupMapping.getSkuPriceGroupId());
		if (oldSkuPricesList == null) {
			if (newSkuPricesList != null) {
				addSkuPrices(newSkuPricesList,parameters);
			}
		} else {
			if (newSkuPricesList == null) {
				skupriceDao.updateIsdelete(parameters);
			} else {
				/** 获取old中存在而new中不存在的产品，删除 **/
				List<SkuPrice> skuPriceList = compareList(oldSkuPricesList,newSkuPricesList);
				if(skuPriceList != null && skuPriceList.size() > 0){
					String skuIds = "";
					for (int i = 0; i < skuPriceList.size(); i++) {
						skuIds+=skuPriceList.get(i).getSkuId()+",";
					}
					parameters.put("skuIds", skuIds);
					skupriceDao.updatefindInSet(parameters);
				}
				/** 获取new中存在而old中不存在的产品，新增 **/
				List<SkuPrice> newSkuPriceList = compareList(newSkuPricesList,oldSkuPricesList);
				if(newSkuPriceList != null && newSkuPriceList.size() > 0){
					addSkuPrices(newSkuPriceList,parameters);
				}
				/**获取new==old中都存在价格修改了的产品,修改价格*/
				for (int i = 0; i < newSkuPricesList.size(); i++) {
					parameters.put("skuId", newSkuPricesList.get(i).getSkuId());
					SkuPrice skuPrice = skupriceDao.findSkuPriceByParmarter(parameters);
					if(skuPrice != null){
						skuPrice.setPrice(newSkuPricesList.get(i).getPrice());
						skupriceDao.update(skuPrice);
					}
				}
				
			}
		}
		return rows;
	}
	

		

	@Override
	public int addSkuPriceList(PopSkuPriceGroupMapping popSkuPriceGroupMapping) {
		/**分组新增*/
		SkuPriceGroup skuPriceGroup=new SkuPriceGroup();
		skuPriceGroup.setClientId(popSkuPriceGroupMapping.getClientId());
		skuPriceGroup.setGroupName(popSkuPriceGroupMapping.getGroupName());
		Integer skuPriceGroupId =skuPriceGroupDao.insert(skuPriceGroup);
		/**产品价格分组和终端映射*/
		popSkuPriceGroupMapping.setSkuPriceGroupId(skuPriceGroupId);
		popSkuPriceGroupMappingDao.insert(popSkuPriceGroupMapping);
		/**产品价格(价格账套)*/
		if(popSkuPriceGroupMapping.getSkuPrices() !=null && !popSkuPriceGroupMapping.getSkuPrices().equals("")){
			JSONArray jsonArray=JSONArray.fromObject(popSkuPriceGroupMapping.getSkuPrices());
			List<SkuPrice> skuPriceList=(List<SkuPrice>)JSONArray.toCollection(jsonArray, SkuPrice.class);
			SkuPrice skuPrice=new SkuPrice();
			BigDecimal bd = null;
			for (SkuPrice jsonSkuPrice : skuPriceList) {
				skuPrice.setSkuId(jsonSkuPrice.getSkuId());
				if(jsonSkuPrice.getPrice()!= null){
					bd = new BigDecimal(jsonSkuPrice.getPrice().toString());
				}
				skuPrice.setPrice(bd);
				skuPrice.setSkuPriceGroupId(skuPriceGroupId);
				skuPrice.setClientId(popSkuPriceGroupMapping.getClientId());
				skupriceDao.insert(skuPrice);
			}
		}
		return skuPriceGroupId;
	}
	
	private void addSkuPrices(List<SkuPrice> skuPrices,Map<String,Object> parameters){
		BigDecimal bd = null;
		for (int i = 0; i < skuPrices.size(); i++) {
			parameters.put("skuId", skuPrices.get(i).getSkuId());
			SkuPrice skuPrice = skupriceDao.findSkuPriceByParmarter(parameters);
			if(skuPrice != null){
				skuPrice.setIsDelete(Constants.IS_DELETE_FALSE);
				skupriceDao.update(skuPrice);
			}else{
				SkuPrice newSkuPrice = new SkuPrice();
				newSkuPrice.setClientId(Integer.parseInt(parameters.get("clientId").toString()));
				newSkuPrice.setSkuPriceGroupId(Integer.parseInt(parameters.get("skuPriceGroupId").toString()));
				newSkuPrice.setSkuId(skuPrices.get(i).getSkuId());
				if(skuPrices.get(i).getPrice()!= null){
					bd = new BigDecimal(skuPrices.get(i).getPrice().toString());
				}
				newSkuPrice.setPrice(bd);
				skupriceDao.insert(newSkuPrice);
			}
		}
	}
	/**
	 * 比较两个list
	 * @return
	 */
	private List<SkuPrice> compareList(List<SkuPrice> oldSkuPricesList,List<SkuPrice> newSkuPricesList){
		List<SkuPrice> oldAndNewList = new ArrayList<SkuPrice>();
		for (int i = 0; i < oldSkuPricesList.size(); i++) {
			boolean bContained = false;
			for (int j = 0; j < newSkuPricesList.size(); j++) {
				if(oldSkuPricesList.get(i).getSkuId().equals(newSkuPricesList.get(j).getSkuId())){
					bContained = true;
					break;
				}
			}
			if(!bContained){
				oldAndNewList.add(oldSkuPricesList.get(i));
			}
		}
		return oldAndNewList;
	}

}
