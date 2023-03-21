package com.zua.MybatisConfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Component
public class MybatisMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        String id = UUID.randomUUID().toString().replace("-", "");
        metaObject.setValue("id",id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        metaObject.setValue("createTime", format.format(new Date()));
        metaObject.setValue("updateTime", format.format(new Date()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        metaObject.setValue("updateTime", format.format(new Date()));
    }
}
