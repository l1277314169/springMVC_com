package cn.mobilizer.channel.colgate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.colgate.dao.ColgateDao;
import cn.mobilizer.channel.colgate.service.ColgateService;
import cn.mobilizer.channel.colgate.vo.BrandSKUOS;
import cn.mobilizer.channel.colgate.vo.BrandSOS;
import cn.mobilizer.channel.colgate.vo.ColgateConst;
import cn.mobilizer.channel.colgate.vo.DataVo;
import cn.mobilizer.channel.colgate.vo.DistributionVo;
import cn.mobilizer.channel.colgate.vo.FilterVo;
import cn.mobilizer.channel.colgate.vo.OverViewVo;
import cn.mobilizer.channel.colgate.vo.Price;
import cn.mobilizer.channel.colgate.vo.PriceDataVo;
import cn.mobilizer.channel.colgate.vo.SKUDistribution;
import cn.mobilizer.channel.colgate.vo.SKUPrice;
import cn.mobilizer.channel.colgate.vo.ShareOfShelf;
import cn.mobilizer.channel.colgate.vo.SkuOnShelf;

@Service
public class ColgateServiceImpl implements ColgateService {

	@Autowired
	private ClientStructureDao clientStructureDao;
	@Autowired
	private ColgateDao colgateDao;
	
	@Override
	public List<ClientStructure> getStructureByParentId(Map<String, Object> params) {
		
		return clientStructureDao.getStructureByParentId(params);
	}

	@Override
	public List<OverViewVo> loadOverView(FilterVo filterVo) {
		
		return colgateDao.loadOverView(filterVo);
	}

	@SuppressWarnings("unchecked")
	private DistributionVo loadColgateDistribution(FilterVo filterVo) {
		List<?> lists = colgateDao.loadColgateDistribution(filterVo);
		DistributionVo distributionVo = new DistributionVo();
		SKUDistribution sKUDistribution = new SKUDistribution();
		if(!lists.isEmpty()){
			for (int i = 0; i < lists.size();i+=2) {
				List<Integer> head =  (List<Integer>) lists.get(i);
				List<DataVo> datas = (List<DataVo>) lists.get(i+1);
				distributionVo.setStoresAudited(head.get(0));
				distributionVo.setDashboardId(ColgateConst.DashBoard.DISTRIBUTION);
				sKUDistribution.setData(datas);
				distributionVo.setSKUDistribution(sKUDistribution);
			}
		}
		return distributionVo;
	}
	
	
	@SuppressWarnings("unchecked")
	private ShareOfShelf loadColgateShareOfShelf(FilterVo filterVo){
		List<?> lists = colgateDao.loadColgateShareOfShelf(filterVo);
		ShareOfShelf shareOfShelf = new ShareOfShelf();
		BrandSOS brandSOS = new BrandSOS();
		if(!lists.isEmpty()){
			for (int i = 0; i < lists.size();i+=2) {
				List<Integer> head =  (List<Integer>) lists.get(i);
				List<DataVo> datas = (List<DataVo>) lists.get(i+1);
				shareOfShelf.setStoresAudited(head.get(0));
				shareOfShelf.setDashboardId(ColgateConst.DashBoard.SHARE_OF_SHELF);
				brandSOS.setData(datas);
				shareOfShelf.setBrandSOS(brandSOS);
			}
		}
		return shareOfShelf;
	}
	
	@SuppressWarnings("unchecked")
	private Price loadColgatePrice(FilterVo filterVo){
		Price price = new Price();
		SKUPrice skuPrice = new SKUPrice();
		List<?> lists = colgateDao.loadColgatePrice(filterVo);
		if(!lists.isEmpty()){
			for (int i = 0; i < lists.size();i+=2) {
				List<Integer> head =  (List<Integer>) lists.get(i);
				List<PriceDataVo> datas = (List<PriceDataVo>) lists.get(i+1);
				price.setStoresAudited(head.get(0));
				price.setDashboardId(ColgateConst.DashBoard.PRICE);
				skuPrice.setData(datas);
				price.setSKUPrice(skuPrice);
			}
		}
		return price;
	}
	
	
	@SuppressWarnings("unchecked")
	private SkuOnShelf loadColgateSkuOnShelf(FilterVo filterVo){
		SkuOnShelf skuOnShelf = new SkuOnShelf();
		BrandSKUOS brandSKUOS = new BrandSKUOS();
		List<?> lists = colgateDao.loadColgateSkuOnShelf(filterVo);
		if(!lists.isEmpty()){
			for (int i = 0; i < lists.size();i+=2) {
				List<Integer> head =  (List<Integer>) lists.get(i);
				List<DataVo> datas = (List<DataVo>) lists.get(i+1);
				skuOnShelf.setStoresAudited(head.get(0));
				skuOnShelf.setDashboardId(ColgateConst.DashBoard.SKU_ON_SHELF);
				brandSKUOS.setData(datas);
				skuOnShelf.setBrandSKUOS(brandSKUOS);
			}
		}
		return skuOnShelf;
	}
	

	@Override
	public Object loadColgateDashBoardData(FilterVo filterVo) {
		Object object = null;
		if(null==filterVo.getDashboardId()){ //默认查询 DISTRIBUTION
			filterVo.setDashboardId(ColgateConst.DashBoard.DISTRIBUTION);
		}
		if(filterVo.getDashboardId().equals(ColgateConst.DashBoard.DISTRIBUTION)){
			object = this.loadColgateDistribution(filterVo);
		}else if(filterVo.getDashboardId().equals(ColgateConst.DashBoard.SHARE_OF_SHELF)){
			object = this.loadColgateShareOfShelf(filterVo);
		}else if(filterVo.getDashboardId().equals(ColgateConst.DashBoard.PRICE)){
			object = this.loadColgatePrice(filterVo);
		}else if(filterVo.getDashboardId().equals(ColgateConst.DashBoard.SKU_ON_SHELF)){
			object = this.loadColgateSkuOnShelf(filterVo);
		}
		return object;
	}

}
