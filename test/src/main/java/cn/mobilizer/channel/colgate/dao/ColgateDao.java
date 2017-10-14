package cn.mobilizer.channel.colgate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.colgate.vo.FilterVo;
import cn.mobilizer.channel.colgate.vo.OverViewVo;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class ColgateDao extends MyBatisDao{

	public ColgateDao(){
		super("COLGATE");
	}
	
	public List<OverViewVo> loadOverView(FilterVo filterVo){
		
		return super.queryForList("loadOverView",filterVo);
	}
	
	public List<?> loadColgateDistribution(FilterVo filterVo){
		List<?> lists = super.queryForList("loadColgateDistribution",filterVo);
		return lists;
	}
	
	public List<?> loadColgateShareOfShelf(FilterVo filterVo){
		List<?> lists = super.queryForList("loadColgateShareOfShelf",filterVo);
		return lists;
	}
	
	public List<?> loadColgatePrice(FilterVo filterVo){
		List<?> lists = super.queryForList("loadColgatePrice",filterVo);
		return lists;
	}
	
	public List<?> loadColgateSkuOnShelf(FilterVo filterVo){
		List<?> lists = super.queryForList("loadColgateSkuOnShelf",filterVo);
		return lists;
	}
	
}
