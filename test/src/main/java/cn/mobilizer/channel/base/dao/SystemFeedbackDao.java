package cn.mobilizer.channel.base.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SystemFeedback;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class SystemFeedbackDao extends MyBatisDao{
	
	public SystemFeedbackDao() {
		super("SYSTEM_FEEDBACK");
	}
    int deleteByPrimaryKey(String feedbackId){
    	return 0 ;
    }

    public int insert(SystemFeedback record){
    	return super.insert("insertSelective", record);
    }

    int insertSelective(SystemFeedback record){
    	return 0 ;
    }

    SystemFeedback selectByPrimaryKey(String feedbackId){
    	return null ;
    }

    int updateByPrimaryKeySelective(SystemFeedback record){
    	return 0 ;
    }

    int updateByPrimaryKey(SystemFeedback record){
    	return 0 ;
    }
}