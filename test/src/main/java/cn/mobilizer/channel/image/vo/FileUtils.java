package cn.mobilizer.channel.image.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtils {

	private static Log log = LogFactory.getLog(FileUtils.class);
	
	public static void copyAndCutImage(String sourceFile,String targetFile,boolean isWeb) throws Exception{
		if(copyFile(sourceFile, targetFile)){
			File file = new File(targetFile);
			String thumbPath = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator)+1)+ImageGlobal.Global.SMALL_IMAGE_PATH;
			String fileName = file.getName();
			
			String sFile = thumbPath+File.separator+"s_"+fileName;
			String mFile = thumbPath+File.separator+"m_"+fileName;
			String lFile = thumbPath+File.separator+"l_"+fileName;
			
			ImageMagick.cutImageCenter(targetFile,sFile, ImageGlobal.Size.SMALL, ImageGlobal.Size.SMALL);
			ImageMagick.cutImageCenter(targetFile,mFile, ImageGlobal.Size.MODERATE, ImageGlobal.Size.MODERATE);
			ImageMagick.cutImageCenter(targetFile,lFile, ImageGlobal.Size.LARGE, ImageGlobal.Size.LARGE);
			
			if(isWeb){ //对于web端上传的图片，需要做等比例压缩 390*561
				String _file = thumbPath+File.separator+"xl_"+fileName;
				ImageMagick.cutWebImage(targetFile,_file);
			}
			
			log.debug("copyAndCutImage targetFile:"+targetFile);
		}
	}
	
	public static void copyAndCutImageOne(String sourceFiles,String targetRootPath,boolean isWeb) throws Exception{
		String fileName = new File(sourceFiles).getName();
		String tempDir = getFileDirByName(fileName);
		String targetFile = targetRootPath+File.separator+tempDir;
		copyAndCutImage(sourceFiles, targetFile,isWeb);
	}
	
	
	public static void copyAndCutImage(List<String> sourceFiles,String targetRootPath,boolean isWeb) throws Exception{
		for (String sfile : sourceFiles) {
			String fileName = new File(sfile).getName();
			String targetFile = targetRootPath+File.separator+fileName;
			copyAndCutImage(sfile, targetFile,isWeb);
		}
	}
	
	
	/**
	 * NIO实现的文件复制功能
	 * 
	 * @param sourceFile
	 *            源文件路径
	 * @param targetFile
	 *            目标文件路径
	 */
	public static boolean copyFile(String sourceFile, String targetFile) throws Exception {
		FileInputStream fin = null;
		FileOutputStream fou = null;
		boolean copyFlag = false;
		
		File source = new File(sourceFile);
		if(source.exists()){
			if(createDirectiory(targetFile)){
				try {
					fin = new FileInputStream(sourceFile);
					fou = new FileOutputStream(targetFile);
					FileChannel fc = fin.getChannel();
					FileChannel fo = fou.getChannel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					while (true) {
						buffer.clear();
						int r = fc.read(buffer);
						if (r == -1) {
							break;
						}
						buffer.flip();
						fo.write(buffer);
					}
					copyFlag = true;
					log.debug("copyFile success,sourceFile = "+sourceFile+",targetFile="+targetFile);
				} finally {
					if (null != fou) {
						fou.close();
					}
					if (null != fin) {
						fin.close();
					}
					
					if(copyFlag && ImageGlobal.Global.IS_DELETE){
						source.delete();
					}
				}
			}else{
				log.info("createDirectiory fail,sourceFile = "+sourceFile+",targetFile="+targetFile);
			}
		}else{
			log.info("file:"+sourceFile +" don't exist ");
		}
		return copyFlag;
	}

	/**
	 * 创建文件夹
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 * @throws IOException
	 */
	public static boolean createDirectiory(String destFileName) throws IOException {
		boolean flag = true;
		File destFile = new File(destFileName); 
		if (!destFile.getParentFile().exists()) {
			flag = destFile.getParentFile().mkdirs();// 目标文件所在目录不存在
		}
		return flag;
	}
	
	
	/**
	 * 根据文件名称获取文件目标目录
	 * @param fileName
	 */
	public static String getFileDirByName(String fileName){
		String[] nameArr = fileName.split("_");
		StringBuffer path = new StringBuffer();
		if(null!=nameArr && nameArr.length>=3){
			String clientId;
			String userId;
			if(nameArr[0].equals(ImageGlobal.Global.BN)){
				clientId = nameArr[2];
				path.append(ImageGlobal.Global.BN_DIR).append(File.separator);
				path.append(clientId).append(File.separator).append(fileName);
			}else if(nameArr[0].equals(ImageGlobal.Global.WEB)){
				clientId = nameArr[1];
				path.append(clientId).append(File.separator);
				path.append(ImageGlobal.Global.WEB_DIR).append(File.separator).append(fileName);
			}else if(nameArr[0].equals(ImageGlobal.Global.QUICKVIEW)){
				String year = nameArr[1];
				String month = nameArr[2];
				path.append("quickview").append(File.separator);
				path.append(year).append(File.separator).append(month).append(File.separator);
				path.append(fileName);
			}else{
				clientId = nameArr[1];
				userId = nameArr[2];
				path.append(clientId).append(File.separator).append(userId).append(File.separator).append(fileName);
			}
		}
		return path.toString();
	}
	
	
	/**
	 * 根据文件名称获取文件目标目录
	 * @param fileName
	 */
	public static String getFileDirByNameForSmall(String fileName){
		String[] nameArr = fileName.split("_");
		StringBuffer path = new StringBuffer();
		if(null!=nameArr && nameArr.length>=3){
			String clientId;
			String userId;
			if(nameArr[0].equals(ImageGlobal.Global.BN)){
				clientId = nameArr[2];
				path.append(ImageGlobal.Global.BN_DIR).append(File.separator);
				path.append(clientId).append(File.separator).append(fileName);
			}else if(nameArr[0].equals(ImageGlobal.Global.WEB)){
				clientId = nameArr[1];
				path.append(clientId).append(File.separator);
				//path.append(ImageGlobal.Global.WEB_DIR).append(File.separator).append(fileName);
				path.append(ImageGlobal.Global.WEB_DIR).append(File.separator).append(ImageGlobal.Global.SMALL_IMAGE_PATH).append(File.separator).append("l_"+fileName);
			}else{
				clientId = nameArr[1];
				userId = nameArr[2];
				path.append(clientId).append(File.separator).append(userId).append(File.separator).append(ImageGlobal.Global.SMALL_IMAGE_PATH).append(File.separator).append("l_"+fileName);
			}
		}
		return path.toString();
	}
	
	/**
	 * 获取目录
	 * @param clientId
	 * @param userId
	 * @return
	 */
	public static String getDir(Integer clientId,Integer userId){
		StringBuffer buffer = new StringBuffer();
		buffer.append(clientId).append(File.separator).append(userId).append(File.separator);
		return buffer.toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println("/var/www/uploadfiles/"+FileUtils.getFileDirByName("BN_I_5_1432645128256.jpg"));
		System.out.println("/var/www/uploadfiles/"+FileUtils.getFileDirByName("I_4_14260_1424183740787.jpg"));
	}
	
}
