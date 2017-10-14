package cn.mobilizer.channel.comm.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultMessage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7339461273795844294L;

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";
	
	public static final String NULL = "null";
	
	public static final String SYS_ERROR = "sysError";

	/**
	 * 新增成功
	 */
	public static final ResultMessage ADD_SUCCESS_RESULT = new ResultMessage(SUCCESS, "新增成功");
	/**
	 * 新增失败
	 */
	public static final ResultMessage ADD_FAIL_RESULT = new ResultMessage(ERROR, "新增失败");
	/**
	 * 保存成功
	 */
	public static final ResultMessage SAVE_SUCCESS_RESULT = new ResultMessage(SUCCESS, "保存成功");
	/**
	 * 修改成功
	 */
	public static final ResultMessage UPDATE_SUCCESS_RESULT = new ResultMessage(SUCCESS, "修改成功");
	/***
	 * 修改失败
	 */
	public static final ResultMessage UPDATE_FAIL_RESULT = new ResultMessage(ERROR, "修改失败");
	
	/**
	 * 导出成功
	 */
	public static final ResultMessage EXPORT_SUCCESS_RESULT = new ResultMessage(SUCCESS, "导出成功");
	/**
	 * 导出失败
	 */
	public static final ResultMessage EXPORT_FAIL_RESULT = new ResultMessage(ERROR, "导出失败");
	/**
	 * 很抱歉，这段时间没有数据
	 */
	public static final ResultMessage EXPORT_NULL_RESULT = new ResultMessage(NULL, "很抱歉，这段时间没有数据");
	
	/**
	 *导入成功 
	 */
	public static final ResultMessage IMPORT_SUCCESS_RESULT = new ResultMessage(SUCCESS, "导入成功");
	/**
	 * 导入失败
	 */
	public static final ResultMessage IMPORT_FAIL_RESULT = new ResultMessage(ERROR, "导入失败");

	/**
	 * 设置成功
	 */
	public static final ResultMessage SET_SUCCESS_RESULT = new ResultMessage(SUCCESS, "设置成功");
	/**
	 * 审核通过
	 */
	public static final ResultMessage CHECK_SUCCESS_RESULT = new ResultMessage(SUCCESS, "审核通过");
	/**
	 * 审核失败
	 */
	public static final ResultMessage CHECK_FAIL_RESULT = new ResultMessage(SUCCESS, "审核失败");
	/**
	 * 驳回成功
	 */
	public static final ResultMessage REJECT_SUCCESS_RESULT = new ResultMessage(SUCCESS, "驳回成功");

	/**
	 * 删除成功
	 */
	public static final ResultMessage DELETE_SUCCESS_RESULT = new ResultMessage(SUCCESS, "删除成功");
	/**
	 * 删除失败
	 */
	public static final ResultMessage DELETE_FAIL_RESULT = new ResultMessage(ERROR, "删除失败");
	
	/**
	 * 短信发送成功
	 */
	public static final ResultMessage SMS_SEND_SUCCESS_RESULT = new ResultMessage(SUCCESS, "短信发送成功");
	/**
	 * 短信发送失败
	 */
	public static final ResultMessage SMS_SEND_FAIL_RESULT = new ResultMessage(ERROR, "短信发送失败");

	public static final ResultMessage ID_UNEXITE_RESULT = new ResultMessage(ERROR, "编号ID不存在");
	
	public static final ResultMessage JOB_START_SUCCESS = new ResultMessage(SUCCESS, "JOB启动成功");
	public static final ResultMessage JOB_START_FAIL = new ResultMessage(ERROR, "JOB启动失败");
	public static final ResultMessage JOB_PARAM_ERROR = new ResultMessage(ERROR, "JOB启动参数错误,请检查该JOB对应的参数值！");
	public static final ResultMessage JOB_STOP_SUCCESS = new ResultMessage(SUCCESS, "JOB关闭成功");
	public static final ResultMessage JOB_STOP_FAIL = new ResultMessage(ERROR, "JOB关闭失败");

	public static final ResultMessage ID_EXITE_RESULT = new ResultMessage(ERROR, "ID已存在");
	
	public static final ResultMessage NO_EXITE_FAIL = new ResultMessage(ERROR, "编号已存在");
	public static final ResultMessage NO_EXITE_SUCCESS = new ResultMessage(SUCCESS, "不存在");
	
	public static final ResultMessage UPDATE_WORKTIMESET_SUCCESS = new ResultMessage(SUCCESS, "编辑排班计划成功");
	public static final ResultMessage UPDATE_WORKTIMESET_FAIL = new ResultMessage(ERROR, "编辑排班计划失败");
	
	public static final ResultMessage STORE_USER_MAPPING_EXITE_FAIL = new ResultMessage(ERROR, "请先解除门店和人员的关联信息");
	
	
	public static final ResultMessage VERIFYUSERNAME_SUCCESS_RESULT = new ResultMessage(SUCCESS, "该职位名称可用");
	public static final ResultMessage VERIFYUSERNAME_FAIL_RESULT = new ResultMessage(ERROR, "职位名称已存在");
	
	public static final ResultMessage CALL_PLANNING_TYPE =  new ResultMessage("exist", "已有非拜访计划");
	public static final ResultMessage WEB_CALL_PLANNING_TYPE =  new ResultMessage("existVisit", "已有计划");
	/**
	 *操作成功 
	 */
	public static final ResultMessage WEB_OPERATE_SUCCESS= new ResultMessage("success", "操作成功");
	/**
	 * 操作失败
	 */
	public static final ResultMessage WEB_OPERATE_FAIL= new ResultMessage("ERROR", "操作失败");
	
	public static final ResultMessage REPORT_DATE_ERROR2= new ResultMessage(ERROR, "开始日期不能大于结束日期");
	public static final ResultMessage REPORT_DATE_ERROR= new ResultMessage(ERROR, "开始日期与结束日期不能大于31天");
	public static final ResultMessage REPORT_DATE_ERROR_7= new ResultMessage(ERROR, "开始日期与结束日期不能大于7天");
	public static final ResultMessage REPORT_DATE_ERROR_15= new ResultMessage(ERROR, "开始日期与结束日期不能大于15天");
	public static final ResultMessage REPORT_DATE_ERROR_100= new ResultMessage(ERROR, "开始日期与结束日期不能大于100天");
	public static final ResultMessage REPORT_DATE_ERROR3= new ResultMessage(ERROR, "开始月份与结束月份不能大于两月");
	public static final ResultMessage REPORT_DATE_ERROR4= new ResultMessage(ERROR, "开始月份不能大于结束月份");
	public static final ResultMessage REPORT_DATE_SYS_ERROR = new ResultMessage(SYS_ERROR, "系统异常");
	public static final ResultMessage REPORT_DATE_SUCCESS= new ResultMessage(SUCCESS, "验证通过");
	
	public static final ResultMessage WEB_CLIENT_STRUCTURE = new ResultMessage("exist","部门已存在");
	
	public static final ResultMessage SKUDISTRIBUTION_GROUPNAME = new ResultMessage("exist","分组名称已存在");
	
	public static final ResultMessage USER_STORE_RELA_SUCCESS  =  new ResultMessage(SUCCESS, "SUCCESS");
	public static final ResultMessage USER_STORE_RELA_ERROR  =  new ResultMessage(ERROR, "请先解除人员和门店的关联关系");
	
	
	public static final ResultMessage UPLOAD_SUCCESS_RESULT = new ResultMessage(SUCCESS, "文件上传成功");
	public static final ResultMessage UPLOAD_FAIL_RESULT = new ResultMessage(ERROR, "上传文件失败");
	
	public static final ResultMessage DATE_FORMAT = new ResultMessage(ERROR, "日期格式不正确");
	
	
	public static final ResultMessage CYCLE_SUCCESS= new ResultMessage(SUCCESS, "操作成功");
	public static final ResultMessage CYCLE_FAIL= new ResultMessage(ERROR, "相同周期内只能添加一份问卷");
	
	
	public static final ResultMessage EXPORT_SUCCESS = new ResultMessage(SUCCESS, "操作成功");
	public static final ResultMessage EXPORT_FAIL = new ResultMessage(SUCCESS, "单次数据导出不能超过20w条，请缩短查询时间");
	/**
	 * POSM 库存不足
	 */
	public static final ResultMessage POSM_MATERIAL_STOCK_ERROR = new ResultMessage(ERROR , "库存不足");
	/**
	 * POSM 库存充足
	 */
	public static final ResultMessage POSM_MATERIAL_STOCK_SUCCESS = new ResultMessage(SUCCESS, "库存充足");
	
	/**
	 * 验证码验证成功
	 */
	public static final ResultMessage CODE_SUCCESS_RESULT = new ResultMessage(SUCCESS, "验证成功");
	/**
	 * 验证码验证失败（验证码错误）
	 */
	public static final ResultMessage CODE_FAIL_RESULT = new ResultMessage(SUCCESS, "验证失败");
	
	
	private String code;

	private String message;

	private Map<String, Object> attributes;

	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultMessage(Map<String, Object> attributes, String code, String message) {
		this.code = code;
		this.message = message;
		this.attributes = attributes;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return SUCCESS.equalsIgnoreCase(code);
	}

	@Override
	public String toString() {
		return "ResultMessage [code=" + code + ", message=" + message + "]";
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void addObject(String key, Object value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
	}

	/**
	 * 调用当前方法把状态改为异常
	 * 
	 * @param msg
	 */
	public void raise(String msg) {
		code = ERROR;
		message = msg;
	}

	public void raise(ResultHandle handle) {
		if (handle.isFail()) {
			raise(handle.getMsg());
		}
	}
	
	public static ResultMessage createResultMessage(){
		ResultMessage msg = new ResultMessage(SUCCESS, "操作成功");
		return msg;
	}
}
