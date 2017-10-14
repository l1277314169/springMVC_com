package cn.mobilizer.channel.visit.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CallPlanningVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7875792019472171632L;

	private String planningId;
	
	private Integer clientUserId;
	
	private String name;
	
	private String positionName;
	
	private String structureName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date callDate;
	
	private String optionText;
	
	private Integer planedTimes;
	
	private Integer visitedTimes;
	
	private Byte visitType;
	
	private String week;
	
	private String showEdit;
	
	private Integer enumValue;
	

	public Integer getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(Integer enumValue) {
		this.enumValue = enumValue;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}


	public Integer getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPositionName() {
		return positionName;
	}

	public Byte getVisitType() {
		return visitType;
	}

	public void setVisitType(Byte visitType) {
		this.visitType = visitType;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public Integer getPlanedTimes() {
		return planedTimes;
	}

	public void setPlanedTimes(Integer planedTimes) {
		this.planedTimes = planedTimes;
	}

	public Integer getVisitedTimes() {
		return visitedTimes;
	}

	public void setVisitedTimes(Integer visitedTimes) {
		this.visitedTimes = visitedTimes;
	}

	public String getPlanningId() {
		return planningId;
	}

	public void setPlanningId(String planningId) {
		this.planningId = planningId;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getShowEdit() {
		return showEdit;
	}

	public void setShowEdit(String showEdit) {
		this.showEdit = showEdit;
	}
}
