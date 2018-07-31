package com.plugs.timer;

import com.plugs.utils.SysConfigHelper;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * api定时任务
 *
 * @author Zoro
 * @since 2016/11/2
 */
@Component
public class ApiTimer {

    // 进程安全锁
    Lock lock = new ReentrantLock();

    private static final Logger logger = Logger.getLogger(ApiTimer.class);

    // 每10分钟执行一次
    @Scheduled(cron = "0 0/10 *  * * ? ")
    public void min1Task() {
        if (lock.tryLock()) {
            try {
                SysConfigHelper.clear();
                System.out.println("每10分钟执行一次");
            } catch (Exception ex) {
                logger.debug("min1Task Exception：" + ex);
            } finally {
                lock.unlock();
            }
        }
    }


}