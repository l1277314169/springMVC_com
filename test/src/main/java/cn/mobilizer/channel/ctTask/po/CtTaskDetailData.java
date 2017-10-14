package cn.mobilizer.channel.ctTask.po;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class CtTaskDetailData {
    private String ctTaskDetailDataId;

    private String ctTaskDataId;

    private String target1Id;

    private String target2Id;

    private Integer ctTaskParameterId;

    private String value;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public String getCtTaskDetailDataId() {
        return ctTaskDetailDataId;
    }

    public void setCtTaskDetailDataId(String ctTaskDetailDataId) {
        this.ctTaskDetailDataId = ctTaskDetailDataId;
    }

    public String getCtTaskDataId() {
        return ctTaskDataId;
    }

    public void setCtTaskDataId(String ctTaskDataId) {
        this.ctTaskDataId = ctTaskDataId;
    }

    public String getTarget1Id() {
        return target1Id;
    }

    public void setTarget1Id(String target1Id) {
        this.target1Id = target1Id;
    }

    public String getTarget2Id() {
        return target2Id;
    }

    public void setTarget2Id(String target2Id) {
        this.target2Id = target2Id;
    }

    public Integer getCtTaskParameterId() {
        return ctTaskParameterId;
    }

    public void setCtTaskParameterId(Integer ctTaskParameterId) {
        this.ctTaskParameterId = ctTaskParameterId;
    }

    public String getValue() {
    	try {
    		if(StringUtils.isNotEmpty(value)){
    			value = URLDecoder.decode(this.value, "UTF-8");
    		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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