package cn.mobilizer.channel.survey.vo;

import java.io.Serializable;

public class SurveyParam implements Serializable {

	/**
	 * 为了跟存储过程保持一致，字段没有遵循命名规范
	 */
	private static final long serialVersionUID = 2884233889573004517L;
	private String arg_store_no;
	private String arg_visitor;
	private String feedbackDate;
	private Integer city_ids;
	private Integer province_ids;
	private String arg_store_name;
	private Integer arg_types;
	private Integer arg_channel_ids;
	private Integer arg_dept_ids;
	private Integer arg_status;
    private String arg_store_type;
	private Integer arg_client_id;
	private String arg_start_date;
	private String arg_end_date;
	private String arg_filter_user_ids;
	private String arg_filter_structure_ids;
	
	public String getArg_filter_user_ids() {
		return arg_filter_user_ids;
	}

	public void setArg_filter_user_ids(String arg_filter_user_ids) {
		this.arg_filter_user_ids = arg_filter_user_ids;
	}

	public String getArg_filter_structure_ids() {
		return arg_filter_structure_ids;
	}

	public void setArg_filter_structure_ids(String arg_filter_structure_ids) {
		this.arg_filter_structure_ids = arg_filter_structure_ids;
	}

	public String getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getArg_store_no() {
		return arg_store_no;
	}

	public void setArg_store_no(String arg_store_no) {
		this.arg_store_no = arg_store_no;
	}

	public String getArg_visitor() {
		return arg_visitor;
	}

	public void setArg_visitor(String arg_visitor) {
		this.arg_visitor = arg_visitor;
	}

	public Integer getCity_ids() {
		return city_ids;
	}

	public void setCity_ids(Integer city_ids) {
		this.city_ids = city_ids;
	}

	public Integer getProvince_ids() {
		return province_ids;
	}

	public void setProvince_ids(Integer province_ids) {
		this.province_ids = province_ids;
	}

	public String getArg_store_name() {
		return arg_store_name;
	}

	public void setArg_store_name(String arg_store_name) {
		this.arg_store_name = arg_store_name;
	}

	public Integer getArg_types() {
		return arg_types;
	}

	public void setArg_types(Integer arg_types) {
		this.arg_types = arg_types;
	}

	public Integer getArg_channel_ids() {
		return arg_channel_ids;
	}

	public void setArg_channel_ids(Integer arg_channel_ids) {
		this.arg_channel_ids = arg_channel_ids;
	}

	public Integer getArg_dept_ids() {
		return arg_dept_ids;
	}

	public void setArg_dept_ids(Integer arg_dept_ids) {
		this.arg_dept_ids = arg_dept_ids;
	}

	public Integer getArg_status() {
		return arg_status;
	}

	public void setArg_status(Integer arg_status) {
		this.arg_status = arg_status;
	}

	public Integer getArg_client_id() {
		return arg_client_id;
	}

	public void setArg_client_id(Integer arg_client_id) {
		this.arg_client_id = arg_client_id;
	}

	public String getArg_start_date() {
		return arg_start_date;
	}

	public void setArg_start_date(String arg_start_date) {
		this.arg_start_date = arg_start_date;
	}

	public String getArg_end_date() {
		return arg_end_date;
	}

	public void setArg_end_date(String arg_end_date) {
		this.arg_end_date = arg_end_date;
	}

	public String getArg_store_type() {
		return arg_store_type;
	}

	public void setArg_store_type(String arg_store_type) {
		this.arg_store_type = arg_store_type;
	}

}
