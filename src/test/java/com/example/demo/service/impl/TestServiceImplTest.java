package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TestServiceImplTest {
    Logger logger = LoggerFactory.getLogger(TestServiceImplTest.class);
    @Resource
    private TestServiceImpl testService;

    @Test
    void test1() {

        logger.info("test:list:{}", JSON.toJSONString(testService.test()));
    }
}