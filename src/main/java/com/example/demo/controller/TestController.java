package com.example.demo.controller;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.alibaba.fastjson.JSON;
import com.example.demo.akka.works.TestActor;
import com.example.demo.config.akka.SpringExtension;
import com.example.demo.entity.Test;
import com.example.demo.proxy.MyCglibProxyFactory;
import com.example.demo.service.TestService;
import com.example.demo.service.impl.TestServiceImpl;
import com.example.demo.util.thread.ThreadExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: gin
 * @Date: 2021/7/26 上午11:37
 */
@Slf4j()
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private TestService testService;

    private final ActorRef testActor1;
    private final ActorRef testActor2;

    public TestController(@Autowired
                                  ActorSystem actorSystem,
                          @Autowired
                                  SpringExtension springExtension) {
        testActor1 = actorSystem.actorOf(springExtension.props("testActor"), "testActor1");
        testActor2 = actorSystem.actorOf(springExtension.props("testActor"), "testActor2");
    }

    @GetMapping("unique")
    public void unique() {
        TestServiceImpl proxy = (TestServiceImpl) MyCglibProxyFactory.getProxy(TestServiceImpl.class);
        log.info("unique:response:{}", JSON.toJSONString(proxy.unique()));
        //completableFutureService.get("test1");
    }

    @GetMapping("save")
    public void save() {
        testService.insert();
        //completableFutureService.get("test1");
    }


    private int score = 0;

    @GetMapping("test1")
    public void test1() {
        testActor1.tell("test1", ActorRef.noSender());
        //completableFutureService.get("test1");
    }

    @GetMapping("test2")
    public void test2() {
        testActor2.tell("test2", ActorRef.noSender());
        //completableFutureService.get("test2");
    }

    public static boolean check(Test test) {
        return false;
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);

    }

}
