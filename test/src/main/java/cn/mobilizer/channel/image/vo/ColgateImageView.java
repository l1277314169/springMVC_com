package cn.mobilizer.channel.image.vo;

import java.util.List;

public class ColgateImageView {

	private String storeNameAndAddr;
	private List<ColgateImage> images;

	public String getStoreNameAndAddr() {
		return storeNameAndAddr;
	}

	public void setStoreNameAndAddr(String storeNameAndAddr) {
		this.storeNameAndAddr = storeNameAndAddr;
	}

	public List<ColgateImage> getImages() {
		return images;
	}

	public void setImages(List<ColgateImage> images) {
		this.images = images;
	}

}
