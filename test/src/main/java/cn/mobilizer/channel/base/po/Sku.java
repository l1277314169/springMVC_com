package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Sku implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -9099911231947204353L;

	private Integer skuId;
	
    private String skuNo;

    private String skuName;

    private String skuNameEn;

    private String skuNameAbbr;

    private String barcode;

    private Integer brandId;

    private Integer categoryId;

    private BigDecimal price;

    private String spec;

    private String packSpec;

    private BigDecimal weight;

    private BigDecimal volume;

    private Integer unitId;

    private Byte isMain;

    private Integer skuGroupId;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    private Integer skuTypeId;
    
    private Integer skuSeriesId;
    
    
    /**skutypeName-关联skutype.skutype_name**/
    private String  skuTypeName;
    /**skuseriesName-关联skuseries.skuseries_name**/
    private String skuSeriesName;
    /**brandName-关联brand.brand_name**/
    private String brandName;
    /**categoryName-关联category.category_name**/
    private String categoryName;
    
    /**gruop_name关联sku_group_mapping和sku_group表**/
    private String groupName;
    
	private Integer mappingId;
	
	private String id;
	
	private String name;
	
	private Integer priceSkuId;
	
	private BigDecimal skuPrice;
    
	  public Integer getSkuSeriesId() {
		return skuSeriesId;
	}

	public void setSkuSeriesId(Integer skuSeriesId) {
		this.skuSeriesId = skuSeriesId;
	}

	public Integer getskuTypeId() {
			return skuTypeId;
		}

		public void setSkutypeid(Integer skuTypeId) {
			this.skuTypeId = skuTypeId;
		}
	   public BigDecimal getSkuPrice() {
			return skuPrice;
		}

		public void setSkuPrice(BigDecimal skuPrice) {
			this.skuPrice = skuPrice;
		}

	public Integer getPriceSkuId() {
		return priceSkuId;
	}

	public void setPriceSkuId(Integer priceSkuId) {
		this.priceSkuId = priceSkuId;
	}

	public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo == null ? null : skuNo.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSkuNameEn() {
        return skuNameEn;
    }

    public void setSkuNameEn(String skuNameEn) {
        this.skuNameEn = skuNameEn == null ? null : skuNameEn.trim();
    }

    public String getSkuNameAbbr() {
        return skuNameAbbr;
    }

    public void setSkuNameAbbr(String skuNameAbbr) {
        this.skuNameAbbr = skuNameAbbr == null ? null : skuNameAbbr.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getPackSpec() {
        return packSpec;
    }

    public void setPackSpec(String packSpec) {
        this.packSpec = packSpec == null ? null : packSpec.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Byte getIsMain() {
        return isMain;
    }

    public void setIsMain(Byte isMain) {
        this.isMain = isMain;
    }

    public Integer getSkuGroupId() {
        return skuGroupId;
    }

    public void setSkuGroupId(Integer skuGroupId) {
        this.skuGroupId = skuGroupId;
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

	
	public String getBrandName(){
		return brandName;
	}

	public void setSkuSeriesName(String skuSeriesName){
		this.skuSeriesName = skuSeriesName;
	}

	public String getSkuSeriesName(){
		return skuSeriesName;
	}
	public void setSkutypeName(String skuTypeName){
		this.skuTypeName = skuTypeName;
	}

	public String getSkuTypeName(){
		return skuTypeName;
	}

	public void setBrandName(String brandName){
		this.brandName = brandName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getMappingId() {
		return mappingId;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}