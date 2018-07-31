package com.plugs.listener;

import com.plugs.constants.ConfigConstants;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/4/14.
 */
public class SessionListener implements HttpSessionListener {

    private static ConcurrentHashMap hUserName = new ConcurrentHashMap();//保存sessionID和username的映射

    /**
     * 以下是实现HttpSessionListener中的方法
     **/
    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        hUserName.remove(se.getSession().getId());
    }

    /**
     * isAlreadyEnter-用于判断用户是否已经登录以及相应的处理方法
     * @param sUserName String-登录的用户名称
     * @return boolean-该用户是否已经登录过的标志
     */
    public static boolean isAlreadyEnter(HttpSession session, String sUserName) {
        boolean flag;
        if (hUserName.containsValue(sUserName)) {//如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在hUserName中)
            flag = true;
            //遍历原来的hUserName，删除原用户名对应的sessionID(即删除原来的sessionID和username)
            Iterator iter = hUserName.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                if ((val).equals(sUserName)) {
                    hUserName.remove(key);
                }
            }
            hUserName.put(session.getId(), sUserName);//添加现在的sessionID和username
            System.out.println("hUserName = " + hUserName);
        } else {//如果该用户没登录过，直接添加现在的sessionID和username
            flag = false;
            hUserName.put(session.getId(), sUserName);
            System.out.println("hUserName = " + hUserName);
        }
        return flag;

    }

    /**
     * isOnline-用于判断用户是否在线
     * @param session HttpSession-登录的用户名称
     * @return boolean-该用户是否在线的标志
     */
    public static boolean isOnline(HttpSession session) {
        if (hUserName.containsKey(session.getId())) {
            return true;
        }
        return false;
    }

    /**
     *  监听器检测是否有效
     * @param session
     * @param flag 1：后台用户， 2：官网企业
     * @return
     */
    public static Integer isSessionOnline(HttpSession session, Integer flag) {
        if(hUserName.containsKey(session.getId())){
            return 1; //当前会话有效
        } else {
            String sessionKey;
            if (flag == 1) {
                sessionKey = ConfigConstants.ADMIN_SESSION_KEY;
            } else {
                sessionKey = ConfigConstants.ENT_SESSION_KEY;
            }

            Object sessionUser = session.getAttribute(sessionKey);
            if (sessionUser != null) {
                return 2; //当前会话无效，其他会话有效
            } else {
                return 3; //无会话 无用户
            }
        }
    }



}
