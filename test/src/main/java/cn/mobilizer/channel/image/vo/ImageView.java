package cn.mobilizer.channel.image.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.mobilizer.channel.image.po.Image;

public class ImageView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String showDate;
	private List<Image> images;
	private int size;
	
	public int getSize() {
		this.size = images.size();
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	public void addImage(Image image){
		if(null==images){
			images = new ArrayList<Image>();
		}
		if(!images.contains(image)){
			images.add(image);
		}
	}

}
