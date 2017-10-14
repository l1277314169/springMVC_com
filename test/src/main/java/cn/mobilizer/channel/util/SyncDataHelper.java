package cn.mobilizer.channel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import cn.mobilizer.channel.sync.po.SyncRequestEntity;
import cn.mobilizer.channel.sync.po.SyncTableData;
import cn.mobilizer.channel.sync.po.SyncTableDefined;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SyncDataHelper {
	private static final ObjectMapper mapper = new ObjectMapper();
	public static String serializeTableDefined(List<SyncTableDefined> tableDefinedList, String ztype){
		String tableStr = "";
		try {
			tableStr = mapper.writeValueAsString(tableDefinedList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String zipStr = "";
		if(SyncRequestEntity.ZTYPE_Z.equals(ztype)){
			zipStr = ZipCompress.compress(tableStr);
		}else if(SyncRequestEntity.ZTYPE_GZ.equals(ztype)){
			zipStr = GZipCompress.compress(tableStr);
		}else if(SyncRequestEntity.ZTYPE_N.equals(ztype)){
			try {
				zipStr = Base64.encodeBase64String(tableStr.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			try {
//				FileWriter fw = new FileWriter("/Users/Kris/Documents/zips/untableDefinedStrB.txt");
//				fw.write(new String(Base64.decodeBase64(zipStr),"UTF-8"));
//				fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		}else{
			zipStr = ZipCompress.compress(tableStr);
		}
		return zipStr;
	}
	
	public static String serializeTableData(List<SyncTableData> tableDataList, String ztype){
		String tableDataStr = "";
		try {
			tableDataStr = mapper.writeValueAsString(tableDataList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String zipStr = "";
		if(SyncRequestEntity.ZTYPE_Z.equals(ztype)){
			zipStr = ZipCompress.compress(tableDataStr);
		}else if(SyncRequestEntity.ZTYPE_GZ.equals(ztype)){
			zipStr = GZipCompress.compress(tableDataStr);
		}else if(SyncRequestEntity.ZTYPE_N.equals(ztype)){
//			FileWriter fw;
//			try {
//				fw = new FileWriter("/Users/Kris/Documents/zips/tableDataStrA.txt");
//				fw.write(tableDataStr);
//				fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			try {
				zipStr = Base64.encodeBase64String(tableDataStr.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			zipStr = ZipCompress.compress(tableDataStr);
		}
		return zipStr;
//		return ZipCompress.compress(tableStr);
	}
	
	public static List<SyncTableDefined> parseTableDefined(String str){
		String upzipStr = ZipCompress.decompress(str);
		List<SyncTableDefined> tlist = new ArrayList<SyncTableDefined>();
		try {
			if(!StringUtils.isEmpty(upzipStr))
				tlist = mapper.readValue(upzipStr, new TypeReference<List<SyncTableDefined>>() {});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tlist;
	}

	public static List<SyncTableData> parseTableData(String str, String ztype) throws JsonParseException, JsonMappingException, IOException{
		//需要同时满足Android和iOS的压缩适配，暂时提供一个N的不压缩算法
		 String upzipStr= decompress(str, ztype);
//		if(SyncRequestEntity.ZTYPE_Z.equals(ztype)){
//			upzipStr = ZipCompress.decompress(str);
//		}else if(SyncRequestEntity.ZTYPE_GZ.equals(ztype)){
//			upzipStr = GZipCompress.uncompress(str);
//		}else if(SyncRequestEntity.ZTYPE_N.equals(ztype)){
//			upzipStr = new String(Base64.decodeBase64(str),"UTF-8");
//		}else{
//			upzipStr = ZipCompress.decompress(str);
//		}
		List<SyncTableData> tlist = new ArrayList<SyncTableData>();
		if(!StringUtils.isEmpty(upzipStr))
			tlist = mapper.readValue(upzipStr, new TypeReference<List<SyncTableData>>() {});

		return tlist;
	}
	
	public static String encodeStringValue(String str){
		byte[] b = new byte[0];
		try {
			b = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[" + Base64.encodeBase64String(b) +"]";
	}
	
	public static String decodeStringValue(String baseStr){
		String realValue = null;
		if(baseStr.startsWith("[") && baseStr.endsWith("]")){
			String base64Str = baseStr.substring(1, baseStr.length() - 1);
			if(Base64.isBase64(base64Str)){
				byte[] bytes = Base64.decodeBase64(base64Str);
				try {
					realValue = new String(bytes, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		//防止分号影响sql 插入，替换'为空格
		realValue = realValue.replaceAll("'", " ");
		return realValue;
	}
	
	public static String decompress(String str, String ztype) throws IOException {
		String upzipStr = null;
		if(SyncRequestEntity.ZTYPE_Z.equals(ztype)){
			upzipStr = ZipCompress.decompress(str);
		}else if(SyncRequestEntity.ZTYPE_GZ.equals(ztype)){
			upzipStr = GZipCompress.uncompress(str);
		}else if(SyncRequestEntity.ZTYPE_N.equals(ztype)){
			upzipStr = new String(Base64.decodeBase64(str),"UTF-8");
		}else{
			upzipStr = ZipCompress.decompress(str);
		}
		return upzipStr;
	}
}
