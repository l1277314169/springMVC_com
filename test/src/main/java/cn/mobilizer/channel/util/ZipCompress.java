package cn.mobilizer.channel.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.comm.utils.StringUtil;

import com.google.common.primitives.UnsignedBytes;
public class ZipCompress {
	private static final Log LOG = LogFactory.getLog(ZipCompress.class);
//	public static void main(String[] args) throws IOException {
//		// File file = new File("d:\\search_user_max_create_zip.xml");
//		// File file = new File("d:\\abc\\search_user_max.xml");
////		File file = new File("d:\\abc\\offer_max.xml");
//		
//		String str = "你好啊,aBc!";
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		DataOutputStream outputstream = new DataOutputStream(baos);
//		outputstream.writeChars(str);
//
//		byte[] contents = baos.toByteArray();
//		for (int i = 0; i < contents.length; i++) {
////			System.out.println("contents:"+contents[i]);
//		}
//		
//		System.out.println("zipccc ="+compress(str));
//		System.out.println("base64="+Base64.encodeBase64String(str.getBytes("utf-8")));
//			String aaa = "你好啊,aBc!";
////			System.out.println(Base64.encodeBase64String("This is a smaller string. Ansdfgsdfgsdfgsdfgsdfgsdfgsdfgd some other stuff".getBytes("utf-8")));
////			System.out.println("zip="+compress(aaa));
//
//
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < 40; i++) {
//			sb.append("五笔字型电子计算机");
//			sb.append("-"+i);
//			sb.append("\n");
//		}
//		
//		try {
//			String json = "\"Name\":\"province_en\"";
//			String strx = "";
//			byte[] bb = sb.toString().getBytes("UTF-8");
//			System.out.println("文字大小，size = " + bb.length/1024 + "k");
////			FileReader fr = new FileReader(file);
////			BufferedReader bfr = new BufferedReader(fr);
////			StringBuffer sb = new StringBuffer("");
////			String str = null;
////			while ((str = bfr.readLine()) != null) {
////				sb.append(str);
////				sb.append("\r\n");
////			}
////			bfr.close();
////			fr.close();
//			// System.out.println(sb.toString());
//			long startTime = System.currentTimeMillis();
//			String result = ZipCompress.compress(json);
//			System.out.println("压缩后的大小，size = " + result.getBytes().length);
//			System.out.println("压缩耗时：" + (System.currentTimeMillis() - startTime) + "ms");
//			startTime = System.currentTimeMillis();
//			String zip = "";
//			String deCompress = ZipCompress.decompress(zip);
//			System.out.println("解压后的大小，size = " + deCompress);
//			System.out.println("解压缩耗时：" + (System.currentTimeMillis() - startTime) + "ms");
//			System.out.println("解压前后文字是否匹配：" + deCompress.equals(sb.toString()));
//			System.out.println("元数据：" + json);
//			System.out.println("压缩后：" + result);
//			System.out.println("解压后：" + deCompress);
////			 OutputStream os = new
////			 FileOutputStream("d:\\compress_users_string.xml");
////			 os.write(result.getBytes(), 0, result.getBytes().length);
////			 System.out.println(deCompress);
//			FileWriter fw = new FileWriter("/Users/Kris/Documents/zips/tableDataStr1.txt");
//			fw.write(deCompress);
//			// os.close();
//			fw.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	/**
	 * 压缩字符串为 byte[] 储存可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)方法
	 * 保存为字符串
	 * 
	 * @param str
	 *            压缩前的文本
	 * @return
	 */
	public static final String compress(String str) {
		if (str == null) {
			return null;
		}

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes("UTF-8"));
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		if (compressed != null) {
//			printBytex(compressed);
			return Base64.encodeBase64String(compressed);
		}

		return null;
	}
	

	/**
	 * 将压缩后的 byte[] 数据解压缩
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(String str) {
		byte[] compressed = Base64.decodeBase64(str);
		//printByte(compressed);
		if (compressed == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString("UTF-8");
			if(StringUtil.isNotEmptyString(str) && StringUtil.isEmptyString(decompressed)){
				LOG.info("解压出现异常！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}
	
	private static String printByte(byte[] b){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]+",");
		}
//		System.out.println(b.length+"-UnsignedBytes="+UnsignedBytes.join(",", b));;
//		System.out.println("printbyte:"+sb.toString());
		return sb.toString();
	}
}