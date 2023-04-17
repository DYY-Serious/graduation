package com.zua.controller;

import com.zua.annotation.Auth;
import com.zua.pojo.Menu;
import com.zua.service.MenuService;
import com.zua.util.JwtUtils;
import com.zua.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取菜单列表
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getMenuList(HttpServletRequest request) {
        List<Menu> listMenu = menuService.getListMenu();
        return R.SUCCESS("查询成功",listMenu);
    }

    /**
     * 选择上级菜单
     * @return
     */
    @Auth
    @GetMapping("/parent")
    public R getParentList(HttpServletRequest request){
        List<Menu> list = menuService.parentList();
        return R.SUCCESS("查询成功",list);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @Auth
    @PostMapping("addMenu")
    public R addMenu(@RequestBody Menu menu,HttpServletRequest request) {
        return menuService.saveMenu(menu);
    }

    //编辑
    @Auth
    @PostMapping("editMenu")
    public R editMenu(@RequestBody Menu menu,HttpServletRequest request){
        return menuService.editMenu(menu);
    }

    //删除
    @Auth
    @DeleteMapping("delMenu/{id}")
    public R deleteMenu(@PathVariable("id") String id,HttpServletRequest request){
        return menuService.delMenu(id);
    }


}
