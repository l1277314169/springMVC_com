package cn.mobilizer.channel.sales.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.DimDay;

@Repository
public class DimDayDao extends MyBatisDao{

	public DimDayDao(){
		super("DIMDAY");
	}
	
	public Map<String, DimDay> getDimDayByYearId(Map<String, Object> params){
		
		return super.queryForMap("getDimDayByYearId",params,"dayId");
	}
	
}
