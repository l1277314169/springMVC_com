package cn.mobilizer.channel.comm.vo;

import java.io.Serializable;

public class Enum2Bean  implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -6687397545187026170L;

	private Integer id;

    private String name;

	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}