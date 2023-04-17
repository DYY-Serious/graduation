package com.zua.util;

import com.zua.pojo.Class;
import com.zua.pojo.Menu;
import com.zua.pojo.Seat;
import com.zua.vo.RouterVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MakeTree {
    public static List<Menu> makeMenuTree(List<Menu> menuList, String pid) {
        List<Menu> list = new ArrayList<Menu>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(dom -> {
                    Menu menu = new Menu();
                    BeanUtils.copyProperties(dom, menu);//查询该项的下级菜单
                    List<Menu> sysMenus = makeMenuTree(menuList,
                            dom.getId());
                    menu.setChildren(sysMenus);
                    list.add(menu);
                });
        return list;
    }

    public static List<Class> makeClassTree(List<Class> classList, String pid) {
        List<Class> list = new ArrayList<Class>();
        Optional.ofNullable(classList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(dom -> {
                    Class menu = new Class();
                    BeanUtils.copyProperties(dom, menu);//查询该项的下级菜单
                    List<Class> sysMenus = makeClassTree(classList,
                            dom.getId());
                    menu.setChildren(sysMenus);
                    list.add(menu);
                });
        return list;
    }

    /**
     * 生成路由数据格式
     */
    public static List<RouterVO> makeRouter(List<Menu> menuList, String pid) {
        //接受生产的路由数据
        List<RouterVO> list = new ArrayList<>();
        //组装数据
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(item -> {
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //判断是否是一级菜单
                    if (item.getParentId() == null) {
                        router.setComponent("Layout");
                        router.setAlwaysShow(true);
                    } else {
                        router.setComponent(item.getUrl());
                        router.setAlwaysShow(false);
                    }
                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getCode().split(",")
                    ));
                    //设置children
                    List<RouterVO> children = makeRouter(menuList,
                            item.getId());
                    router.setChildren(children);
                    if (router.getChildren().size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    list.add(router);
                });
        return list;

    }

    public static List<Seat> makeSeatTree(List<Seat> seatList, String pid) {
        List<Seat> list = new ArrayList<Seat>();
        Optional.ofNullable(seatList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(dom -> {
                    Seat seat = new Seat();
                    BeanUtils.copyProperties(dom, seat);//查询该项的下级菜单
                    List<Seat> seats = makeSeatTree(seatList,
                            dom.getId());
                    seat.setChildren(seats);
                    list.add(seat);
                });
        return list;
    }
}
