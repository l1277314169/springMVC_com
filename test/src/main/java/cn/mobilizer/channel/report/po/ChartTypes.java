package cn.mobilizer.channel.report.po;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.comm.utils.json.JsonTool;

public class ChartTypes implements Serializable {

	private static final long serialVersionUID = 1L;

	private String charType;

	@SuppressWarnings("unchecked")
	public List<ChartTypes> transJSON(String json) {
		List<ChartTypes> charTypes = null;
		try {
			charTypes = (List<ChartTypes>) JsonTool.transToList(json,ChartTypes.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return charTypes;
	}

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

}
