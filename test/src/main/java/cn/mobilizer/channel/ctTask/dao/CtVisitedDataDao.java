package cn.mobilizer.channel.ctTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtVisitedData;

@Repository
public class CtVisitedDataDao extends MyBatisDao{
	public CtVisitedDataDao() {
		super("CT_VISITED_DATA");
	}
	
	public List<CtVisitedData> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
}