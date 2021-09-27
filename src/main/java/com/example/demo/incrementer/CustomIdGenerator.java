package com.example.demo.incrementer;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.example.demo.config.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: gin
 * @Date: 2021/9/11 下午2:17
 */
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        final IdentifierGenerator identifierGenerator =
                new DefaultIdentifierGenerator(localHost);
        return identifierGenerator.nextId(entity);
    }
}
