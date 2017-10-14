package cn.mobilizer.channel.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;

public class FileDirectoryUtil {

	public static void copyImgFileAndThumbnail(File sourceFile, File targetFile) throws IOException {
		if(sourceFile.exists() && !sourceFile.getName().endsWith(".jpg"))
			return;
		if(targetFile.exists() && targetFile.length() == sourceFile.length()){
			String thumbPath = targetFile.getAbsolutePath().substring(0,targetFile.getAbsolutePath().lastIndexOf("/")+1)+"thumbnail";
			if(!new File(thumbPath).exists())
				new File(thumbPath).mkdirs();
			try {
				if(!new File(thumbPath+"/s_"+targetFile.getName()).exists())
					Thumbnails.of(targetFile).size(40, 40).toFile(thumbPath+"/s_"+targetFile.getName());
				if(!new File(thumbPath+"/m_"+targetFile.getName()).exists())
					Thumbnails.of(targetFile).size(80, 80).toFile(thumbPath+"/m_"+targetFile.getName());
				if(!new File(thumbPath+"/l_"+targetFile.getName()).exists())
					Thumbnails.of(targetFile).size(120, 120).toFile(thumbPath+"/l_"+targetFile.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		String thumbPath = targetFile.getAbsolutePath().substring(0,targetFile.getAbsolutePath().lastIndexOf("/")+1)+"thumbnail";
		if(!new File(thumbPath).exists())
			new File(thumbPath).mkdirs();
		try {
			if(!new File(thumbPath+"/s_"+targetFile.getName()).exists())
				Thumbnails.of(targetFile).size(40, 40).toFile(thumbPath+"/s_"+targetFile.getName());
			if(!new File(thumbPath+"/m_"+targetFile.getName()).exists())
				Thumbnails.of(targetFile).size(80, 80).toFile(thumbPath+"/m_"+targetFile.getName());
			if(!new File(thumbPath+"/l_"+targetFile.getName()).exists())
				Thumbnails.of(targetFile).size(120, 120).toFile(thumbPath+"/l_"+targetFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moveFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		//删除
		sourceFile.deleteOnExit();
	}

	// 复制文件夹
	public static Integer copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		if(StringUtils.isEmpty(targetDir) || StringUtils.isEmpty(sourceDir))
			return 0;
		(new File(targetDir)).mkdirs();
		int count = 0;
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				count++;
				if(sourceFile.canRead() && sourceFile.length() > 0)
					copyImgFileAndThumbnail(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
		return count;
	}

}
