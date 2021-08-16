package com.example.demo.controller;


import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: gin
 * @Date: 2021/7/26 上午11:37
 */
@Slf4j()
@RestController
@RequestMapping("test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("test")
    public List<Test> test() {
        List<Test> test = testService.test();
        List<Test> tests = test.stream().sorted(Comparator.comparing(TestController::check)).collect(Collectors.toList());
        return tests;
    }

    public static boolean check(Test test) {
        return false;
    }

    public static void main(String[] args) {
        log.info("info");
        log.debug("debug");
        log.error("error");
        log.warn("warn");
    }
}
