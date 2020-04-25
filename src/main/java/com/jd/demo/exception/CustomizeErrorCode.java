package com.jd.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    INVALID_URL(444, "无效url");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
