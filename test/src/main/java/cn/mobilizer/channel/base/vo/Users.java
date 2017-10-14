/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.vo;

import java.util.Date;

import cn.mobilizer.channel.util.DateTimeUtils;

public class Users {

	private String name;

	private String code;

	private String mobile;

	private String title;

	private String status;

	private String IDCard;

	private String province;

	private String city;

	private String joinDate;

	private String leaveDate;

	private Date joinDateTime;
	private Date leaveDateTime;

	public Date getJoinDateTime() {
		if(null == this.getJoinDate()){
			return null;
		}
		try {
			this.joinDateTime = DateTimeUtils.toTime(this.getJoinDate(), DateTimeUtils.yyyyMMdd);
		} catch (Exception e) {
			return null;
		}
		return this.joinDateTime;
	}

	public void setJoinDateTime(Date joinDateTime) {
		
		this.joinDateTime = joinDateTime;
	}

	public Date getLeaveDateTime() {
		if(null==this.getLeaveDate()){
			return null;
		}
		try {
			this.leaveDateTime = DateTimeUtils.toTime(this.getLeaveDate(), DateTimeUtils.yyyyMMdd);
		} catch (Exception e) {
			return null;
		}
		return this.leaveDateTime;
	}

	public void setLeaveDateTime(Date leaveDateTime) {
		this.leaveDateTime = leaveDateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
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

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

}
