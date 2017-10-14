package cn.mobilizer.channel.specialTask.vo;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.specialTask.po.SpecialTask;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;

public class SpecialTaskVO extends SpecialTask implements Serializable {


	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2474091422370135713L;

	private Integer clientId;

	private Integer clientUserId;
	
    private Integer total;
    
    private Integer perform;
    /**	专项任务参数定义*/
    private List<SpecialTaskParameter> specialTaskParameterlist;

	/**专项任务对象*/
    private String  objectIds;

	/**专项任务与职位映射*/
    private Integer clientPositionId;

	
	public Integer getClientId(){
		return clientId;
	}

	
	public void setClientId(Integer clientId){
		this.clientId = clientId;
	}

	
	public Integer getClientUserId(){
		return clientUserId;
	}

	
	public void setClientUserId(Integer clientUserId){
		this.clientUserId = clientUserId;
	}

	public List<SpecialTaskParameter> getSpecialTaskParameterlist() {
		return specialTaskParameterlist;
	}

	public void setSpecialTaskParameterlist(
			List<SpecialTaskParameter> specialTaskParameterlist) {
		this.specialTaskParameterlist = specialTaskParameterlist;
	}

	public Integer getClientPositionId() {
		return clientPositionId;
	}

	public void setClientPositionId(Integer clientPositionId) {
		this.clientPositionId = clientPositionId;
	}


	public String getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPerform() {
		return perform;
	}

	public void setPerform(Integer perform) {
		this.perform = perform;
	}
}