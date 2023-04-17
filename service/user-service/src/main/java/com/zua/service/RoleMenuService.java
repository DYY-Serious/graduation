package com.zua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.RoleMenu;
import com.zua.util.R;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 角色分配权限
     * @param roleId
     * @param menuList
     */
    R assignSave(String roleId, List<String> menuList);
}
