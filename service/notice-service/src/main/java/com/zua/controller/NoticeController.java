package com.zua.controller;

import com.zua.pojo.Notice;
import com.zua.service.NoticeService;
import com.zua.utils.R;
import com.zua.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 获取通告列表
     * @param noticeVo
     * @return
     */
    @GetMapping("list")
    public R getNoticeList(NoticeVo noticeVo) {
        return noticeService.getList(noticeVo);
    }

    /**
     * 编辑
     * @param notice
     * @return
     */
    @PostMapping("editNotice")
    public R editNotice(@RequestBody Notice notice) {
        boolean flag = noticeService.updateById(notice);
        return flag ? R.SUCCESS("编辑成功") : R.ERRORMSG("编辑失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("delNotice/{id}")
    public R delNotice(@PathVariable("id") String id) {
        boolean flag = noticeService.removeById(id);
        return flag ? R.SUCCESS("删除成功") : R.ERRORMSG("删除失败");
    }

    /**
     * 新增
     * @param notice
     * @return
     */
    @PostMapping("addNotice")
    public R addNotice(@RequestBody Notice notice) {
        boolean flag = noticeService.save(notice);
        return flag ? R.SUCCESS("新增成功") : R.ERRORMSG("新增失败");
    }
}
