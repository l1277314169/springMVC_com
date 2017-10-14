package cn.mobilizer.channel.base.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface ImportStoreService {
	
	public Map<String,Object> validataColgateStore(MultipartFile file,Integer clientId);
	
	public Map<String,Object> validataAppleStore(MultipartFile file,Integer clientId);
	
	public Map<String,Object> validataBwybStore(MultipartFile file,Integer clientId);
	
	public Map<String,Object> validataWorkStore(MultipartFile file,Integer clientId);

}
