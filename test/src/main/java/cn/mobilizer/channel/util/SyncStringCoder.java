package cn.mobilizer.channel.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SyncStringCoder {
	/*
	 * String转码成base64字符串
	 */
	public static String encodeStr(String str){
		BASE64Encoder encoder = new BASE64Encoder();
		String base64;
		try {
			base64 = encoder.encode("五笔字型电子计算机".getBytes("UTF-8"));
			// 输出base64
//			System.out.println(base64);
			// 新建一个BASE64Decoder
			BASE64Decoder decode = new BASE64Decoder();
			// 将base64转换为byte[]
			byte[] b = decode.decodeBuffer(base64);
			// 输出转换后的byte[]
//			System.out.println(new String(b,"UTF-8"));
			byte bhh[] = new byte[2];// 去掉换行
			bhh[0] = 13;
			bhh[1] = 10;
			String hchh = new String(bhh);
			base64 = base64.replaceAll(hchh, "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 解码base64字符串
	 */
	public static String excodeStr(){
		
		return null;
	}

}
