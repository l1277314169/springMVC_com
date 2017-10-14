package cn.mobilizer.channel.export.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.mobilizer.channel.report.po.Column;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.vo.ReportGlobal;

public class DataInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> heads;
	private List<TreeMap<String, DataVo>> values;
	private String sheetName;
	
	private int items; //导出报表时不需要
	
	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public int getColIndex(String colName){
		int item = -1 ;
		for (int i = 0; i < heads.size(); i++) {
			if(colName.equals(heads.get(i))){
				item = i;
				break;
			}
		}
		return item;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String> getHeads() {
		return heads;
	}

	public void setHeadsForColumn(List<Column> heads) {
		this.heads = new ArrayList<String>();
		for (Column column : heads) {
			if(column.getHide()!=ReportGlobal.HIDE){
				this.heads.add(column.getDesc());
			}
		}
	}
	
	public void setHeads(List<String> heads) {
		this.heads = heads;
	}

	public List<TreeMap<String, DataVo>> getValues() {
		return values;
	}

	public void setValues(List<TreeMap<String, DataVo>> values) {
		this.values = values;
	}

}
