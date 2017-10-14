package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;

import org.apache.log4j.Logger;

import cn.mobilizer.channel.comm.utils.StringUtil;

public class FilterVo implements Serializable {

	private static final long serialVersionUID = 3510162995792370284L;
	protected Logger log = Logger.getLogger(this.getClass());
	
	private String argChannelIds;
	private String argDeptIds;
	private String argChainIds;
	private String argAccountIds;
	private Integer argMonthId;
	private String argFilterUserIds;
	private String argFilterStructureIds;
	private String argStructureId;
	private String argBrandName;
	private Integer dashboardId;
	private String argCategorys;
	
	public String getArgCategorys() {
		return argCategorys;
	}

	public void setArgCategorys(String argCategorys) {
		this.argCategorys = argCategorys;
	}

	public Integer getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Integer dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getArgBrandName() {
		if(StringUtil.isEmptyString(argBrandName)){
			argBrandName = "0";
		}
		return argBrandName;
	}

	public void setArgBrandName(String argBrandName) {
		this.argBrandName = argBrandName;
	}

	public String getArgStructureId() {
		return argStructureId;
	}

	public void setArgStructureId(String argStructureId) {
		this.argStructureId = argStructureId;
	}

	public String getArgFilterUserIds() {
		return argFilterUserIds;
	}

	public void setArgFilterUserIds(String argFilterUserIds) {
		this.argFilterUserIds = argFilterUserIds;
	}

	public String getArgFilterStructureIds() {
		return argFilterStructureIds;
	}

	public void setArgFilterStructureIds(String argFilterStructureIds) {
		this.argFilterStructureIds = argFilterStructureIds;
	}

	private String province;
	private String city;

	public Integer getArgMonthId() {
		return argMonthId;
	}

	public void setArgMonthId(Integer argMonthId) {
		this.argMonthId = argMonthId;
	}

	public String getArgChannelIds() {
		return argChannelIds;
	}

	public void setArgChannelIds(String argChannelIds) {
		this.argChannelIds = argChannelIds;
	}

	public String getArgDeptIds() {
		/*List<String> lists = new ArrayList<String>();
		if(StringUtil.isNotEmptyString(this.argDeptIds)){
			String[] depts = this.argDeptIds.split(",");
			lists.addAll(Arrays.asList(depts));
		}
		if(StringUtil.isNotEmptyString(this.argStructureId)){
			String[] structures = this.argStructureId.split(",");
			lists.addAll(Arrays.asList(structures));
		}
		if(StringUtil.isNotEmptyString(this.province)){
			String[] ps = this.getProvince().split(",");
			lists.addAll(Arrays.asList(ps));
		}
		if(StringUtil.isNotEmptyString(this.city)){
			String[] cs = this.city.split(",");
			lists.addAll(Arrays.asList(cs));
		}
		Set<String> set = new HashSet<String>(lists);
		StringBuilder builder = new StringBuilder();
		for (String string : set) {
			builder.append(string).append(",");
		}
		if(builder.toString().endsWith(",")){
			builder = builder.deleteCharAt(builder.length()-1);
		}
		this.argDeptIds = builder.toString();
		log.info("filter depts: "+this.argDeptIds);*/
		return argDeptIds;
	}

	public void setArgDeptIds(String argDeptIds) {
		this.argDeptIds = argDeptIds;
	}

	public String getArgChainIds() {
		return argChainIds;
	}

	public void setArgChainIds(String argChainIds) {
		this.argChainIds = argChainIds;
	}

	public String getArgAccountIds() {
		return argAccountIds;
	}

	public void setArgAccountIds(String argAccountIds) {
		this.argAccountIds = argAccountIds;
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

}
