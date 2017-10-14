package cn.mobilizer.channel.base.vo;

import java.io.Serializable;


public class TreeNodeVO implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -1819840238733594586L;

	private Integer id;

    private Integer pId;

    private String name;
    
    private String isParent;
    
    private String open;
    
    private Integer cId;
    
    private Integer parentId;
    
    private String checked;
    
	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getpId(){
		return pId;
	}

	public void setpId(Integer pId){
			this.pId = pId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public Integer getcId(){
		return cId;
	}

	public void setcId(Integer cId){
		this.cId = cId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}
	
	public String getChecked(){
		return checked;
	}

	public void setChecked(String checked){
		this.checked = checked;
	}

}