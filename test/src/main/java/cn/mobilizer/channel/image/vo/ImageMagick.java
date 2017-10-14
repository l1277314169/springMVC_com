package cn.mobilizer.channel.image.vo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.ProcessStarter;
import org.im4java.utils.ExtensionFilter;
import org.im4java.utils.FilenameLoader;

import cn.mobilizer.channel.util.PropertiesHelper;

public class ImageMagick {

	public static final int TYPE_VALUE = 1; // 按照比例缩放
	public static final int TYPE_SCALE = 2; // 按传入的值所缩放

	static {
		String osName = System.getProperty("os.name").toLowerCase();
		String imageMagickPath = null;
		if (osName.contains("win")) {
			imageMagickPath = "C:/Program Files (x86)/GraphicsMagick-1.3.21-Q8";
		} else {
			imageMagickPath = PropertiesHelper.getInstance().getProperty(
					PropertiesHelper.IMAGEMAGICKPATH);
			;
		}
		ProcessStarter.setGlobalSearchPath(imageMagickPath);
	}

	/**
	 * 根据尺寸缩放图片
	 * 
	 * @param width
	 *            缩放后的图片宽度
	 * @param height
	 *            缩放后的图片高度
	 * @param srcPath
	 *            源图片路径
	 * @param newPath
	 *            缩放后图片的路径
	 * @param type
	 *            1为按照像素处理，2为大小处理，如（比例：1024x1024,大小：50%x50%）
	 */
	public static String cutImage(int width, int height, String srcPath,String newPath, int type) throws Exception {
		IMOperation op = new IMOperation();
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = null;
		if (type == TYPE_VALUE) {// 按像素
			op.resize(width, height, '^').gravity("center")
					.extent(width, height);
		} else if (type == TYPE_SCALE) {// 按像素百分比
			raw = width + "%x" + height + "%";
			op.addRawArgs("-sample", raw);
		}
		op.addImage();
		FileUtils.createDirectiory(newPath);
		cmd.run(op, srcPath, newPath);
		return newPath;
	}

	/**
	 * web图片切割
	 * @param srcPath
	 * @param newPath
	 * @throws Exception
	 */
	public static void cutWebImage(String srcPath,String newPath) throws Exception {
		IMOperation op = new IMOperation();
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = null;
		
		ImageInfo ii = ImageMagick.getImageInfo(srcPath);
		double temp = 100;
		if(ii.getWidth()>390){
			temp = 390d/ii.getWidth()*100;
		}
		raw = temp + "%x" + temp + "%";
		op.addRawArgs("-sample", raw);
		
		op.addImage();
		FileUtils.createDirectiory(newPath);
		cmd.run(op, srcPath, newPath);
	}

	/**
	 * 先缩放，后居中切割图片
	 * 
	 * @param srcPath
	 *            源图路径
	 * @param desPath
	 *            目标图保存路径
	 * @param rectw
	 *            待切割在宽度
	 * @param recth
	 *            待切割在高度
	 * @throws IOException
	 */
	public static void cutImageCenter(String srcPath, String desPath,int width, int height) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage();
		op.resize(width, height, '^').gravity("center").extent(width, height);
		op.addImage();

		ConvertCmd convert = new ConvertCmd(true);

		FileUtils.createDirectiory(desPath);

		convert.run(op, srcPath, desPath);
	}

	/**
	 * 根据文件夹获取文件清单
	 * 
	 * @param dir
	 * @return
	 */
	public static List<String> loadFiles(String dir, String suffix) {
		ExtensionFilter filter = new ExtensionFilter(suffix);
		filter.setRecursion(true);
		filter.ignoreDotDirs(true);
		FilenameLoader loader = new FilenameLoader(filter);
		List<String> files = loader.loadFilenames(dir);
		return files;
	}

	/**
	 * 图片信息
	 * 
	 * @param imagePath
	 * @return
	 */
	public static ImageInfo getImageInfo(String imagePath) {
	    String line = null;
	    ImageInfo ii = null;
	    try {
	        IMOperation op = new IMOperation();
	        op.format("%w@%h@%d%f@%b");
	        op.addImage(1);
	        IdentifyCmd identifyCmd = new IdentifyCmd(true);
	        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
	        identifyCmd.setOutputConsumer(output);
	        identifyCmd.run(op, imagePath);
	        ArrayList<String> cmdOutput = output.getOutput();
	        if(cmdOutput.size() == 1){
	        	line = cmdOutput.get(0);
	        	String[] info = line.split("@");
	        	ii = new ImageInfo();
	        	ii.setWidth(Integer.parseInt(info[0]));
	        	ii.setHeight(Integer.parseInt(info[1]));
	        	ii.setPath(info[2]);
	        	ii.setSize(info[3]);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ii;
	}

	public static void main(String[] args)  throws Exception{
		/*ImageInfo ii = ImageMagick.getImageInfo("D://2.jpg");
		System.out.println(ii.getWidth()+","+ii.getHeight()+","+ii.getPath()+","+ii.getSize());
		double temp = 390d/ii.getWidth()*100;
		System.out.println(temp+","+ii.getWidth());*/
		
		ImageMagick.cutWebImage("D://8.jpg", "D://1.jpg");
	}

}