package cn.mobilizer.channel.ctTask.po;

import java.util.Date;
import java.util.List;

public class CtTaskData {
    private String ctTaskDataId;

    private Integer clientUserId;

    private String popId;

    private Byte popType;

    private Integer ctTaskId;

    private Date startTime;

    private Date endTime;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private List<CtTaskDetailData> ctTaskDetailDatas;
    
    private String imageNames;
    
    private List<CtTaskDataAttachment> ctTaskDataAttachments;
    
    private String visitDate;				//高露洁访问日期字段
    
    private Integer lastUpdateBy;
    
    private Integer createBy;
    
    public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Integer lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getCtTaskDataId() {
        return ctTaskDataId;
    }

    public void setCtTaskDataId(String ctTaskDataId) {
        this.ctTaskDataId = ctTaskDataId == null ? null : ctTaskDataId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId == null ? null : popId.trim();
    }

    public Byte getPopType() {
        return popType;
    }

    public void setPopType(Byte popType) {
        this.popType = popType;
    }

    public Integer getCtTaskId() {
        return ctTaskId;
    }

    public void setCtTaskId(Integer ctTaskId) {
        this.ctTaskId = ctTaskId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
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

	public List<CtTaskDetailData> getCtTaskDetailDatas() {
		return ctTaskDetailDatas;
	}

	public void setCtTaskDetailDatas(List<CtTaskDetailData> ctTaskDetailDatas) {
		this.ctTaskDetailDatas = ctTaskDetailDatas;
	}

	public String getImageNames() {
		return imageNames;
	}

	public void setImageNames(String imageNames) {
		this.imageNames = imageNames;
	}

	public List<CtTaskDataAttachment> getCtTaskDataAttachments() {
		return ctTaskDataAttachments;
	}

	public void setCtTaskDataAttachments(
			List<CtTaskDataAttachment> ctTaskDataAttachments) {
		this.ctTaskDataAttachments = ctTaskDataAttachments;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
}