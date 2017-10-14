package cn.mobilizer.channel.survey.po;

public class MsiSurveyFinishCount {
	
	private String name;            //问卷名称
	private Integer surveyType;     //问卷类型
	private Integer count;       //完成次数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(Integer surveyType) {
		this.surveyType = surveyType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
