/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.posm.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PromotionMaterialStockVo {
		private Integer warehouseId;
		private Integer stockId;
		private String structureName;
		private String city;
		private String addr;
		private String contact;
		private String telephoneNo;
		private String brand;
		private String subCategory;
		private String category;
		private String materialName;
		private String batch;
		private String materialCode;
		private String spec;
		private Integer quantity;
	    private BigDecimal price;
	    private String Details;//地址详细信息
	    private Date lastUpdateTime;
	    private String remark;
	    
		public String getSpec() {
			return spec;
		}
		public void setSpec(String spec) {
			this.spec = spec;
		}
		public String getDetails() {
			return this.addr+",联系人:"+this.contact+",电话:"+this.telephoneNo;
		}
		public void setDetails(String details) {
			Details = details;
		}
		public Integer getWarehouseId() {
			return warehouseId;
		}
		public void setWarehouseId(Integer warehouseId) {
			this.warehouseId = warehouseId;
		}
		public Integer getStockId() {
			return stockId;
		}
		public void setStockId(Integer stockId) {
			this.stockId = stockId;
		}
		public String getStructureName() {
			return structureName;
		}
		public void setStructureName(String structureName) {
			this.structureName = structureName;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		public String getTelephoneNo() {
			return telephoneNo;
		}
		public void setTelephoneNo(String telephoneNo) {
			this.telephoneNo = telephoneNo;
		}
		public String getBrand() {
			return brand;
		}
		public void setBrand(String brand) {
			this.brand = brand;
		}
		public String getSubCategory() {
			return subCategory;
		}
		public void setSubCategory(String subCategory) {
			this.subCategory = subCategory;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getMaterialName() {
			return materialName;
		}
		public void setMaterialName(String materialName) {
			this.materialName = materialName;
		}
		public String getBatch() {
			return batch;
		}
		public void setBatch(String batch) {
			this.batch = batch;
		}
		public String getMaterialCode() {
			return materialCode;
		}
		public void setMaterialCode(String materialCode) {
			this.materialCode = materialCode;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
	
		public Date getLastUpdateTime() {
			return lastUpdateTime;
		}
		public void setLastUpdateTime(Date lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	    
}
