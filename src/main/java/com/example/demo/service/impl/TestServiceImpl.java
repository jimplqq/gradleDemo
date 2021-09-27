package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Test;
import com.example.demo.service.TestService;
import com.example.demo.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
 * @Author gin
 * @date 2021年9月18日 上午10:50:59
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

}




