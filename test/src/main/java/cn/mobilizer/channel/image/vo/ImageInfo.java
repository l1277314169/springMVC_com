package cn.mobilizer.channel.image.vo;

import java.io.Serializable;

public class ImageInfo implements Serializable {

	private static final long serialVersionUID = -3940214752454385700L;

	private double width;
	private double height;
	private String path;
	private String size;

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
