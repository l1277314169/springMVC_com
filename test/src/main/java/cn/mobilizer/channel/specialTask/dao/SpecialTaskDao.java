package cn.mobilizer.channel.specialTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.specialTask.po.SpecialTask;
import cn.mobilizer.channel.specialTask.vo.SpecialTaskVO;
@Repository
public class SpecialTaskDao extends MyBatisDao {
	public SpecialTaskDao(){
		super("SPECIAL_TASK");
	}
	
	public String insertSpecialTask(SpecialTask specialTask){
		super.insert("insertSelective", specialTask);
		return specialTask.getSpecialTaskId();
	}
	
	public int updateSpecialTask(SpecialTask specialTask){
		return super.update("updateByPrimaryKeySelective", specialTask);
	}
	
	public List<SpecialTaskVO> selectSpecialTask(Map<String,Object> parameter){
		return super.queryForList("selectSpecialTask", parameter);
	}
	
	public SpecialTaskVO selectSpecialTaskVOByParameter(Map<String,Object> parameter){
		return super.get("selectSpecialTaskVOByParameter", parameter);
	}
}
