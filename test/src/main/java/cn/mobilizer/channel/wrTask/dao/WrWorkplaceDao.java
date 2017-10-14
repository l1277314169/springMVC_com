package cn.mobilizer.channel.wrTask.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrWorkplace;

@Repository
public class WrWorkplaceDao extends MyBatisDao{
	
	public WrWorkplaceDao(){
		super("WR_WORKPLACE");
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

    public WrWorkplace selectByPrimaryKey(Integer workplaceId){
    	return super.get("selectByPrimaryKey",workplaceId);
    }

    public int updateByPrimaryKeySelective(WrWorkplace record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WrWorkplace record){
    	return super.update("updateByPrimaryKey", record);
    }
}