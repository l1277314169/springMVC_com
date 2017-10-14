package cn.mobilizer.channel.sync.po;

import java.io.Serializable;

public class TableDataPrivilegeTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 732826265507300103L;
	private String templateID;
	private String tableDefinitionID;
	private String clientID;
	private String version;
	private String sqlTemplate;
	private String isIncludeInitLogic;
	public String getTemplateID() {
		return templateID;
	}
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	public String getTableDefinitionID() {
		return tableDefinitionID;
	}
	public void setTableDefinitionID(String tableDefinitionID) {
		this.tableDefinitionID = tableDefinitionID;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSqlTemplate() {
		return sqlTemplate;
	}
	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}
	public String getIsIncludeInitLogic() {
		return isIncludeInitLogic;
	}
	public void setIsIncludeInitLogic(String isIncludeInitLogic) {
		this.isIncludeInitLogic = isIncludeInitLogic;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("templateID="+templateID);
		sb.append(",tableDefinitionID="+tableDefinitionID);
		sb.append(",clientID="+clientID);
		sb.append(",version="+version);
		sb.append(",sqlTemplate="+sqlTemplate);
		sb.append(",isIncludeInitLogic="+isIncludeInitLogic);
		return sb.toString();
	}

}
