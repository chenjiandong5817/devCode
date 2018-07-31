package com.plugs.utils.templatemsg;

import com.plugs.base.AjaxList;
import com.plugs.module_wechat.constants.Constants;
import com.plugs.module_wechat.util.AccessToken;
import com.plugs.module_wechat.util.CommonUtil;
import com.plugs.module_wechat.util.WeChatUtil;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/6/8.
 */
public class TemplateMsgHelper {

    private static String sendApiUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";


    /**
     * 发送模板消息
     * @param jsonStr json字符串
     * @return {ApiResult}
     */
    public static AjaxList send(String jsonStr, String token) {

        String sendUrl = sendApiUrl + token;
        JSONObject jSONObject = CommonUtil.httpsRequest(sendUrl, "POST", jsonStr);
        return AjaxList.createSuccess("发送模板消息",jSONObject);
    }

    public static void main(String[] args) {

        String jsonDate = TemplateData.New()
                .setTouser("od_i0wDNv9xoxbq65xj5INZjARiw")
                .setTemplate_id("GmW5YsUrED7zW6H466vLSW3z5_VU5jedNdAXitNDzeA")
                .setUrl("wxtest.yxzc01.com/wechat/view/index")
                .add("first","司机已到达","#ddd")
                .add("keyword1","厦门站","#000")
                .add("keyword2","小贱人","#000")
                .add("keyword3","1775950001","#000")
                .add("keyword4","闽D001","#000")
                .add("keyword5","保时捷","#000")
                .add("remark","测试","#000")
                .build();
        AccessToken accessToken = WeChatUtil.getAccessToken(Constants.APPID, Constants.APPSECRET);
        System.out.println(send(jsonDate, accessToken.getToken()));
    }



}
