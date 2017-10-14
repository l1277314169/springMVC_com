package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SkuDistributionDao;
import cn.mobilizer.channel.base.dao.SkuDistributionMappingDao;
import cn.mobilizer.channel.base.dao.SkuDistributionPopMappingDao;
import cn.mobilizer.channel.base.po.SkuDistribution;
import cn.mobilizer.channel.base.po.SkuDistributionMapping;
import cn.mobilizer.channel.base.po.SkuDistributionPopMapping;
import cn.mobilizer.channel.base.service.SkuDistributionService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
@Service
public class SkuDistributionServiceImpl implements SkuDistributionService{
	@Autowired
	private SkuDistributionDao skuDistributionDao;
	@Autowired
	private SkuDistributionPopMappingDao skuDistributionPopMappingDao;
	@Autowired
	private SkuDistributionMappingDao skuDistributionMappingDao;
	@Override
	public List<SkuDistribution> skuDistributionList(Map<String, Object> parmars)
			throws BusinessException {
		return skuDistributionDao.queryByParmas(parmars);
	}

	@Override
	public Integer queryCount(Map<String, Object> parmars)
			throws BusinessException {
		return skuDistributionDao.getCount(parmars);
	}

	@Override
	public int addSkuDistribution(SkuDistribution skuDistribution)
			throws BusinessException {
		/**分组*/
		Integer rows = skuDistributionDao.insert(skuDistribution);
		/**产品分销和终端映射*/
		SkuDistributionPopMapping	skuDistributionPopMapping = new  SkuDistributionPopMapping();
		skuDistributionPopMapping.setChainId(skuDistribution.getChainId());
		skuDistributionPopMapping.setChannelId(skuDistribution.getChannelId());
		skuDistributionPopMapping.setDistributorId(skuDistribution.getDistributorId());
		skuDistributionPopMapping.setSkuDistributionId(rows);
		skuDistributionPopMapping.setClientId(skuDistribution.getClientId());
		skuDistributionPopMappingDao.insert(skuDistributionPopMapping);
		/**产品分销和产品映射*/
		String[]  str = (skuDistribution.getSkuIds() == null || skuDistribution.getSkuIds().equals(""))?null:skuDistribution.getSkuIds().split(",");
		if(str != null){
			for (int i = 0; i < str.length; i++) {
				SkuDistributionMapping	skuDistributionMapping = new SkuDistributionMapping();
				skuDistributionMapping.setSkuDistributionId(rows);
				skuDistributionMapping.setSkuId(Integer.parseInt(str[i]));
				skuDistributionMapping.setClientId(skuDistribution.getClientId());
				skuDistributionMappingDao.insert(skuDistributionMapping);
			}
		}
		return rows;
	}

	@Override
	public int updateSkuDistribution(SkuDistribution skuDistribution)
			throws BusinessException {
		/**分组*/
		Integer rows = skuDistributionDao.update(skuDistribution);
		/**产品分销和终端映射*/
		SkuDistributionPopMapping	skuDistributionPopMapping = new  SkuDistributionPopMapping();
		skuDistributionPopMapping.setChainId(skuDistribution.getChainId());
		skuDistributionPopMapping.setChannelId(skuDistribution.getChannelId());
		skuDistributionPopMapping.setDistributorId(skuDistribution.getDistributorId());
		skuDistributionPopMapping.setClientId(skuDistribution.getClientId());
		skuDistributionPopMapping.setSkuDistributionId(skuDistribution.getSkuDistributionId());
		skuDistributionPopMappingDao.update(skuDistributionPopMapping);
		/**产品分销和产品映射*/
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", skuDistribution.getClientId());
		parameters.put("skuDistributionId", skuDistribution.getSkuDistributionId());
		String[]  newstr = (skuDistribution.getSkuIds() == null || skuDistribution.getSkuIds().equals(""))?null:skuDistribution.getSkuIds().split(",");
		String[] oldstr = (skuDistribution.getOldSkuIds() == null || skuDistribution.getOldSkuIds().equals(""))?null:skuDistribution.getOldSkuIds().split(",");
		if (oldstr == null) {
			if (newstr != null) {
				addSkuDistributionMapping(newstr,parameters,skuDistribution);
			}
		} else {
			if (newstr == null) {
				parameters.put("skuId", skuDistribution.getOldSkuIds());
				skuDistributionMappingDao.updateIsdelete(parameters);
			} else {
				/** 获取old中存在而new中不存在的门店，删除 **/
				String oldSkuIdsStr = ArrayUtil.arraySubtract2Str(oldstr, newstr);
				if (oldSkuIdsStr != null && oldSkuIdsStr != "") {
					parameters.put("skuId", oldSkuIdsStr);
					skuDistributionMappingDao.updateIsdelete(parameters);
				}
				/** 获取new中存在而old中不存在的门店，新增 **/
				String[] newSkuIdsStr = ArrayUtil.arraySubtract(newstr, oldstr);
				if (newSkuIdsStr != null && newSkuIdsStr.length > 0) {
					addSkuDistributionMapping(newstr,parameters,skuDistribution);
				}
			}
		}
		return rows;
		
	}

	private void addSkuDistributionMapping(String[] str,Map<String,Object> parmars,SkuDistribution skuDistribution){
		for (int i = 0; i < str.length; i++) {
			parmars.put("skuId", Integer.parseInt(str[i]));
			SkuDistributionMapping	skuDistributionMapping = skuDistributionMappingDao.findSkuDistributionMappingByParmar(parmars);
			if(skuDistributionMapping != null){
				skuDistributionMapping.setIsDelete(Constants.IS_DELETE_FALSE);
				skuDistributionMappingDao.update(skuDistributionMapping);
			}else{
				SkuDistributionMapping	sdbm = new SkuDistributionMapping();
				sdbm.setSkuDistributionId(skuDistribution.getSkuDistributionId());
				sdbm.setSkuId(Integer.parseInt(str[i]));
				sdbm.setClientId(skuDistribution.getClientId());
				skuDistributionMappingDao.insert(sdbm);
			}
		}
	}
	@Override
	public SkuDistribution findSkuDistributionByParmars(
			Integer clientId,Integer skuDistributionId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("skuDistributionId", skuDistributionId);
		return skuDistributionDao.findSkuDistributionById(params);
	}

	@Override
	public int deleteSkuDistribution(Integer clientId,Integer skuDistributionId)
			throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("skuDistributionId", skuDistributionId);
		return skuDistributionDao.updateIsdelte(params);
	}

	@Override
	public List<SkuDistribution> onlySkuDistribution(Integer clientId, String groupName)
			throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("groupName", groupName);
		return skuDistributionDao.findSkuDistributionGroupName(params);
	}

}
