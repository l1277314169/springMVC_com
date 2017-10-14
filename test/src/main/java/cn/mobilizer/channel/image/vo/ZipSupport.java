package cn.mobilizer.channel.image.vo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import cn.mobilizer.channel.image.po.Image;
import cn.mobilizer.channel.survey.vo.SurveyImageVo;
import cn.mobilizer.channel.util.PropertiesHelper;

public class ZipSupport {

	protected static Logger log = Logger.getLogger(ZipSupport.class);

	private static ZipSupport zipSupport = null;
	private static String imageFolder = null;
	private final static int BUFFER = 1024;

	static {
		imageFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
	}

	private ZipSupport() {

	}

	public static ZipSupport getInstance() {
		if (null == zipSupport) {
			zipSupport = new ZipSupport();
		}
		return zipSupport;
	}

	/**
	 * 根据文件名，压缩文件
	 * 
	 * @param fileNames需要压缩的索引文件名
	 * @param zipFileName压缩后的文件名
	 * @param out
	 * @throws Exception
	 */
	public void exportFileByName(List<ImageView> images, String zipFileName,OutputStream out) throws Exception {
		if (null != images && !images.isEmpty()) {
			zipFileByName(images, zipFileName, out);
		}
	}

	/**
	 * 根据文件夹，压缩
	 * 
	 * @param folder
	 *            需要压缩的文件夹
	 * @param zipFileName
	 *            压缩后的文件名
	 * @param out
	 * @throws Exception
	 */
	public void exportFileByFolder(String folder, OutputStream out) throws Exception {
		zipFolder(folder, out);
	}

	/**
	 * 根据文件名称获取文件存储路径
	 * 
	 * @param fileNames
	 * @return
	 */
	public List<File> getFileByName(Set<String> fileNames) {
		List<File> files = new ArrayList<File>();
		String imageFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FTP_FOLDER);
		log.debug("imageFolder:" + imageFolder);

		for (String name : fileNames) {
			String[] temp = name.split("_");
			String clientId = temp[1];
			String userId = temp[2];
			StringBuffer path = new StringBuffer();
			path.append(imageFolder).append(File.separator).append(clientId).append(File.separator).append(userId).append(File.separator).append(name);
			File file = new File(path.toString());
			files.add(file);
		}
		return files;
	}

	
	public void zipVisitingImage(List<ImageView> images, String zipFileName,OutputStream out) throws Exception {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		zipOut.setEncoding("UTF-8");
		try {
			int i = 0;
			for (ImageView view : images) {
				List<Image> imges = view.getImages();
				for (Image image : imges) {
					i++;
					String dir = imageFolder + File.separator+ image.getLargeImagepath();
					if (!exists(dir)) {
						continue;
					}
					String suffix = image.getLargeImagepath().substring(image.getLargeImagepath().lastIndexOf("."),image.getLargeImagepath().length());
					
					String entry = zipFileName + File.separator+ image.getStoreName() + File.separator+ view.getShowDate() + File.separator+ image.getImageShowName()+"_"+i+suffix;
					zipOut.putNextEntry(new ZipEntry(entry));
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dir));
					int count;
					byte data[] = new byte[BUFFER];
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						zipOut.write(data, 0, count);
					}
					bis.close();
					zipOut.closeEntry();
				}
			}
		} finally {
			zipOut.close();
		}
	}
	
	private void zipFileByName(List<ImageView> images, String zipFileName,OutputStream out) throws Exception {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		zipOut.setEncoding("UTF-8");
		try {
			for (ImageView view : images) {
				List<Image> imges = view.getImages();
				for (Image image : imges) {
					String dir = imageFolder + File.separator+ image.getLargeImagepath();
					if (!exists(dir)) {
						continue;
					}
					String entry = zipFileName + File.separator+ image.getStoreName() + File.separator+ view.getShowDate() + File.separator+ image.getImageName();
					zipOut.putNextEntry(new ZipEntry(entry));
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dir));
					int count;
					byte data[] = new byte[BUFFER];
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						zipOut.write(data, 0, count);
					}
					bis.close();
					zipOut.closeEntry();
				}
			}
		} finally {
			zipOut.close();
		}
	}

/*	public void zipFileForAppleCare(List<SurveyImageVo> images,
			String zipFileName, OutputStream out) throws Exception {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		zipOut.setEncoding("UTF-8");
		try {
			for (SurveyImageVo image : images) {
				String[] values = image.getImages();
				for (String img : values) {
					String largeDir = FileUtils.getFileDirByName(img);
					String dir = imageFolder + File.separator + largeDir;
					if (!exists(dir)) {
						continue;
					}
					String entry = zipFileName + File.separator
							+ image.getCity() + File.separator
							+ image.getStoreNo() + File.separator + img;
					zipOut.putNextEntry(new ZipEntry(entry));
					BufferedInputStream bis = new BufferedInputStream(
							new FileInputStream(dir));
					int count;
					byte data[] = new byte[BUFFER];
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						zipOut.write(data, 0, count);
					}
					bis.close();
					zipOut.closeEntry();
				}
			}
		} finally {
			zipOut.close();
		}
	}*/

	public boolean zipFileForAppleCare1(Integer clientId, List<SurveyImageVo> images, String zipFileName) throws Exception {
		boolean isOK = true;
		
			String downLoadImgZipFolder = PropertiesHelper.getInstance() .getProperty(PropertiesHelper.IMG_FOLDER);
			File file = new File(downLoadImgZipFolder + File.separator + clientId + File.separator + "download");
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName = downLoadImgZipFolder + File.separator + clientId + File.separator + "download" + File.separator + zipFileName + ".zip";
			OutputStream out = new FileOutputStream(fileName);
			ZipOutputStream zipOut = new ZipOutputStream(out);
			zipOut.setEncoding("UTF-8");
			if(!(images==null)){
			try {
				String entry = null;
				for (SurveyImageVo image : images) {
					String[] values = image.getImages();
					for (String img : values) {
						String largeDir = FileUtils.getFileDirByName(img);
						String dir = imageFolder + File.separator + largeDir;
						if (!exists(dir)) {
							continue;
						}
						entry = zipFileName + File.separator + image.getCity() + File.separator + image.getStoreNo() + File.separator + img;
						zipOut.putNextEntry(new ZipEntry(entry));
						BufferedInputStream bis = new BufferedInputStream( new FileInputStream(dir));
						int count;
						byte data[] = new byte[BUFFER];
						while ((count = bis.read(data, 0, BUFFER)) != -1) {
							zipOut.write(data, 0, count);
						}
						bis.close();
						zipOut.closeEntry();
					}
				}
	
			} catch (Exception e) {
				isOK = false;
			} finally {
				zipOut.close();
			}
	} else{
		isOK = false;
		}
		return isOK;

	}

/*	public void zipFileByNameColgate(
			Map<String, List<ColgateImageVo>> mapImages, String zipFileName,
			String cycle, OutputStream out) throws Exception {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		zipOut.setEncoding("UTF-8");
		try {
			Set<String> keys = mapImages.keySet();
			for (String key : keys) {
				List<ColgateImageVo> imges = mapImages.get(key);
				for (int i = 0; i < imges.size(); i++) {
					ColgateImageVo image = imges.get(i);
					String dir = imageFolder + File.separator
							+ image.getLargeImagepath();
					if (!exists(dir)) {
						continue;
					}
					StringBuffer base = new StringBuffer();
					base.append(zipFileName).append(File.separator)
							.append(cycle).append(File.separator);
					String suffix = image.getLargeImagepath().substring(
							image.getLargeImagepath().lastIndexOf("."),
							image.getLargeImagepath().length());
					base.append(image.getStoreName()).append(File.separator)
							.append(image.getImageShowName()).append("_")
							.append(i).append(suffix);
					ZipEntry entry = new ZipEntry(base.toString());
					zipOut.putNextEntry(entry);
					BufferedInputStream bis = new BufferedInputStream(
							new FileInputStream(dir));
					int count;
					byte data[] = new byte[BUFFER];
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						zipOut.write(data, 0, count);
					}
					bis.close();
					zipOut.closeEntry();
				}
			}
		} finally {
			zipOut.close();
		}
	}*/

	public boolean zipFileByNameColgate1(Integer clientId,Map<String, List<ColgateImageVo>> mapImages, String zipFileName)
			throws Exception {
		boolean isOK = true;
		String downLoadImgZipFolder = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
		File file = new File(downLoadImgZipFolder + File.separator + clientId + File.separator + "download");
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = downLoadImgZipFolder + File.separator + clientId + File.separator + "download" + File.separator + zipFileName + ".zip";
		OutputStream out = new FileOutputStream(fileName);
		ZipOutputStream zipOut = new ZipOutputStream(out);
		zipOut.setEncoding("UTF-8");
		if(!(mapImages==null)){
			try {
				Set<String> keys = mapImages.keySet();
				for (String key : keys) {
					List<ColgateImageVo> imges = mapImages.get(key);
					for (int i = 0; i < imges.size(); i++) {
						ColgateImageVo image = imges.get(i);
						String dir = imageFolder + File.separator
								+ image.getLargeImagepath();
						if (!exists(dir)) {
							continue;
						}
						StringBuffer base = new StringBuffer();
						base.append(zipFileName).append(File.separator);
						String suffix = image.getLargeImagepath().substring( image.getLargeImagepath().lastIndexOf("."), image.getLargeImagepath().length());
						base.append(image.getStoreName()).append(File.separator) .append(image.getImageShowName()).append("_") .append(i).append(suffix);
						ZipEntry entry = new ZipEntry(base.toString());
						zipOut.putNextEntry(entry);
						BufferedInputStream bis = new BufferedInputStream( new FileInputStream(dir));
						int count;
						byte data[] = new byte[BUFFER];
						while ((count = bis.read(data, 0, BUFFER)) != -1) {
							zipOut.write(data, 0, count);
						}
						bis.close();
						zipOut.closeEntry();
					}
				}
			}
			catch (FileNotFoundException e) {
				isOK = false;
				e.printStackTrace();
			} finally {
				zipOut.close();
			}
		}else{
		  isOK = false;
		}
		
		return isOK;
	}

	public void zipImageFile(List<ImageVo> imageList, String zipFileName,String cycle, OutputStream out) throws Exception {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		zipOut.setEncoding("UTF-8");
		try {
			for (int i = 0; i < imageList.size(); i++) {
				ImageVo image = imageList.get(i);
				String[] values = image.getImageName().split(",");
				if (null != values && values.length > 0) {
					for (String img : values) {
						String largeImage = FileUtils.getFileDirByName(img);
						String dir = imageFolder + File.separator + largeImage;
						StringBuffer base = new StringBuffer();
						base.append(zipFileName).append(File.separator);
						base.append(image.getStoreNo() + "_" + image.getStoreName());
						base.append(File.separator).append(img);
						if (!exists(dir)) {
							log.error("file error: " + base.toString());
							continue;
						}
						ZipEntry entry = new ZipEntry(base.toString());
						zipOut.putNextEntry(entry);
						BufferedInputStream bis = new BufferedInputStream( new FileInputStream(dir));
						int count;
						byte data[] = new byte[BUFFER];
						while ((count = bis.read(data, 0, BUFFER)) != -1) { zipOut.write(data, 0, count);
						}
						bis.close();
						zipOut.closeEntry();
					}
				}
			}
		} finally {
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
	private void zipFile(ZipOutputStream output, File file, String basePath)
			throws IOException {
		FileInputStream input = null;
		try {
			// 文件为目录
			if (file.isDirectory()) {
				// 得到当前目录里面的文件列表
				File list[] = file.listFiles();
				basePath = basePath+ (basePath.length() == 0 ? "" : File.separator)+ file.getName();
				// 循环递归压缩每个文件
				for (File f : list) {
					zipFile(output, f, basePath);
				}
			} else {
				// 压缩文件
				basePath = (basePath.length() == 0 ? "" : basePath+ File.separator)+ file.getName();
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

	private boolean exists(String dir) {
		File file = new File(dir);
		if (file.exists() && file.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	private void zipFolder(String zipFolder, OutputStream out) throws Exception {
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
