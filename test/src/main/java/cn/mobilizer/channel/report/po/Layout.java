package cn.mobilizer.channel.report.po;

import java.io.Serializable;

import cn.mobilizer.channel.comm.utils.StringUtil;

public class Layout implements Serializable {

	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_HEIGHT = 250;
	private int x = 1;
	private int y = 1;
	private int width = 100;
	private int height = DEFAULT_HEIGHT;

	public Layout(String layout) {
		if(!StringUtil.isEmptyString(layout)){
			String[] array = layout.split("\\*");
			this.x = Integer.parseInt(array[0]);
			this.y = Integer.parseInt(array[1]);
//			this.width = (this.width/this.x);
			this.width = this.x;
			this.height = this.y * this.height;
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
