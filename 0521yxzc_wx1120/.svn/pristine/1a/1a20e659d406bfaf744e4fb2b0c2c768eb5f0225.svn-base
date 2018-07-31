package com.plugs.module_member.dtos;

import com.util.PropertiesUtil;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/24.
 */
public class RequestDataBaseInfo implements Serializable {
    private String dataCenterID;
    private String userName;
    private String passWord;
    private String userToken;
    private String isEncrypt;

    public RequestDataBaseInfo() {
        //配置一些默认信息
        Properties propt = PropertiesUtil.getConfigProperties();
        dataCenterID= propt.getProperty("dataCenterID");
        userName= propt.getProperty("userName");
        passWord= propt.getProperty("passWord");
        userToken= propt.getProperty("userToken");
        isEncrypt= "false";
    }

    public String getDataCenterID() {
        return dataCenterID;
    }

    public void setDataCenterID(String dataCenterID) {
        this.dataCenterID = dataCenterID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }
}
