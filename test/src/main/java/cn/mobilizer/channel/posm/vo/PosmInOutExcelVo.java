/**   
* @Title: PosmInOutVo.java 
* @Package cn.mobilizer.channel.posm.vo 
* @author 仪动信息技术（上海）有限公司
* @date 2015年9月24日 下午2:15:50 
* @version V1.0   
*/
package cn.mobilizer.channel.posm.vo;

import java.util.Date;

import cn.mobilizer.channel.posm.po.PosmInOut;

/** 
 * @ClassName: PosmInOutVo 
 * @author pengwei
 * @date 2015年9月24日 下午2:15:50 
 *  
 */
public class PosmInOutExcelVo {
	
	private Integer inOutId;
	
	/**
	 * 大区
	 */
 	private String structureName;
 	/**
 	 * 城市
 	 */
 	private String cityName;
 	
 	/**
 	 * 仓库编号
 	 */
 	private Integer warehouseId;
 	
 	/**
 	 * 目标仓库
 	 */
 	private Integer outWarehouseId;
 	private String outWarehouseNo;
 	/**
 	 * 品牌
 	 */
 	private String brand;
 	/**
 	 * 分类
 	 */
 	private String category;
 	/**
 	 * 物料名称
 	 */
 	private String materialName;

 	/**
 	 * 
 	"物料编号",
 	 */
 	private String materialCode;
 	/**
	 	"年份",
 	 */
 	private String batch;
 	/**
 	 * 
 	"收进/发出",
 	 */
 	private Byte billType;
 	/**
 	 * 
	 	"数量(每箱规格)",
 	 */
 	private String spec;
 	/**
 	 * 
 	"数量(个)",
 	 */
 	private Integer quantity;
 	/**
 	 * 
 	"时间",
 	 */
 	private Date inOutTime;
 	
 	/**
 	"门店编号",
 	 */
 	private Integer storeId;
 	
 	private String strStoreId;
 	
 	/***
 	"客户"
 	 * 
 	 */
 	private String storeName;
 	/**
 	 * 
 	"门店",
 	 */
 	private String storeNo;
 	/***
 	 * 
 	"运输负责人",
 	 */
 	private String handler;
 	/**
 	"备注"
 	 */
 	private String remark;
 	
 	/**
 	 * 仓库编号
 	 */
 	private String warehouseNo;
 	
 	/**
 	 * 仓库地址
 	 */
 	private String addr;
 	
 	/**
 	 * 客户姓名
 	 */
 	private String clientName;
 	
 	/**
 	 * 目的地仓库的编号
 	 */
 	private String receiveWarehouseNo;

 	/**
 	 * 家乐福
 	 */
 	private String chainName;
 	
	public String getReceiveWarehouseNo() {
		return receiveWarehouseNo;
	}
	public void setReceiveWarehouseNo(String receiveWarehouseNo) {
		this.receiveWarehouseNo = receiveWarehouseNo;
	}
	public String getChainName() {
		return chainName;
	}
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	public String getStructureName() {
		return structureName;
	}
	public String getWarehouseNo() {
		return warehouseNo;
	}
	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getOutWarehouseId() {
		return outWarehouseId;
	}
	public void setOutWarehouseId(Integer outWarehouseId) {
		this.outWarehouseId = outWarehouseId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public Byte getBillType() {
		return billType;
	}
	public void setBillType(Byte billType) {
		this.billType = billType;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getInOutTime() {
		return inOutTime;
	}
	public void setInOutTime(Date inOutTime) {
		this.inOutTime = inOutTime;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStrStoreId() {
		return strStoreId;
	}
	public void setStrStoreId(String strStoreId) {
		this.strStoreId = strStoreId;
	}
	
	
	public String getOutWarehouseNo() {
		return outWarehouseNo;
	}
	public void setOutWarehouseNo(String outWarehouseNo) {
		this.outWarehouseNo = outWarehouseNo;
	}
	public Integer getInOutId() {
		return inOutId;
	}
	public void setInOutId(Integer inOutId) {
		this.inOutId = inOutId;
	}
	public PosmInOut getPosmInOut(Integer materialId,Integer userId ,Integer clientId){
		PosmInOut posmInOut = new PosmInOut();
		posmInOut.setQuantity(this.getQuantity());
		posmInOut.setWarehouseId(this.getWarehouseId());
		posmInOut.setBillType(this.getBillType());
		posmInOut.setRemark(this.getRemark());
		if(this.billType == 1){
			posmInOut.setReceivingWarehouseId(this.getOutWarehouseId());
			posmInOut.setStoreId(this.getStrStoreId());
		}
		posmInOut.setHandler(this.getHandler());
		if(null != this.inOutTime){
			posmInOut.setInOutTime(this.inOutTime);
		}
		posmInOut.setMaterialId(materialId);
		posmInOut.setClientId(clientId);
		posmInOut.setCreateBy(userId);
		return posmInOut;
	}
}
