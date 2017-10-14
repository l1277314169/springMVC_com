package cn.mobilizer.channel.visit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SkuDao;
import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_TASK_STEP_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.TASK_STEP_SELECT_TYPE;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.VisitingTaskStepObjectDao;
import cn.mobilizer.channel.visit.po.VisitingTaskStepObject;
import cn.mobilizer.channel.visit.service.VisitingTaskStepObjectService;


@Service
public class VisitingTaskStepObjectServiceImpl implements VisitingTaskStepObjectService {
	private static final Log LOG = LogFactory.getLog(VisitingTaskStepObjectServiceImpl.class);
	
	@Autowired
	private VisitingTaskStepObjectDao visitingTaskStepObjectDao;
	@Autowired
	private SkuDao skudao;
	@Autowired
	private StoreDao storeDao;
	
	@Override
	public List<VisitingTaskStepObject> getVisitingObjectListByStep(Integer clientUserId, Integer visitingTaskStepId, Byte stepType, Integer clientId, 
			Byte selectType,String popId, Byte popType, String visitDate) throws BusinessException{
		
		List<VisitingTaskStepObject> ls = null;
		try{
			/**判断步骤类型，根据不同的类型查询不同的对象**/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientUserId", clientUserId);
			params.put("clientId", clientId);
			params.put("visitingTaskStepId", visitingTaskStepId);
			params.put("popId", popId);
			params.put("popType", popType);
			
			if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_SKU.getKey ())){//如果是sku关联产品表
				/**通过popId查询该门店的相关数据**/
				Store store = storeDao.selectByPrimaryKey(popId);
				
				params.put("channelId", store.getChannelId());
				params.put("chainId", store.getChainId());
				params.put("distributorId", store.getDistributorId());
				params.put("startIntime", DateUtil.getDayStart (visitDate));
				params.put("endIntime", DateUtil.getDayEnd (visitDate));
				
				if(selectType !=null && selectType.equals(TASK_STEP_SELECT_TYPE.JGZT.getKey())){//如果为价格账套
					ls = visitingTaskStepObjectDao.getVisitingSkuList4jgztByParams(params);
				} else if(selectType !=null && selectType.equals(TASK_STEP_SELECT_TYPE.FX.getKey())){//如果为分销
					ls = visitingTaskStepObjectDao.getVisitingSkuList4fxByParams(params);
				} else {
					ls = visitingTaskStepObjectDao.getVisitingSkuListByParams(params);
				}
			} else if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_BRAND.getKey ())){//如果是brand关联品牌表
				ls = visitingTaskStepObjectDao.getVisitingBrandListByParams(params);
			}else if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_CATEGORY.getKey ())){//如果是category关联类别表
				ls = visitingTaskStepObjectDao.getVisitingCategoryListByParams(params);
			}else if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_USER.getKey ())){//如果是USER关联client_user表
				ls = visitingTaskStepObjectDao.getVisitingUserListByParams(params);
			}else if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_PROMOTION.getKey ())){//如果是PROMOTION.关联物料表
				ls = visitingTaskStepObjectDao.getVisitingPromotionListByParams(params);
			}
		}catch(BusinessException e){
			LOG.error("method getVisitingObjectListByStep error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}
	
}
