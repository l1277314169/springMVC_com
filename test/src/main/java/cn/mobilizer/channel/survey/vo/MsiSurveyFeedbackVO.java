package cn.mobilizer.channel.survey.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackDetail;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MsiSurveyFeedbackVO extends MsiSurveyFeedback implements Serializable{
  
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 3396471394515245959L;
	
	private String imageNames;

	private List<MsiSurveyFeedbackDetail> msiSurveyFeedbackDetails;
	
	private Map<Integer,String> questionImgMap;
	
	public String getImageNames(){
		return imageNames;
	}

	public void setImageNames(String imageNames){
		this.imageNames = imageNames;
	}

	public List<MsiSurveyFeedbackDetail> getMsiSurveyFeedbackDetails(){
		return msiSurveyFeedbackDetails;
	}
	
	public void setMsiSurveyFeedbackDetails(List<MsiSurveyFeedbackDetail> msiSurveyFeedbackDetails){
		this.msiSurveyFeedbackDetails = msiSurveyFeedbackDetails;
	}

	public Map<Integer, String> getQuestionImgMap() {
		return questionImgMap;
	}

	public void setQuestionImgMap(Map<Integer, String> questionImgMap) {
		this.questionImgMap = questionImgMap;
	}
	
}