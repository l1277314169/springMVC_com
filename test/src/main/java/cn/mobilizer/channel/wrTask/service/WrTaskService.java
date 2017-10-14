package cn.mobilizer.channel.wrTask.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.api.vo.WrTaskVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrTask;

public interface WrTaskService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrTask> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
	
	public int deleteByPrimaryKey(Integer wrTaskId) throws BusinessException;
	
    public int insert(WrTask record) throws BusinessException;

    public int insertSelective(WrTask record) throws BusinessException;

    public WrTask selectByPrimaryKey(Integer wrTaskId) throws BusinessException;

    public int updateByPrimaryKeySelective(WrTask record) throws BusinessException;

    public int updateByPrimaryKey(WrTask record) throws BusinessException;
    /**
     * 查询门店最新数据
     * @param clientId
     * @param clientUserId
     * @param cityId
     * @param timesTamp
     * @return
     * @throws BusinessException
     */
    public WrTaskVO findStoreList(Integer clientId,Integer clientUserId,Integer cityId,String timesTamp)throws BusinessException;
    
    
    public List<WrTask> getWrTaskList(Integer clientId) throws BusinessException;

}
