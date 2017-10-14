package cn.mobilizer.channel.wrTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrTask;
import cn.mobilizer.channel.wrTask.po.WrTaskData;
import cn.mobilizer.channel.wrTask.vo.WrTaskFinishCount;

public interface WrTaskDataService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrTaskData> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
    
    public List<WrTaskFinishCount> getWrTaskDataByUserId(Map<String,Object> param) throws BusinessException;
    
    public Integer getWrTaskDataCountByUserId(Map<String,Object> param) throws BusinessException;
    
    public WrTaskData selectByPrimaryKey(String wrTaskDataId) throws BusinessException;

}
