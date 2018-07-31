package com.plugs.thread;


import com.plugs.base.WsMsg;
import com.plugs.utils.WebSocketUtil;

import java.util.List;

/**
 * Created by Zoro on 2016/9/24.
 */
public class SocketPushThread extends Thread {

    private List<String> uuids;
    private WsMsg wsMsg;
    private String exclude;

    public SocketPushThread(List<String> uuids, WsMsg wsMsg) {
        this.uuids = uuids;
        this.wsMsg = wsMsg;
    }

    public SocketPushThread(List<String> uuids, WsMsg wsMsg, String exclude) {
        this.uuids = uuids;
        this.wsMsg = wsMsg;
        this.exclude = exclude;
    }

    @Override
    public void run() {
        for (String uuid : uuids) {
            if (null != exclude && exclude.equals(uuid))
                continue;
            WebSocketUtil.sendObjMessageByToken(uuid, wsMsg);
        }
    }
}
