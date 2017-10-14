package cn.mobilizer.channel.specialTask.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;
@Repository
public class SpecialTaskObjectDao extends MyBatisDao{
	public SpecialTaskObjectDao(){
		super("SPECIAL_TASK_OBJECT");
	}
	
	public int addSpecialTaskObject(SpecialTaskObject specialTaskObject){ 
		return super.insert("insertSelective", specialTaskObject);
	}
	
	public int updateSpecialTaskObject(SpecialTaskObject specialTaskObject){
		return super.update("updateByPrimaryKeySelective", specialTaskObject);
	}
	
	public SpecialTaskObject findSpecialTaskObjectByMap(Map<String,Object> params){
		return super.get("findSpecialTaskObjectByMap", params);
	}
	
	public int updateIsdelete(Map<String,Object> params){
		return super.update("updateIsdelete", params);
	}
	
	public String findOldSpecialTaskObject(Map<String,Object> params){
		return super.get("findOldSpecialTaskObject", params);
	}
}
