package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Menu;
import com.zua.pojo.Role;
import com.zua.service.MenuService;
import com.zua.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     * @return
     */
    @GetMapping("list")
    public R getMenuList() {
        List<Menu> listMenu = menuService.getListMenu();
        return R.SUCCESS("查询成功",listMenu);
    }

    /**
     * 选择上级菜单
     * @return
     */
    @GetMapping("/parent")
    public R getParentList(){
        List<Menu> list = menuService.parentList();
        return R.SUCCESS("查询成功",list);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping("addMenu")
    public R addMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    //编辑
    @PostMapping("editMenu")
    public R editMenu(@RequestBody Menu menu){
        return menuService.editMenu(menu);
    }

    //删除
    @DeleteMapping("delMenu/{id}")
    public R deleteMenu(@PathVariable("id") String id){
        return menuService.delMenu(id);
    }


}
