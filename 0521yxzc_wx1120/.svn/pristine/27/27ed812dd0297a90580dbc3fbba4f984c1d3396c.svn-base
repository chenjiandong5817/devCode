package com.plugs.utils;

import com.exception.BizException;
import com.util.StringUtil;
import org.springframework.util.Assert;

/**
 * Created by Zhouhy on 2016/9/20.
 */
public class BizValidate {
    public static void notNull(Object object) {
        try {
            Assert.notNull(object);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    public static void notNull(Object object, String msg) {
        try {
            Assert.notNull(object);
        } catch (Exception e) {
            throw new BizException(msg);
        }
    }

    public static void notEmpty(Object object) {
        notEmpty(object, "");
    }

    public static void notEmpty(Object object, String msg) {
        if (object instanceof Integer) {
            if (object == null || StringUtil.getIntegerOfObject(object) == 0) {
                throw new BizException(msg);
            }
        }
        if (StringUtil.isNull(object) || StringUtil.isEmpty(String.valueOf(object))) {
            throw new BizException(msg);
        }
    }

    public static void notEmpty(String msg, Object... objs) {
        for (Object obj : objs) {
            if (obj instanceof Integer) {
                if (obj == null || StringUtil.getIntegerOfObject(obj) == 0) {
                    throw new BizException(msg);
                }
            }
            if (StringUtil.isNull(obj) || StringUtil.isEmpty(String.valueOf(obj))) {
                throw new BizException(msg);
            }
        }

    }

    public static void isTrue(boolean isTrue, String msg) {
        if (!isTrue) {
            throw new BizException(msg);
        }
    }

    public static void bizErr(String msg, int errCode) {
        throw new BizException(msg, errCode);
    }
}
