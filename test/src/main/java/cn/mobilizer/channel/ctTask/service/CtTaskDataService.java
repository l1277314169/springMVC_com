package cn.mobilizer.channel.ctTask.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.chart.BeginRecord;

import cn.mobilizer.channel.ctTask.po.CtTaskData;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;

public interface CtTaskDataService{
	
	public List<CtTaskData> selectByParams(Map<String,Object> param);
	
	public Integer selectByParamCount(Map<String,Object> param);
	
	public String insert(CtTaskData ctTaskData);
	
	public void saveCtTaskData(CtTaskData ctTaskData);	
	
	public CtTaskData getCtTaskDataById(String id);
	
	/**根据门店与任务查询数据
	 * @param ctTaskDataSerchVo
	 * @return
	 */
	public CtTaskData selectDataByPopIdAndTaskId(CtTaskDataSerchVo ctTaskDataSerchVo);
	
	public void updateCtTaskData(CtTaskData ctTaskData);
	
	/**
	 * 删除周期任务数据
	 * @param ctTaskDataId
	 */
	public int deleteCtTaskData(String ctTaskDataId);
	
	/**
	 * 高露洁需求，新增修改时判断访问时间的月份是否已经有数据
	 * @param ctTaskDataSerchVo
	 * @return
	 */
	public CtTaskData getCtTaskDataByStartTime(Integer clientId,Integer ctTaskId,String popId,Date beginDate,Date endDate);

	/**
	 * 高露洁周期任务添加以往周期没有填报过的功能需求
	 * @param ctTaskData
	 * @return
	 */
	public void saveColgateCtTaskData(CtTaskData ctTaskData,Date visitDate);
}
