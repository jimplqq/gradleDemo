package com.example.demo.util.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import javax.inject.Singleton;
import java.util.concurrent.*;

/**
 * Created by nana on 2015/10/6.
 */
@Singleton
public class ThreadExecutor {

    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Long keepAliveTime;
    private TimeUnit timeUnit;

    private ThreadPoolExecutor threadPoolExecutor;
    private BlockingQueue<Runnable> queue;

    public ThreadExecutor() {
    }

    private static class ThreadExecutorHolder {
        /**
         * The Executor.
         */
        static ThreadExecutor executor = new ThreadExecutor();
    }

    /**
     * Get instance thread executor.
     *
     * @return the thread executor
     */
    public static ThreadExecutor getInstance() {
        return ThreadExecutorHolder.executor;
    }


    /**
     * Init.
     */
    public synchronized void init() {
        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
        }
        if (corePoolSize == null) {
            corePoolSize = 10;
        }
        if (maximumPoolSize == null) {
            maximumPoolSize = 100;
        }
        if (keepAliveTime == null) {
            keepAliveTime = 1L;
        }
        if (timeUnit == null) {
            timeUnit = TimeUnit.HOURS;
        }
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
//        threadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime, this.timeUnit, this.queue);
        threadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize,
                this.keepAliveTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * Execute.
     *
     * @param runnable the runnable
     */
    public void execute(Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    /**
     * Gets core pool size.
     *
     * @return the core pool size
     */
    public int getCorePoolSize() {
        return corePoolSize;
    }

    /**
     * Sets core pool size.
     *
     * @param corePoolSize the core pool size
     */
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    /**
     * Gets maximum pool size.
     *
     * @return the maximum pool size
     */
    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    /**
     * Sets maximum pool size.
     *
     * @param maximumPoolSize the maximum pool size
     */
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    /**
     * Gets keep alive time.
     *
     * @return the keep alive time
     */
    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    /**
     * Sets keep alive time.
     *
     * @param keepAliveTime the keep alive time
     */
    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    /**
     * Gets time unit.
     *
     * @return the time unit
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Sets time unit.
     *
     * @param timeUnit the time unit
     */
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * Gets thread pool excutor.
     *
     * @return the thread pool excutor
     */
    public ThreadPoolExecutor getThreadPoolExcutor() {
        return threadPoolExecutor;
    }

    /**
     * Sets thread pool excutor.
     *
     * @param threadPoolExecutor the thread pool executor
     */
    public void setThreadPoolExcutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    /**
     * Gets queue.
     *
     * @return the queue
     */
    public BlockingQueue<Runnable> getQueue() {
        return queue;
    }

    /**
     * Sets queue.
     *
     * @param queue the queue
     */
    public void setQueue(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

}
