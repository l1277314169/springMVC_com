package cn.mobilizer.channel.base.vo;

import java.io.Serializable;

public class ImportVO implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1954980221538285098L;
	
	private Boolean result;
	private String msg;
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
