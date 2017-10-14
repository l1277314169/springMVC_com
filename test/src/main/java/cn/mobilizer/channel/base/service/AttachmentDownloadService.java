package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

 

import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.po.AttachmentDownload;

public interface AttachmentDownloadService {

	public List<AttachmentDownload> getEntitysByInvalidTime(Map<String, Object> params);

	public Integer getEntitysCountByInvalidTime(Map<String, Object> params);
	
	public void removeImageZipList(List<AttachmentDownload> attachmentDownloads);

	public void AddAttachmentDownload(AttachmentDownload attachmentDownload);
	
	public void sendImageZipList(String fileName,Integer clientId,String email,AttachmentDownload attachmentDownload,Map<String,Object> params);

	public Integer getEntitysCountByCreateidTime(Map<String, Object> params);
	
	public List<AttachmentDownload> getEntitysByCreateidTime(Map<String, Object> params);
}
