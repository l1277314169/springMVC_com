package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuSeries;
import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class SkuSeriesDao extends MyBatisDao {
	
	public SkuSeriesDao() {
		super("SKU_SERIES");
	}
	
	public List<SkuSeries> selectByParams(Map<String, Object> paramMap) {
		return super.getList("selectByParams", paramMap);		
	}
	public List<SkuSeries> selectbyid(Map<String, Object> paramMap) {
		return super.getList("selectByParams", paramMap);		
	}
	public SkuSeries selectbyskuseriesid(Integer id){
	return super.get("selectByPrimaryKey", id);
	}
	
}