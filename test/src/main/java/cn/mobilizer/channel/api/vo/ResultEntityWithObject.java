package cn.mobilizer.channel.api.vo;


public class ResultEntityWithObject<T> extends ResultEntity{
	public ResultEntityWithObject() {
		super();
	}
	public ResultEntityWithObject(boolean sucess) {
		super(sucess);
	}
	private T dataInfo;
	public T getDataInfo() {
		return dataInfo;
	}
	public void setDataInfo(T dataInfo) {
		this.dataInfo = dataInfo;
	}
}
