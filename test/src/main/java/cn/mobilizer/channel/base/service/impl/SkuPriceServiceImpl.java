package cn.mobilizer.channel.base.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SkuPriceDao;
import cn.mobilizer.channel.base.dao.SkuPriceGroupDao;
import cn.mobilizer.channel.base.po.SkuPrice;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.base.service.SkuPriceService;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class SkuPriceServiceImpl implements SkuPriceService {
	private static final Log LOG = LogFactory.getLog(SkuPriceServiceImpl.class);
	
	@Autowired
	private SkuPriceDao skupriceDao;
	@Autowired
	private SkuPriceGroupDao skuPriceGroupDao;
	

	@Override
	public List<SkuPrice> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuPrice getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SkuPrice> findSkuPriceByClientId(Integer clientId)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TreeNodeVO> getTreeNodes(Integer skuPriceId, Integer id)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer querySkuPriceCount(Map<String, Object> param)
			throws BusinessException {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = skupriceDao.querySkuPriceCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method querySkuPriceCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<SkuPrice> querySkuPriceList(Map<String, Object> param)
			throws BusinessException {
		List<SkuPrice> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = skupriceDao.querySkuPriceList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method querySkuPriceList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public int addSkuPrice(SkuPrice skuPrice) {
		try {
			return skupriceDao.insert(skuPrice);
		} catch (BusinessException e) {
			LOG.error("method addSkuPrice error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addSkuPrice error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

//	@Override
//	public int updateSkuPrice(SkuPrice skuPrice) {
//			 Integer result=0;
//			 Map<String,Object> parames=new HashMap<String,Object>();
//			 parames.put("skuPriceGroupId", skuPrice.getSkuPriceGroupId());
//			 //parames.put("clientId", skuPrice.getChainId());
//			 skupriceDao.updateIsdelete(parames);
//			 if(skuPrice != null && //skuPrice.getSkuPrices() != ""){
//			//	JSONArray jsonArray=JSONArray.fromObject(skuPrice.getSkuPrices());
//				List<SkuPrice> skuPriceList=(List<SkuPrice>)JSONArray.toCollection(jsonArray, SkuPrice.class);
//				for(SkuPrice jsonSkuPrice : skuPriceList){
//					parames.put("skuId", jsonSkuPrice.getSkuId());
//					SkuPrice skuPrices= skupriceDao.selectSkuPrice(parames);
//					if(skuPrices != null){
//						skuPrices.setIsDelete(Constants.IS_DELETE_FALSE);
//						skuPrices.setPrice(jsonSkuPrice.getPrice());
//						result=skupriceDao.update(skuPrices);
//					}else{
//						SkuPrice skuPr=new SkuPrice();
//						skuPr.setSkuId(jsonSkuPrice.getSkuId());
//						BigDecimal bd=new BigDecimal(jsonSkuPrice.getPrice().toString());
//						skuPr.setPrice(bd);
//						skuPr.setClientId(skuPrice.getClientId());
//						skuPr.setSkuPriceGroupId(skuPrice.getSkuPriceGroupId());
//						skupriceDao.insert(skuPr);
//					}
//				}
//		 }
//		return result;
//	}

	@Override
	public int deleteSkuPrice(Integer skuPriceId) {
		int result = 0;
		SkuPrice skuPrice = new SkuPrice();
		try {
			skuPrice.setSkuPriceId(skuPriceId);
			skuPrice.setIsDelete (Constants.IS_DELETE_TRUE);
			result = skupriceDao.update(skuPrice);
		} catch (BusinessException e) {
			LOG.error("method deleteSkuPrice error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteSkuPrice error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public SkuPriceGroup findByPrimaryKey(Integer skuPriceId) {
		return skuPriceGroupDao.findSkuPriceGroupById(skuPriceId);
	}
	
	public SkuPrice skuPriceList(Integer skuPriceGroupId,Integer clientId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("skuPriceGroupId", skuPriceGroupId);
		params.put("clientId", clientId);
		return skupriceDao.findSkuPriceId(params);
	}

	@Override
	public int updateSkuPrice(SkuPrice skuPrice) {
		
		return 0;
	}

	@Override
	public List<SkuPriceVO> skuPriceVOList(Map<String, Object> parmarter) {
		return skupriceDao.findSkuAndPrices(parmarter);
	}
	
	
}
