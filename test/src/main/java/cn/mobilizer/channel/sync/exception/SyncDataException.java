package cn.mobilizer.channel.sync.exception;

public class SyncDataException extends RuntimeException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 9083067204032216358L;
	public int code;

	public SyncDataException(Throwable cause) {
		super(cause);
	}

	public SyncDataException(String detailMessage) {
		super(detailMessage);
	}
	
	public SyncDataException(String detailMessage,int code) {
		super(detailMessage);
		this.code=code;
	}
	
	public SyncDataException(String detailMessage, Throwable cause) {
		super(detailMessage, cause);
	}

	public SyncDataException(String detailMessage, Throwable cause,int code) {
		super(detailMessage, cause);
		this.code =code;
	}
}