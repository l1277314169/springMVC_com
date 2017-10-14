package cn.mobilizer.channel.image.vo;

public class ColgateImageVo {

	private String imageName;
	private String imageShowName;
	private String storeName;
	private String addr;
	private String storeId;

	private String smallImagePath;
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
	}

	public String getImageShowName() {
		return imageShowName;
	}

	public void setImageShowName(String imageShowName) {
		this.imageShowName = imageShowName;
	}

	public String getSmallImagePath() {
		return smallImagePath;
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

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

}
