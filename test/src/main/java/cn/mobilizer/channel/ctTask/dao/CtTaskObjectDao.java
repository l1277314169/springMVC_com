package cn.mobilizer.channel.ctTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTaskObject;

@Repository
public class CtTaskObjectDao extends MyBatisDao{
	public CtTaskObjectDao() {
		super("CT_TASK_OBJECT");
	}
	
	public List<CtTaskObject> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
	public CtTaskObject selectByPrimaryKey(String id){
		return super.get("selectByPrimaryKey",id);
	}
	
	public List<CtTaskObject> selectTaskObjectBySku(Map<String, Object> parames){
		return super.queryForList("selectTaskObjectBySku", parames);
	}
	
	public Integer selectTaskObjectCountBySku(Map<String, Object> parames){
		return super.get("selectTaskObjectCountBySku", parames);
	}
	
}