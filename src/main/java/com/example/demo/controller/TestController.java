package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.Test;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/** @Author: gin @Date: 2021/9/14 上午9:56 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

  @Resource private TestService testService;

  @GetMapping("search")
  public void search() {
    final List<Test> search = testService.list();
    log.info("search:response:{}", JSON.toJSONString(search));
  }

  @PutMapping("update")
  public void update() {
    testService.update();
  }
}
