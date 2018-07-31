package com.plugs.listener;

import com.plugs.thread.ThreadHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolListener
 *
 * @author Zoro
 * @date 2016/12/29
 */
public class ThreadPoolListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ThreadPoolListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("shutdown thread pool ....");
        ThreadHelper.shutdown();
    }

}
