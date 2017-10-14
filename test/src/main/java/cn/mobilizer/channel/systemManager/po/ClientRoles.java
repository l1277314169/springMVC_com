package cn.mobilizer.channel.systemManager.po;

import java.util.Date;

public class ClientRoles {
	private Integer id;
	
	private Integer roleId;

	private String roleName;

    private String roleNameEn;

    private Byte webLogin;

    private Byte appLogin;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String name;
    
    private String webstr;
    
    private String appstr;
    

    
    private Byte roleLevel;//角色等级
    
    public String getWebstr() {
		return webstr;
	}

	public void setWebstr(String webstr) {
		this.webstr = webstr;
	}

	public String getAppstr() {
		return appstr;
	}

	public void setAppstr(String appstr) {
		this.appstr = appstr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleNameEn() {
        return roleNameEn;
    }

    public void setRoleNameEn(String roleNameEn) {
        this.roleNameEn = roleNameEn == null ? null : roleNameEn.trim();
    }

    public Byte getWebLogin() {
        return webLogin;
    }

    public void setWebLogin(Byte webLogin) {
        this.webLogin = webLogin;
    }

    public Byte getAppLogin() {
        return appLogin;
    }

    public void setAppLogin(Byte appLogin) {
        this.appLogin = appLogin;
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

	public Byte getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Byte roleLevel) {
		this.roleLevel = roleLevel;
	}
}