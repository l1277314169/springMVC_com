package com.comm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultMessage implements Serializable {
    private static final long serialVersionUID = -7563387945976477789L;
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String SYS_ERROR = "sysError";
    public static final ResultMessage ADD_SUCCESS_RESULT = new ResultMessage(
            "success", "新增成功");
    public static final ResultMessage ADD_FAIL_RESULT = new ResultMessage(
            "error", "新增失败");

    public static final ResultMessage UPDATE_CATEGORY_ERROR_RESULT = new ResultMessage(
            "error", "改品类已存在");

    public static final ResultMessage UPDATE_SUCCESS_RESULT = new ResultMessage(
            "success", "修改成功");
    public static final ResultMessage UPDATE_FAIL_RESULT = new ResultMessage(
            "error", "修改失败");

    public static final ResultMessage SET_SUCCESS_RESULT = new ResultMessage(
            "success", "设置成功");
    public static final ResultMessage SET_FAIL_RESULT_NOPARM = new ResultMessage(
            "error", "取消失败，无参数传入");
    public static final ResultMessage SET_FAIL_RESULT_NORELATION = new ResultMessage(
            "error", "取消失败,该产品无关联关系存在");
    public static final ResultMessage SET_FAIL_RESULT = new ResultMessage(
            "error", "设置失败");
    public static final ResultMessage CHECK_SUCCESS_RESULT = new ResultMessage(
            "success", "审核通过");
    public static final ResultMessage REJECT_SUCCESS_RESULT = new ResultMessage(
            "success", "驳回成功");

    public static final ResultMessage DELETE_SUCCESS_RESULT = new ResultMessage(
            "success", "删除成功");
    public static final ResultMessage DELETE_FAIL_RESULT = new ResultMessage(
            "error", "删除失败");





    private String code;
    private String message;
    private Map<String, Object> attributes;

    public ResultMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultMessage(Map<String, Object> attributes, String code,
                         String message) {
        this.code = code;
        this.message = message;
        this.attributes = attributes;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(this.code);
    }

    public String toString() {
        return "ResultMessage [code=" + this.code + ", message=" + this.message
                + "]";
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addObject(String key, Object value) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        this.attributes.put(key, value);
    }

    public void raise(String msg) {
        this.code = "error";
        this.message = msg;
    }


    public static ResultMessage createResultMessage() {
        ResultMessage msg = new ResultMessage("success", "操作成功");
        return msg;
    }
}