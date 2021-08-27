package com.example.demo.adapter;

import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import com.example.demo.service.impl.TestServiceImpl;

import java.util.List;


/**
 * @Author: gin
 * @Date: 2021/7/28 上午9:51
 */
public class TestAdapter {
    private final TestServiceImpl testService;

    public TestAdapter(TestServiceImpl testService) {
        this.testService = testService;
    }


    public static void main(String[] args) {
        TestAdapter testAdapter = new TestAdapter(new TestServiceImpl());
        //testAdapter.test();
    }
}
