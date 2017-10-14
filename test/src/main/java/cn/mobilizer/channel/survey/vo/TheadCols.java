package cn.mobilizer.channel.survey.vo;

public class TheadCols {

	private String colName;
	private int sequence;
	private String foreignCol;
	private String width;

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getForeignCol() {
		return foreignCol;
	}

	public void setForeignCol(String foreignCol) {
		this.foreignCol = foreignCol;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
