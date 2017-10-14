package cn.mobilizer.channel.autho;

import org.apache.shiro.authc.AuthenticationException;

public class LoginPermissionException extends AuthenticationException {


	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8250111104022453337L;

	public LoginPermissionException() {  
        super();  
    }  
  
    public LoginPermissionException(String message, Throwable cause) {  
        super(message, cause);  
    }  
  
    public LoginPermissionException(String message) {  
        super(message);  
    }  
  
    public LoginPermissionException(Throwable cause) {  
        super(cause);  
    }  
}
