package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.RoleMenuMapper;
import com.zua.pojo.RoleMenu;
import com.zua.service.RoleMenuService;
import com.zua.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    /**
     * 角色分配权限
     * @param roleId
     * @param menuList
     */
    @Override
    @Transactional
    public R assignSave(String roleId, List<String> menuList) {
        //先删除角色对应的权限
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleId);
        this.remove(queryWrapper);
        //插入新的权限
        List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
        if (menuList.size() == 0) {
            return R.SUCCESS("分配成功");
        }
        for (String menuId : menuList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        return this.saveBatch(roleMenus) ? R.SUCCESS("分配成功") : R.ERRORMSG("分配失败");
    }
}
