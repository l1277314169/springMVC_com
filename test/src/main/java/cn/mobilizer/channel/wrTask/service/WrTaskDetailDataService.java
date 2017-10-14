package cn.mobilizer.channel.wrTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrTaskDetailData;

public interface WrTaskDetailDataService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrTaskDetailData> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
}
