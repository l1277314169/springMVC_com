package cn.mobilizer.channel.survey.po;

import java.util.Date;

public class SurveyObjectGroup {
    
	private Integer objectGroupId;

    private String groupName;

    private Integer grade;

    private Integer parentId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private Integer subSurveyId;
    
    public Integer getSubSurveyId() {
		return subSurveyId;
	}

	public void setSubSurveyId(Integer subSurveyId) {
		this.subSurveyId = subSurveyId;
	}

	public Integer getObjectGroupId() {
        return objectGroupId;
    }

    public void setObjectGroupId(Integer objectGroupId) {
        this.objectGroupId = objectGroupId;
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
}