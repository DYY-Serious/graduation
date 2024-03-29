package com.zua.util;

import lombok.Data;

/**
 * 前后端统一返回数据格式
 */
@Data
public class R {
    private Integer code;

    private String message;

    private Object data;

    public static R SUCCESS() {
        R r = new R();
        r.setCode(StatusCode.SUCCESS_CODE);
        r.setMessage("成功");
        return r;
    }

    public static R SUCCESS(String msg) {
        R r = new R();
        r.setCode(StatusCode.SUCCESS_CODE);
        r.setMessage(msg);
        return r;
    }

    public static R SUCCESS(Object data) {
        R r = new R();
        r.setCode(StatusCode.SUCCESS_CODE);
        r.setMessage("成功");
        r.setData(data);
        return r;
    }

    public static R SUCCESS(String msg, Object data) {
        R r = new R();
        r.setCode(StatusCode.SUCCESS_CODE);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static R ERRORMSG() {
        R r = new R();
        r.setCode(StatusCode.ERROR_CODE);
        r.setMessage("未知错误");
        return r;
    }

    public static R ERRORMSG(String msg) {
        R r = new R();
        r.setCode(StatusCode.ERROR_CODE);
        r.setMessage(msg);
        return r;
    }

    public static R ERROR404() {
        R r = new R();
        r.setCode(StatusCode.ERROR_CODE);
        r.setMessage("失败，404");
        return r;
    }

    public static R ERROR403() {
        R r = new R();
        r.setCode(StatusCode.ERROR_CODE);
        r.setMessage("失败，403");
        return r;
    }

    public static R ERROR(String message, Integer code) {
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R ERROR(String message, Object data) {
        R r = new R();
        r.setCode(500);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static R MESSAGE(String message,Integer code) {
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
}
