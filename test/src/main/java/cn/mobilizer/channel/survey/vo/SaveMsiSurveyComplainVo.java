package cn.mobilizer.channel.survey.vo;

import java.util.List;

import cn.mobilizer.channel.survey.po.MsiSurveyComplain;

public class SaveMsiSurveyComplainVo {
	
	private List<MsiSurveyComplain> msiSurveyComplains;
	
	private String imageNames;
	
	private String feedbackId;

	public List<MsiSurveyComplain> getMsiSurveyComplains() {
		return msiSurveyComplains;
	}

	public void setMsiSurveyComplains(List<MsiSurveyComplain> msiSurveyComplains) {
		this.msiSurveyComplains = msiSurveyComplains;
	}

	public String getImageNames() {
		return imageNames;
	}

	public void setImageNames(String imageNames) {
		this.imageNames = imageNames;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	
}
