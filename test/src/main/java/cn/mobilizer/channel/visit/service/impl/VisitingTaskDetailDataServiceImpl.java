package cn.mobilizer.channel.visit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.init.InitOptionsBean;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_TASK_STEP_TYPE;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.VisitingTaskDetailDataDao;
import cn.mobilizer.channel.visit.po.VisitingTaskDetailData;
import cn.mobilizer.channel.visit.service.VisitingTaskDetailDataService;

@Service
public class VisitingTaskDetailDataServiceImpl implements VisitingTaskDetailDataService {
	private static final Log LOG = LogFactory.getLog(VisitingTaskDetailDataServiceImpl.class);
	
	@Autowired
	private VisitingTaskDetailDataDao visitingTaskDetailDataDao;
	@Autowired
	private ChannelCommService channelCommService;
	
	@Override
	public List<VisitingTaskDetailData> findVisitTaskStepByDataId(String visitingTaskDataId, Integer clientId) throws BusinessException{
		List<VisitingTaskDetailData> ls = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("visitingTaskDataId", visitingTaskDataId);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			ls = visitingTaskDetailDataDao.findVisitTaskStepByDataId(params);
		} catch(BusinessException e){
			LOG.error("method findVisitTaskStepByDateId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public Map<String, HashMap<String, String>> getTaskStepDataByParameter(Integer clientUserId,String popId,Byte popType,String visitDate,
			Integer visitingTaskStepId,Byte stepType,Integer clientId, Byte taskType) throws BusinessException{
		List<VisitingTaskDetailData> ls = null;
		Map<String,HashMap<String,String>> detailMap = new HashMap<String,HashMap<String,String>>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientUserId", clientUserId);
			params.put("popId", popId);
			params.put("popType", popType);
			params.put("taskType", taskType);
			params.put("visitingTaskStepId", visitingTaskStepId);
			params.put("startInTime", DateUtil.getDayStart (visitDate));
			params.put("endInTime", DateUtil.getDayEnd (visitDate));
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			ls = visitingTaskDetailDataDao.getTaskStepDataByParameter(params);
			/**ls不为空,按对象id 和属性值嵌套封装进map**/
			if(ls != null && ls.size ()>0) {
				//获取optionsMap;
				Map<String,Map<String,Map<String,String>>> optionMap = InitOptionsBean.optionMap;
				
				String objectKey = "";
				String paraKey = null;
				String paraValue = "";
				Integer paraControlType = null;
				String paraControlName = "";
				if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_QUESTIONNAIRE.getKey ())){//如果是步骤类型是"问卷(无对象)"
					objectKey = stepType.toString ();
					HashMap<String, String> tmpMap = new HashMap<String,String> ();
					for ( VisitingTaskDetailData visitingTaskDetailData : ls ) {
						if(visitingTaskDetailData != null) {
							paraKey = visitingTaskDetailData.getVisitingParameterId ().toString ();
							paraValue = visitingTaskDetailData.getValue ();
							paraControlType = visitingTaskDetailData.getControlType();
							paraControlName = visitingTaskDetailData.getControlName();
							String paraControlValue = channelCommService.getPoraControlValue(paraControlType, paraControlName, paraValue, optionMap, clientId);
							tmpMap.put (paraKey, paraControlValue);
						}
					}
					detailMap.put (objectKey, tmpMap);
				} else if(stepType.equals (VISIT_TASK_STEP_TYPE.RELATED_USER.getKey ())){//如果步骤类型是"人员相关"
					for ( VisitingTaskDetailData visitingTaskDetailData : ls ) {
						if(visitingTaskDetailData != null) {
							HashMap<String, String> tmpMap = new HashMap<String,String> ();
							objectKey = visitingTaskDetailData.getTarget1Id ()+"-"+visitingTaskDetailData.getTarget2Id();
							paraKey = visitingTaskDetailData.getVisitingParameterId ().toString ();
							paraValue = visitingTaskDetailData.getValue();
							paraControlType = visitingTaskDetailData.getControlType();
							paraControlName = visitingTaskDetailData.getControlName();
							String paraControlValue = channelCommService.getPoraControlValue(paraControlType, paraControlName, paraValue, optionMap, clientId);
							
							if(detailMap.containsKey (objectKey)){
								tmpMap = detailMap.get (objectKey);
								if(tmpMap.containsKey (paraKey)){
//									do nothing;理论上对于同一个objectId,他的参数对象是唯一的
								} else {
									tmpMap.put (paraKey, paraControlValue);
								}
								detailMap.put (objectKey, (tmpMap));
							} else {
								tmpMap.put (paraKey, paraControlValue);
								detailMap.put (objectKey, tmpMap);
							}
						}
					}
				} else {//其他步骤类型
					for ( VisitingTaskDetailData visitingTaskDetailData : ls ) {
						if(visitingTaskDetailData != null) {
							HashMap<String, String> tmpMap = new HashMap<String,String> ();
							objectKey = visitingTaskDetailData.getTarget1Id ();
							paraKey = visitingTaskDetailData.getVisitingParameterId ().toString ();
							paraValue = visitingTaskDetailData.getValue();
							paraControlType = visitingTaskDetailData.getControlType();
							paraControlName = visitingTaskDetailData.getControlName();
							String paraControlValue = channelCommService.getPoraControlValue(paraControlType, paraControlName, paraValue, optionMap, clientId);
							
							if(detailMap.containsKey (objectKey)){
								tmpMap = detailMap.get (objectKey);
								if(tmpMap.containsKey (paraKey)){
//									do nothing;理论上对于同一个objectId,他的参数对象是唯一的
								} else {
									tmpMap.put (paraKey, paraControlValue);
								}
								detailMap.put (objectKey, (tmpMap));
							} else {
								tmpMap.put (paraKey, paraControlValue);
								detailMap.put (objectKey, tmpMap);
							}
						}
					}
				}
			}
		} catch(BusinessException e){
			LOG.error("method getTaskStepDataByParameter error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return detailMap;
	}	 
	
	/*public static List<Sku> getSkusByDRP(VisitingTaskInfo taskInfo, VisitingTaskStep taskStep, String searchContent, 
			String categoryId) { 

			String sql = "SELECT a.sku_id, a.sku_no, a.sku_name, a.category_id, a.barcode,a.price ifnull(e.target1_id, '0') AS isDone, b.is_temp FROM sku a INNER JOIN ( SELECT sku_id, 0 AS is_temp FROM must_have_sku WHERE is_delete = 0 AND ifnull(channel_id, 0) =? AND ifnull(chain_id, 0) =? UNION SELECT CAST (target1_id AS int), is_temp FROM visiting_task_step_object WHERE visiting_task_step_id = ? AND is_delete = 0 ) b ON a.sku_id = b.sku_id LEFT JOIN ( SELECT target1_id FROM visiting_task_detail_data WHERE visiting_task_data_id =? AND visiting_task_step_id =? AND is_delete = 0 GROUP BY target1_id ) e ON CAST (a.sku_id AS varchar) = e.target1_id LEFT JOIN category category ON category.is_delete = 0 AND category.category_id = a.category_id WHERE a.is_delete = 0 AND ( a.barcode LIKE ? OR a.sku_name LIKE ? ) "; 

			String sqlOrderBy= " order by is_temp desc, category.sequence, a.category_id ,a.barcode "; 

			if (TextUtils.isEmpty(categoryId)) { 
			sql = sql + sqlOrderBy; 
			}else{ 
			String sqlCondition = " AND a.category_id = "+categoryId; 
			sql = sql + sqlCondition + sqlOrderBy; 
			} 
			return Dao.getInstance().findEntityList(Sku.class, sql, 
			taskInfo.store.channel_id, 
			taskInfo.store.chain_id, 
			taskStep.visiting_task_step_id, 
			taskInfo.taskData.visiting_task_data_id, 
			taskStep.visiting_task_step_id, 
			Dao.getLikeStr(searchContent), 
			Dao.getLikeStr(searchContent)); 

			} 


			*//** 
			* 根据价格帐套取得产品列表 
			* @param taskInfo 
			* @param taskStep 
			* @param searchContent 
			* @param categoryId 
			* @return 
			*//* 
			public static List<Sku> getSkusBySOB(VisitingTaskInfo taskInfo, VisitingTaskStep taskStep, String searchContent, 
			String categoryId) { 

			String sql = "SELECT aa.sku_id, aa.sku_no, aa.sku_name, aa.category_id, aa.barcode,aa.price ,ifnull(cc.target1_id, '0') AS isDone, bb.is_temp FROM sku aa INNER JOIN ( SELECT a.sku_id, 0 AS is_temp FROM sku_price a INNER JOIN sku_price_group b ON b.sku_price_group_id = a.sku_price_group_id INNER JOIN pop_sku_price_group_mapping c ON c.distributor_id = ? AND c.is_delete = 0 AND b.sku_price_group_id = c.sku_price_group_id UNION SELECT CAST (a.target1_id AS int) AS sku_id, a.is_temp FROM visiting_task_step_object a WHERE a.visiting_task_step_id = ? AND a.is_delete = 0 ) bb ON aa.sku_id = bb.sku_id LEFT JOIN ( SELECT target1_id FROM visiting_task_detail_data WHERE visiting_task_data_id =? AND visiting_task_step_id =? AND is_delete = 0 GROUP BY target1_id ) cc ON CAST (aa.sku_id AS varchar) = cc.target1_id LEFT JOIN category category ON category.is_delete = 0 AND category.category_id = aa.category_id WHERE aa.is_delete = 0 AND ( aa.barcode LIKE ? OR aa.sku_name LIKE ? ) "; 

			String sqlOrderBy= " order by is_temp desc, category.sequence, aa.category_id ,aa.barcode "; 
			if (TextUtils.isEmpty(categoryId)) { 
			sql = sql + sqlOrderBy; 
			}else{ 
			String sqlCondition = " AND aa.category_id = "+ categoryId; 
			sql = sql + sqlCondition + sqlOrderBy; 
			} 
			return Dao.getInstance().findEntityList(Sku.class, sql, 
			taskInfo.store.distributor_id, 
			taskStep.visiting_task_step_id, 
			taskInfo.taskData.visiting_task_data_id, 
			taskStep.visiting_task_step_id, 
			Dao.getLikeStr(searchContent), 
			Dao.getLikeStr(searchContent)); 
			} 


			public static List<Sku> getSkusByStepObj(VisitingTaskInfo taskInfo, VisitingTaskStep taskStep, String searchContent, 
			String categoryId){ 
			String sqlSelect = " SELECT CASE WHEN EXISTS ( SELECT visiting_task_detail_data_id FROM visiting_task_detail_data WHERE is_delete = 0 and visiting_task_step_id = obj.visiting_task_step_id AND target1_id = obj.target1_id AND visiting_task_data_id = ? ) THEN 1 ELSE 0 END AS isDone, "; 

			String sqlFrom = " obj.is_temp, c.sequence, c.category_name, sku.sku_id,sku.sku_name,sku.category_id, sku.barcode ,sku.price FROM sku sku "; 

			// 关联 visiting_task_step_object的语句 
			String sqlJoinCondition = " INNER JOIN visiting_task_step_object obj ON obj.is_delete = 0 AND obj.visiting_task_step_id = ? left join category c on c.is_delete = 0 and c.category_id = sku.category_id "; 
			//where 
			String sqlWhere = " where sku.is_delete = 0 AND sku.sku_id = obj.target1_id AND ( sku.barcode LIKE ? OR sku.sku_name LIKE ? ) " ; 

			// 排序 
			String sqlOrderBy = " order by obj.is_temp desc, c.sequence, sku.category_id ,sku.barcode "; 

			StringBuilder sb = new StringBuilder(); 
			sb.append(sqlSelect); 
			sb.append(sqlFrom); 
			sb.append(sqlJoinCondition); 
			sb.append(sqlWhere); 
			if (!TextUtils.isEmpty(categoryId)) { 
			sb.append("AND sku.category_id = "+ categoryId); 
			} 
			sb.append(sqlOrderBy); 


			return Dao.getInstance().findEntityList(Sku.class, sb.toString(), taskInfo.taskData.visiting_task_data_id, 
			taskStep.visiting_task_step_id, Dao.getLikeStr(searchContent),Dao.getLikeStr(searchContent)); 
			} 



			*//** 
			* 获取sku列表按照分类排序 
			* @param taskInfo 
			* @param taskStep 
			* @param searchContent 
			* @param categoryId 
			* @return 
			*//* 
			public static List<Sku> getSkus(VisitingTaskInfo taskInfo, VisitingTaskStep taskStep, String searchContent, 
			String categoryId){ 
			if(ConsVisiting.StepSelectType.SOB == taskStep.select_type){ 
			return getSkusBySOB(taskInfo, taskStep, searchContent, categoryId); 
			}else if(ConsVisiting.StepSelectType.DRP == taskStep.select_type){ 
			return getSkusByDRP(taskInfo, taskStep, searchContent, categoryId); 
			}else{ 
			return getSkusByStepObj(taskInfo, taskStep, searchContent,categoryId); 
			} 
			} 


			*//** 
			* 获取sku的价格 
			* @param store 
			* @param sku 
			* @return 价格帐套存在 返回价格 没有返回默认的sku价格 
			*//* 
			public static String getSkuPriceBySOB(Store store,Sku sku){ 
			String sql = "SELECT a.price FROM sku_price a INNER JOIN sku_price_group b ON b.is_delete = 0 AND b.sku_price_group_id = a.sku_price_group_id INNER JOIN pop_sku_price_group_mapping c ON c.is_delete = 0 AND c.distributor_id = ? AND b.sku_price_group_id = c.sku_price_group_id WHERE a.is_delete = 0 AND a.sku_id = ?"; 
			DbModel dbModel = Dao.getInstance().findDbModelFirstNoTrigger(new SqlInfo(sql, store.distributor_id ,sku.sku_id)); 
			if(dbModel!=null){ 
			String price = dbModel.getString("price"); 
			if(!TextUtils.isEmpty(price)){ 
			return price; 
			} 
			} 
			return sku.price; 

			}*/
	
}
