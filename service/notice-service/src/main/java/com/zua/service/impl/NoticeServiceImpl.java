package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.NoticeMapper;
import com.zua.pojo.Notice;
import com.zua.service.NoticeService;
import com.zua.util.R;
import com.zua.vo.NoticeVo;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    /**
     * 获取通告列表
     * @param noticeVo
     * @return
     */
    @Override
    public R getList(NoticeVo noticeVo) {
        //构造分页
        IPage<Notice> page = new Page<Notice>(noticeVo.getCurPage(),noticeVo.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<Notice>();
        queryWrapper.like(noticeVo.getNoticeTitle() != null && !noticeVo.getNoticeTitle().equals(""),
                Notice::getNoticeTitle,noticeVo.getNoticeTitle());
        page = this.page(page,queryWrapper);
        return R.SUCCESS("查询成功",page);
    }
}
