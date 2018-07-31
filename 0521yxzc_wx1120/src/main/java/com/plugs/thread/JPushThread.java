package com.plugs.thread;


import com.plugs.utils.JPushHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 极光推送线程类
 *
 * @author Zoro
 * @since 2016/10/6
 */
public class JPushThread extends Thread {

    public enum SendType {
        ALIAS, TAGS, ALL
    }

    private JPushHelper.TargetTo target;//发送目标
    private JPushHelper.Os os;//目标平台
    private SendType sendType;//发送方式

    private String title = "";//标题
    private String content = "";//内容
    private int badge = 0;//角标

    private String[] tags;//标签
    private String alias[];//别名

    private Map<String, String> extras = new HashMap<String, String>();//附带信息


    /**
     * 构造函数
     *
     * @param title
     * @param content
     * @param badge
     * @param extras
     */
    public JPushThread(String title, String content, int badge, Map<String, String> extras) {
        this.title = title;
        this.content = content;
        if (badge != 0)
            this.badge = badge;
        if (null != extras)
            this.extras = extras;
    }

    /**
     * 初始化发送目标和发送类型
     *
     * @param target
     * @param sendType
     * @param os
     * @return
     */
    public JPushThread init(JPushHelper.TargetTo target, SendType sendType, JPushHelper.Os os) {
        this.sendType = sendType;
        this.os = os;
        this.target = target;
        return this;
    }

    /**
     * 设置具体目标
     *
     * @param tags
     * @param alias
     * @return
     */
    public JPushThread setTargets(String[] tags, String[] alias) {
        this.tags = tags;
        this.alias = alias;
        return this;
    }


    @Override
    public void run() {
        JPushHelper jPushHelper = new JPushHelper(target);//创建极光推送对象

        //判断发送方式
        switch (sendType) {
            case ALIAS:
                //判断平台
                if (JPushHelper.Os.ANDROID == os) {
                    jPushHelper.pushMessage4AndroidWithAlias(alias, title, content, extras);
                } else if (JPushHelper.Os.IOS == os) {
                    jPushHelper.pushNotification4IosWithAlias(alias, content, badge, extras);
                } else {
                    jPushHelper.pushMessage4AndroidWithAlias(alias, title, content, extras);
                    jPushHelper.pushNotification4IosWithAlias(alias, content, badge, extras);
                }
                break;
            case TAGS:
                if (JPushHelper.Os.ANDROID == os) {
                    jPushHelper.pushMessage4AndroidWithTags(tags, title, content, extras);
                } else if (JPushHelper.Os.IOS == os) {
                    jPushHelper.pushNotification4IosWithTags(tags, content, 0, extras);
                } else {
                    jPushHelper.pushMessage4AndroidWithTags(tags, title, content, extras);
                    jPushHelper.pushNotification4IosWithTags(tags, content, 0, extras);
                }
                break;
            case ALL:
                jPushHelper.pushAll(title, content, badge, extras, JPushHelper.Os.ANDROID);
                jPushHelper.pushAll(title, content, badge, extras, JPushHelper.Os.IOS);
                break;
            default:
                break;
        }


    }
}
