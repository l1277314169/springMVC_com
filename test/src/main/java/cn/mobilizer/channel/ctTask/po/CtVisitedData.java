package cn.mobilizer.channel.ctTask.po;

import java.util.Date;

public class CtVisitedData {
    private String ctVisitedDataId;

    private Integer clientUserId;

    private String popId;

    private Byte popType;

    private Date inTime;

    private String inPic;

    private String inLongitude;

    private String inLatitude;

    private Byte inLocatingType;

    private Date outTime;

    private String outPic;

    private String outLongitude;

    private String outLatitude;

    private Byte outLocatingType;

    private String summary;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public String getCtVisitedDataId() {
        return ctVisitedDataId;
    }

    public void setCtVisitedDataId(String ctVisitedDataId) {
        this.ctVisitedDataId = ctVisitedDataId == null ? null : ctVisitedDataId.trim();
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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getInPic() {
        return inPic;
    }

    public void setInPic(String inPic) {
        this.inPic = inPic == null ? null : inPic.trim();
    }

    public String getInLongitude() {
        return inLongitude;
    }

    public void setInLongitude(String inLongitude) {
        this.inLongitude = inLongitude == null ? null : inLongitude.trim();
    }

    public String getInLatitude() {
        return inLatitude;
    }

    public void setInLatitude(String inLatitude) {
        this.inLatitude = inLatitude == null ? null : inLatitude.trim();
    }

    public Byte getInLocatingType() {
        return inLocatingType;
    }

    public void setInLocatingType(Byte inLocatingType) {
        this.inLocatingType = inLocatingType;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getOutPic() {
        return outPic;
    }

    public void setOutPic(String outPic) {
        this.outPic = outPic == null ? null : outPic.trim();
    }

    public String getOutLongitude() {
        return outLongitude;
    }

    public void setOutLongitude(String outLongitude) {
        this.outLongitude = outLongitude == null ? null : outLongitude.trim();
    }

    public String getOutLatitude() {
        return outLatitude;
    }

    public void setOutLatitude(String outLatitude) {
        this.outLatitude = outLatitude == null ? null : outLatitude.trim();
    }

    public Byte getOutLocatingType() {
        return outLocatingType;
    }

    public void setOutLocatingType(Byte outLocatingType) {
        this.outLocatingType = outLocatingType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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
}