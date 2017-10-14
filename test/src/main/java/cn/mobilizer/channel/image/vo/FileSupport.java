package cn.mobilizer.channel.image.vo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.util.PropertiesHelper;

public class FileSupport {

	private static Log log = LogFactory.getLog(FileSupport.class);
	
	private static String errorFolder = null;
	private static String imageFolder = null;
	private static String ftpFolder = null;
	private List<String> files = new ArrayList<String>();
	private int max = 1;
	private int index = 0;
	
	static{
		errorFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERROR_FOLDER);
		imageFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
		ftpFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
	}
	
	/**
	 * 
	 * @param file
	 * @param suffix
	 * @param size 如果size为-1查询所有图片
	 * @return
	 * @throws Exception
	 */
	public List<String> loadFiles(File file, String suffix,int size) throws Exception {
		this.max = size;
		this.index = 0;
		files.clear();
		this.search(file, suffix);
		return files;
	}
	
	private void search(File file, String suffix) throws Exception {
		if (file == null){
			return;
		}
		if (file.isFile()) {// 判断是否是文件
			String name = file.getName();
			name = name == null ? "" : name.trim();
			if (name.endsWith(suffix)){
				String dir = FileUtils.getFileDirByName(name);
				String path = file.getPath();
				if(file.length()<=0){
					log.info("file error: file length is zero,file path = "+path);
					//将文件移动到新的文件夹
					FileUtils.copyFile(path, errorFolder+ File.separator +dir);
					return;
				}
				if(!StringUtil.isEmptyString(dir)){
					if(!ThreadSender.isContains(path)){
						log.debug("search file path:"+path);
						files.add(path);
						index ++;
					}
				}else{
					log.info("file name verify fail,file name="+name+",file path = "+ path);
					String tempPath = path.replace(ftpFolder, "");
					StringBuffer buffer = new StringBuffer(imageFolder);
					buffer.append(tempPath);
					log.info(buffer.toString());
					FileUtils.copyFile(path, buffer.toString());
				}
			}
		}
		File[] files = file.listFiles();//取得文件夹中包含的文件及文件夹
		if (files == null || files.length <= 0){
			return;// 如果没有其中没有文件或文件夹，返回
		}
		for (File file2 : files) {// 循环其下所有文件及文件夹
			if(max!=-1 && index>=max){
				break;
			}
			search(file2, suffix);// 递归
		}
	}
	
	
	/**
	 * 根据文件名称获取文件目标目录
	 * @param fileName
	 */
	/*public String getFileDirByName2(String fileName){
		String[] nameArr = fileName.split("_");
		StringBuffer path = new StringBuffer();
		if(null!=nameArr && nameArr.length>3){
			String clientId = nameArr[1];
			String userId = nameArr[2];
			path.append(clientId).append(File.separator).append(userId).append(File.separator).append(fileName);
		}
		return path.toString();
	}*/
}
