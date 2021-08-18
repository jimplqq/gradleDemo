package com.example.demo.service;

import com.example.demo.entity.Test;

import java.util.List;

/**
 * @Author: gin
 * @Date: 2021/7/26 上午11:46
 */
public interface TestService {
     List<Test> test();

     Test unique();


}
