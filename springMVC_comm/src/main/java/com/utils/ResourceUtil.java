package com.utils;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResourceUtil {

	private static final Log LOG = LogFactory.getLog(ResourceUtil.class);

	public static File getResourceFile(String resourceName) {
		String path = ResourceUtil.class.getResource("/").getFile();
		String root = null;
		if (path.lastIndexOf("/WEB-INF/")>0) {
			root = path.substring(0, path.lastIndexOf("/WEB-INF/"));
		}else if (path.lastIndexOf("/target/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/target/classes/"));
		}else if (path.lastIndexOf("/build/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/build/classes/"));
		}else if (path.lastIndexOf("/bin/")>0){
			root = path.substring(0, path.lastIndexOf("/bin/"));
		}
		return new File(root + resourceName);
	}
	/**
	 * 
	 * @param resourceName
	 * @return
	 * @author nixianjun 2013.8.9
	 */
	public static String getResourceFileName(String resourceName) {
		String path = ResourceUtil.class.getResource("/").getFile();
		String root = null;
		if (path.lastIndexOf("/WEB-INF/")>0) {
			root = path.substring(0, path.lastIndexOf("/WEB-INF/"));
		}else if (path.lastIndexOf("/target/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/target/classes/"));
		}else if (path.lastIndexOf("/build/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/build/classes/"));
		}else if (path.lastIndexOf("/bin/")>0){
			root = path.substring(0, path.lastIndexOf("/bin/"));
		}
		return  root + resourceName;
	}

	/**
	 * xls文件下载
	 * @author ZHANG Nan
	 * @param map
	 * @param path
	 * @param fileName
	 */
	public static void exportXLS(Map<String, Object> map, String path, String fileName,HttpServletResponse response){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName =System.getProperty("java.io.tmpdir") + "/" + fileName + ".xls";
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, map, destFileName);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			File file = new File(destFileName);
			BufferedInputStream inputStream= new BufferedInputStream(new FileInputStream(destFileName));
			if (file != null && file.exists()) {
				OutputStream os=response.getOutputStream();
				byte[] b=new byte[1024];
				int length;
				while((length=inputStream.read(b))>0){
					os.write(b,0,length);
				}
				os.flush();
				inputStream.close();
			} else {
				throw new RuntimeException("Download failed!  path:"+path+" fileName:"+fileName);
			}
		} catch (Exception e) {
			LOG.error("OtherPaymentMonitorAction error: ", e);
			throw new RuntimeException(e);
		}
	}
}
