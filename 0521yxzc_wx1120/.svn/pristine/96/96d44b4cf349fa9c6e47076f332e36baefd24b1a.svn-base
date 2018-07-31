package com.plugs.module_member.services;

import com.plugs.module_member.dtos.RequestDataBaseInfo;
import com.plugs.module_member.utils.HttpClientUtils;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.utils.StringUtils;
import com.util.MapUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 会员信息管理
 * Created by songchen on 2017/7/25.
 */
@Service
public class VIPMemberService {
    private static final Logger logger = Logger.getLogger(VIPMemberService.class);
    @Autowired
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;
    /**
     * 单独注册会员信息,注册之前需要先验证手机号是否存在
     * @param userPassengerDto
     * @return 返回翔会会员编号
     */
    private String registerVIPMember(UserPassengerDto userPassengerDto){
        //直接注册翔会会员
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("ServiceCode","MBMT000010"); //接口编码为查询
        jsonObject.put("ExternalReference","register"+System.currentTimeMillis()); //请求流水号
        RequestDataBaseInfo requestDataBaseInfo=new RequestDataBaseInfo();
        JSONObject dataJson = JSONObject.fromObject(requestDataBaseInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String TimeStamp = sdf.format(new Date());
        dataJson.put("TimeStamp",TimeStamp);
        JSONObject vipParamsJson = new JSONObject();
        vipParamsJson.put("BranchNumber","100.10000");
        vipParamsJson.put("VipName",userPassengerDto.getRealName());
        vipParamsJson.put("MobileNo",userPassengerDto.getMobile());
        vipParamsJson.put("Email",userPassengerDto.getEmail());
        vipParamsJson.put("Weixin",userPassengerDto.getWechat());
        vipParamsJson.put("QQ",userPassengerDto.getQq());
        dataJson.put("vipParams",vipParamsJson);
        dataJson.put("cardParams",JSONObject.fromObject("{'CardNo': '','CardType': '02'}"));
        jsonObject.put("Data",dataJson); //设置请求中的Data
        String vipMember=HttpClientUtils.httpURLConnectionPOST(jsonObject);
        System.out.println(vipMember);
        JSONObject registerVIPResponse=JSONObject.fromObject(vipMember);
        JSONObject reponseData=registerVIPResponse.getJSONObject("Data");
        if(!reponseData.getBoolean("Result")){ //注册失败 则记录日志
            logger.info("翔会会员注册失败："+reponseData.getString("Message"));
            return null;
        }
        Map params=MapUtil.buildMap();
        params.put("mobile",userPassengerDto.getMobile());
        params.put("fNumber",reponseData.getJSONArray("Data").getJSONObject(0).getString("FNumber"));
        userPassengerMapper.update(params);//修改会员账号
        return reponseData.getJSONArray("Data").getJSONObject(0).getString("FNumber");//返回会员编号
    }
    /**
     * 单独注册会员信息,注册之前需要先验证手机号是否存在
     * 注意：在本地保存完之后调用
     * @param mobile 手机号
     * @return 返回翔会会员编号
     */
    public String registerVIPMember(String mobile){
        try {
            //根据手机号查询出对应的本地乘客信息
            Map params = MapUtil.buildMap();
            params.put("mobile",mobile);
            UserPassengerDto userPassengerDto=userPassengerMapper.findUser(params);
            //判断是否有翔会会员id，如果没有则需要注册
            if(!StringUtils.isEmpty(userPassengerDto.getFNumber())){
                return userPassengerDto.getFNumber();
            }
            //先根据手机号查询出会员信息
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("ServiceCode","MBMT000020"); //接口编码为查询
            jsonObject.put("ExternalReference","Query"+System.currentTimeMillis()); //请求流水号
            RequestDataBaseInfo requestDataBaseInfo=new RequestDataBaseInfo();
            JSONObject dataJson = JSONObject.fromObject(requestDataBaseInfo);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String TimeStamp = sdf.format(new Date());
            dataJson.put("TimeStamp",TimeStamp);
            dataJson.put("MobileNo",mobile);
            jsonObject.put("Data",dataJson); //设置请求中的Data
            String vipMember=HttpClientUtils.httpURLConnectionPOST(jsonObject);
            System.out.println(vipMember);
            JSONObject queryVIPResponse=JSONObject.fromObject(vipMember);
            String data=queryVIPResponse.getJSONObject("Data").getString("Data");
            //如果查询到 则直接返回会员编号
            if(!StringUtils.isEmpty(data)){
                Map updateParams=MapUtil.buildMap();
                updateParams.put("mobile",mobile);
                updateParams.put("fNumber",JSONObject.fromObject(data).getString("FNumber"));
                userPassengerMapper.update(updateParams);//修改会员账号
                return JSONObject.fromObject(data).getString("FNumber");//返回会员编号
            }
            //没有查询到 则需要开始注册
            return registerVIPMember(userPassengerDto);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("翔会会员注册失败", e);
            return null;
        }
    }
    /**
     * 会员信息修改
     * 注意：需要在执行修改之后再调用此方法，因为此方法会获取数据库中的最新信息
     * @param userPassengerDto 修改后的用户数据
     * @return 返回修改成功与否
     */
    public boolean modifyVIPMember(UserPassengerDto userPassengerDto){
        try {
            //先根据手机号查询出会员信息
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("ServiceCode","MBMT000030"); //接口编码为修改
            jsonObject.put("ExternalReference","modify"+System.currentTimeMillis()); //请求流水号
            RequestDataBaseInfo requestDataBaseInfo=new RequestDataBaseInfo();
            JSONObject dataJson = JSONObject.fromObject(requestDataBaseInfo);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String TimeStamp = sdf.format(new Date());
            dataJson.put("TimeStamp",TimeStamp);
            dataJson.put("bUpdateAll",false);
            JSONObject vipParamsJson = new JSONObject();
            //查询出数据库中最新的会员信息
            Map qParams=MapUtil.buildMap();
            qParams.put("uuid",userPassengerDto.getUuid());
            userPassengerDto=this.userPassengerMapper.findUser(qParams);
            vipParamsJson.put("BranchNumber","100.10000");
            vipParamsJson.put("VipNumber",userPassengerDto.getFNumber());
            vipParamsJson.put("VipName",StringUtils.isNotEmpty(userPassengerDto.getRealName())?URLEncoder.encode(userPassengerDto.getRealName(),"utf-8"):"");
            vipParamsJson.put("Sex", URLEncoder.encode(userPassengerDto.getSex()==1?"男":"女","utf-8"));
            vipParamsJson.put("MobileNo",userPassengerDto.getMobile());
            vipParamsJson.put("Email",userPassengerDto.getEmail());
            vipParamsJson.put("Weixin",userPassengerDto.getWechat());
            vipParamsJson.put("QQ",userPassengerDto.getQq());
            dataJson.put("vipParams",vipParamsJson);
            jsonObject.put("Data",dataJson); //设置请求中的Data
            String vipMember=HttpClientUtils.httpURLConnectionPOST(jsonObject);
            System.out.println(vipMember);
            JSONObject modifyVIPResponse=JSONObject.fromObject(vipMember);
            JSONObject reponseData=modifyVIPResponse.getJSONObject("Data");
            //如果修改失败则记录日志
            if(!reponseData.getBoolean("Result")){
                logger.info("翔会会员修改失败："+reponseData.getString("Message"));
                return false;
            }
            return reponseData.getBoolean("Result");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("翔会会员修改失败", e);
            return false;
        }
    }
}
