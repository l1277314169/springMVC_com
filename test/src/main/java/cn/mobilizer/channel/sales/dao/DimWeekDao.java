package cn.mobilizer.channel.sales.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.DimWeek;

@Repository
public class DimWeekDao extends MyBatisDao{

	public DimWeekDao(){
		super("DIMWEEK");
	}
	
	public void updateByPrimaryKeySelective(DimWeek dimWeek){
		super.update("updateByPrimaryKeySelective", dimWeek);
	}
	
}
