package com.utils.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

/**
 * QxLog（log4j）自定义日志级别
 *
 * @author Zoro
 * @date 2016/11/2
 */

public class QxLog {

    /**
     * 继承Level
     */
    private static class QxLogLevel extends Level {
        private static final long serialVersionUID = 1076913470822079835L;

        private QxLogLevel(int level, String levelStr, int syslogEquivalent) {
            super(level, levelStr, syslogEquivalent);
        }
    }

    /**
     * 自定义级别名称，以及级别范围
     */
    public static final Level LEVEL = new QxLogLevel(20050, "QX", 5);

}