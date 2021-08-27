package com.example.demo.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: gin
 * @Date: 2021/8/24 下午4:54
 */
@Slf4j
public class MyInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("before method {}", method.getName());
        Object object = proxy.invokeSuper(obj, args);
        log.info("after method {}", method.getName());
        return object;
    }
}
