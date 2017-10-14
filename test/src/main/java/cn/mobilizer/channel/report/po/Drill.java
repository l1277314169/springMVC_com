package cn.mobilizer.channel.report.po;

import java.io.Serializable;

public class Drill implements Serializable {

	private static final long serialVersionUID = 1L;

	private int partId; // 钻取报表id
	private String drillType; // 钻取得列
	private String isDrill; // 是否被钻取
	private String foreignName; // 钻取列明
	private String argName;
	
	private String foreignNamex; // 钻取列明
	private String argNamex;
	
	public String getForeignNamex() {
		return foreignNamex;
	}

	public void setForeignNamex(String foreignNamex) {
		this.foreignNamex = foreignNamex;
	}

	public String getArgNamex() {
		return argNamex;
	}

	public void setArgNamex(String argNamex) {
		this.argNamex = argNamex;
	}

	public String getArgName() {
		return argName;
	}

	public void setArgName(String argName) {
		this.argName = argName;
	}

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	@Override
	public String toString() {
		return ",partId=" + this.partId + ",drillType=" + this.drillType
				+ ",isDrill=" + this.isDrill;
	}

	public String getIsDrill() {
		return isDrill == null ? "" : isDrill;
	}

	public void setIsDrill(String isDrill) {
		this.isDrill = isDrill;
	}

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public String getDrillType() {
		return drillType;
	}

	public void setDrillType(String drillType) {
		this.drillType = drillType;
	}

}
