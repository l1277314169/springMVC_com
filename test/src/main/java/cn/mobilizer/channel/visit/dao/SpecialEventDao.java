package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.SpecialEvent;

@Repository
public class SpecialEventDao extends MyBatisDao{
  public SpecialEventDao(){
	  super("SPECIAL_EVENT");
  }
  
  public Integer querySpecialEventCount(Map<String, Object> parameters) {
		return super.get("queryTotalCount", parameters);
	}
  public List<Map<String, Object>> findSpecialEventList(Map<String, Object> pageParam){
	  return super.queryForList("findListByParams", pageParam);
  }
  
}
