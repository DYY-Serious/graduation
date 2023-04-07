package com.zua.vo;

import com.zua.pojo.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeVo extends Notice {
    private Integer curPage;
    private Integer pageSize;
}
