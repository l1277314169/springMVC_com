package cn.mobilizer.channel.base.exception;

public class ImprotException extends RuntimeException {
	private String msg;

	public ImprotException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
