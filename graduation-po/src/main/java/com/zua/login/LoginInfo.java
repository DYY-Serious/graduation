package com.zua.login;

import lombok.Data;

@Data
public class LoginInfo {

    private String name;
    private String avatar;
    private String introduction;
    private Object[] roles;
}
