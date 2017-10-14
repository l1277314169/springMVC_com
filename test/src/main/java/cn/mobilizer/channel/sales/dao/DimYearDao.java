package cn.mobilizer.channel.sales.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.DimYear;

@Repository
public class DimYearDao extends MyBatisDao {

	public DimYearDao(){
		super("DIMYEAR");
	}
	
	public List<DimYear> getYear(Map<String, Object> params){
		
		return super.queryForList("getYear",params);
	}
	
}
