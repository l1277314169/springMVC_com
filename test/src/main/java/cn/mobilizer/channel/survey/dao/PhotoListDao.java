package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.PhotoList;

@Repository
public class PhotoListDao extends MyBatisDao {
	
	public PhotoListDao(){
		super("PHOTO_LIST");
	}
	
    public int deleteByPrimaryKey(Integer photoId){
    	return super.delete("deleteByPrimaryKey", photoId);
    }

    public int insert(PhotoList record){
    	return super.insert("insert", record);
    }

    public int insertSelective(PhotoList record){
    	return super.insert("insertSelective", record);
    }

    public PhotoList selectByPrimaryKey(Integer photoId){
    	return super.get("selectByPrimaryKey", photoId);
    }

    public int updateByPrimaryKeySelective(PhotoList record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(PhotoList record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public List<PhotoList> selectEntityByTypeAndRelationId(Map<String,Object> params){
    	return super.getList("selectEntityByTypeAndRelationId", params);
    }
}