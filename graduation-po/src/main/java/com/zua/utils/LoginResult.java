package com.zua.utils;

import lombok.Data;

@Data
public class LoginResult {
    private String id;
    private String token;
    //token过期时间
    private Long expireTime;
}
