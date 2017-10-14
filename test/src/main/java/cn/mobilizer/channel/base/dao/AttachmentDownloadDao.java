package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.AttachmentDownload;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class AttachmentDownloadDao extends MyBatisDao {
	public AttachmentDownloadDao() {
		super("ATTACHMENT_DOWNLOAD");
	}
	
	public List<AttachmentDownload> getEntitysByInvalidTime(Map<String, Object> params){
		return super.getList("getEntitysByInvalidTime", params);
	}
	
    public List<AttachmentDownload> getEntitysByCreateidTime(Map<String, Object> params){
    	List<AttachmentDownload> list = super.queryForList("getEntitysBycaeateidTime", params);
		return null != list && list.size() > 0? list : null;
	}
	
	public int updateByPrimaryKeySelective(AttachmentDownload attachmentDownload){
		return super.update("updateByPrimaryKeySelective", attachmentDownload);
	}
	public  void AddAttachmentDownload(AttachmentDownload attachmentDownload){
		super.insert("insertSelective", attachmentDownload);
	}	 
	public Integer getEntitysCountByCreateidTime(Map<String, Object> params){
		return super.get("getEntitysCountBycaeateidTime", params);
	}
	public Integer getEntitysCountByInvalidTime(Map<String, Object> params){
		return super.get("getEntitysCountBycaeateidTime", params);
	}
	public int updateByPrimaryKey(Map<String, Object> params){
		return super.update("updatestatus", params);
	}


}