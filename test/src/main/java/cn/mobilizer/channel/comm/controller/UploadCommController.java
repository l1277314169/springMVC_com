package cn.mobilizer.channel.comm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.image.vo.FileUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

@Controller
@RequestMapping("/uploadComm")
public class UploadCommController extends BaseActionSupport{

	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());
	
	private static String ftpFolder = null;
	private static String imgFolder = null;
	static{
		ftpFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
		imgFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
	}
	
	/**
	 * 文件上传公共方法
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/upload",produces="application/json")
	@ResponseBody
	public Object upload(HttpServletRequest request,HttpServletResponse response) throws Exception {
		StringBuffer messages = new StringBuffer();
		List<String> files = new ArrayList<String>();
		
		CommonsMultipartResolver multipartResolver = null;
		MultipartHttpServletRequest multiRequest = null;
		
		try {
			log.debug("upload："+request.getSession().getServletContext());
			multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			multiRequest = (MultipartHttpServletRequest) request;
			if (multipartResolver.isMultipart(request)) {
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String myFileName = file.getOriginalFilename();
						if(StringUtil.isEmptyString(myFileName)){
							continue;
						}
						
						String suffix = getSuffix(myFileName);
						String newName = getFileNewName(myFileName,suffix);
						String dir = getDir();
						String filePath = dir+newName; //重命名文件
						File localFile = new File(filePath);
						FileUtils.createDirectiory(filePath);
						file.transferTo(localFile);
						
						files.add(filePath);
						messages.append(newName).append(",");
					}
				}
			}
			
			//压缩上传文
			for (String str : files) {
				FileUtils.copyAndCutImageOne(str,imgFolder,true);
			}
			if(messages.toString().endsWith(",")){
				messages.deleteCharAt(messages.length()-1);
			}
			return new ResultMessage(ResultMessage.SUCCESS,messages.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPLOAD_FAIL_RESULT;
		}finally{
			//清除零时文件
			if(null!=multiRequest){
				multipartResolver.cleanupMultipart(multiRequest);
			}
		}
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName
	 * @return
	 */
	public String getSuffix(String fileName){
		int index = fileName.lastIndexOf(".");
		String suffix = fileName.substring(index+1);
		return suffix;
	}
	
	/**
	 * 获取文件路径
	 * @return
	 */
	public String getDir(){
		StringBuffer dir = new StringBuffer();
		dir.append(ftpFolder).append(File.separator).append(super.getClientId()).append(File.separator).append("web").append(File.separator);
		return dir.toString();
	}
	
	
	/**
	 * 获取文件路径
	 * @return
	 */
	public String getAudioDir(){
		StringBuffer dir = new StringBuffer();
		dir.append(imgFolder).append(File.separator).append(super.getClientId()).append(File.separator).append("web").append(File.separator);
		return dir.toString();
	}
	
	
	/**
	 * 获取文件路径
	 * @return
	 */
	public String getDefaultDir(){
		StringBuffer dir = new StringBuffer();
		dir.append(imgFolder).append(File.separator).append(super.getClientId()).append(File.separator).append("web").append(File.separator);
		return dir.toString();
	} 
	
	/**
	 * 从命名上传文件
	 * @param uploadFileName
	 * @return
	 */
	public String getFileNewName(String uploadFileName,String suffix){
		StringBuffer buffer = new StringBuffer();
		buffer.append("w_").append(super.getClientId()).append("_").append(System.currentTimeMillis()).append(".").append(suffix);
		log.debug(buffer.toString());
		return buffer.toString();
	}
	
	public String getAudioFileNewName(String uploadFileName,String suffix){
		StringBuffer buffer = new StringBuffer();
		Calendar car = Calendar.getInstance();
		buffer.append(car.get(Calendar.YEAR)).append(File.separator).append(car.get(Calendar.MONTH)).append(File.separator).append(System.currentTimeMillis()).append(".").append(suffix);
		return buffer.toString();
	}
	
	public String getFileName(String uploadFileName,String suffix){
		StringBuffer buffer = new StringBuffer();
		Calendar car = Calendar.getInstance();
		buffer.append(car.get(Calendar.YEAR)).append(File.separator).append(car.get(Calendar.MONTH)).append(File.separator).append(System.currentTimeMillis()).append(".").append(suffix);
		return buffer.toString();
	}
	
	
	/**
	 * 音频文件上传公共方法
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadAudio",produces="application/json")
	@ResponseBody
	public Object uploadAudio(HttpServletRequest request,HttpServletResponse response) throws Exception {
		StringBuffer messages = new StringBuffer();
		List<String> files = new ArrayList<String>();
		
		CommonsMultipartResolver multipartResolver = null;
		MultipartHttpServletRequest multiRequest = null;
		
		try {
			log.debug("upload："+request.getSession().getServletContext());
			multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			multiRequest = (MultipartHttpServletRequest) request;
			if (multipartResolver.isMultipart(request)) {
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String myFileName = file.getOriginalFilename();
						if(StringUtil.isEmptyString(myFileName)){
							continue;
						}
						String suffix = getSuffix(myFileName);
						String newName = getAudioFileNewName(myFileName,suffix);
						String dir = getAudioDir();
						String filePath = dir+newName; //重命名文件
						File localFile = new File(filePath);
						FileUtils.createDirectiory(filePath);
						file.transferTo(localFile);
						
						files.add(filePath);
						messages.append(newName);
					}
				}
			}
 			return new ResultMessage(ResultMessage.SUCCESS,messages.toString().replaceAll("\\\\", "/"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPLOAD_FAIL_RESULT;
		}finally{
			//清除零时文件
			if(null!=multiRequest){
				multipartResolver.cleanupMultipart(multiRequest);
			}
		}
	}
	
	
	
	
	
	/**
	 * 图片上传方法
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadFile",produces="application/json")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request,HttpServletResponse response) throws Exception {
		StringBuffer messages = new StringBuffer();
		long fileSize=0;
		List<String> files = new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = null;
		MultipartHttpServletRequest multiRequest = null;
		try {
			log.debug("uploadFile："+request.getSession().getServletContext());
			multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			multiRequest = (MultipartHttpServletRequest) request;
			if (multipartResolver.isMultipart(request)) {
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String myFileName = file.getOriginalFilename();
						if(StringUtil.isEmptyString(myFileName)){
							continue;
						}
						fileSize = file.getSize();
						String suffix = getSuffix(myFileName);
						String newName = getFileName(myFileName,suffix);
						String dir = getDefaultDir();
						String filePath = dir+newName; //重命名文件
						File localFile = new File(filePath);
						FileUtils.createDirectiory(filePath);
						file.transferTo(localFile);
						
						files.add(filePath);
						messages.append(newName);
						messages.append("@"+fileSize);
					}
				}
			}
 		 return new ResultMessage(ResultMessage.SUCCESS,messages.toString().replaceAll("\\\\", "/"));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPLOAD_FAIL_RESULT;
		}finally{
			//清除零时文件
			if(null!=multiRequest){
				multipartResolver.cleanupMultipart(multiRequest);
			}
		}
	}
		
}
