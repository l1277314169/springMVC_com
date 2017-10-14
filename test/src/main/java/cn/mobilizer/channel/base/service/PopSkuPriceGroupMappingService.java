package cn.mobilizer.channel.base.service;

import cn.mobilizer.channel.base.po.PopSkuPriceGroupMapping;

public interface PopSkuPriceGroupMappingService {
	
    public int addPopSkuPriceGroupMapping(PopSkuPriceGroupMapping popSkuPriceGroupMapping);
    /**
     * 
     * @param popSkuPriceGroupMapping
     * @return
     */
    public int updateSkuPriceGroupMapping(PopSkuPriceGroupMapping popSkuPriceGroupMapping);
    /**
     * 价格账套新增
     * @param popSkuPriceGroupMapping
     * @return
     */
    public int addSkuPriceList(PopSkuPriceGroupMapping popSkuPriceGroupMapping);
    
    	
    
    
}
