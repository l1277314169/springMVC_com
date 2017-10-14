package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class SkuTypeDao extends MyBatisDao {
	
	public SkuTypeDao() {
		super("SKU_TYPE");
	}
		
	public List<SkuType> selectByParams(Map<String, Object> paramMap) {
		return super.getList("selectByParams", paramMap);		
	}
	
	public List<SkuType> selectById(Map<String, Object> paramMap) {
		return super.getList("selectByParams", paramMap);		
	}
	public SkuType selectByskutypeId(Integer skutypeid) {
		return super.get("selectByPrimaryKey", skutypeid);	
	}

	
	
}