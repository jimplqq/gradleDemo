package com.example.demo.config.mybatis_plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/** @Author: gin @Date: 2021/9/11 下午2:53 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("start insert fill ....");
    final LocalDateTime now = LocalDateTime.now();
    this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
    this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
    this.strictInsertFill(metaObject, "delFlag", Boolean.class, false);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("start update fill ....");
    this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
  }
}
