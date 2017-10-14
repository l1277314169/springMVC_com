package cn.mobilizer.channel.wrTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrTaskParameter;

public interface WrTaskParameterService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrTaskParameter> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
}
