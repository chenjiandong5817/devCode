package com.plugs.thread;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadHelper
 *
 * @author Zoro
 * @date 2016/12/22
 */
public class ThreadHelper {

    private static final Logger logger = Logger.getLogger(ThreadHelper.class);

    //订单任务线程池
    private static ThreadPoolExecutor orderTaskPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();


    /**
     * 执行订单任务
     *
     * @param orderTask
     */
    public static void executeOrderTask(Thread orderTask) {
        orderTaskPool.execute(orderTask);
        logger.debug("【订单线程池任务】线程池中线程数：" + orderTaskPool.getPoolSize());
        logger.debug("【订单线程池任务】队列中等待执行的任务数：" + orderTaskPool.getQueue().size());
        logger.debug("【订单线程池任务】已执行完任务数：" + orderTaskPool.getCompletedTaskCount());
    }


    /**
     * 关闭线程池
     */
    public static void shutdown() {
        logger.debug("shutdown pool...");
        orderTaskPool.shutdown();
        try {
            if (!orderTaskPool.isTerminated()) {
                logger.debug("直接关闭失败[" + orderTaskPool.toString() + "]");
                orderTaskPool.awaitTermination(3, TimeUnit.SECONDS);
                if (orderTaskPool.isTerminated()) {
                    logger.debug("成功关闭[" + orderTaskPool.toString() + "]");
                } else {
                    logger.debug("[" + orderTaskPool.toString() + "]关闭失败，执行shutdownNow...");
                    if (orderTaskPool.shutdownNow().size() > 0) {
                        logger.debug("[" + orderTaskPool.toString() + "]没有关闭成功");
                    } else {
                        logger.debug("shutdownNow执行完毕，成功关闭[" + orderTaskPool.toString() + "]");
                    }
                }
            } else {
                logger.debug("成功关闭[" + orderTaskPool.toString() + "]");
            }
        } catch (InterruptedException e) {
            logger.warn("接收到中断请" + orderTaskPool.toString() + "停止操作");
        }
    }
}
