package cn.mobilizer.channel.base.vo;

import java.io.Serializable;

public class StoreSearchVO implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6557005239660941556L;
	
	private Integer storeId;
	
	private String strStoreId;

	private String storeName;

	private Integer channelId;
	
	private String channelName;
	
	private String storeNo;
	
	private Integer clientUserId;
	
	private String clientUserName;
	
	private Integer clientStructureId;
	
	private String structureName;
	
	private Integer distributorId;
	
	private String distributorName;
	
	private Byte visitType;
	
	private Byte status;
	
	
	
	public Integer getStoreId() {
		return storeId;
	}


	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}


	public String getStoreName(){
		return storeName;
	}

	
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	

	
	public String getChannelName(){
		return channelName;
	}

	
	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	
	public String getStoreNo(){
		return storeNo;
	}

	
	public void setStoreNo(String storeNo){
		this.storeNo = storeNo;
	}

	
	public Integer getClientUserId(){
		return clientUserId;
	}

	
	public void setClientUserId(Integer clientUserId){
		this.clientUserId = clientUserId;
	}


	public String getDistributorName(){
		return distributorName;
	}

	
	public void setDistributorName(String distributorName){
		this.distributorName = distributorName;
	}


	public String getClientUserName() {
		return clientUserName;
	}


	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}


	public Integer getClientStructureId() {
		return clientStructureId;
	}


	public void setClientStructureId(Integer clientStructureId) {
		this.clientStructureId = clientStructureId;
	}


	public Integer getChannelId() {
		return channelId;
	}


	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}


	public Integer getDistributorId() {
		return distributorId;
	}


	public void setDistributorId(Integer distributorId) {
		this.distributorId = distributorId;
	}


	public Byte getVisitType() {
		return visitType;
	}


	public void setVisitType(Byte visitType) {
		this.visitType = visitType;
	}


	public Byte getStatus() {
		return status;
	}


	public void setStatus(Byte status) {
		this.status = status;
	}
	
	
	public String getStructureName(){
		return structureName;
	}

	
	public void setStructureName(String structureName){
		this.structureName = structureName;
	}


	public String getStrStoreId() {
		return strStoreId;
	}


	public void setStrStoreId(String strStoreId) {
		this.strStoreId = strStoreId;
	}
	

}