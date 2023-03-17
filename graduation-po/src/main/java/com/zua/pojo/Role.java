package com.zua.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private String id;
    private String roleName;
    private List<Limit> limits; //权限列表
}
