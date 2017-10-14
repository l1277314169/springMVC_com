package cn.mobilizer.channel.survey.vo;

import java.util.List;

import cn.mobilizer.channel.survey.po.SurveyObject;

public class SurveyObjectVo {

	private String groupName;
	private String parentId;
	private Integer grade;
	private List<SurveyObject> surveyObjects;

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		if (groupName.equalsIgnoreCase("null")) {
			groupName = "";
		}
		this.groupName = groupName == null ? "" : groupName.trim();
	}

	public List<SurveyObject> getSurveyObjects() {
		return surveyObjects;
	}

	public void setSurveyObjects(List<SurveyObject> surveyObjects) {
		this.surveyObjects = surveyObjects;
	}

}
