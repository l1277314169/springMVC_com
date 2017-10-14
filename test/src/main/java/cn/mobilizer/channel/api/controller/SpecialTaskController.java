package cn.mobilizer.channel.api.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.api.vo.ResultEntityWithObject;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.specialTask.po.SpecialTaskData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskDetailData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;
import cn.mobilizer.channel.specialTask.service.SpecialTaskDataService;
import cn.mobilizer.channel.specialTask.service.SpecialTaskDetailDataService;
import cn.mobilizer.channel.specialTask.service.SpecialTaskService;
import cn.mobilizer.channel.specialTask.vo.SpecialTaskVO;
@Controller
@RequestMapping(value = "/api/specialTask")
public class SpecialTaskController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7439728792295143832L;
	@Autowired
	private SpecialTaskService specialTaskService;
	@Autowired
	private SpecialTaskDataService specialTaskDataService;
	@Autowired
	private SpecialTaskDetailDataService specialTaskDetailDataService;
	
	/**
	 * 专项任务列表
	 * @param clientId <p>客户Id</p>
	 * @param clientUserId <p>用户Id</p>
	 * @return
	 */
	@RequestMapping(value = "/query",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithObject<List<SpecialTaskVO>> querySpecialTaskList(Integer clientId,Integer clientUserId){
		List<SpecialTaskVO> ls = specialTaskService.specialTaskList(clientId,clientUserId);
		ResultEntityWithObject<List<SpecialTaskVO>> resultEntityWithObject= new ResultEntityWithObject<List<SpecialTaskVO>>();
		resultEntityWithObject.setDataInfo(ls);
		return resultEntityWithObject;
	}
	
	/**
	 * 专项任务的新增
	 * @param specialTaskVO
	 * @return
	 */
	@RequestMapping(value = "/addSpecialTask",produces = "application/json; charset=UTF-8" )
	public @ResponseBody ResultEntityWithObject<Boolean> addSpecialTask(@RequestBody SpecialTaskVO specialTaskVO){
		String SpecialTaskId =	specialTaskService.addSpecialTask(specialTaskVO);
		if(SpecialTaskId != null && !SpecialTaskId.equals("")){
			return new ResultEntityWithObject<Boolean>(true);
		}else{
			return new ResultEntityWithObject<Boolean>(false);
		}
	}
	
	/**
	 * 展示专项任务编辑数据
	 * @param clientId
	 * @param specialTaskId
	 * @return
	 */
	@RequestMapping(value = "/showEditBySpecialTaskId",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithObject<SpecialTaskVO> showEditBySpecialTask(Integer clientId,String specialTaskId){
		SpecialTaskVO specialTaskVO = specialTaskService.findSpecialTaskByParmas(clientId,specialTaskId);
		ResultEntityWithObject<SpecialTaskVO> resultEntityWithObject= new ResultEntityWithObject<SpecialTaskVO>();
		resultEntityWithObject.setDataInfo(specialTaskVO);
		return resultEntityWithObject;
	}
	
	/**
	 * 更新专项任务数据
	 * @param specialTaskVO
	 * @return
	 */
	@RequestMapping(value = "/updateSpecialTask",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithObject<Boolean> updateSpecialTask(@RequestBody SpecialTaskVO specialTaskVO){
		int rows = specialTaskService.updateSpecialTask(specialTaskVO);
		if(rows > 0){
			return new ResultEntityWithObject<Boolean>(true);
		}else{
			return new ResultEntityWithObject<Boolean>(false);
		}
	}
	
	/**
	 * 专项任务执行对象列表
	 * @param clientId
	 * @param clientUserId
	 * @param specialTaskId
	 * @param objectType(必填)
	 * @return
	 */
	@RequestMapping(value = "/showSpecialTaskObject",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithObject<List<SpecialTaskObject>> showSpecialTaskObject(Integer clientId, String specialTaskId, Byte objectType,Integer clientPositionId){
		List<SpecialTaskObject> specialTaskObject = specialTaskDataService.findSpecialTaskDataObje(clientId, specialTaskId, objectType,clientPositionId);
		ResultEntityWithObject<List<SpecialTaskObject>> resultEntityWithObject= new ResultEntityWithObject<List<SpecialTaskObject>>();
		resultEntityWithObject.setDataInfo(specialTaskObject);
		return resultEntityWithObject;
	}
	
	/**
	 * 专项任务执行对象的详细数据 用于主管查看对象执行的详细
	 * @param clientId
	 * @param specialTaskId
	 * @param objectType
	 * @param popId
	 * @param clientPositionId
	 * @return
	 */
	@RequestMapping(value = "/showSpecialTaskDetailData",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithObject<List<SpecialTaskParameter>> showSpecialTaskDetailData(Integer clientId,String specialTaskId, Byte objectType, String popId, Integer clientPositionId,Integer clientUserId,String specialTaskDataId){
		List<SpecialTaskParameter> seletDetailDataList= specialTaskDetailDataService.findDetailDataByObjId(clientId, specialTaskId, objectType, popId, clientPositionId,clientUserId,specialTaskDataId);
		ResultEntityWithObject<List<SpecialTaskParameter>> resultEntityWithObject= new ResultEntityWithObject<List<SpecialTaskParameter>>();
		resultEntityWithObject.setDataInfo(seletDetailDataList);
		return resultEntityWithObject;
	}
	
	
}
