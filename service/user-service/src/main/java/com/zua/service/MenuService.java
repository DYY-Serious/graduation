package com.zua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Menu;
import com.zua.util.R;

import java.util.List;

public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单列表
     * @return
     */
    List<Menu> getListMenu();

    /**
     * 选择上级菜单
     * @return
     */
    List<Menu> parentList();

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    R saveMenu(Menu menu);

    /**
     * 编辑菜单
     * @param menu
     * @return
     */
    R editMenu(Menu menu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    R delMenu(String id);

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    List<Menu> getMenuByUserId(String userId);

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    List<Menu> getMenuByRoleId(String roleId);

    /**
     * 根据学生id查询权限
     * @param studentId
     * @return
     */
    List<Menu> getReaderMenuByUserId(String studentId);
}
