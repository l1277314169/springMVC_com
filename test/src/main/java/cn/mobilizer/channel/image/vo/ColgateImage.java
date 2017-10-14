package cn.mobilizer.channel.image.vo;

import java.util.ArrayList;
import java.util.List;

import cn.mobilizer.channel.comm.utils.StringUtil;

public class ColgateImage {

	private String storeName;
	private String addr;
	private Integer objectId;
	private String value;
	private String storeId;
	private String imageName;
	
	public List<String> getPictures(){
		List<String> imgList = new ArrayList<String>();
		if(!StringUtil.isEmptyString(getValue())){
			String[] temp = getValue().split(",");
			for (String img : temp) {
				imgList.add(img);
			}
		}
		return imgList;
	}
	
	public String getImageName() {
		if (this.objectId==99) {
			this.imageName = "门头照";
		} else if (this.objectId==248) {
			this.imageName = "主货架";
		} else if (this.objectId==249) {
			this.imageName = "二级陈列";
		} else if (this.objectId==250) {
			this.imageName = "其他";
		}
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStoreId() {
		return storeId;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

}
