package com.utils;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.springframework.aop.support.AopUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String aa= "";
		StringBuilder  sb =new StringBuilder (aa);
		System.out.println(sb.insert(4,"-").insert(7,"-").toString());

	}
}

