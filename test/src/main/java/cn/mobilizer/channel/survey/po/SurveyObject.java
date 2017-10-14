package cn.mobilizer.channel.survey.po;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.mobilizer.channel.comm.utils.StringUtil;

public class SurveyObject {
	
	private Integer objectId;

	private Integer subSurveyId;

	private String objectNo;

	private String objectName;

	private Integer sequence;

	private String col1;

	private String col2;

	private String col3;

	private String col4;

	private String col5;

	private String parameterIds;

	private List<String> parameterIdList;

	private Integer objectGroupId;

	private Integer clientId;

	private Date createTime;

	private Date lastUpdateTime;

	private Byte isDelete;

	private String groupName;

	private String parentId;
	
	private Integer grade;
	
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName==null?"":groupName.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<String> getParameterIdList() {
		return parameterIdList;
	}

	public void setParameterIdList(List<String> parameterIdList) {
		this.parameterIdList = parameterIdList;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getSubSurveyId() {
		return subSurveyId;
	}

	public void setSubSurveyId(Integer subSurveyId) {
		this.subSurveyId = subSurveyId;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo == null ? null : objectNo.trim();
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName == null ? null : objectName.trim();
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1 == null ? null : col1.trim();
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2 == null ? null : col2.trim();
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3 == null ? null : col3.trim();
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4 == null ? null : col4.trim();
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5 == null ? null : col5.trim();
	}

	public String getParameterIds() {
		return parameterIds;
	}

	public void setParameterIds(String parameterIds) {
		this.parameterIds = parameterIds == null ? null : parameterIds.trim();
		if (!StringUtil.isEmptyString(this.parameterIds)) {
			String[] temp = this.parameterIds.split(",");
			this.setParameterIdList(Arrays.asList(temp));
		}
	}

	public Integer getObjectGroupId() {
		return objectGroupId;
	}

	public void setObjectGroupId(Integer objectGroupId) {
		this.objectGroupId = objectGroupId;
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