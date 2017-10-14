package cn.mobilizer.channel.visit.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.vo.CallPlanningQuery;
import cn.mobilizer.channel.visit.vo.CallPlanningVO;

public interface CallPlanningService {
	/**
	 * 
	 * @param callPlanning
	 * @return
	 */
	public int addCallPlanning(CallPlanning callPlanning)throws BusinessException; 
	/**
	 * 
	 * @param pageParam
	 * @return
	 */
	public List<CallPlanning> getCallPlanning(Map<String, Object> pageParam);
	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean saveAll(List<CallPlanning> list);
	
	/**
	 * 查询某人某天需要拜访的对象，对象之间用union关联
	 * @param clientUserId
	 * @param clientId
	 * @param visitDate
	 * @return
	 * @throws BusinessException
	 */
	public List<CallPlanning> findListByParams4union(Integer clientUserId, Integer clientId, String visitDate, Byte taskType) throws BusinessException;
	/**执行计划管理 
	 * @author Nany
	 * 2015年1月8日上午10:33:41
	 * @param parameters
	 * @return
	 */
	public int queryCallPlanningCount(Map<String, Object> parameters) throws BusinessException;
	public CallPlanning getCallPlanningById(String planningId)throws BusinessException;
	public void updateCallPlanning(Map<String, Object> parameters)throws BusinessException;
	public List<CallPlanning> getCallPlanningByClientUserId(Integer clientUserId,String startDate, String endDate)throws BusinessException;
	public List<CallPlanning> getCallPlanningByClientUserIdAndCallDate(Integer clientUserId, String callDate,String afterDay)throws BusinessException;
	public List<CallPlanning> getCallPlanningByClientUserIdAndStoreId(
			Map<String, Object> parameters);
	
	public int updateCallStatus(Map<String, Object> parameters)throws BusinessException;
	
	/**
	 * 删除拜访任务对应门店
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public int updateisdelte(Map<String, Object> parameters)throws BusinessException;
	
	public List<CallPlanning> getNotCallPlanning(Map<String, Object> parameters)throws BusinessException;
	
	public Integer deleteCallPlanning(Integer clientId,String callDate,Integer clientUserId,Integer visitType)throws BusinessException;
	public Integer deleteCallPlanningByDay(Map<String, Object> parameters)throws BusinessException;
		
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public Object addCallPlanning()throws BusinessException;
	/**
	 * 查找有没有执行计划的数据
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<Object> findCallPlanning(Map<String,Object> params)throws BusinessException;
	/**
	 * 
	 * @param callPlanning
	 * @return
	 * @throws BusinessException
	 */
	public int updateCallPlanning(CallPlanning callPlanning )throws BusinessException;
	
	public int addCallPlanning(Map<String,Object> params)throws BusinessException;
	
	public CallPlanning findCallPlanningIsdelete(Map<String,Object> params)throws BusinessException;
	
	public List<CallPlanning> exportCallPlanning(Map<String, Object> parameters)throws BusinessException;
	
	/**
	 * 修改状态
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public Integer  updateCallPlanningStatus(Integer clientUserId,Integer clientId)throws BusinessException;
	
	/**
	 * 人员门店关系解除，未开始的计划删除
	 * @param clientUserId
	 * @param clientId
	 * @param storeId
	 * @param currentTime
	 * @return
	 * @throws BusinessException
	 */
	public int isdeleteCallplanning(Integer clientUserId,Integer clientId,String storeIds)throws BusinessException;
	/**
	 * 人员门店关系解除，未开始的计划删除方法重载
	 * @param clientUserIds
	 * @param clientId
	 * @param storeId
	 * @param currentTime
	 * @return
	 * @throws BusinessException
	 */
	public int isdeleteCallplanning(String clientUserIds,Integer clientId,String storeId)throws BusinessException;
	
	/**
	 * ctbat计划导入
	 * @param file
	 * @param req
	 * @param res
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public ResultMessage addPlannImport(MultipartFile file, HttpServletRequest req, HttpServletResponse res,Integer clientId)throws BusinessException;
		
}
