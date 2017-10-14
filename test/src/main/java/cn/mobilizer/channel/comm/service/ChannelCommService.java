package cn.mobilizer.channel.comm.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.comm.vo.MonthVo;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;


public interface ChannelCommService {
	/**
	 * 通过某部门的id,获取该部门下所有的部门包括自己
	 * @param id
	 * @return 返回符合条件的所有部门-是以","符号隔开的字符串
	 */
	public String getSubStructrueIds(Integer id);
	
	/**
	 * 通过某部门的id,获取该部门的所有上级部门
	 * @param id
	 * @return 返回符合条件的所有部门-是以","符号隔开的字符串
	 */
	public String getParentStructrueIds(Integer id);
	
	/**
	 * 获取某个用户的所有下级人员,包括自己
	 * @param clientUserIds
	 * @return
	 */
	public String getSubordinates(String clientUserIds);
	
	public String getSubStructrueIds(String id);
	
	/**
	 * 获取某个用户的所有下级人员,不包括自己
	 * @param clientUserIds
	 * @return
	 */
	public String getSubordinatesWithOutSelf(String clientUserId);
	
	/**
	 * 
	 * @param tablename 表名
	 * @param pageTpye 页面类型
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientBusinessDefinition> getModBusinessDefinition(String tablename, Integer pageTpye, Integer clientId) throws BusinessException;

	
	/**
	 * 根据不同的控件类型返回option文本
	 * @param controlType
	 * @param optionName
	 * @param value
	 * @param optionMap
	 * @param clientId
	 * @return
	 */
	public String getPoraControlValue(Integer controlType, String optionName,String value,Map<String, Map<String, Map<String, String>>> optionMap,Integer clientId);


	/**
	 * 初始化月份控件
	 * @return
	 */
	public List<MonthVo> initializeMonthControl() throws BusinessException;
	
	/**
	 * 经销商 部门特殊查询-
	 * @param id 部门id
	 * @return 该部门的所有下一级和其直属的上一级
	 */
	public String getDistributorStructrueIds(Integer id);
	
	/**
	 * 查找指定clientUserId的直属下级
	 * @param clientUserId
	 * @return
	 */
	public String findUserIdsByParentId(Integer clientUserId);
}
