package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Assign;
import com.zua.pojo.Role;
import com.zua.utils.R;
import com.zua.vo.AssignVo;

public interface RoleService extends IService<Role> {
    /**
     * 获取角色列表，或者根据条件获取角色列表
     * @param role
     * @param pageSize
     * @param curPage
     * @return
     */
    IPage<Role> getListRole(Role role, Integer pageSize, Integer curPage);

    /**
     * 新增角色
     * @param role
     * @return
     */
    R addRole(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    R editRole(Role role);

    /**
     * 角色权限的回显
     * @param parm
     * @return
     */
    Assign getAssignShow(AssignVo parm);

}
