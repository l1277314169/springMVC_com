package cn.mobilizer.channel.util;

public final class ErrorCodeMsg {

	//
	private ErrorCodeMsg() {

	}

	public static final String ERR_FAIL = "失败";
	// 系统内部异常
	public static final String ERR_SYS = "系统内部异常！";
	//登录失败，客户名称错误
	public static final String CLIENT_NAME = "登录失败，客户名称错误！";
	//登录失败，客户名称错误
	public static final String LOGIN_PERMISSION = "您没有登录权限！";
	//登录失败，用户名密码错误
	public static final String USERNAME_OR_PASSWORD = "登录失败，用户名密码错误！";
	//新增失败
	public static final String ERR_ADD = "新增失败";
	//查询失败
	public static final String ERR_QUERY = "查询出现异常！";
	//更新失败
	public static final String ERR_UPDATE = "更新失败！";
	//删除失败
	public static final String ERR_DELETE = "删除失败！";
	//比对失败
	public static final String ERR_COMPARE = "数据比对失败！";
	//数据同步失败
	public static final String ERR_SYNC_TABLEDATA_SEND = "数据下发失败";

}
