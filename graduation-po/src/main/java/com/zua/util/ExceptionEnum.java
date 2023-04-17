package com.zua.util;

public enum ExceptionEnum {
    SERVER_ERROR(500, "服务器异常！"),
    NO_STOCK(1001,"---->存不足!"),
    ;
    private Integer code;
    private String message;
    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

}
