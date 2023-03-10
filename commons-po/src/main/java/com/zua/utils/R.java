package com.zua.utils;

import lombok.Data;

/**
 * 前后端统一返回数据格式
 */
@Data
public class R {
    private Integer code;

    private String message;

    private Object data;

    public static R success() {
        R r = new R();
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(200);
        r.setMessage("成功");
        r.setData(data);
        return r;
    }

    public static R error404() {
        R r = new R();
        r.setCode(404);
        r.setMessage("失败，404");
        return r;
    }

    public static R error403() {
        R r = new R();
        r.setCode(403);
        r.setMessage("失败，403");
        return r;
    }
}
