package com.zua.vo;

import lombok.Data;

import java.util.List;

@Data
public class AssignVo {
    private String userId;
    private String roleId;
    private List<String> menuList;
}
