package com.example.demo.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @Author: gin
 * @Date: 2021/8/24 下午4:59
 */
public class MyCglibProxyFactory {
    public static Object getProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MyInterceptor());
        return enhancer.create();
    }
}
