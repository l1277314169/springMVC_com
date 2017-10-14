package cn.mobilizer.channel.api.vo;

import java.util.List;

public class ResultEntityWithList<T> extends ResultEntity{
	public ResultEntityWithList() {
		super();
	}
	public ResultEntityWithList(boolean sucess) {
		super(sucess);
	}
	private List<T> dataInfo;

	public List<T> getDataInfo() {
		return dataInfo;
	}
	public void setDataInfo(List<T> dataInfo) {
		this.dataInfo = dataInfo;
	}

}
