package cn.mobilizer.channel.util;

import cn.mobilizer.channel.autho.ShiroConstants;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.security.utils.Digests;
import cn.mobilizer.channel.comm.utils.Encodes;

public class PasswordHelper {
	private static final int SALT_SIZE = 8;
	
	public static String encodePasswd(String plainpw,String saltStr){
		byte[] salt = Encodes.decodeHex(saltStr);
		byte[] hashPassword = Digests.sha1(plainpw.getBytes(), salt, ShiroConstants.HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}

	public static void entryptPassword(ClientUser clientUser) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		clientUser.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(clientUser.getPlainPassword().getBytes(), salt, ShiroConstants.HASH_INTERATIONS);
		clientUser.setLoginPwd(Encodes.encodeHex(hashPassword));
	}
	
	public static boolean checkPasswd(ClientUser clientUser, String painPasswd){
		String enpasswd = encodePasswd(painPasswd, clientUser.getSalt());
		if(enpasswd.equals(clientUser.getLoginPwd()))
			return true;
		else
			return false;
	}
	
	/**
	 * 判断输入的密码是否和数据库中保存的密码一致
	 * @param truePasswd 数据库中保存的密码
	 * @param salt 数据中salt值
	 * @param thisPasswd 当前需要确认的密码
	 * @return
	 */
	public static boolean checkPasswd(String truePasswd, String salt, String thisPasswd){
		String enpasswd = encodePasswd(thisPasswd, salt);
		if(enpasswd.equals(truePasswd))
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		ClientUser clientUser =new ClientUser();
		clientUser.setPlainPassword(8888+"");
		entryptPassword(clientUser);
	}
}
