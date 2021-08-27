package com.example.demo.common.myDelayed;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gin
 * @Date: 2021/8/26 下午2:12
 */
public class OrderDelay implements Delayed {

    private String tradeId;
    private long start = System.currentTimeMillis();
    private long timeout;

    public OrderDelay(String tradeId, Long timeout) {
        this.tradeId = tradeId;
        this.timeout = timeout;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((start + timeout) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        OrderDelay o1 = (OrderDelay) o;
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o1.getDelay(TimeUnit.MILLISECONDS));
    }

    void print() {
        System.out.println("订单要删除了：" + tradeId);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            list.add("000000" + i);
        }
        DelayQueue<OrderDelay> orderDelays = new DelayQueue<>();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            orderDelays.put(new OrderDelay(list.get(i), TimeUnit.MILLISECONDS.convert(3, TimeUnit.SECONDS)));
            try {
                orderDelays.take().print();
                System.out.println("after " + (System.currentTimeMillis() - l) + " milliSeconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
