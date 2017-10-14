package cn.mobilizer.channel.api.vo;

import cn.mobilizer.channel.sync.po.Constants;


public class ResultEntity implements Constants{
	private int resultCode;
	private String resultMSG;
	private long currentTime;
	public ResultEntity() {
		
	}
	public ResultEntity(boolean sucess) {
		if(sucess){
			resultCode = RESULT_SUCCESS;
			resultMSG = RESULT_SUCCESS_DESC;
		}else{
			resultCode = RESULT_FAILED;
			resultMSG = RESULT_FAILED_DESC;
		}
		currentTime = System.currentTimeMillis();
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		currentTime = System.currentTimeMillis();
		this.resultCode = resultCode;
	}
	public String getResultMSG() {
		return resultMSG;
	}
	public void setResultMSG(String resultMSG) {
		this.resultMSG = resultMSG;
	}
	public long getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

}
