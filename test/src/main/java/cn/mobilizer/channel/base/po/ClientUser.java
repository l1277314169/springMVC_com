package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

public class ClientUser implements Serializable {
	private static final long serialVersionUID = -204409596352815005L;

	private Integer id;

	private Integer clientUserId;

	private String userNo;

	private String loginName;

	private String loginPwd;

	private String mobileNo;

	private String name;

	private Integer clientPositionId;

	private Integer clientStructureId;

	private Byte sex;

	private String telephoneNo;

	private Integer provinceId;

	private String province;

	private Integer cityId;

	private String city;

	private Integer districtId;

	private String district;

	private String addr;

	private String postcode;

	private String idcard;

	private String remark;

	private Byte status;

	private Byte isActivation;

	private Date lastLoginTime;

	private Integer clientId;

	private Date createTime;

	private Date lastUpdateTime;

	private Byte isDelete;

	private String salt;

	private String roles;

	private String plainPassword;

	private String email;

	/** 职位名称-关联clientPositionId **/
	private String positionName;

	/** 部门名称-clientStructureId **/
	private String structureName;

	/** 上级 **/
	private Integer parentId;

	private String logoPrefix;

	private Integer[] rolesId;

	/** 上级领导名字 */
	private String uplevelName;

	private String permissionsData;

	private String roleNames;

	private String clientUserIds;

	private String parentName;

	private String childAll;

	private Integer clientUserExpandId;

	private String homePage;

	private String col1;
	private String col2;
	private String col3;
	private Date joinDate;
	private Date leaveDate;
	private String keyName;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getClientUserIds() {
		return clientUserIds;
	}

	public void setClientUserIds(String clientUserIds) {
		this.clientUserIds = clientUserIds;
	}

	public String getChildAll() {
		return childAll;
	}

	public void setChildAll(String childAll) {
		this.childAll = childAll;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getUplevelName() {
		return uplevelName;
	}

	public void setUplevelName(String uplevelName) {
		this.uplevelName = uplevelName;
	}

	public Integer[] getRolesId() {
		return rolesId;
	}

	public void setRolesId(Integer[] rolesId) {
		this.rolesId = rolesId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd == null ? null : loginPwd.trim();
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo == null ? null : mobileNo.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getClientPositionId() {
		return clientPositionId;
	}

	public void setClientPositionId(Integer clientPositionId) {
		this.clientPositionId = clientPositionId;
	}

	public Integer getClientStructureId() {
		return clientStructureId;
	}

	public void setClientStructureId(Integer clientStructureId) {
		this.clientStructureId = clientStructureId;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo == null ? null : telephoneNo.trim();
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr == null ? null : addr.trim();
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard == null ? null : idcard.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getIsActivation() {
		return isActivation;
	}

	public void setIsActivation(Byte isActivation) {
		this.isActivation = isActivation;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles == null ? null : roles.trim();
	}

	public List<String> getRoleList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		List<String> rols = new ArrayList<String>();
		String[] rs = StringUtils.split(roles, ",");
		if (rs != null)
			for (int i = 0; i < rs.length; i++) {
				rols.add(rs[i]);
			}
		return rols;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPositionName() {
		return positionName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getLogoPrefix() {
		return logoPrefix;
	}

	public void setLogoPrefix(String logoPrefix) {
		this.logoPrefix = logoPrefix;
	}

	public String getPermissionsData() {
		return permissionsData;
	}

	public void setPermissionsData(String permissionsData) {
		this.permissionsData = permissionsData;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public Integer getClientUserExpandId() {
		return clientUserExpandId;
	}

	public void setClientUserExpandId(Integer clientUserExpandId) {
		this.clientUserExpandId = clientUserExpandId;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

}