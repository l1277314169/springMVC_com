package cn.mobilizer.channel.autho;

import org.apache.shiro.authc.UsernamePasswordToken;

public class LoginToken extends UsernamePasswordToken {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3300143319888015359L;
	private String clientName;
	private Integer clientId;
	
	private String loginType; //登录成功后放回地址
	private String onLoginType ; // 登录失败后返回的链接地址
	

	public String getOnLoginType() {
		return onLoginType;
	}

	public void setOnLoginType(String onLoginType) {
		this.onLoginType = onLoginType;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public LoginToken(String username, char[] password, boolean rememberMe, String host, String clientName) {
		super(username, password, rememberMe, host);
		this.clientName = clientName;
	}
	
	public LoginToken(String username, char[] password, boolean rememberMe, String host, String clientName, Integer clientId) {
		super(username+ShiroConstants.CONNECT_STR+clientId, password, rememberMe, host);
		this.clientName = clientName;
		this.clientId = clientId;
	}
}
