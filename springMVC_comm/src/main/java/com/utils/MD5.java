package com.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.MessageDigest;

public class MD5 {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	public static void main(String[] args) {
//		System.out.println(md5("31119@qq.com"+"123456"));
//		System.out.println(md5("mj1"));
		String salt = "hello";
		String password = null;
		try {
			password = new Md5Hash("123", salt).toString();
			System.out.println(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
