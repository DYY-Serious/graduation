package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.MenuMapper;
import com.zua.pojo.Menu;
import com.zua.service.MenuService;
import com.zua.utils.MakeTree;
import com.zua.utils.R;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getListMenu() {
        //构造查询条件
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> list = this.list(queryWrapper);
        //组装树
        List<Menu> menus = MakeTree.makeMenuTree(list, null);
        //进行分页查询
        return menus;
    }

    /**
     * 选择上级菜单
     * @return
     */
    @Override
    public List<Menu> parentList() {
        String[] types = {"0","1"};
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.in(Menu::getType, Arrays.asList(types)).orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.list(queryWrapper);
        //组装顶级菜单，防止无数据没有菜单
        Menu menu = new Menu();
        menu.setId(null);
        menu.setParentId("top-menu");
        menu.setTitle("顶级菜单");
        menus.add(menu);
        List<Menu> menuList = MakeTree.makeMenuTree(menus,"top-menu");
        return menuList;
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @Override
    public R saveMenu(Menu menu) {
        //构造查询条件
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.eq(Menu::getTitle,menu.getTitle());
        queryWrapper.eq(Menu::getType,menu.getType());
        queryWrapper.eq(menu.getParentId() != null,Menu::getParentId,menu.getParentId());
        Menu one = this.getOne(queryWrapper);
        if (one != null) {
            return R.ERRORMSG("该目录下已有此菜单");
        }
        this.save(menu);
        return R.SUCCESS("添加成功");
    }

    /**
     * 编辑菜单
     * @param menu
     * @return
     */
    @Override
    public R editMenu(Menu menu) {
        Menu select = this.baseMapper.selectById(menu.getId());
        //构造查询条件
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        //如果没有更改title则不加title查询条件
        if (select.getTitle().equals(menu.getTitle())) {
            queryWrapper.eq(Menu::getTitle,"7ce3e1c88f6b4296a36598d0be43ad57");
        }else {
            queryWrapper.eq(Menu::getTitle,menu.getTitle());
        }
        //修改目录要判但该目录是否存在,两个目录不一样，也要进行title判断
        if (!menu.getType().equals(select.getType())) {
            queryWrapper.clear();
            queryWrapper.eq(Menu::getTitle,menu.getTitle());
        }
        //如果parentId不同则需要进行判断
        if (menu.getParentId() != null) {
            if (!menu.getParentId().equals(select.getParentId())) {
                queryWrapper.clear();
                queryWrapper.eq(Menu::getTitle,menu.getTitle());
            }
        }

        queryWrapper.eq(Menu::getType,menu.getType());
        queryWrapper.eq(menu.getParentId() != null,Menu::getParentId,menu.getParentId());
        Menu one = this.getOne(queryWrapper);
        if (one != null) {
            return R.ERRORMSG("该目录下已有此菜单");
        }
        this.updateById(menu);
        return R.SUCCESS("修改成功");
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public R delMenu(String id) {
        //判断是否有下级，有下级，不能删除
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.eq(Menu::getParentId,id);
        List<Menu> list = this.list(queryWrapper);
        if (list.size() > 0) {
            return R.ERRORMSG("该菜单存在下级，不能删除!");
        }
        this.removeById(id);
        return R.SUCCESS("删除成功!");
    }

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    @Override
    public List<Menu> getMenuByUserId(String userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> getMenuByRoleId(String roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    /**
     * 根据学生id查询权限
     * @param studentId
     * @return
     */
    @Override
    public List<Menu> getReaderMenuByUserId(String studentId) {
        return this.baseMapper.getReaderMenuByUserId(studentId);
    }
}
