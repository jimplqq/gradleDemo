package com.example.demo.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.java_beans.vo.request.TestRequest;
import com.example.demo.mapper.TestMapper;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class TestServiceImplTest {

  @Resource private TestService testService;
  @Resource private TestMapper testMapper;

  @Test
  void insert() {
    final com.example.demo.domain.Test test = new com.example.demo.domain.Test();
    test.setUid("11");
    test.setName("11");
    final int insert = testMapper.insert(test);
    Assertions.assertEquals(1, insert);
  }

  @Test
  void query() {
    QueryWrapper<com.example.demo.domain.Test> wrapper = new QueryWrapper<>();

    final TestRequest request = new TestRequest();
    request.setName("11");
    wrapper.eq("name", request.getName());
    final Page<com.example.demo.domain.Test> testPage = testMapper.selectPage(request, wrapper);
    log.info("testPage:{}", JSON.toJSONString(testPage));
  }

  @Test
  void delete() {
    final int i = testMapper.deleteById(1L);
    Assertions.assertEquals(1, i);
  }

  @Test
  void update() {
    com.example.demo.domain.Test test = testMapper.selectById(2L);
    QueryWrapper<com.example.demo.domain.Test> wrapper = new QueryWrapper<>();
    test.setName(System.currentTimeMillis() + "");
    wrapper.eq("id", test.getId());
    final int update = testMapper.update(test, wrapper);
    Assertions.assertEquals(1, update);
  }

  @Test
  void database() {
    String url =
        "jdbc:mysql://rm-2zehi55y213b26m9i8o.mysql.rds.aliyuncs.com/test26?characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
    String username = "hkgietest";
    String password = "ZCkAsRE$hk153102859";
    // 生成 16 位随机 AES 密钥
    String randomKey = AES.generateRandomKey();
    log.info("url:randomKey:{}", randomKey);
    // 随机密钥加密
    String result = AES.encrypt(url, randomKey);
    log.info("url:result:{}", result);
    String username1 = AES.encrypt(username, randomKey);
    log.info("username:result:{}", username1);
    String password1 = AES.encrypt(password, randomKey);
    log.info("password:result:{}", password1);
  }

  @Test
  void hutoolThread() {
    ThreadUtil.execute(() -> {});
  }
}
