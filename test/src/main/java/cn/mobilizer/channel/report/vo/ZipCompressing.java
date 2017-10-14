package cn.mobilizer.channel.report.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import cn.mobilizer.channel.util.PropertiesHelper;

public class ZipCompressing {

	protected static Logger log = Logger.getLogger(ZipCompressing.class);

	/**
	 * 根据文件名，压缩文件
	 * 
	 * @param fileNames 需要压缩的索引文件名
	 * @param zipFileName 压缩后的文件名
	 * @param out
	 * @throws Exception
	 */
	public static void exportFileByName(Set<String> fileNames, String zipFileName,OutputStream out) throws Exception {
		if(null!=fileNames && !fileNames.isEmpty()){
			zipFileByName(fileNames, zipFileName, out);
		}
	}
	
	/**
	 * 根据文件夹，压缩
	 * @param folder 需要压缩的文件夹
	 * @param zipFileName 压缩后的文件名
	 * @param out
	 * @throws Exception
	 */
	public static void exportFileByFolder(String folder,OutputStream out) throws Exception{
		zipFolder(folder,out);
	}
	
	/**
	 * 根据文件名称获取文件存储路径
	 * @param fileNames
	 * @return
	 */
	private static List<File> getFileByName(Set<String> fileNames) {
		List<File> files = new ArrayList<File>();
		String imageFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
		log.debug("imageFolder:" + imageFolder);

		for (String name : fileNames) {
			String[] temp = name.split("_");
			String clientId = temp[1];
			String userId = temp[2];
			StringBuffer path = new StringBuffer();
			path.append(imageFolder).append("/").append(clientId).append("/")
					.append(userId).append("/").append(name);
			File file = new File(path.toString());
			files.add(file);
		}
		return files;
	}

	private static void zipFileByName(Set<String> fileNames,String zipFileName, OutputStream out) throws Exception {
		InputStream input = null;
		ZipOutputStream zipOut = new ZipOutputStream(out);
		try {
			List<File> files = getFileByName(fileNames);
			for (int i = 0; i < files.size(); ++i) {
				input = new FileInputStream(files.get(i));
				String entry = zipFileName + File.separator+ files.get(i).getName();
				zipOut.putNextEntry(new ZipEntry(entry));
				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
				input.close();
			}
		} finally {
			if (null != input) {
				input.close();
			}
			zipOut.flush();
			zipOut.close();
		}
	}


	/**
	 * 压缩文件为zip格式
	 * 
	 * @param output
	 *            ZipOutputStream对象
	 * @param file
	 *            要压缩的文件或文件夹
	 * @param basePath
	 *            条目根目录
	 * @throws IOException
	 */
	private static void zipFile(ZipOutputStream output, File file,String basePath) throws IOException {
		FileInputStream input = null;
		try {
			// 文件为目录
			if (file.isDirectory()) {
				// 得到当前目录里面的文件列表
				File list[] = file.listFiles();
				basePath = basePath + (basePath.length() == 0 ? "" : "/")+ file.getName();
				// 循环递归压缩每个文件
				for (File f : list){
					zipFile(output, f, basePath);
				}
			} else {
				// 压缩文件
				basePath = (basePath.length() == 0 ? "" : basePath + "/") + file.getName();
				output.putNextEntry(new ZipEntry(basePath));
				input = new FileInputStream(file);
				int readLen = 0;
				byte[] buffer = new byte[1024 * 8];
				while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
					output.write(buffer, 0, readLen);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭流
			if (input != null) {
				input.close();
			}
		}
	}

	public static void zipFolder(String zipFolder,OutputStream out) throws Exception {
		ZipOutputStream output = null;
		try {
			File file = new File(zipFolder);
			output = new ZipOutputStream(out);
			// 顶层目录开始
			zipFile(output, file, "");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭流
			if (output != null) {
				output.flush();
				output.close();
			}
		}
	}
}
