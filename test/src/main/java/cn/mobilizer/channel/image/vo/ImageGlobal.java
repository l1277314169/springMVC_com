package cn.mobilizer.channel.image.vo;

public class ImageGlobal {

	public final static class Size {
		public static int LARGE 	= 120;  // 大图
		public static int MODERATE 	= 80;  // 中图
		public static int SMALL 	= 40; // 小图

	}

	public final static class Global {
		public static String SMALL_IMAGE_PATH = "thumbnail"; // 压缩后的小图的存放目录
		public static String SUFFIX = "jpg";
		public static String[] IMAGES = new String[]{"jpg","png","jpeg"};//web端上传图片格式
		
		public static String KEY_CODE = "user_activity_log";
		public static boolean IS_DELETE = true; // 移除文件后是否删除原文件
		public static String BN = "BN";
		public static String WEB = "w";
		public static String QUICKVIEW = "Q";
		public static String BN_DIR = "bn";
		public static String WEB_DIR = "web";
		public static String ZIP_NAME = "拜访图片";
	}
	
	public final static class NotifyType{
		public static int DEFAULT = 1; //默认图片
		public static int BN 	  = 2; //门店图片
	}
	
	public final static class PicName{
		public static String INPIC_NAME  = "进店照片";
		public static String OUTPIC_NAME = "出店照片"; 
	}
	
}
