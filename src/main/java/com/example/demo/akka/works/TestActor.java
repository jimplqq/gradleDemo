package com.example.demo.akka.works;

import akka.actor.AbstractActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: gin
 * @Date: 2021/8/17 下午3:42
 */
@Slf4j
@Component("testActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("test1", this::test1)
                .matchEquals("test2", this::test2)
                .build();
    }

    private <P> void test2(P p) {
        log.info("test2");
    }

    private <P> void test1(P p) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("test1");
    }
}
