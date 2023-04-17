package com.zua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Notice;
import com.zua.util.R;
import com.zua.vo.NoticeVo;

public interface NoticeService extends IService<Notice> {

    /**
     * 获取通告列表
     * @param noticeVo
     * @return
     */
    R getList(NoticeVo noticeVo);
}
