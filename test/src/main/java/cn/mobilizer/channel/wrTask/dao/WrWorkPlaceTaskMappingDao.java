package cn.mobilizer.channel.wrTask.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrWorkplace;
import cn.mobilizer.channel.wrTask.po.WrWorkplaceTaskMapping;

@Repository
public class WrWorkPlaceTaskMappingDao extends MyBatisDao{
	
	public WrWorkPlaceTaskMappingDao(){
		super("WR_WORKPLACE_TASK_MAPPING");
	}
	
    public int deleteByPrimaryKey(Integer workplaceId){
    	return super.delete("deleteByPrimaryKey", workplaceId);
    }

    public int insert(WrWorkplace record){
    	return super.insert("insert", record);
    }

    public int insertSelective(WrWorkplace record){
    	return super.insert("insertSelective", record);
    }

    public WrWorkplaceTaskMapping selectByPrimaryKey(Integer workplaceId){
    	return super.get("selectByPrimaryKey", workplaceId);
    }

    public int updateByPrimaryKeySelective(WrWorkplace record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WrWorkplace record){
    	return super.update("updateByPrimaryKey", record);
    }
}