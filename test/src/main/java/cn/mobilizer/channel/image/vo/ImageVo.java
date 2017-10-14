package cn.mobilizer.channel.image.vo;

public class ImageVo {

	private String storeName;
	private String storeNo;
	private String imageName;

	/*private String smallImagePath;
	private String largeImagepath;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		String dir = FileUtils.getFileDirByName(getImageName());
		this.largeImagepath = dir;
		String smallDir = FileUtils.getFileDirByNameForSmall(getImageName());
		this.smallImagePath = smallDir;
	}*/
	
	
	

	public String getStoreName() {
		return storeName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

}
