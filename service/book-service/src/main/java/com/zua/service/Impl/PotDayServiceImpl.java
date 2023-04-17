package com.zua.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.PotDayMapper;
import com.zua.pojo.PotDay;
import com.zua.service.PotDayService;
import org.springframework.stereotype.Service;

@Service
public class PotDayServiceImpl extends ServiceImpl<PotDayMapper, PotDay> implements PotDayService {
}
