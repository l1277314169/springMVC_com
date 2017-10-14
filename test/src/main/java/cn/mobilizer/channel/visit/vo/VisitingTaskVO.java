package cn.mobilizer.channel.visit.vo;

import java.util.List;

import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.po.VisitingTaskStep;

public class VisitingTaskVO extends VisitingTask {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3838527653178572895L;

    private Integer channelId;
	private List<VisitingTaskStep> visitingTaskSteps;
    private String storeParameterDatas;
    private String distributorParameterDatas;
    private String rolesParameterDatas;
	
	public Integer getChannelId(){
		return channelId;
	}

	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	}
    
	public List<VisitingTaskStep> getVisitingTaskSteps(){
		return visitingTaskSteps;
	}

	public void setVisitingTaskSteps(List<VisitingTaskStep> visitingTaskSteps){
		this.visitingTaskSteps = visitingTaskSteps;
	}

	public String getStoreParameterDatas(){
		return storeParameterDatas;
	}

	public void setStoreParameterDatas(String storeParameterDatas){
		this.storeParameterDatas = storeParameterDatas;
	}
	
	public String getDistributorParameterDatas(){
		return distributorParameterDatas;
	}

	public void setDistributorParameterDatas(String distributorParameterDatas){
		this.distributorParameterDatas = distributorParameterDatas;
	}

	public String getRolesParameterDatas() {
		return rolesParameterDatas;
	}

	public void setRolesParameterDatas(String rolesParameterDatas) {
		this.rolesParameterDatas = rolesParameterDatas;
	}

}