package cn.mobilizer.channel.image.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.image.vo.FileUtils;
import cn.mobilizer.channel.image.vo.ImageGlobal;

public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	private String storeId;
	private String storeName;
	private Timestamp inTime;
	private String imageName;
	private String imageShowName; //图片显示名称
	private String parameterName;
	
	private String smallImagePath;
	private String largeImagepath;
	
	private String inPic;
	private String outPic;
	private String value;
	
	public List<String> getPictures(){
		List<String> pics = new ArrayList<String>();
		
		if(!StringUtil.isEmptyString(inPic)){
			pics.add(inPic+"@"+ImageGlobal.PicName.INPIC_NAME);
		}
		
		if(!StringUtil.isEmptyString(value)){
			String[] temp = value.split(",");
			for (String str : temp) {
				pics.add(str+"@"+parameterName);
			}
		}
		if(!StringUtil.isEmptyString(outPic)){
			pics.add(outPic+"@"+ImageGlobal.PicName.OUTPIC_NAME);
		}
		return pics;
	}
	
	public String getSmallImagePath() {
		return smallImagePath;
	}
	
	public String getImageShowName() {
		return imageShowName;
	}

	public void setImageShowName(String imageShowName) {
		this.imageShowName = imageShowName;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}

	public String getLargeImagepath() {
		return largeImagepath;
	}

	public void setLargeImagepath(String largeImagepath) {
		this.largeImagepath = largeImagepath;
	}

	@Override
	public int hashCode() {
		int result = 17;
        result = 37 * result + getImageName().hashCode();
        return result; 
	}

	@Override
	public boolean equals(Object obj) {
	    if(obj == null){
	    	return false;
	    }
	    if(this == obj){
			return true;
		}
	    if(getClass() != obj.getClass()){
	    	return false;
	    }
	    if(null == getImageName()){
	    	return false;
	    }
	    Image other = (Image)obj;
	    if(null==other.getImageName()){
	    	return false;
	    }
	    return getImageName().equals(other.getImageName());
	    //return new EqualsBuilder().append(getValue(), other.getValue()). isEquals();
	}

	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
		String dir = FileUtils.getFileDirByName(getImageName());
		this.largeImagepath = dir;
		String smallDir = FileUtils.getFileDirByNameForSmall(getImageName());
		this.smallImagePath = smallDir;
	}


	public Timestamp getInTime() {
		return inTime;
	}

	public String getInPic() {
		return inPic;
	}

	public void setInPic(String inPic) {
		this.inPic = inPic;
	}

	public String getOutPic() {
		return outPic;
	}

	public void setOutPic(String outPic) {
		this.outPic = outPic;
	}

	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

}