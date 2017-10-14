package cn.mobilizer.channel.util;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.security.utils.Digests;
import cn.mobilizer.channel.comm.utils.Encodes;

public class Encipher {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	public static String entryptPassword(String passwd) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
//		System.out.println(Encodes.encodeHex(salt));
		//user.setSalt(Encodes.encodeHex(salt));
		String st = "7efbd59d9741d34f";
		byte[] hashPassword = Digests.sha1(passwd.getBytes(), st.getBytes(), HASH_INTERATIONS);
		//user.setPassword(Encodes.encodeHex(hashPassword));
		return Encodes.encodeHex(hashPassword);
	}
	
	public static boolean passwdMatcher(ClientUser user, String passwd){
		
		return true;
	}

	public static void main(String[] args) {
		System.out.println(entryptPassword("admin"));
	}
	
}
