package com.zua.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zua.annotation.Auth;
import com.zua.pojo.Notice;
import com.zua.service.NoticeService;
import com.zua.util.JwtUtils;
import com.zua.util.R;
import com.zua.vo.NoticeVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取通告列表
     * @param noticeVo
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getNoticeList(NoticeVo noticeVo, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        if (claims == null) {
            return R.ERROR("token过期!",600);
        }
        return noticeService.getList(noticeVo);
    }

    /**
     * 编辑
     * @param notice
     * @return
     */
    @Auth
    @PostMapping("editNotice")
    public R editNotice(@RequestBody Notice notice, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        if (claims == null) {
            return R.ERROR("token过期!",600);
        }
        boolean flag = noticeService.updateById(notice);
        return flag ? R.SUCCESS("编辑成功") : R.ERRORMSG("编辑失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("delNotice/{id}")
    public R delNotice(@PathVariable("id") String id, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        if (claims == null) {
            return R.ERROR("token过期!",600);
        }
        boolean flag = noticeService.removeById(id);
        return flag ? R.SUCCESS("删除成功") : R.ERRORMSG("删除失败");
    }

    /**
     * 新增
     * @param notice
     * @return
     */
    @Auth
    @PostMapping("addNotice")
    public R addNotice(@RequestBody Notice notice, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        if (claims == null) {
            return R.ERROR("token过期!",600);
        }
        boolean flag = noticeService.save(notice);
        return flag ? R.SUCCESS("新增成功") : R.ERRORMSG("新增失败");
    }

    //列表
    @Auth
    @GetMapping("/getTopList")
    public R getTopList(){
        QueryWrapper<Notice> query = new QueryWrapper<Notice>();
        query.lambda().orderByDesc(Notice::getCreateTime).last("limit 3");
        List<Notice> list = noticeService.list(query);
        return R.SUCCESS("查询成功",list);
    }

}
