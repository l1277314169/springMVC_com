package cn.mobilizer.channel.ctTask.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.PromotionMaterial;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.service.PromotionMaterialService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.ctTask.dao.CtTaskDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskObjectDao;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.po.CtTaskObject;
import cn.mobilizer.channel.ctTask.service.CtTaskObjectService;
import cn.mobilizer.channel.ctTask.service.CtTaskService;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;
import cn.mobilizer.channel.util.Constants;

@Service
public class CtTaskObjectServiceImpl implements CtTaskObjectService {

	@Autowired
	private CtTaskObjectDao ctTaskObjectDao;
	@Autowired
	private CtTaskDao ctTaskao;
	@Autowired
	private SkuService skuService; 
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PromotionMaterialService promotionMaterialService;
	
	@Override
	public List<CtTaskObject> selectByParams(Integer clientId,Integer ctTaskId) {
		CtTask ctTask = ctTaskao.selectByPrimaryKey(ctTaskId);	
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("ctTaskId", ctTaskId);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		params.put("_orderby", "sequence");
		params.put("_order", "asc");
		List<CtTaskObject> ctTaskObjects = ctTaskObjectDao.selectByParams(params);
		List<CtTaskObject> ctTaskObjectsList = new ArrayList<CtTaskObject>(); 
		for(CtTaskObject ctTaskObject : ctTaskObjects){
			if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_SKU.getKey().byteValue()){                       //产品相关任务	
				Sku sku = skuService.getSkuByIdAndClientId(clientId,new Integer(ctTaskObject.getTarget1Id()));
				if(sku!=null){
					ctTaskObject.setObjectName(sku.getSkuName());
					ctTaskObjectsList.add(ctTaskObject);
				}
			}else if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_BRAND.getKey().byteValue()){                   //品牌相关
				Brand brand = brandService.getBrandByIdAndClientId(clientId, new Integer(ctTaskObject.getTarget1Id()));
				if(brand!=null){
					ctTaskObject.setObjectName(brand.getBrandName());
					ctTaskObjectsList.add(ctTaskObject);
				}
			}else if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_CATEGORY.getKey().byteValue()){				      //品类相关	
				Category category = categoryService.getCategoryByClientIdAndId(clientId,new Integer(ctTaskObject.getTarget1Id()));
				if(category!=null){
					ctTaskObject.setObjectName(category.getCategoryName());
					ctTaskObjectsList.add(ctTaskObject);
				}
			}else if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_PROMOTION.getKey().byteValue()){                  //物料相关
				PromotionMaterial promotionMaterial = promotionMaterialService.getPromotionMaterialByIdAndClientId(clientId,new Integer(ctTaskObject.getTarget1Id()));
				if(promotionMaterial!=null){
					ctTaskObject.setObjectName(promotionMaterial.getMaterialName());
					ctTaskObjectsList.add(ctTaskObject);
				}								
			}
		}		
		return ctTaskObjectsList;
	}
	
	@Override
	public Integer selectByParamCount(Map<String, Object> param) {
		return ctTaskObjectDao.selectByParamCount(param);
	}

	@Override
	public List<CtTaskObject> selectTaskObjectBySku(CtTaskDataSerchVo ctTaskDataSerchVo) {
		CtTask ctTask = ctTaskao.selectByPrimaryKey(ctTaskDataSerchVo.getCtTaskId());	
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", ctTaskDataSerchVo.getClientId());
		params.put("ctTaskId", ctTaskDataSerchVo.getCtTaskId());
		params.put("taskType", ctTaskDataSerchVo.getTaskType());
		params.put("brandId", ctTaskDataSerchVo.getBrandId()==null?null:ctTaskDataSerchVo.getBrandId());
		params.put("categoryId", ctTaskDataSerchVo.getCategoryId()==null?null:ctTaskDataSerchVo.getCategoryId());
		params.put("skuSeriesId", ctTaskDataSerchVo.getSkuSeriesId()==null?null:ctTaskDataSerchVo.getSkuSeriesId());
		params.put("skuTypeId", ctTaskDataSerchVo.getSkuTypeId()==null?null:ctTaskDataSerchVo.getSkuTypeId());	
		params.put("objectName",ctTaskDataSerchVo.getObjectName()==null?null:ctTaskDataSerchVo.getObjectName());	
		params.put("_orderby", "sequence");
		params.put("_order", "asc");
		List<CtTaskObject> ctTaskObjects = ctTaskObjectDao.selectTaskObjectBySku(params);
		List<CtTaskObject> ctTaskObjectsList = new ArrayList<CtTaskObject>(); 
		for(CtTaskObject ctTaskObject : ctTaskObjects){
			if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_SKU.getKey().byteValue()){                       //产品相关任务	
				Sku sku = skuService.getSkuByIdAndClientId(ctTaskDataSerchVo.getClientId(),new Integer(ctTaskObject.getTarget1Id()));
				if(sku!=null){
					ctTaskObject.setObjectName(sku.getSkuName());
					ctTaskObjectsList.add(ctTaskObject);
				}
			}else if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_BRAND.getKey().byteValue()){                   //品牌相关
				Brand brand = brandService.getBrandByIdAndClientId(ctTaskDataSerchVo.getClientId(), new Integer(ctTaskObject.getTarget1Id()));
				if(brand!=null){
					ctTaskObject.setObjectName(brand.getBrandName());
					ctTaskObjectsList.add(ctTaskObject);
				}
			}else if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_CATEGORY.getKey().byteValue()){				      //品类相关	
				Category category = categoryService.getCategoryByClientIdAndId(ctTaskDataSerchVo.getClientId(),new Integer(ctTaskObject.getTarget1Id()));
				if(category!=null){
					ctTaskObject.setObjectName(category.getCategoryName());
					ctTaskObjectsList.add(ctTaskObject);
				}
			}else if(ctTask.getTaskType().byteValue() == ChannelEnum.VISIT_TASK_STEP_TYPE.RELATED_PROMOTION.getKey().byteValue()){                  //物料相关
				PromotionMaterial promotionMaterial = promotionMaterialService.getPromotionMaterialByIdAndClientId(ctTaskDataSerchVo.getClientId(),new Integer(ctTaskObject.getTarget1Id()));
				if(promotionMaterial!=null){
					ctTaskObject.setObjectName(promotionMaterial.getMaterialName());
					ctTaskObjectsList.add(ctTaskObject);
				}								
			}
		}		
		return ctTaskObjectsList;
	}

	@Override
	public Integer selectTaskObjectCountBySku(CtTaskDataSerchVo ctTaskDataSerchVo) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", ctTaskDataSerchVo.getClientId());
		params.put("ctTaskId", ctTaskDataSerchVo.getCtTaskId());
		params.put("taskType", ctTaskDataSerchVo.getTaskType());
		params.put("brandId", ctTaskDataSerchVo.getBrandId()==null?null:ctTaskDataSerchVo.getBrandId());
		params.put("categoryId", ctTaskDataSerchVo.getCategoryId()==null?null:ctTaskDataSerchVo.getCategoryId());
		params.put("skuSeriesId", ctTaskDataSerchVo.getSkuSeriesId()==null?null:ctTaskDataSerchVo.getSkuSeriesId());
		params.put("skuTypeId", ctTaskDataSerchVo.getSkuTypeId()==null?null:ctTaskDataSerchVo.getSkuTypeId());		
		params.put("objectName",ctTaskDataSerchVo.getObjectName()==null?null:ctTaskDataSerchVo.getObjectName());
		return ctTaskObjectDao.selectTaskObjectCountBySku(params);
	}
	
}
