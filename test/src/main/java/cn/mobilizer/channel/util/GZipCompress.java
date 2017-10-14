package cn.mobilizer.channel.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.comm.utils.StringUtil;

public class GZipCompress {
	private static final Log LOG = LogFactory.getLog(GZipCompress.class);
	// 压缩
	public static String compress(String str) {
		
		if ((str == null) || (str.length() == 0)) { return str; }
		
		byte[] compressed;
		ByteArrayOutputStream out = null;
		GZIPOutputStream gzip = null;
		try {
			out = new ByteArrayOutputStream();
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes("UTF-8"));
			gzip.close();

			compressed = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			compressed = null;
		}finally {
			if (gzip != null) {
				try {
					gzip.close();
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
		if(compressed != null){
			return Base64.encodeBase64String(compressed);
		}
		return null;
	}

	// 解压缩
	public static String uncompress(String str) throws IOException{
		if ((str == null) || (str.length() == 0)) { return str; }

		byte[] bytes = Base64.decodeBase64(str);
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in  = null;
		GZIPInputStream gunzip = null;
		String decompressed = null;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(bytes);
			gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int n;

			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			decompressed = out.toString("UTF-8");
			if(StringUtil.isNotEmptyString(str) && StringUtil.isEmptyString(decompressed)){
				LOG.info("解压出现异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			decompressed = null;
		}finally {
			if (gunzip != null) {
				try {
					gunzip.close();
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

	// 测试方法
	public static void main(String[] args) throws IOException{
		// 测试字符串
		String str = "[{\"tableName\":\"visiting_task_data\",\"rowNames\":\"visiting_task_data_id,client_user_id,pop_id,pop_type,in_time,visit_type,in_pic,in_longitude,in_latitude,in_locating_type,out_time,out_pic,out_longitude,out_latitude,out_locating_type,summary,remark,client_id,create_time,is_delete\",\"keyRowName\":\"visiting_task_data_id\",\"dataRows\":[\"[OTMyYTllNDgtOTYxZi00MzEyLWI2ZWQtYmYyMTE4MGQ1OGQw],8857,[ODA0Mjg4ZWYtODg4Yi00MTYzLWE4MzktZGU5OTg2ZTkxMGY4],1,'2015-05-05 09:22:19.0',3,,,,0,'2015-05-05 11:30:37.0',,,,0,,,4,'2015-05-05 09:22:19.0',0\"]},{\"tableName\":\"visiting_task_detail_data\",\"rowNames\":\"visiting_task_detail_data_id,visiting_task_data_id,visiting_task_step_id,object_id,target1_id,target2_id,visiting_parameter_id,value,client_id,create_time,is_delete\",\"keyRowName\":\"visiting_task_detail_data_id\",\"dataRows\":[\"[M2ZhZjM1NTAtOGU4Zi00OTQ4LWEwNTktODgwNmUxZDBjYzU5],[OTMyYTllNDgtOTYxZi00MzEyLWI2ZWQtYmYyMTE4MGQ1OGQw],9,,[Njk3],,65,[Mg==],4,'2015-05-05 11:30:32.0',0\"]},{\"tableName\":\"mobile_modules_config\",\"rowNames\":\"module_id,module_code,module_name,module_name_en,sequence,is_display,client_position_id,remark,create_time,is_delete\",\"keyRowName\":\"module_id\",\"dataRows\":[\"1,[TTE=],[6Zeo5bqX5L+h5oGv],[U3Ryb2UgSW5mbw==],1,1,65,,'2015-05-05 14:36:24.0',1\",\"2,[TTI=],[6Zeo5bqX5ouc6K6/],[U3RvcmUgVmlzaXQ=],2,1,65,,'2015-05-05 14:36:24.0',1\"]}]";

		System.out.println("原长度：" + str.length());
		System.out.println("压缩后长度：" + GZipCompress.compress(str).length() + "\n压缩后内容：" + GZipCompress.compress(str));
		System.out.println("解压缩长度：" + GZipCompress.uncompress(GZipCompress.compress(str)).length() + "\n解压缩内容：" + GZipCompress.uncompress(GZipCompress.compress(str)));
	}
}
