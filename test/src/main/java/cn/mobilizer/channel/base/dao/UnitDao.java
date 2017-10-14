package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Unit;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class UnitDao extends MyBatisDao {

	public UnitDao() {
		super("UNIT");
	}

	public List<Unit> queryAll(Map<String, Object> params){
		return super.queryForList("select_by_param",params);
	}
	public List<Unit> getUnitList(Map<String, Object> paramMap) {
		return super.getList("select_by_param", paramMap); 
	}

	public Unit getUnitByUnitId(Integer unitId) {
		return super.get("selectByPrimaryKey", unitId);
	}
}
