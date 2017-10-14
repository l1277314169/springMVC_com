package cn.mobilizer.channel.autho;

import org.apache.shiro.authc.AuthenticationException;

public class LoginAuthenticationException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6667522586656970512L;

	public LoginAuthenticationException() {  
        super();  
    }  
  
    public LoginAuthenticationException(String message, Throwable cause) {  
        super(message, cause);  
    }  
  
    public LoginAuthenticationException(String message) {  
        super(message);  
    }  
  
    public LoginAuthenticationException(Throwable cause) {  
        super(cause);  
    }  
}
