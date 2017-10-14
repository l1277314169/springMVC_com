package cn.mobilizer.channel.comm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.util.PropertiesHelper;

@Controller
@RequestMapping("/audio")
public class DownLoadAudioFileController extends BaseActionSupport{
	
	private static final long serialVersionUID = 4055338211684925646L;

	/**
	 * 下载音频文件
	 * @param resp
	 * @param fileName
	 */
	@RequestMapping(value="/downLoad")	
	public void downLoadAudio(HttpServletResponse response,String fileName){
		String imageFolder =  PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
		File file = new File(imageFolder+File.separator+fileName);
        response.setContentType("multipart/form-data");  
        response.setHeader("Content-Disposition", "attachment;fileName="+file.getName());  
        ServletOutputStream out;  
        try {  
            InputStream inputStream = new FileInputStream(file);  
            out = response.getOutputStream(); 
            byte [] bb=new byte[1024];  //接收缓存
            int len;
            while((len=inputStream.read(bb))>0){ //接收
            	out.write(bb, 0, len);  //写入文件
            }
            out.flush(); 
            out.close();
            inputStream.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
	
}
