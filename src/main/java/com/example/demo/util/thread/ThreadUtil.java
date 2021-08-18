package com.example.demo.util.thread;


import java.util.concurrent.ThreadPoolExecutor;

/**
 * The type Thread util.
 */
public class ThreadUtil {

    /**
     * Get thread pool executor.
     *
     * @return the thread pool executor
     */
    public static ThreadPoolExecutor get() {
        return ThreadExecutor.getInstance().getThreadPoolExcutor();
    }

}
