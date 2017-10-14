package cn.mobilizer.channel.log.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.log.po.Sms;
@Repository
public class SmsDao extends MyBatisDao{
	public SmsDao(){
		super("SMS");
	}
	public int insert(Map<String,Object> param){
		return super.insert("insertSms", param);
	}
	
	public List<Sms> findListByParams(Map<String,Object> param){
		return super.queryForList("findListByParams", param);
	}
	
	public int updateByPrimaryKeySelective(Sms sms){
		return super.insert("updateByPrimaryKeySelective", sms);
	}
	
	public Integer findListByParamsCount(Map<String,Object> param){
		return super.get("findListByParamsCount", param);
	}
	
}
