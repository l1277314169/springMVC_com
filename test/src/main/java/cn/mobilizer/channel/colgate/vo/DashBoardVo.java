package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashBoardVo implements Serializable {

	private static final long serialVersionUID = -3896002787568070319L;

	private Integer items;
	private List<DataVo> dataVos = new ArrayList<DataVo>();

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public List<DataVo> getDataVos() {
		return dataVos;
	}

	public void setDataVos(List<DataVo> dataVos) {
		this.dataVos = dataVos;
	}

}
