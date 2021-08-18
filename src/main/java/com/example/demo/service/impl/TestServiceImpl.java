package com.example.demo.service.impl;

import com.example.demo.dao.TestDao;
import com.example.demo.entity.Test;
import com.example.demo.example.TestExample;
import com.example.demo.service.TestService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: gin
 * @Date: 2021/7/26 上午11:46
 */

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    @Resource
    private RestHighLevelClient highLevelClient;


    @Override
    public List<Test> test() {
        TestExample testExample = new TestExample();
        testExample.createCriteria().andNameIsNotNull();
        List<Test> tests = testDao.selectByExample(testExample);
        return tests;
    }

    @Override
    public Test unique() {
        return testDao.selectByPrimaryKey(1L);
    }



}
