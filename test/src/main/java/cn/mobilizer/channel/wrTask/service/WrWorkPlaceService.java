package cn.mobilizer.channel.wrTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrWorkplace;

public interface WrWorkPlaceService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrWorkplace> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
	
	public int deleteByPrimaryKey(Integer workplaceId) throws BusinessException;
	
    public int insert(WrWorkplace record) throws BusinessException;

    public int insertSelective(WrWorkplace record) throws BusinessException;

    public WrWorkplace selectByPrimaryKey(Integer workplaceId) throws BusinessException;

    public int updateByPrimaryKeySelective(WrWorkplace record) throws BusinessException;

    public int updateByPrimaryKey(WrWorkplace record) throws BusinessException;

}
