package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.UserMapper;
import com.zua.pojo.User;
import com.zua.pojo.UserRole;
import com.zua.service.UserRoleService;
import com.zua.service.UserService;
import com.zua.util.MD5;
import com.zua.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 新增用户
     * @param user
     */
    @Override
    @Transactional
    public R saveUser(User user) {
        //先判断学号是否重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getAccount,user.getAccount());
        User one = this.getOne(queryWrapper);
        if (one != null) {
            return R.ERRORMSG("账号重复!!!");
        }
        //密码加密
        String password = MD5.MD5Encode(user.getPassword(),"utf-8");
        user.setPassword(password);
        boolean flag = this.save(user);
        if (user.getRoleId() != null && !user.getRoleId().equals("")) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getId());
            userRoleService.save(userRole);
        }
        if (flag) {
            return R.SUCCESS("注册成功");
        }
        return R.ERRORMSG("注册失败!!!");
    }

    /**
     * 获取用户列表
     * @param user
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage<User> getUserList(User user, Integer pageSize, Integer curPage) {
        IPage<User> page = new Page<User>(curPage,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.like(user.getUsername() != null && !user.getUsername().equals(""),User::getUsername,user.getUsername());
        queryWrapper.like(user.getPhone() != null && !user.getPhone().equals(""),User::getPhone,user.getPhone());
        queryWrapper.eq(user.getIsDeleted() != null,User::getIsDeleted,user.getIsDeleted());
        page = this.page(page,queryWrapper);
        return page;
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @Override
    @Transactional
    public R editUser(User user) {
        //先查询出修改用户的原来信息
        User userInit = this.getById(user.getId());
        //如果原来的和修改的studentId相等，则直接进行修改，负责则要判断数据库中是否有一样的studentId
        if (!user.getAccount().equals(userInit.getAccount())) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
            queryWrapper.eq(user.getAccount()!=null,User::getAccount,user.getAccount());
            User one = this.getOne(queryWrapper);
            if (one != null) {
                return R.ERRORMSG("账号重复!!!");
            }
        }
        if (user.getRoleId() != null && !user.getRoleId().equals("")) {
            //先将用户对应的角色删除掉，再进行了插入
            LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>();
            queryWrapper.eq(UserRole::getUserId,user.getId());
            userRoleService.remove(queryWrapper);
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getId());
            userRoleService.save(userRole);
        }
        this.updateById(user);
        return R.SUCCESS("修改成功");
    }

    @Override
    public User loadById(Object id) {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.lambda().eq(User::getId,id);
        return this.baseMapper.selectOne(query);
    }
}
