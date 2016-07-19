package com.comm;

/**
 * Created by liuhonger on 2016/7/14.
 */
public class CustomException extends  Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
