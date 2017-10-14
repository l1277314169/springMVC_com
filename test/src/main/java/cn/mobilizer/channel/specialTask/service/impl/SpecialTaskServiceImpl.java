package cn.mobilizer.channel.specialTask.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.service.impl.ClientPositionServiceImpl;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskDao;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskDataDao;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskObjectDao;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskParameterDao;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskPositionMappingDao;
import cn.mobilizer.channel.specialTask.po.SpecialTask;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;
import cn.mobilizer.channel.specialTask.po.SpecialTaskPositionMapping;
import cn.mobilizer.channel.specialTask.service.SpecialTaskService;
import cn.mobilizer.channel.specialTask.vo.SpecialTaskVO;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class SpecialTaskServiceImpl implements SpecialTaskService{
	
	private static final Log LOG = LogFactory.getLog(ClientPositionServiceImpl.class);
	@Autowired
	private SpecialTaskDao specialTaskDao;
	@Autowired
	private SpecialTaskDataDao specialTaskDataDao;
	@Autowired
	private SpecialTaskParameterDao specialTaskParameterDao;
	@Autowired
	private SpecialTaskObjectDao specialTaskObjectDao;
	@Autowired
	private SpecialTaskPositionMappingDao specialTaskPositionMappingDao;
	
	public String addSpecialTask(SpecialTaskVO specialTaskVO) throws BusinessException {
		String specialTaskId="";
			try {
				/**专项任务主表新增*/
				SpecialTask specialTask = new SpecialTask();
				BeanUtils.copyProperties(specialTaskVO, specialTask);
				String specialTaskUUId = UUID.randomUUID().toString();
				specialTask.setSpecialTaskId(specialTaskUUId);
				specialTaskId = specialTaskDao.insertSpecialTask(specialTask);
				/**专项任务参数定义*/
				List<SpecialTaskParameter> specialTaskParameterlist = specialTaskVO.getSpecialTaskParameterlist();
				if (specialTaskParameterlist != null
						&& specialTaskParameterlist.size() > 0) {
					for (int i = 0; i < specialTaskParameterlist.size(); i++) {
						SpecialTaskParameter specialTaskParameter = specialTaskParameterlist.get(i);
						String uuid = UUID.randomUUID().toString();
						specialTaskParameter.setSpecialTaskParameterId(uuid);
						specialTaskParameter.setSpecialTaskId(specialTaskId);
						specialTaskParameter.setClientId(specialTaskVO.getClientId());
						specialTaskParameterDao.insertSpecialTaskParameter(specialTaskParameter);
					}
				}
				/**专项任务对象是人员*/
				if (specialTaskVO != null && specialTaskVO.getObjectType().equals(Constants.OBJ_CLIENTUSER)) {
					String[] clientUserIdStr = (specialTaskVO.getObjectIds() == null || specialTaskVO.getObjectIds().equals("")) ? null : specialTaskVO.getObjectIds().split(",");
					if (clientUserIdStr != null) {
						for (int i = 0; i < clientUserIdStr.length; i++) {
							SpecialTaskObject specialTaskObject = new SpecialTaskObject();
							String ObjectId = UUID.randomUUID().toString();
							specialTaskObject.setSpecialTaskObjectId(ObjectId);
							specialTaskObject.setSpecialTaskId(specialTaskId);
							specialTaskObject.setObjectType(Constants.OBJ_CLIENTUSER);
							specialTaskObject.setObjectId(clientUserIdStr[i]);
							specialTaskObject.setClientId(specialTaskVO.getClientId());
							specialTaskObjectDao.addSpecialTaskObject(specialTaskObject);
						}
					}
				} else {
					/**专项任务对象是门店*/
					String[] storeStr = (specialTaskVO.getObjectIds() == null || specialTaskVO.getObjectIds().equals("")) ? null : specialTaskVO.getObjectIds().split(",");
					if (storeStr != null) {
						for (int i = 0; i < storeStr.length; i++) {
							SpecialTaskObject specialTaskObject = new SpecialTaskObject();
							String ObjectId = UUID.randomUUID().toString();
							specialTaskObject.setSpecialTaskObjectId(ObjectId);
							specialTaskObject.setSpecialTaskId(specialTaskId);
							specialTaskObject.setObjectType(Constants.OBJ_STORE);
							specialTaskObject.setObjectId(storeStr[i]);
							specialTaskObject.setClientId(specialTaskVO.getClientId());
							specialTaskObjectDao.addSpecialTaskObject(specialTaskObject);
						}
					}
					/**专项任务与职位映射*/
					SpecialTaskPositionMapping specialTaskPositionMapping = new SpecialTaskPositionMapping();
					String ObjectId = UUID.randomUUID().toString();
					specialTaskPositionMapping.setMappingId(ObjectId);
					specialTaskPositionMapping.setSpecialTaskId(specialTaskId);
					specialTaskPositionMapping.setClientPositionId(specialTaskVO.getClientPositionId());
					specialTaskPositionMapping.setClientId(specialTaskVO.getClientId());
					specialTaskPositionMappingDao.addSpecialTaskPositionMapping(specialTaskPositionMapping);
				}
			} catch (Exception e) {
				LOG.error("method addSpecialTask error, ", e);
				throw new BusinessException(ErrorCodeMsg.ERR_ADD);
			}
		return specialTaskId;
	}

	@Override
	public int updateSpecialTask(SpecialTaskVO specialTaskVO)
			throws BusinessException {
		/**专项任务主表修改*/
		int rows = 0;
		try {
			SpecialTask specialTask = new SpecialTask();
			BeanUtils.copyProperties(specialTaskVO, specialTask);
			rows = specialTaskDao.updateSpecialTask(specialTask);
			/**专项任务参数定义修改*/
			updateSpecialTaskParameterlist(specialTaskVO);
			if(specialTaskVO.getObjectType().equals(Constants.OBJ_CLIENTUSER)){
				/**专项任务对象是人员*/
				specialTaskObjectRelation(specialTaskVO);
			}else{
				/**专项任务对象是门店*/
				specialTaskObjectRelation(specialTaskVO);
			}
			/**专项任务与职位映射修改*/
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", specialTaskVO.getClientId());
			parameters.put("specialTaskId",specialTaskVO.getSpecialTaskId());
			SpecialTaskPositionMapping specialTaskPositionMapping = specialTaskPositionMappingDao.findSpecialTaskPositionMappingBySpecialTaskId(parameters);
			if(specialTaskPositionMapping != null){
				specialTaskPositionMapping.setClientPositionId(specialTaskVO.getClientPositionId());
				specialTaskPositionMappingDao.updateSpecialTaskPositionMapping(specialTaskPositionMapping);
			}else{
				SpecialTaskPositionMapping spm = new SpecialTaskPositionMapping();
				String clientPositionIdUUid = UUID.randomUUID().toString();
				spm.setMappingId(clientPositionIdUUid);
				spm.setClientId(specialTaskVO.getClientId());
				spm.setClientPositionId(specialTaskVO.getClientPositionId());
				spm.setSpecialTaskId(specialTaskVO.getSpecialTaskId());
				specialTaskPositionMappingDao.addSpecialTaskPositionMapping(spm);
			}
		} catch (Exception e) {
			LOG.error("method updateSpecialTask error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return rows;
	}
	
	

	@Override
	public SpecialTaskVO findSpecialTaskByParmas(Integer clientId,String specialTaskId)
			throws BusinessException {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("specialTaskId", specialTaskId);
			//parameters.put("clientUserId", clientUserId);
			SpecialTaskVO obj = specialTaskDao.selectSpecialTaskVOByParameter(parameters);
			/**关联参数*/
			List<SpecialTaskParameter> specialTaskParameter = specialTaskParameterDao.selectSpecialTaskParameterBySpecialTaskId(parameters);
			if(obj != null){
				obj.setSpecialTaskParameterlist(specialTaskParameter);
			}
			return obj;
		} catch (Exception e) {
			LOG.error("method findSpecialTaskByParmas error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public List<SpecialTaskVO> specialTaskList(Integer clientId,Integer clientUserId)
			throws BusinessException {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("clientUserId", clientUserId);
			List<SpecialTaskVO> specialTaskDataList = specialTaskDao.selectSpecialTask(parameters);
			return specialTaskDataList;
		} catch (Exception e) {
			LOG.error("method specialTaskList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}
	/**
	 * 修改专项任务参数列表的方法
	 * @param specialTaskVO
	 */
	public void updateSpecialTaskParameterlist(SpecialTaskVO specialTaskVO){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", specialTaskVO.getClientId());
		parameters.put("specialTaskId", specialTaskVO.getSpecialTaskId());
		/**得到参数列表修改*/
		List<SpecialTaskParameter> specialTaskParameterList = specialTaskVO.getSpecialTaskParameterlist();
		/**全部删除*/
		specialTaskParameterDao.updateIsdelete(parameters);
		
		if(specialTaskParameterList != null && specialTaskParameterList.size() > 0){
			for (int i = 0; i < specialTaskParameterList.size(); i++) {
				if(specialTaskParameterList.get(i).getSpecialTaskParameterId() == null || specialTaskParameterList.get(i).getSpecialTaskParameterId().equals("")){
					SpecialTaskParameter specialTaskParameter = specialTaskParameterList.get(i);
					String uuid = UUID.randomUUID().toString();
					specialTaskParameter.setSpecialTaskParameterId(uuid);
					specialTaskParameter.setSpecialTaskId(specialTaskVO.getSpecialTaskId());
					specialTaskParameter.setClientId(specialTaskVO.getClientId());
					specialTaskParameterDao.insertSpecialTaskParameter(specialTaskParameter);
				}else{
					SpecialTaskParameter specialTaskParameter = specialTaskParameterList.get(i);
					if(specialTaskParameter != null){
						specialTaskParameter.setIsDelete(Constants.IS_DELETE_FALSE);
						specialTaskParameterDao.updateSpecialTaskParameter(specialTaskParameter);
					}
				}
			}
		}
		
	}
	
	/**
	 * 封装的方法
	 * @param specialTaskVO
	 */
	private void specialTaskObjectRelation(SpecialTaskVO specialTaskVO){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", specialTaskVO.getClientId());
		parameters.put("specialTaskId", specialTaskVO.getSpecialTaskId());
		parameters.put("objectType", specialTaskVO.getObjectType());
		
		String[]  newstr = (specialTaskVO.getObjectIds() == null || specialTaskVO.getObjectIds().equals(""))?null:specialTaskVO.getObjectIds().split(",");
		/**数据库查询*/
		String objectIds = specialTaskObjectDao.findOldSpecialTaskObject(parameters);
		String[] oldstr = (objectIds == null || objectIds.equals(""))?null:objectIds.split(",");
		if (oldstr == null) {
			if (newstr != null) {
				addSpecialTaskObject(newstr,parameters,specialTaskVO);
			}
		} else {
			if (newstr == null) {
				parameters.put("objectIds", objectIds);
				specialTaskObjectDao.updateIsdelete(parameters);
			} else {
				/** 获取old中存在而new中不存在的专项任务对象，删除 **/
				String oldObjectsStr = ArrayUtil.arraySubtract2Str(oldstr, newstr);
				if (oldObjectsStr != null && oldObjectsStr != "") {
					parameters.put("objectIds", oldObjectsStr);
					specialTaskObjectDao.updateIsdelete(parameters);
				}
				/** 获取new中存在而old中不存在的专项任务对象，新增 **/
				String[] newObjectsStr = ArrayUtil.arraySubtract(newstr, oldstr);
				if (newObjectsStr != null && newObjectsStr.length > 0) {
					addSpecialTaskObject(newObjectsStr,parameters,specialTaskVO);
				}
			}
		}
	}
	
	private void addSpecialTaskObject(String[] str,Map<String,Object> parmars,SpecialTaskVO specialTaskVO){
		for (int i = 0; i < str.length; i++) {
			parmars.put("objectId", str[i]);
			SpecialTaskObject	specialTaskObject = specialTaskObjectDao.findSpecialTaskObjectByMap(parmars);
			if(specialTaskObject != null){
				specialTaskObject.setIsDelete(Constants.IS_DELETE_FALSE);
				specialTaskObjectDao.updateSpecialTaskObject(specialTaskObject);
			}else{
				SpecialTaskObject stoObj = new SpecialTaskObject();
				String ObjectId = UUID.randomUUID ().toString ();
				stoObj.setSpecialTaskObjectId(ObjectId);
				stoObj.setSpecialTaskId(specialTaskVO.getSpecialTaskId());
				if(specialTaskVO.getObjectType().equals(Constants.OBJ_CLIENTUSER)){
					stoObj.setObjectType(Constants.OBJ_CLIENTUSER);
				}else{
					stoObj.setObjectType(Constants.OBJ_STORE);
				}
				stoObj.setObjectId(str[i]);
				stoObj.setClientId(specialTaskVO.getClientId());
				specialTaskObjectDao.addSpecialTaskObject(stoObj);
			}
		}
	}

}
