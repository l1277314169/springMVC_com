package cn.mobilizer.channel.base.service;


import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.PopSkuPriceGroupMapping;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

public interface SkuPriceGroupService extends BasicService<SkuPriceGroup>{
	
	/**
	 * 查询列表
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<SkuPriceGroup> findSkuPriceGroup(Map<String, Object> searchParams) throws BusinessException;
	
	public int  addSkuPriceGroup(SkuPriceGroup skuPriceGroup)throws BusinessException;
	
    public int updateSkuPriceGroup(SkuPriceGroup skuPriceGroup)throws BusinessException;
    
    public int deleteSkuPriceGroup(int skuPriceGoupId)throws BusinessException;
    /**
     * 获取总记录数
     * @param searchParams
     * @return
     * @throws BusinessException
     */
    public Integer queryCount(Map<String, Object> searchParams)throws BusinessException;
    /**
     * 获取编辑一条数据
     * @return
     */
    public SkuPriceGroup getSkuPriceGroup(Map<String,Object> parmarter);
    /**
     * 验证分组名称
     * @param clientId
     * @param groupName
     * @return
     * @throws BusinessException
     */
    public SkuPriceGroup ajaxValidation(Integer clientId,String groupName)throws BusinessException;
}
