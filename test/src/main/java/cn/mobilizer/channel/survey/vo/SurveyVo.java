package cn.mobilizer.channel.survey.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mobilizer.channel.survey.po.SurveyObject;

public class SurveyVo {

	private Head head; // 表头
	private List<SurveyObjectVo> surveyObjectVos = new ArrayList<SurveyObjectVo>(); // object对象
	private Integer surveySubId;
	private String surveySubName;
	private Byte subSurveyType;
	private List<SurveyObject> surveyObjects; // 没有组的object对象

	public List<SurveyObjectVo> getSurveyObjectVos() {
		return surveyObjectVos;
	}

	public void setSurveyObjectVos(Map<String, List<SurveyObject>> surObjectMap) {
		if(null== surObjectMap || surObjectMap.isEmpty()){
			return;
		}
		Set<String> keys = surObjectMap.keySet();
		for (String key : keys) {
			List<SurveyObject> sov = surObjectMap.get(key);
			SurveyObjectVo sv = new SurveyObjectVo();
			String[] temp = key.split("@");
			sv.setGroupName(temp[0]);
			sv.setParentId(temp[1]);
			sv.setGrade(Integer.parseInt(temp[2]));
			sv.setSurveyObjects(sov);
			this.surveyObjectVos.add(sv);
		}
	}
	
	
	public Byte getSubSurveyType() {
		return subSurveyType;
	}

	public void setSubSurveyType(Byte subSurveyType) {
		this.subSurveyType = subSurveyType;
	}

	public List<SurveyObject> getSurveyObjects() {
		return surveyObjects;
	}

	public void setSurveyObjects(List<SurveyObject> surveyObjects) {
		this.surveyObjects = surveyObjects;
	}

	public String getSurveySubName() {
		return surveySubName;
	}

	public void setSurveySubName(String surveySubName) {
		this.surveySubName = surveySubName;
	}

	public Integer getSurveySubId() {
		return surveySubId;
	}

	public void setSurveySubId(Integer surveySubId) {
		this.surveySubId = surveySubId;
	}

	public void setSurveyObjectVos(List<SurveyObjectVo> surveyObjectVos) {
		this.surveyObjectVos = surveyObjectVos;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

}
