package cn.mobilizer.channel.ctTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTaskParameter;

@Repository
public class CtTaskParameterDao extends MyBatisDao{
	public CtTaskParameterDao() {
		super("CT_TASK_PARAMETER");
	}
	
	public List<CtTaskParameter> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
	public int insert(CtTaskParameter ctTaskParameter){
		super.insert ("insert", ctTaskParameter);
		return ctTaskParameter.getCtTaskParameterId();
	} 
	
	public CtTaskParameter selectByPrimaryKey(Integer ctTaskParameterId){
		return super.get("selectByPrimaryKey", ctTaskParameterId);
	}
}