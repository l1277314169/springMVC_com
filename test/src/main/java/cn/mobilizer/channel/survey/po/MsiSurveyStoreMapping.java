package cn.mobilizer.channel.survey.po;

import java.io.Serializable;
import java.util.Date;

public class MsiSurveyStoreMapping implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 6103795597110822710L;

	private Integer mappingId;

    private Integer msiSurveyId;

    private String storeId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getMsiSurveyId() {
        return msiSurveyId;
    }

    public void setMsiSurveyId(Integer msiSurveyId) {
        this.msiSurveyId = msiSurveyId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
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