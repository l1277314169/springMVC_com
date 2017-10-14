package cn.mobilizer.channel.specialTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskDetailData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;
@Repository
public class SpecialTaskParameterDao extends MyBatisDao{
	public SpecialTaskParameterDao(){
		super("SPECIAL_TASK_PARAMETER");
	}
	public int insertSpecialTaskParameter(SpecialTaskParameter specialTaskParameter){
		return super.insert("insertSelective", specialTaskParameter);
	}
	
	public int updateSpecialTaskParameter(SpecialTaskParameter specialTaskParameter){ 
		return super.update("updateByPrimaryKeySelective", specialTaskParameter);
	}
	
	public List<SpecialTaskParameter> selectSpecialTaskParameterBySpecialTaskId(Map<String,Object> parameter){
		return super.queryForList("selectSpecialTaskParameterBySpecialTaskId", parameter);
	}
	public List<SpecialTaskParameter> findClientUserDataByParameter(Map<String,Object> parameter){
		return super.queryForList("findSpecialTaskDetailDataByParameter", parameter);
	}
	
	public List<SpecialTaskParameter> findStoreDateByParameter(Map<String,Object> parameter){
		return super.queryForList("findStoreDateByParameter", parameter);
	}
	
	public int updateIsdelete(Map<String,Object> parameter){
		return super.update("updateIsdelete", parameter);
	}
	
	public SpecialTaskParameter findSpecialTaskParameterById(String specialTaskParameterId){
		return super.get("selectByPrimaryKey", specialTaskParameterId);
	}
	
	public List<SpecialTaskParameter> findSpecialTaskParameterByNotExecution(Map<String,Object> parameter){
		return super.queryForList("findSpecialTaskParameterByNotExecution", parameter); 
	}
}
