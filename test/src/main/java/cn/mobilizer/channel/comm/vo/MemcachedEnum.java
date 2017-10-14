package cn.mobilizer.channel.comm.vo;

public enum MemcachedEnum {

	
	
	/**
	 * 用户
	 */
	ClientUser("ChBasicClientUser_", 24 * 60 * 60),
	
	/**
	 * 用户数据权限
	 */
	ClientUserPerData("ChUserPerData_", 24 * 60 * 60),
	
	/**
	 * 门店
	 */
	Store("ChBasicStore_", 24 * 60 * 60);

	private String key;
	private int sec;

	MemcachedEnum(String key, int sec) {
		this.key = key;
		this.sec = sec;
	}

	public String getKey() {
		return key;
	}

	public int getSec() {
		return this.sec;
	}
}
