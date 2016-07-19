package com.po;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 * 
 * @author Thinkpad
 * 
 */
public class ActiveUser implements Serializable {
	private String userid;//用户id（主键）
	private String username;// 用户名称

	private List<Permission> menus;// 菜单
	private List<Permission> permissions;// 权限

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<Permission> getMenus() {
		return menus;
	}

	public void setMenus(List<Permission> menus) {
		this.menus = menus;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	
}
