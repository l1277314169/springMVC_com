package cn.mobilizer.channel.survey.po;

import java.util.Date;
import java.util.List;

import cn.mobilizer.channel.survey.vo.MsiQuestionVO;

public class MsiQuestionGroup {
    private Integer questionGroupId;

    private Integer msiSurveyId;

    private String groupName;

    private Integer grade;

    private Integer parentId;

    private Integer sequence;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private List<MsiQuestionGroup> childrenList;
    
    private List<MsiQuestionVO> msiQuestionVos;

    public Integer getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(Integer questionGroupId) {
        this.questionGroupId = questionGroupId;
    }

    public Integer getMsiSurveyId() {
        return msiSurveyId;
    }

    public void setMsiSurveyId(Integer msiSurveyId) {
        this.msiSurveyId = msiSurveyId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

	public List<MsiQuestionGroup> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<MsiQuestionGroup> childrenList) {
		this.childrenList = childrenList;
	}

	public List<MsiQuestionVO> getMsiQuestionVos() {
		return msiQuestionVos;
	}

	public void setMsiQuestionVos(List<MsiQuestionVO> msiQuestionVos) {
		this.msiQuestionVos = msiQuestionVos;
	}
}