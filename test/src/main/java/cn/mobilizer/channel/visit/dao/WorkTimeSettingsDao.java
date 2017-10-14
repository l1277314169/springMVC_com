package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.WorkTimeSettings;
/**
 * @author Nany
 * 2015年1月16日上午10:40:14
 */
@Repository
public class WorkTimeSettingsDao extends MyBatisDao {
	public WorkTimeSettingsDao(){
		super("WORK_TIME_SETTINGS");
	}
	public Integer queryWorKTimeSettingsCount(Map<String, Object> parameters) {
		return super.get("queryTotalCount", parameters);
	}
	public List<WorkTimeSettings> queryWorkTimeSettingsList(
			Map<String, Object> parameters) {
		return super.getList("findListByParams", parameters);
	}
	public WorkTimeSettings getWorkTimeSettings(String settingId) {
		return super.get("findObjectBySettingId", settingId);
	}
	public void updateWorkTimesSetting(WorkTimeSettings workTimeSettings) {
		super.update("updateByPrimaryKeySelective", workTimeSettings);
	}
	
	public int insertByList(List<WorkTimeSettings> list){
		return super.insert("insertByList", list);
	}
	
	public List<WorkTimeSettings> findWokSettingByDate(Map<String, Object> parameters){
		return super.queryForList("findWokSettingByDate", parameters);
	}

}
