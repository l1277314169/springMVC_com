package cn.mobilizer.channel.base.vo;


import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.base.po.ClientUser;

public class ClientUserVO implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6479198922642584303L;
   
	private Integer page;
	private List<ClientUser> items;
	
	public Integer getPage(){
		return page;
	}
	
	public void setPage(Integer page){
		this.page = page;
	}

	public List<ClientUser> getItems(){
		return items;
	}
	
	public void setItems(List<ClientUser> items){
		this.items = items;
	}

}