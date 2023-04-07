package com.zua.MybatisConfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class MybatisMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        String id = UUID.randomUUID().toString().replace("-", "");
        //设置各实体类的id
        metaObject.setValue("id",id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置创建时间
        metaObject.setValue("createTime", format.format(new Date()));
        //设置更新
        metaObject.setValue("updateTime", format.format(new Date()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        metaObject.setValue("updateTime", format.format(new Date()));
    }
}
