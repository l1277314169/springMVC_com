package cn.mobilizer.channel.sync.po;

import java.io.Serializable;

////[{"is_primary_key":false,"Name":"city_id","Type":1,"Length":NULL},{"is_primary_key":false,"Name":"district_no","Type":4,"Length":10},{"is_primary_key":false,"Name":"district_en","Type":4,"Length":50},{"is_primary_key":false,"Name":"is_delete","Type":1,"Length":NULL},{"is_primary_key":true,"Name":"district_id","Type":1,"Length":NULL},{"is_primary_key":false,"Name":"district","Type":4,"Length":50},{"is_primary_key":false,"Name":"create_time","Type":3,"Length":NULL}]
public class TableColumnDefined implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -9183623631639907746L;
	private String name;
	private int type;//
	private int Length;
	private boolean isPrimaryKey;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLength() {
		return Length;
	}
	public void setLength(int length) {
		Length = length;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
}
