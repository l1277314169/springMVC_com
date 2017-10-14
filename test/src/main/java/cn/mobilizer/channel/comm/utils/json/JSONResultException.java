/**
 * 
 */
package cn.mobilizer.channel.comm.utils.json;

@SuppressWarnings("serial")
public class JSONResultException extends Exception {

	private int errorCode;
	
	public JSONResultException(int code,String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.errorCode=code;
	}

	public JSONResultException(String message)
	{
		this(-1,message);
	}
	
	

	public JSONResultException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
		this.errorCode=-1;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
