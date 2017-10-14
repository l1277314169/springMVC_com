package cn.mobilizer.channel.survey.vo;

import java.io.Serializable;
import java.util.List;

public class SurveyExportVo implements Serializable {

	private static final long serialVersionUID = 4201587558914294018L;

	private List<String> heads;
	private List<List<String>> dataList;
	private String sheetName;
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String> getHeads() {
		return heads;
	}

	public void setHeads(List<String> heads) {
		this.heads = heads;
	}

	public List<List<String>> getDataList() {
		return dataList;
	}

	public void setDataList(List<List<String>> dataList) {
		this.dataList = dataList;
	}

}
