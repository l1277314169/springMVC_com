package cn.mobilizer.channel.survey.vo;

import java.util.List;

import cn.mobilizer.channel.survey.po.SurveyParameter;

public class Head {

	private List<SurveyParameter> parameterList; //参数
	private List<TheadCols> theadColsList; //表格标题列定义

	public List<SurveyParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<SurveyParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public List<TheadCols> getTheadColsList() {
		return theadColsList;
	}

	public void setTheadColsList(List<TheadCols> theadColsList) {
		this.theadColsList = theadColsList;
	}

}
