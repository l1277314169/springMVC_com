package cn.mobilizer.channel.base.vo;



import cn.mobilizer.channel.base.po.Client;

public class ClientVO extends Client {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4250067969486317815L;
	
	private String androidUrl;
	
	private String iphoneUrl;
	
	private String ipadUrl;

	
	public String getAndroidUrl(){
		return androidUrl;
	}

	
	public void setAndroidUrl(String androidUrl){
		this.androidUrl = androidUrl;
	}

	
	public String getIphoneUrl(){
		return iphoneUrl;
	}

	
	public void setIphoneUrl(String iphoneUrl){
		this.iphoneUrl = iphoneUrl;
	}

	
	public String getIpadUrl(){
		return ipadUrl;
	}

	
	public void setIpadUrl(String ipadUrl){
		this.ipadUrl = ipadUrl;
	}
    
	
}