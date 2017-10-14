package cn.mobilizer.channel.visit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_DATA_CONTROL_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_POP_TYPE;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.ClientRolesTaskMappingDao;
import cn.mobilizer.channel.visit.dao.VisitingParameterDao;
import cn.mobilizer.channel.visit.dao.VisitingPopMappingDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskChannelMappingDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskPositionMappingDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskStepDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskStepObjectDao;
import cn.mobilizer.channel.visit.po.ClientRolesTaskMapping;
import cn.mobilizer.channel.visit.po.VisitingParameter;
import cn.mobilizer.channel.visit.po.VisitingPopMapping;
import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.po.VisitingTaskChannelMapping;
import cn.mobilizer.channel.visit.po.VisitingTaskPositionMapping;
import cn.mobilizer.channel.visit.po.VisitingTaskStep;
import cn.mobilizer.channel.visit.po.VisitingTaskStepObject;
import cn.mobilizer.channel.visit.service.VisitingTaskService;
import cn.mobilizer.channel.visit.vo.VisitingTaskVO;

/**
 * 
 * @author yeeda.tian
 * 拜访任务业务处理层
 */
@Service
public class VisitingTaskServiceImpl implements VisitingTaskService {
	private static final Log LOG = LogFactory.getLog(VisitingTaskServiceImpl.class);
	
	@Autowired
	private VisitingTaskDao visitingTaskDao;
	@Autowired
	private VisitingTaskChannelMappingDao visitingTaskChannelMappingDao;
	@Autowired
	private VisitingPopMappingDao visitingPopMappingDao;
	@Autowired
	private VisitingTaskStepDao visitingTaskStepDao;
	@Autowired
	private VisitingTaskStepObjectDao visitingTaskStepObjectDao;
	@Autowired
	private VisitingParameterDao visitingParameterDao;
	@Autowired
	private VisitingTaskPositionMappingDao visitingTaskPositionMappingDao;
	@Autowired
	private ClientRolesTaskMappingDao clientRolesTaskMappingDao;
	
	@Override
	public Integer queryVisitingTaskCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = visitingTaskDao.queryVisitingTaskCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryVisitingTaskCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}
	
	@Override
	public List<VisitingTask> queryVisitingTaskList(Map<String, Object> params) throws BusinessException{
		List<VisitingTask> list = null;
		try {
			list = visitingTaskDao.queryVisitingTaskList(params);
		} catch (BusinessException e) {
			LOG.error("method queryVisitingTaskList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return list;
	}

	@Override
	public void saveVisitingTaskVO(VisitingTaskVO visitingTaskVO,Integer channelId) throws BusinessException{
		try {
			int visitingTaskId;
			int clientId = visitingTaskVO.getClientId ();
			int clientPositionId = visitingTaskVO.getClientPositionId();
			Byte pop = visitingTaskVO.getPopType ();
			Map<Integer, String>  controlMap= new HashMap<Integer,String> ();
			for(VISIT_DATA_CONTROL_TYPE item:VISIT_DATA_CONTROL_TYPE.values()){
				controlMap.put (item.getKey (), item.getCnName ());
			}
			/**任务关联对象**/
			String taskParameterDatas = null;
			
			/**抽取visitingTask(拜访任务)数据
			 * 目前字段有：visitingTaskName、clientPositionId、startDate、endDate、popType、channelId、storeGroupId
			 * popType字段页面目前为select，只能单选(只有门店)，后面会改为多选。
			 **/
			VisitingTask visitingTask = new VisitingTask();
			visitingTask.setVisitingTaskName (visitingTaskVO.getVisitingTaskName());//任务名称
			visitingTask.setStartDate (visitingTaskVO.getStartDate());//开始时间
			visitingTask.setEndDate (visitingTaskVO.getEndDate());//结束时间
			visitingTask.setVisitingTaskType(visitingTaskVO.getVisitingTaskType());//任务类型
//			visitingTask.setClientPositionId (clientPositionId);//职位id
			visitingTask.setStartLocating(visitingTaskVO.getStartLocating());//开始定位
			visitingTask.setEndLocating(visitingTaskVO.getEndLocating());//结束定位
			visitingTask.setStartPic(visitingTaskVO.getStartPic());//开始拍照
			visitingTask.setEndPic(visitingTaskVO.getEndPic());//结束拍照
			visitingTask.setPopType (pop);//终端类型
			visitingTask.setClientId (clientId);//客户id
			/**按终端类型抽取关联对象的数据
			 * 如果终端类型为门店，需要记录门店分组信息
			 **/
			if(pop.equals (VISIT_POP_TYPE.STORE.getKey ())){
				visitingTask.setPopGroupId (visitingTaskVO.getPopGroupId ());//门店分组
				visitingTaskId = visitingTaskDao.insert (visitingTask);
				/**抽取关联的门店数据**/
				taskParameterDatas = visitingTaskVO.getStoreParameterDatas ();
				
				/**如果渠道不为空，抽取VisitingTaskChannelMapping(拜访任务和渠道映射关系)并保存**/
				if(channelId != null) {
					VisitingTaskChannelMapping visitingTaskChannelMapping = new VisitingTaskChannelMapping();
					visitingTaskChannelMapping.setVisitingTaskId(visitingTaskId);
					visitingTaskChannelMapping.setChannelId (channelId);
					visitingTaskChannelMapping.setClientId (clientId);
					visitingTaskChannelMappingDao.insert (visitingTaskChannelMapping);
				}
				
			} else if(pop.equals (VISIT_POP_TYPE.DISTRIBUTOR.getKey ())) {
				visitingTaskId = visitingTaskDao.insert (visitingTask);
				
				/**抽取关联的供应商数据**/
				taskParameterDatas = visitingTaskVO.getDistributorParameterDatas ();
			} else {
				visitingTaskId = visitingTaskDao.insert (visitingTask);
			}
			
			/**判断职位是否为空
			 * 不为空-保存拜访任务职位关联表信息
			 * 为空-页面校验错误。服务端暂时不抛错**/
			
			VisitingTaskPositionMapping visitingTaskPositionMapping = new VisitingTaskPositionMapping();
			visitingTaskPositionMapping.setVisitingTaskId(visitingTaskId);
			visitingTaskPositionMapping.setClientPositionId(clientPositionId);
			visitingTaskPositionMapping.setClientId(clientId);
			visitingTaskPositionMappingDao.insert(visitingTaskPositionMapping);
			
			/**taskParameterDatas关联数据不为空时，循环插入**/
			if (taskParameterDatas !=null && taskParameterDatas != "") {
				String ids[] = taskParameterDatas.split (",");
				for ( int i = 0 ; i < ids.length ; i++ ) {
					VisitingPopMapping visitingPopMapping = new VisitingPopMapping();
					if(ids[i] != null && ids[i] != ""){
						visitingPopMapping.setMappingId (UUID.randomUUID ().toString());
						visitingPopMapping.setVisitingTaskId (visitingTaskId);
						visitingPopMapping.setClientId (clientId);
						visitingPopMapping.setPopId (ids[i]);
						visitingPopMappingDao.insert(visitingPopMapping);
					}
				}
			}
			
			/**关联角色*/
			if(StringUtils.isNotEmpty(visitingTaskVO.getRolesParameterDatas())){
				String ids[] = visitingTaskVO.getRolesParameterDatas().split(",");
				for(int i = 0 ; i < ids.length ; i++){
					ClientRolesTaskMapping clientRolesTaskMapping = new ClientRolesTaskMapping();
					if(StringUtils.isNotEmpty(ids[i])){
						clientRolesTaskMapping.setVisitingTaskId(visitingTaskId);
						clientRolesTaskMapping.setClientId(clientId);
						clientRolesTaskMapping.setRoleId(new Integer(ids[i]));
						clientRolesTaskMappingDao.insert(clientRolesTaskMapping);
					}
				}
			}
					
			/**提取所有步骤信息，循环处理**/
			List<VisitingTaskStep> stepList = visitingTaskVO.getVisitingTaskSteps ();
			String taskStepParameterDatas = null;
			for ( VisitingTaskStep step : stepList ) {
				/**如果步骤不为空执行**/
				if(step != null) {
					/**提取某一步骤的基础信息-visitingTaskStep**/
					int visitingTaskStepId;
					//Byte parametertype;
					Byte stepType = step.getStepType ();
					/**提取关联对象**/
					taskStepParameterDatas = step.getTaskStepParameterDatas ();
					
					VisitingTaskStep visitingTaskStep = new VisitingTaskStep();
					visitingTaskStep.setVisitingTaskId (visitingTaskId);
					visitingTaskStep.setStepName(step.getStepName ());//步骤名称
					visitingTaskStep.setSortBy(step.getSortBy());//分组方式
					visitingTaskStep.setStepType (step.getStepType ());//选择方式
					visitingTaskStep.setIsMustDo (step.getIsMustDo ());//是否必做
					visitingTaskStep.setIsOnePage(step.getIsOnePage()); //是否一页做
					visitingTaskStep.setStepType(step.getStepType()); //步骤类型
					visitingTaskStep.setRemark(step.getRemark()); //备注
					visitingTaskStep.setClientId(clientId); //客户id
					visitingTaskStepId = visitingTaskStepDao.insert(visitingTaskStep);
					
					/**如果拜访步骤对象为“品牌+品类”时，需要向target2字段插入数据-暂时没有这个选项**/
					/**taskStepParameterDatas关联数据不为空时，循环插入**/
					if (taskStepParameterDatas !=null && taskStepParameterDatas != "") {
						String ids[] = taskStepParameterDatas.split (",");
						for ( int i = 0 ; i < ids.length ; i++ ) {
							VisitingTaskStepObject visitingTaskStepObject = new VisitingTaskStepObject();
							if(ids[i] != null && ids[i] != ""){
								visitingTaskStepObject.setVisitingTaskStepId (visitingTaskStepId);
								visitingTaskStepObject.setClientId (clientId);
								visitingTaskStepObject.setTarget1Id (ids[i]);
								visitingTaskStepObjectDao.insert(visitingTaskStepObject);
							}
						}
					}
					
					List<VisitingParameter> vpList = step.getVisitingParameters ();
					if(vpList != null){
						for ( VisitingParameter parameter : vpList ) {
							VisitingParameter visitingParameter = new VisitingParameter();
							if(parameter != null) {
								visitingParameter.setVisitingTaskStepId (visitingTaskStepId);
								visitingParameter.setParameterName (parameter.getParameterName ());
								visitingParameter.setControlType (parameter.getControlType ());
								/**后面需要完善。目前取的有问题**/
								//TODO
								visitingParameter.setControlName (controlMap.get (parameter.getControlType ()));
								visitingParameter.setSequence(parameter.getSequence());
								visitingParameter.setDefaultValue (parameter.getDefaultValue ());
								visitingParameter.setMaxValue (parameter.getMaxValue ());
								visitingParameter.setMinValue (parameter.getMinValue ());
								visitingParameter.setScale(parameter.getScale());
								visitingParameter.setIsMustDo (parameter.getIsMustDo ());
								visitingParameter.setIsRemember(parameter.getIsRemember());
								visitingParameter.setIsEditable(parameter.getIsEditable());
								visitingParameter.setClientId(clientId);
								visitingParameterDao.insert(visitingParameter);
							}
						}
					}
				}
			}
		} catch (BusinessException e) {
			LOG.error("method saveVisitingTaskVO error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}
	
	@Override
	public void updateVisitingTaskVO(VisitingTaskVO visitingTaskVO,Integer channelId) throws BusinessException{
		try {
			Integer visitingTaskId = visitingTaskVO.getVisitingTaskId();
			Integer clientId = visitingTaskVO.getClientId ();
			Integer clientPositionId = visitingTaskVO.getClientPositionId();
			Byte pop = visitingTaskVO.getPopType ();
			Map<Integer, String>  controlMap= new HashMap<Integer,String> ();
			for(VISIT_DATA_CONTROL_TYPE item:VISIT_DATA_CONTROL_TYPE.values()){
				controlMap.put (item.getKey (), item.getCnName ());
			}
			/**任务关联对象**/
			String taskParameterDatas = null;
			
			/**抽取visitingTask(拜访任务)数据
			 * 目前字段有：visitingTaskName、clientPositionId、startDate、endDate、popType、channelId、storeGroupId
			 * popType字段页面目前为select，只能单选(只有门店)，后面会改为多选。
			 **/
			VisitingTask visitingTask = new VisitingTask();
			visitingTask.setVisitingTaskId(visitingTaskId);
			visitingTask.setClientPositionId(clientPositionId);
			visitingTask.setVisitingTaskName (visitingTaskVO.getVisitingTaskName());//任务名称
			visitingTask.setStartDate (visitingTaskVO.getStartDate());//开始时间
			visitingTask.setEndDate (visitingTaskVO.getEndDate());//结束时间
			visitingTask.setVisitingTaskType(visitingTaskVO.getVisitingTaskType());//任务类型
//			visitingTask.setClientPositionId (clientPositionId);//职位id
			visitingTask.setStartLocating(visitingTaskVO.getStartLocating());//开始定位
			visitingTask.setEndLocating(visitingTaskVO.getEndLocating());//结束定位
			visitingTask.setStartPic(visitingTaskVO.getStartPic());//开始拍照
			visitingTask.setEndPic(visitingTaskVO.getEndPic());//结束拍照
			visitingTask.setPopType(pop);//终端类型
			visitingTask.setClientId(clientId);//客户id
			/**按终端类型抽取关联对象的数据
			 * 如果终端类型为门店，需要记录门店分组信息
			 **/
			if(pop.equals (VISIT_POP_TYPE.STORE.getKey ())){
				visitingTask.setPopGroupId (visitingTaskVO.getPopGroupId());//门店分组
				visitingTaskDao.update(visitingTask);
				/**抽取关联的门店数据**/
				taskParameterDatas = visitingTaskVO.getStoreParameterDatas();
				
				/**如果渠道不为空，抽取VisitingTaskChannelMapping(拜访任务和渠道映射关系)并保存**/
				if(channelId != null) {
					VisitingTaskChannelMapping visitingTaskChannelMapping = new VisitingTaskChannelMapping();
					visitingTaskChannelMapping.setVisitingTaskId(visitingTaskId);					
					visitingTaskChannelMapping.setClientId(clientId);
					VisitingTaskChannelMapping info = visitingTaskChannelMappingDao.getEntityByTaskId(visitingTaskChannelMapping);
					if(info == null){   
						visitingTaskChannelMapping.setChannelId(channelId);
						visitingTaskChannelMappingDao.insert (visitingTaskChannelMapping);
					}else if(info.getChannelId().intValue() != channelId.intValue()){
						info.setIsDelete(Constants.IS_DELETE_TRUE);     //删除旧的
						visitingTaskChannelMappingDao.update(info);    			
						visitingTaskChannelMapping.setChannelId(channelId);   
						visitingTaskChannelMappingDao.insert (visitingTaskChannelMapping);
					}	
					
				}
				
			} else if(pop.equals (VISIT_POP_TYPE.DISTRIBUTOR.getKey ())) {
				visitingTaskDao.update(visitingTask);
				
				/**抽取关联的供应商数据**/
				taskParameterDatas = visitingTaskVO.getDistributorParameterDatas ();
			} else {
				visitingTaskDao.update(visitingTask);
			}
			
			/**判断职位是否为空
			 * 不为空-保存拜访任务职位关联表信息
			 * 为空-页面校验错误。服务端暂时不抛错**/
			
			VisitingTaskPositionMapping visitingTaskPositionMapping = new VisitingTaskPositionMapping();
			visitingTaskPositionMapping.setVisitingTaskId(visitingTaskId);
			visitingTaskPositionMapping.setClientId(clientId);
			VisitingTaskPositionMapping info = visitingTaskPositionMappingDao.getEntityByTaskId(visitingTaskPositionMapping);
			if(info==null){
				visitingTaskPositionMapping.setClientPositionId(clientPositionId);
				visitingTaskPositionMappingDao.insert(visitingTaskPositionMapping);
			}else if(info.getClientPositionId().intValue()!=clientPositionId.intValue()){
				info.setIsDelete(Constants.IS_DELETE_TRUE);     //删除旧的
				visitingTaskPositionMappingDao.update(info);
				visitingTaskPositionMapping.setClientPositionId(clientPositionId);
				visitingTaskPositionMappingDao.insert(visitingTaskPositionMapping);    
			}			
			
			/**taskParameterDatas关联数据不为空时，循环插入**/
			if (taskParameterDatas !=null && taskParameterDatas != "") {
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("visitingTaskId", visitingTaskId);
				String oldStrs = visitingPopMappingDao.getPopsByTaskId(params);
				String newStrs = taskParameterDatas;
				String[] oldPopIds = null;
				String[] newPopIds = null;
				if(StringUtils.isNotEmpty(oldStrs)){
					oldPopIds = oldStrs.split(",");
				}
				if(StringUtils.isNotEmpty(newStrs)){
					newPopIds = newStrs.split(",");
				}
				if(oldPopIds == null){
					if(newPopIds != null){
						// 如果以前的Id为空,那么全部为新增
						Map<String, Object> param = new HashMap<String, Object>();
						for(String popId : newPopIds){
							VisitingPopMapping visitingPopMapping = new VisitingPopMapping();
							visitingPopMapping.setMappingId(UUID.randomUUID().toString());
							visitingPopMapping.setVisitingTaskId(visitingTaskId);
							visitingPopMapping.setClientId(clientId);
							visitingPopMapping.setPopId(popId);
							visitingPopMappingDao.insert(visitingPopMapping);
						}								
					}
				}else{
					if(newPopIds == null){
						// 如果新的Id为空,那么全部为删除			 
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("clientId", clientId);
						param.put("visitingTaskId", visitingTaskId);
						param.put("popIds",StringUtils.join(oldPopIds,","));
						visitingPopMappingDao.currentMapppingisdelte(param);					 		 				
					}else{
						/** 获取old中存在而new中不存在的人员，删除 **/
						String popIds = ArrayUtil.arraySubtract2Str(oldPopIds, newPopIds);				
						if(popIds != "" && popIds.lastIndexOf(",")==popIds.length()-1){
							popIds = popIds.substring(0, popIds.length()-1);
						}
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("clientId", clientId);
						param.put("visitingTaskId", visitingTaskId);
						param.put("popIds", popIds);
						visitingPopMappingDao.currentMapppingisdelte(param);		
						/** 获取new中存在而old中不存在的人员，新增 **/
						String[] newIds = ArrayUtil.arraySubtract(newPopIds, oldPopIds);
						if(newIds!=null && newIds.length>0){
							for(String popId : newIds){
								VisitingPopMapping visitingPopMapping = new VisitingPopMapping();
								visitingPopMapping.setMappingId(UUID.randomUUID().toString());
								visitingPopMapping.setVisitingTaskId(visitingTaskId);
								visitingPopMapping.setClientId(clientId);
								visitingPopMapping.setPopId(popId);
								visitingPopMappingDao.insert(visitingPopMapping);
							}		
						}
					}
				}
			}
			
			/**关联角色*/
			if(StringUtils.isNotEmpty(visitingTaskVO.getRolesParameterDatas())){
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("visitingTaskId", visitingTaskId);
				params.put("clientId", clientId);					
				String oldStrs = clientRolesTaskMappingDao.getClientRolesIdsByTaskId(params);
				String newStrs = visitingTaskVO.getRolesParameterDatas();
				String[] oldRoleIds = null;
				String[] newRoleIds = null;
				if(StringUtils.isNotEmpty(oldStrs)){
					oldRoleIds = oldStrs.split(",");
				}
				if(StringUtils.isNotEmpty(newStrs)){
					newRoleIds = newStrs.split(",");
				}
				if(oldRoleIds == null){
					if(newRoleIds != null){
						// 如果以前的Id为空,那么全部为新增
						for(String roleId : newRoleIds){
							if(StringUtils.isNotEmpty(roleId)){
								ClientRolesTaskMapping clientRolesTaskMapping = new ClientRolesTaskMapping();
								clientRolesTaskMapping.setVisitingTaskId(visitingTaskId);
								clientRolesTaskMapping.setClientId(clientId);
								clientRolesTaskMapping.setRoleId(new Integer(roleId));
								clientRolesTaskMappingDao.insert(clientRolesTaskMapping);
							}
						}								
					}
				}else{
					if(newRoleIds == null){
						// 如果新的Id为空,那么全部为删除			 
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("clientId", clientId);
						params.put("visitingTaskId", visitingTaskId);
						param.put("roleIds",StringUtils.join(oldRoleIds,","));
						clientRolesTaskMappingDao.currentMapppingisdelte(param);					 		 				
					}else{
						/** 获取old中存在而new中不存在的对象，删除 **/
						String roleIds = ArrayUtil.arraySubtract2Str(oldRoleIds, newRoleIds);				
						if(roleIds != "" && roleIds.lastIndexOf(",")==roleIds.length()-1){
							roleIds = roleIds.substring(0, roleIds.length()-1);
						}
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("clientId", clientId);
						params.put("visitingTaskId", visitingTaskId);
						param.put("roleIds", roleIds);
						clientRolesTaskMappingDao.currentMapppingisdelte(param);		
						/** 获取new中存在而old中不存在的对象，新增 **/
						String[] newIds = ArrayUtil.arraySubtract(newRoleIds, oldRoleIds);
						if(newIds!=null && newIds.length>0){
							for(String roleId : newIds){
								ClientRolesTaskMapping clientRolesTaskMapping = new ClientRolesTaskMapping();
								clientRolesTaskMapping.setVisitingTaskId(visitingTaskId);
								clientRolesTaskMapping.setClientId(clientId);
								if(StringUtil.isEmptyString(roleId)){
									continue;
								}
								clientRolesTaskMapping.setRoleId(new Integer(roleId));
								clientRolesTaskMappingDao.insert(clientRolesTaskMapping);
							}		
						}
					}
				}
			}
					
			/**提取所有步骤信息，循环处理**/
			List<VisitingTaskStep> stepList = visitingTaskVO.getVisitingTaskSteps ();
			String taskStepParameterDatas = null;
			for ( VisitingTaskStep step : stepList ) {
				/**如果步骤不为空执行**/
				if(step != null) {
					/**提取某一步骤的基础信息-visitingTaskStep**/
					int visitingTaskStepId;
					//Byte parametertype;
					Byte stepType = step.getStepType ();
					/**提取关联对象**/
					taskStepParameterDatas = step.getTaskStepParameterDatas();				
					VisitingTaskStep visitingTaskStep = new VisitingTaskStep();
					if(step.getVisitingTaskStepId()!=null){
						visitingTaskStepId = step.getVisitingTaskStepId();
						visitingTaskStep.setVisitingTaskStepId(step.getVisitingTaskStepId());
						visitingTaskStep.setVisitingTaskId (visitingTaskId);
						visitingTaskStep.setIsDelete(step.getIsDelete());               //删除
						visitingTaskStep.setStepName(step.getStepName());//步骤名称
						visitingTaskStep.setSortBy(step.getSortBy());//分组方式
						visitingTaskStep.setStepType(step.getStepType());//选择方式
						visitingTaskStep.setIsMustDo(step.getIsMustDo());//是否必做
						visitingTaskStep.setIsOnePage(step.getIsOnePage()); //是否一页做
						visitingTaskStep.setStepType(step.getStepType()); //步骤类型
						visitingTaskStep.setRemark(step.getRemark()); //备注
						visitingTaskStep.setClientId(clientId); //客户id
						visitingTaskStepDao.update(visitingTaskStep);						
					}else{
						visitingTaskStep.setVisitingTaskId (visitingTaskId);
						visitingTaskStep.setStepName(step.getStepName());//步骤名称
						visitingTaskStep.setSortBy(step.getSortBy());//分组方式
						visitingTaskStep.setStepType (step.getStepType());//选择方式
						visitingTaskStep.setIsMustDo (step.getIsMustDo());//是否必做
						visitingTaskStep.setIsOnePage(step.getIsOnePage()); //是否一页做
						visitingTaskStep.setStepType(step.getStepType()); //步骤类型
						visitingTaskStep.setRemark(step.getRemark()); //备注
						visitingTaskStep.setClientId(clientId); //客户id
						visitingTaskStepId = visitingTaskStepDao.insert(visitingTaskStep);
					}

					/**如果拜访步骤对象为“品牌+品类”时，需要向target2字段插入数据-暂时没有这个选项**/
					/**taskStepParameterDatas关联数据不为空时，循环插入**/
					if (taskStepParameterDatas !=null && taskStepParameterDatas != "") {
						Map<String,Object> params = new HashMap<String, Object>();
						params.put("visitingTaskStepId", visitingTaskStepId);
						params.put("clientId", clientId);					
						String oldStrs = visitingTaskStepObjectDao.getTarget1IdsByStepId(params);
						String newStrs = taskStepParameterDatas;
						
						String[] oldObjectIds = null;
						String[] newObjectIds = null;
						if(StringUtils.isNotEmpty(oldStrs)){
							oldObjectIds = oldStrs.split(",");
						}
						if(StringUtils.isNotEmpty(newStrs)){
							newObjectIds = newStrs.split(",");
						}
						if(oldObjectIds == null){
							if(newObjectIds != null){
								// 如果以前的Id为空,那么全部为新增
								for(String objectId : newObjectIds){
									VisitingTaskStepObject visitingTaskStepObject = new VisitingTaskStepObject();
									visitingTaskStepObject.setVisitingTaskStepId(visitingTaskStepId);
									visitingTaskStepObject.setClientId(clientId);
									visitingTaskStepObject.setTarget1Id(objectId);
									visitingTaskStepObjectDao.insert(visitingTaskStepObject);
								}								
							}
						}else{
							if(newObjectIds == null){
								// 如果新的Id为空,那么全部为删除			 
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("clientId", clientId);
								param.put("visitingTaskStepId", visitingTaskStepId);
								param.put("objectIds",StringUtils.join(oldObjectIds,","));
								visitingTaskStepObjectDao.currentMapppingisdelte(param);					 		 				
							}else{
								/** 获取old中存在而new中不存在的对象，删除 **/
								String objectIds = ArrayUtil.arraySubtract2Str(oldObjectIds, newObjectIds);				
								if(objectIds != "" && objectIds.lastIndexOf(",")==objectIds.length()-1){
									objectIds = objectIds.substring(0, objectIds.length()-1);
								}
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("clientId", clientId);
								param.put("visitingTaskStepId", visitingTaskStepId);
								param.put("objectIds", objectIds);
								visitingTaskStepObjectDao.currentMapppingisdelte(param);		
								/** 获取new中存在而old中不存在的对象，新增 **/
								String[] newIds = ArrayUtil.arraySubtract(newObjectIds, oldObjectIds);
								if(newIds!=null && newIds.length>0){
									for(String objectId : newIds){
										VisitingTaskStepObject visitingTaskStepObject = new VisitingTaskStepObject();
										visitingTaskStepObject.setVisitingTaskStepId(visitingTaskStepId);
										visitingTaskStepObject.setClientId(clientId);
										visitingTaskStepObject.setTarget1Id(objectId);
										visitingTaskStepObjectDao.insert(visitingTaskStepObject);
									}		
								}
							}
						}
					}
										
					List<VisitingParameter> vpList = step.getVisitingParameters ();
					if(vpList != null){
						for ( VisitingParameter parameter : vpList ) {
							VisitingParameter visitingParameter = new VisitingParameter();
							if(parameter != null) {
								if(parameter.getVisitingParameterId()!=null){
									visitingParameter.setVisitingParameterId(parameter.getVisitingParameterId());
									visitingParameter.setIsDelete(parameter.getIsDelete());             //删除
									visitingParameter.setVisitingTaskStepId(visitingTaskStepId);
									visitingParameter.setParameterName(parameter.getParameterName());
									visitingParameter.setControlType(parameter.getControlType());
									/**后面需要完善。目前取的有问题**/
									//TODO
									visitingParameter.setControlName(controlMap.get(parameter.getControlType()));
									visitingParameter.setSequence(parameter.getSequence());
									visitingParameter.setDefaultValue(parameter.getDefaultValue());
									visitingParameter.setMaxValue(parameter.getMaxValue());
									visitingParameter.setMinValue(parameter.getMinValue());
									visitingParameter.setScale(parameter.getScale());
									visitingParameter.setIsMustDo(parameter.getIsMustDo());
									visitingParameter.setIsRemember(parameter.getIsRemember());
									visitingParameter.setIsEditable(parameter.getIsEditable());
									visitingParameter.setClientId(clientId);
									visitingParameterDao.update(visitingParameter);
								}else{
									visitingParameter.setVisitingTaskStepId(visitingTaskStepId);
									visitingParameter.setParameterName(parameter.getParameterName());
									visitingParameter.setControlType(parameter.getControlType());
									/**后面需要完善。目前取的有问题**/
									//TODO
									visitingParameter.setControlName(controlMap.get (parameter.getControlType()));
									visitingParameter.setSequence(parameter.getSequence());
									visitingParameter.setDefaultValue(parameter.getDefaultValue());
									visitingParameter.setMaxValue(parameter.getMaxValue());
									visitingParameter.setMinValue(parameter.getMinValue());
									visitingParameter.setScale(parameter.getScale());
									visitingParameter.setIsMustDo(parameter.getIsMustDo());
									visitingParameter.setIsRemember(parameter.getIsRemember());
									visitingParameter.setIsEditable(parameter.getIsEditable());
									visitingParameter.setClientId(clientId);
									visitingParameterDao.insert(visitingParameter);
								}								
							}
						}
					}
				}
			}
		} catch (BusinessException e) {
			LOG.error("method saveVisitingTaskVO error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	@Override
	public VisitingTaskVO findVisitingTaskVOByTaskId(Integer visitingTaskId) throws BusinessException {
		VisitingTaskVO visitingTaskVO = new VisitingTaskVO();
		try {
			VisitingTask visitingTask = visitingTaskDao.fingVisitingTaskById(visitingTaskId);
			BeanUtils.copyProperties(visitingTask, visitingTaskVO);
			if(visitingTask != null) {
				/**获取步骤信息**/
				List<VisitingTaskStep> visitingTaskStepList = visitingTaskStepDao.getVisitingTaskStepListByVtId(visitingTaskId);
				for(VisitingTaskStep visitingTaskStep : visitingTaskStepList){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("clientId", visitingTaskStep.getClientId());
					params.put("visitingTaskStepId", visitingTaskStep.getVisitingTaskStepId());
					params.put("isDelete", Constants.IS_DELETE_FALSE);
					List<VisitingParameter> visitingParameters = visitingParameterDao.getVisitingParameterListByStepId(params);
					visitingTaskStep.setVisitingParameters(visitingParameters);
					/**关联对象*/				
					String objectStrs = visitingTaskStepObjectDao.getTarget1IdsByStepId(params);
					if(StringUtils.isNotEmpty(objectStrs)){
						visitingTaskStep.setTaskStepParameterDatas(","+objectStrs);			
					}
				}
				visitingTaskVO.setVisitingTaskSteps(visitingTaskStepList);
				/**关联门店*/
				if(visitingTask.getPopType()==VISIT_POP_TYPE.STORE.getKey ()){
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("clientId",visitingTask.getClientId());
					params.put("visitingTaskId", visitingTaskId);
					String storeParameterDatas = visitingPopMappingDao.getPopsByTaskId(params);
					visitingTaskVO.setStoreParameterDatas(storeParameterDatas);
				}
				/**关联角色*/
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("visitingTaskId", visitingTaskId);
				params.put("clientId", visitingTask.getClientId());	
				String rolesIdsStr = clientRolesTaskMappingDao.getClientRolesIdsByTaskId(params);
				if(StringUtils.isNotEmpty(rolesIdsStr)){
					visitingTaskVO.setRolesParameterDatas(","+rolesIdsStr);				
				}
			}
		} catch (BusinessException e) {
			LOG.error("method queryVisitingTaskList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return visitingTaskVO;
		
	}

	@Override
	public VisitingTask findVisitingTaskById(Integer visitingTaskId) throws BusinessException{
		try {
			return visitingTaskDao.fingVisitingTaskById(visitingTaskId);
		} catch (BusinessException e) {
			LOG.error("method queryVisitingTaskList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
	}
}
