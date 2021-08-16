package com.example.demo.adapter;

import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import com.example.demo.service.impl.TestServiceImpl;

import java.util.List;


/**
 * @Author: gin
 * @Date: 2021/7/28 上午9:51
 */
public class TestAdapter implements TestService {
    private final TestServiceImpl testService;

    public TestAdapter(TestServiceImpl testService) {
        this.testService = testService;
    }


    @Override
    public List<Test> test() {
        return testService.test();
    }

    @Override
    public Test unique() {
        return testService.unique();
    }

    public static void main(String[] args) {
        TestAdapter testAdapter = new TestAdapter(new TestServiceImpl());
        testAdapter.test();
    }
}
