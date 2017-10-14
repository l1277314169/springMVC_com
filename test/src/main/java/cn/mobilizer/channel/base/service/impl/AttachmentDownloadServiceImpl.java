package cn.mobilizer.channel.base.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.AttachmentDownloadDao;
import cn.mobilizer.channel.base.po.AttachmentDownload;
import cn.mobilizer.channel.base.service.AttachmentDownloadService;
import cn.mobilizer.channel.util.PropertiesHelper;
import cn.mobilizer.channel.util.email.SimpleMailSender;
 

@Service
public class AttachmentDownloadServiceImpl implements AttachmentDownloadService {
	
	@Autowired
	private AttachmentDownloadDao attachmentDownloadDao;

	@Override
	public List<AttachmentDownload> getEntitysByInvalidTime(Map<String, Object> params) {
		return attachmentDownloadDao.getEntitysByInvalidTime(params);
	}

	@Override
	public Integer getEntitysCountByInvalidTime(Map<String, Object> params) {
		return attachmentDownloadDao.getEntitysCountByInvalidTime(params);
	}

	@Override
	public void removeImageZipList(List<AttachmentDownload> attachmentDownloads) {
		String ftp_folder =  PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
		for(AttachmentDownload attachmentDownload : attachmentDownloads){
			File file = new File(ftp_folder+File.separator+attachmentDownload.getUrl());
			if(file.exists()){
				file.delete();
				attachmentDownload.setStatus((byte)2);
				attachmentDownloadDao.updateByPrimaryKeySelective(attachmentDownload);
			}
		}
	}

	@Override
	public void AddAttachmentDownload(AttachmentDownload attachmentDownload) {
		attachmentDownloadDao.AddAttachmentDownload(attachmentDownload);
		
	}

	
	@Override
	public void sendImageZipList(String fileName,Integer clientId,String email,AttachmentDownload attachmentDownload,Map<String,Object> params1) {
		String[] addrs = new String[1];
		addrs[0] = email;
		String channelPlus_domain =  PropertiesHelper.getInstance().getProperty(PropertiesHelper.CHANNELPLUS_DOMAIN);
      	String emailurl = channelPlus_domain+"/uploadfiles/"+clientId+"/download/"+fileName+".zip";
		try {
			SimpleMailSender.sendHtmlMail(addrs, "问卷图片下载", "<a href="+emailurl+">点击此链接下载图片zip包</a>(有效期2天)");
			
			attachmentDownloadDao.updateByPrimaryKey(params1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer getEntitysCountByCreateidTime(Map<String, Object> params) {
		return attachmentDownloadDao.getEntitysCountByCreateidTime(params);
 
	}
	
	@Override
	public List<AttachmentDownload> getEntitysByCreateidTime(Map<String, Object> params) {
		return attachmentDownloadDao.getEntitysByCreateidTime(params);
		
	}


	
}
