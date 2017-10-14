/**   
* @Title: WarehouseVo.java 
* @Package cn.mobilizer.channel.posm.vo 
* @author 仪动信息技术（上海）有限公司
* @date 2015年9月24日 下午3:08:44 
* @version V1.0   
*/
package cn.mobilizer.channel.posm.vo;

import cn.mobilizer.channel.posm.po.Warehouse;


/** 
 * @ClassName: WarehouseVo 
 * @Description: 仓库跟大区组合 VO
 * @author pengwei
 * @date 2015年9月24日 下午3:08:44 
 *  
 */
public class WarehouseVo extends Warehouse {

	private Integer structureId;

    private String structureNo;

    private String structureName;

    private String structureNameEn;

    private Integer parentId;

    
	public Integer getStructureId() {
		return structureId;
	}

	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}

	public String getStructureNo() {
		return structureNo;
	}

	public void setStructureNo(String structureNo) {
		this.structureNo = structureNo;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getStructureNameEn() {
		return structureNameEn;
	}

	public void setStructureNameEn(String structureNameEn) {
		this.structureNameEn = structureNameEn;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "WarehouseVo [structureId=" + structureId + ", structureNo="
				+ structureNo + ", structureName=" + structureName
				+ ", structureNameEn=" + structureNameEn + ", parentId="
				+ parentId + ", getStructureId()=" + getStructureId()
				+ ", getStructureNo()=" + getStructureNo()
				+ ", getStructureName()=" + getStructureName()
				+ ", getStructureNameEn()=" + getStructureNameEn()
				+ ", getParentId()=" + getParentId() + ", getWarehouseId()="
				+ getWarehouseId() + ", getWarehouseNo()=" + getWarehouseNo()
				+ ", getWarehouseName()=" + getWarehouseName()
				+ ", getClientStructureId()=" + getClientStructureId()
				+ ", getProvinceId()=" + getProvinceId() + ", getCityId()="
				+ getCityId() + ", getDistrictId()=" + getDistrictId()
				+ ", getAddr()=" + getAddr() + ", getLongitude()="
				+ getLongitude() + ", getLatitude()=" + getLatitude()
				+ ", getArea()=" + getArea() + ", getContact()=" + getContact()
				+ ", getTelephoneNo()=" + getTelephoneNo() + ", getRemark()="
				+ getRemark() + ", getClientId()=" + getClientId()
				+ ", getCreateBy()=" + getCreateBy() + ", getCreateTime()="
				+ getCreateTime() + ", getLastUpdateBy()=" + getLastUpdateBy()
				+ ", getLastUpdateTime()=" + getLastUpdateTime()
				+ ", getIsDelete()=" + getIsDelete() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}


	
}
