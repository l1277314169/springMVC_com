package cn.mobilizer.channel.ctTask.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTaskPopMapping;

@Repository
public class CtTaskPopMappingDao extends MyBatisDao{
	
	public CtTaskPopMappingDao() {
		super("CT_TASK_POP_MAPPING");
	}
	
    public int deleteByPrimaryKey(String mappingId) {
		return super.delete("deleteByPrimaryKey", mappingId);
	}

    public int insert(CtTaskPopMapping record){
    	return super.insert("insert", record);
    }

    public int insertSelective(CtTaskPopMapping record){
    	return super.insert("insertSelective", record);
    }

    public CtTaskPopMapping selectByPrimaryKey(String mappingId){
    	return super.get("selectByPrimaryKey", mappingId);
    }

    public int updateByPrimaryKey(CtTaskPopMapping record){
    	return super.update("updateByPrimaryKey", record);
    }
}