package cn.mobilizer.channel.apple.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.apple.vo.AppleFilter;
import cn.mobilizer.channel.apple.vo.AppleOverViewVo;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class AppleDao extends MyBatisDao{

	public AppleDao(){
		super("APPLE");
	}
	
	public List<AppleOverViewVo> loadOverView(AppleFilter filter){
		
		return super.queryForList("loadOverView",filter);
	}
	
	
	public List<?> loadAppleACSC(AppleFilter filter){
		List<?> lists = super.getList("loadAppleACSC",filter);
		return lists;
	}
	
}
