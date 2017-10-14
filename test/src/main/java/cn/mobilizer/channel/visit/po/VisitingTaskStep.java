package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Category;

public class VisitingTaskStep implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 2115655231804682836L;

	private Integer visitingTaskStepId;

    private Integer visitingTaskId;

    private String stepNo;

    private String stepName;

    private String stepNameEn;

    private Byte stepType;

    private Byte isMustDo;

    private Byte stepPriority;

    private Byte isOnePage;
    
    private Byte sortBy;
    
    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    private Byte selectType;
    
    /**一下为扩展字段**/
    private String taskStepParameterDatas;
    private String skuParameterDatas;
    private String brandParameterDatas;
    private String categoryParameterDatas;
    private String promotionMaterialParameterDatas;
    
    private List<VisitingParameter> visitingParameters;
    private List<Brand> brands;
    private List<Category> categorys;
    
    public Byte getSelectType() {
		return selectType;
	}

	public void setSelectType(Byte selectType) {
		this.selectType = selectType;
	}

	public Integer getVisitingTaskStepId() {
        return visitingTaskStepId;
    }

    public void setVisitingTaskStepId(Integer visitingTaskStepId) {
        this.visitingTaskStepId = visitingTaskStepId;
    }

    public Integer getVisitingTaskId() {
        return visitingTaskId;
    }

    public void setVisitingTaskId(Integer visitingTaskId) {
        this.visitingTaskId = visitingTaskId;
    }

    public String getStepNo() {
        return stepNo;
    }

    public void setStepNo(String stepNo) {
        this.stepNo = stepNo == null ? null : stepNo.trim();
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public String getStepNameEn() {
        return stepNameEn;
    }

    public void setStepNameEn(String stepNameEn) {
        this.stepNameEn = stepNameEn == null ? null : stepNameEn.trim();
    }

    public Byte getStepType() {
        return stepType;
    }

    public void setStepType(Byte stepType) {
        this.stepType = stepType;
    }

    public Byte getIsMustDo() {
        return isMustDo;
    }

    public void setIsMustDo(Byte isMustDo) {
        this.isMustDo = isMustDo;
    }

    public Byte getStepPriority() {
        return stepPriority;
    }

    public void setStepPriority(Byte stepPriority) {
        this.stepPriority = stepPriority;
    }

    public Byte getIsOnePage() {
        return isOnePage;
    }

    public void setIsOnePage(Byte isOnePage) {
        this.isOnePage = isOnePage;
    }

    
	public Byte getSortBy(){
		return sortBy;
	}

	
	public void setSortBy(Byte sortBy){
		this.sortBy = sortBy;
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

	
	public List<VisitingParameter> getVisitingParameters(){
		return visitingParameters;
	}

	
	public void setVisitingParameters(List<VisitingParameter> visitingParameters){
		this.visitingParameters = visitingParameters;
	}

	
	public List<Brand> getBrands(){
		return brands;
	}

	
	public void setBrands(List<Brand> brands){
		this.brands = brands;
	}

	
	public List<Category> getCategorys(){
		return categorys;
	}

	
	public void setCategorys(List<Category> categorys){
		this.categorys = categorys;
	}

	
	public String getSkuParameterDatas(){
		return skuParameterDatas;
	}

	
	public void setSkuParameterDatas(String skuParameterDatas){
		this.skuParameterDatas = skuParameterDatas;
	}

	
	public String getBrandParameterDatas(){
		return brandParameterDatas;
	}

	
	public void setBrandParameterDatas(String brandParameterDatas){
		this.brandParameterDatas = brandParameterDatas;
	}

	
	public String getCategoryParameterDatas(){
		return categoryParameterDatas;
	}

	
	public void setCategoryParameterDatas(String categoryParameterDatas){
		this.categoryParameterDatas = categoryParameterDatas;
	}

	
	public String getPromotionMaterialParameterDatas(){
		return promotionMaterialParameterDatas;
	}

	
	public void setPromotionMaterialParameterDatas(String promotionMaterialParameterDatas){
		this.promotionMaterialParameterDatas = promotionMaterialParameterDatas;
	}

	
	public String getTaskStepParameterDatas(){
		return taskStepParameterDatas;
	}

	
	public void setTaskStepParameterDatas(String taskStepParameterDatas){
		this.taskStepParameterDatas = taskStepParameterDatas;
	}

}