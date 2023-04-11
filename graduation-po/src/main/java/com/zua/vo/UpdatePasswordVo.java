package com.zua.vo;

import lombok.Data;

@Data
public class UpdatePasswordVo {
    private String id;
    private String oldPassword;
    private String password;
}
