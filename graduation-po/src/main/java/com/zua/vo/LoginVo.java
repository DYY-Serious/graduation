package com.zua.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {
    private String account;
    private String password;
    private String loginType;
}
