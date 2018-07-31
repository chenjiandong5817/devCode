package com.plugs.module_wechat.api;

import com.plugs.module_wechat.constants.Constants;
import com.plugs.module_wechat.entity.menu.Button;
import com.plugs.module_wechat.entity.menu.ClickButton;
import com.plugs.module_wechat.entity.menu.Menu;
import com.plugs.module_wechat.entity.menu.ViewButton;
import com.plugs.module_wechat.util.AccessToken;
import com.plugs.module_wechat.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建公众号菜单类
 *
 * @author Administrator
 */
public class WeChatMenuApi {

    private static Logger log = LoggerFactory.getLogger(WeChatMenuApi.class);

    public static void main(String[] args) {

        // 调用接口获取 access_token
        AccessToken at = WeixinUtil.getAccessToken(Constants.APPID, Constants.APPSECRET);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(initMenu(), at.getToken());

            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
    }

    /**
     * 组装菜单
     *
     * @return
     */
    public static Menu initMenu() {

        Menu menu = new Menu();

        /********1*******/
        ViewButton btn_activitys = new ViewButton();
        btn_activitys.setName("接送机");
        btn_activitys.setType("view");
        btn_activitys.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7b2cd97fb9446891&redirect_uri=http%3A%2F%2F5afef62e.ngrok.io%2Fwechat%2Fauth%2FgetUserInfoAndRedirct&response_type=code&scope=snsapi_userinfo&state=YXZC_STATE&uin=NDU4MTA1MzIw&key=a1ef062e63bc1830607f548d949cef029b87447063a34f1f516037dd85a09266a64b044824905795cff86595e29c4086&pass_ticket=RHelAXNFrmKkBl0x6aDPkvp/qdxtqT8ogi7liTD2F2OF4eqhvl+VeDs2Ou/kiDx+rySP7RPeR/iwL5Ae+rZwxA==");

        /********2*******/
        ViewButton btn_search = new ViewButton();
        btn_search.setName("专线查询");
        btn_search.setType("view");
        btn_search.setUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MzIyMTU3OTI2MA==&hid=1&sn=83a9280676a4231bf747cc709a3f1714#wechat_redirect");

        ViewButton btn_appDownload = new ViewButton();
        btn_appDownload.setName("专车APP下载");
        btn_appDownload.setType("view");
        btn_appDownload.setUrl("http://www.yxzc01.com/api/base/appDownload");

        ClickButton btn_persionCarLine = new ClickButton();
        btn_persionCarLine.setName("专车专线");
        btn_persionCarLine.setType("click");
        btn_persionCarLine.setKey("persionCarLine");

        btn_persionCarLine.setSub_button(new Button[]{btn_search, btn_appDownload});

        /***********3**********/
        ClickButton btn_services = new ClickButton();
        btn_services.setName("服务资讯");
        btn_services.setType("click");
        btn_services.setKey("serviceNews");

        ClickButton btn_service_tel = new ClickButton();
        btn_service_tel.setName("服务热线");
        btn_service_tel.setType("click");
        btn_service_tel.setKey("serviceTel");

        ViewButton btn_bus = new ViewButton();
        btn_bus.setName("旅游包车");
        btn_bus.setType("view");
        btn_bus.setUrl("http://mp.weixin.qq.com/s?__biz=MzIyMTU3OTI2MA==&mid=100000131&idx=1&sn=752606b134eb3128396aa7c8408bc08e&chksm=683bd3105f4c5a06ed0edd018bf81c7375eb57a38853687940a36bebcaa397b34477f0a1678f&scene=18#wechat_redirect");

        ViewButton btn_recruit = new ViewButton();
        btn_recruit.setName("招聘");
        btn_recruit.setType("view");
        btn_recruit.setUrl("http://u1696283.jisuwebapp.com/s?id=2644180");

        btn_services.setSub_button(new Button[]{btn_service_tel, btn_bus, btn_recruit});


        menu.setButton(new Button[]{btn_activitys, btn_persionCarLine, btn_services});

        return menu;
    }
}
