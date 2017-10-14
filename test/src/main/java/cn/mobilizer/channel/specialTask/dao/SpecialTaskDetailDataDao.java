package cn.mobilizer.channel.specialTask.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskDetailData;
@Repository
public class SpecialTaskDetailDataDao extends MyBatisDao{
	public SpecialTaskDetailDataDao(){
		super("SPECIAL_TASK_DETAIL_DATA");
	}
	
	
}
