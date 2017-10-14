package cn.mobilizer.channel.report.po;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.comm.utils.json.JsonTool;

public class Func implements Serializable {

	private static final long serialVersionUID = 1L;

	private String viewName;
	private String id;
	private String name;
	private String confirmMsg;

	@SuppressWarnings("unchecked")
	public List<Func> transJSON(String json){
		List<Func> funcs = null;
		try {
			funcs = (List<Func>) JsonTool.transToList(json, Func.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return funcs;
	}
	
	public String getViewName() {
		return viewName;
	}
	
	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
