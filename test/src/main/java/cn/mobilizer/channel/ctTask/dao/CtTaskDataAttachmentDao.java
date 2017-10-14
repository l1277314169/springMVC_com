package cn.mobilizer.channel.ctTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTaskDataAttachment;

@Repository
public class CtTaskDataAttachmentDao extends MyBatisDao{
	
	public CtTaskDataAttachmentDao() {
		super("CT_TASK_DATA_ATTACHMENT");
	}
	
    public int deleteByPrimaryKey(String attachmentId){
    	return super.delete("deleteByPrimaryKey", attachmentId);
    }

    public int insert(CtTaskDataAttachment record){
    	return super.insert("insert", record);
    }

    public int insertSelective(CtTaskDataAttachment record){
    	return super.insert("insertSelective", record);
    }

    public CtTaskDataAttachment selectByPrimaryKey(String attachmentId){
    	return super.get("selectByPrimaryKey", attachmentId);
    }

    public int updateByPrimaryKeySelective(CtTaskDataAttachment record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(CtTaskDataAttachment record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public List<CtTaskDataAttachment> getEntityByCtTaskDataId(Map<String,Object> params){
    	return super.queryForList("getEntityByCtTaskDataId", params);
    }
    
    public String getAttachmentsByCtTaskDataId(Map<String,Object> params){
    	return super.get("getAttachmentsByCtTaskDataId", params);
    }
    
    public int deleteAttachmentsByImageNames(Map<String,Object> params){
    	return super.update("deleteAttachmentsByImageNames",params);
    }
    
    public int updateDelByCtTaskDataId(Map<String,Object> params){
    	return super.update("updateDelByCtTaskDataId",params);
    }
}