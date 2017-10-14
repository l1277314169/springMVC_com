package cn.mobilizer.channel.systemManager.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClientBusinessDefinition implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7889915334857260900L;

	private Integer definitionId;

    private String tableName;

    private String columnName;

    private Integer columnType;

    private Integer controlType;

    private String relationValue;

    private Integer minLength;

    private Integer maxLength;

    private String columnDesc;

    private String columnDescEn;

    private String enumName;

    private Byte enumType;

    private Integer isMustDo;

    private Integer usedFlag;
    
    private Byte editable;

    private Integer editSequence;

    private Integer listSequence;

    private Integer querySequence;
    
    private Integer importSequence;

    private BigDecimal gridWidth;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**扩展字段-attributeName**/
    private String attributeName;

    public Integer getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(Integer definitionId) {
        this.definitionId = definitionId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public String getRelationValue() {
        return relationValue;
    }

    public void setRelationValue(String relationValue) {
        this.relationValue = relationValue == null ? null : relationValue.trim();
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getColumnDesc() {
        return columnDesc;
    }

    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc == null ? null : columnDesc.trim();
    }

    public String getColumnDescEn() {
        return columnDescEn;
    }

    public void setColumnDescEn(String columnDescEn) {
        this.columnDescEn = columnDescEn == null ? null : columnDescEn.trim();
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName == null ? null : enumName.trim();
    }

    public Byte getEnumType() {
        return enumType;
    }

    public void setEnumType(Byte enumType) {
        this.enumType = enumType;
    }

    public Integer getIsMustDo() {
        return isMustDo;
    }

    public void setIsMustDo(Integer isMustDo) {
        this.isMustDo = isMustDo;
    }

    public Integer getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Integer usedFlag) {
        this.usedFlag = usedFlag;
    }

    public Integer getEditSequence() {
        return editSequence;
    }

    public void setEditSequence(Integer editSequence) {
        this.editSequence = editSequence;
    }

    public Integer getListSequence() {
        return listSequence;
    }

    public void setListSequence(Integer listSequence) {
        this.listSequence = listSequence;
    }

    public Integer getQuerySequence() {
        return querySequence;
    }

    public void setQuerySequence(Integer querySequence) {
        this.querySequence = querySequence;
    }

    public BigDecimal getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(BigDecimal gridWidth) {
        this.gridWidth = gridWidth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public String getAttributeName(){
		return attributeName;
	}

	public void setAttributeName(String attributeName){
		this.attributeName = attributeName;
	}
	
	public Byte getEditable(){
		return editable;
	}
	
	public void setEditable(Byte editable){
		this.editable = editable;
	}

	public Integer getImportSequence(){
		return importSequence;
	}
	
	public void setImportSequence(Integer importSequence){
		this.importSequence = importSequence;
	}
}