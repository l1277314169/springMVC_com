package cn.mobilizer.channel.survey.vo;

import java.io.Serializable;
import java.util.List;

import cn.mobilizer.channel.survey.po.MsiAnswer;
import cn.mobilizer.channel.survey.po.MsiQuestion;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;
import cn.mobilizer.channel.survey.po.PhotoList;

public class MsiQuestionVO extends MsiQuestion implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5723410790333241157L;

	private List<MsiAnswer> msiAnswerList;
	private List<MsiQuestionVO> childrenList;
	private List<PhotoList> photoList;
	private List<MsiSurveyFeedbackAttachment> msiSurveyFeedbackAttachments;   //访问员附件
	private List<MsiSurveyFeedbackAttachment> appealSurveyFeedbackAttachments;   //申诉员附件
	private List<MsiAnswer> approvalDataList;
	private List<MsiAnswer> appealDataList;
//	Private List<图片>;
	
	public List<MsiAnswer> getMsiAnswerList(){
		return msiAnswerList;
	}

	public void setMsiAnswerList(List<MsiAnswer> msiAnswerList){
		this.msiAnswerList = msiAnswerList;
	}

	public List<MsiQuestionVO> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<MsiQuestionVO> childrenList) {
		this.childrenList = childrenList;
	}

	public List<PhotoList> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<PhotoList> photoList) {
		this.photoList = photoList;
	}

	public List<MsiSurveyFeedbackAttachment> getMsiSurveyFeedbackAttachments() {
		return msiSurveyFeedbackAttachments;
	}

	public void setMsiSurveyFeedbackAttachments(
			List<MsiSurveyFeedbackAttachment> msiSurveyFeedbackAttachments) {
		this.msiSurveyFeedbackAttachments = msiSurveyFeedbackAttachments;
	}

	public List<MsiAnswer> getApprovalDataList() {
		return approvalDataList;
	}

	public void setApprovalDataList(List<MsiAnswer> approvalDataList) {
		this.approvalDataList = approvalDataList;
	}

	public List<MsiAnswer> getAppealDataList() {
		return appealDataList;
	}

	public void setAppealDataList(List<MsiAnswer> appealDataList) {
		this.appealDataList = appealDataList;
	}

	public List<MsiSurveyFeedbackAttachment> getAppealSurveyFeedbackAttachments() {
		return appealSurveyFeedbackAttachments;
	}

	public void setAppealSurveyFeedbackAttachments(
			List<MsiSurveyFeedbackAttachment> appealSurveyFeedbackAttachments) {
		this.appealSurveyFeedbackAttachments = appealSurveyFeedbackAttachments;
	}
}