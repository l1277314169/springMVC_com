package cn.mobilizer.channel.mobile.po;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class ClientUserStoreScore implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5143649279748539483L;
	
	private Integer clientUserId;
	
	private Integer clientId;
	
	private String storeNo;
	
	private String name;
	
	private String storeName;
    
    private String channelName;;

    private BigDecimal actualScore;
    
    private BigDecimal toothpasteDistTarget;
    
    private BigDecimal toothpasteDistActual;
    
    private BigDecimal toothbrushDistTarget;
    
    private BigDecimal toothbrushDistActual;
    
    private BigDecimal toothpasteDistribution;
    
    private BigDecimal toothbrushDistribution;
    
    private BigDecimal toothpasteSos;
    
    private BigDecimal toothbrushSos;
    
    private BigDecimal toothpaste5p;
    
    private BigDecimal toothbrush5p;
    
    private BigDecimal toothpaste2nd;
    
    private BigDecimal toothbrush2nd;
    
    private BigDecimal regimenDisplay;
    
	public Integer getClientUserId(){
		return clientUserId;
	}
	
	public void setClientUserId(Integer clientUserId){
		this.clientUserId = clientUserId;
	}

	public Integer getClientId(){
		return clientId;
	}

	public void setClientId(Integer clientId){
		this.clientId = clientId;
	}

	public String getStoreNo(){
		return storeNo;
	}
	
	public void setStoreNo(String storeNo){
		this.storeNo = storeNo;
	}
	
	public String getStoreName(){
		return storeName;
	}

	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public BigDecimal getActualScore(){
		return actualScore;
	}

	public void setActualScore(BigDecimal actualScore){
		this.actualScore = actualScore;
	}
	
	public BigDecimal getToothpasteDistribution(){
		return toothpasteDistribution;
	}
	
	public void setToothpasteDistribution(BigDecimal toothpasteDistribution){
		this.toothpasteDistribution = toothpasteDistribution;
	}

	public BigDecimal getToothbrushDistribution(){
		return toothbrushDistribution;
	}
	
	public void setToothbrushDistribution(BigDecimal toothbrushDistribution){
		this.toothbrushDistribution = toothbrushDistribution;
	}
	
	public BigDecimal getToothpasteSos(){
		return toothpasteSos;
	}

	public void setToothpasteSos(BigDecimal toothpasteSos){
		this.toothpasteSos = toothpasteSos;
	}
	
	public BigDecimal getToothbrushSos(){
		return toothbrushSos;
	}

	public void setToothbrushSos(BigDecimal toothbrushSos){
		this.toothbrushSos = toothbrushSos;
	}

	public BigDecimal getToothpaste5p(){
		return toothpaste5p;
	}
	
	public void setToothpaste5p(BigDecimal toothpaste5p){
		this.toothpaste5p = toothpaste5p;
	}
	
	public BigDecimal getToothbrush5p(){
		return toothbrush5p;
	}

	public void setToothbrush5p(BigDecimal toothbrush5p){
		this.toothbrush5p = toothbrush5p;
	}
	
	public BigDecimal getToothpaste2nd(){
		return toothpaste2nd;
	}
	
	public void setToothpaste2nd(BigDecimal toothpaste2nd){
		this.toothpaste2nd = toothpaste2nd;
	}
	
	public BigDecimal getToothbrush2nd(){
		return toothbrush2nd;
	}
	
	public void setToothbrush2nd(BigDecimal toothbrush2nd){
		this.toothbrush2nd = toothbrush2nd;
	}
	
	public BigDecimal getRegimenDisplay(){
		return regimenDisplay;
	}

	public void setRegimenDisplay(BigDecimal regimenDisplay){
		this.regimenDisplay = regimenDisplay;
	}

	public static long getSerialversionuid(){
		return serialVersionUID;
	}

	public String getChannelName(){
		return channelName;
	}

	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	public BigDecimal getToothpasteDistTarget(){
		return toothpasteDistTarget;
	}

	public void setToothpasteDistTarget(BigDecimal toothpasteDistTarget){
		this.toothpasteDistTarget = toothpasteDistTarget;
	}
	
	public BigDecimal getToothpasteDistActual(){
		return toothpasteDistActual;
	}
	
	public void setToothpasteDistActual(BigDecimal toothpasteDistActual){
		this.toothpasteDistActual = toothpasteDistActual;
	}
	
	public BigDecimal getToothbrushDistTarget(){
		return toothbrushDistTarget;
	}
	
	public void setToothbrushDistTarget(BigDecimal toothbrushDistTarget){
		this.toothbrushDistTarget = toothbrushDistTarget;
	}
	
	public BigDecimal getToothbrushDistActual(){
		return toothbrushDistActual;
	}

	public void setToothbrushDistActual(BigDecimal toothbrushDistActual){
		this.toothbrushDistActual = toothbrushDistActual;
	}
    
}