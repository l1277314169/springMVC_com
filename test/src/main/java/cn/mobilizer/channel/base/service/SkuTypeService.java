package cn.mobilizer.channel.base.service;

import java.util.List;

import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface SkuTypeService extends BasicService<SkuType>{

	public List<SkuType> selectByParams(Integer clientId,Integer categoryId) throws BusinessException;
	public List<SkuType> selectById(Integer clientId) throws BusinessException;
	public SkuType selectByskutypeId(Integer skutypeid) throws BusinessException;
}
