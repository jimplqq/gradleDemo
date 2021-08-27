package com.example.demo.service.impl;

import com.example.demo.common.myException.ToUserException;
import com.example.demo.dao.TestDao;
import com.example.demo.entity.Test;
import com.example.demo.example.TestExample;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: gin
 * @Date: 2021/7/26 上午11:46
 */

@Scope()
@Slf4j
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert() {
        Test test = new Test();
        test.setName("a");
        try {
            testDao.insertSelective(test);
            this.b();
        } catch (Exception e) {
            log.error("",e);
            throw new ToUserException("aa");
        }

        return true;
    }

    private void b() {
        Test test = new Test();
        test.setName("b");
        testDao.insertSelective(test);
        Integer.valueOf("a");
    }


}
