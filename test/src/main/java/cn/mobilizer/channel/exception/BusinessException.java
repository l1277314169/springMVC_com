package cn.mobilizer.channel.exception;

public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7803845205542076055L;
	private String message;

	public BusinessException(String message, String... paras) {
		this.message = String.format(message, paras);
	}

	public BusinessException(String message) {
//		super(message);
		this.message = message;
	}
	public BusinessException(String message,Throwable throwable) {
		super(message, throwable);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
