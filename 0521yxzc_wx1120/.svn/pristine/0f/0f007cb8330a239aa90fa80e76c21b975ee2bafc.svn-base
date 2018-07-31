package com.plugs.module_system.dtos;

import java.util.Date;

/**
 * 系统操作日志表
 *
 * @author Zoro
 * @since 2016/12/9
 */
public class SysOpLogDto {

    private String uuid;        //主键
    private String module;        //操作模块
    private String operateObject;        //操作对象
    private String operateContent;        //操作内容
    private String operatorUuid;        //操作者uuid
    private Date operateTime;        //操作时间

    private String operatorName;    //  操作者
    private Integer operatorType;
    //非数据库字段


    public SysOpLogDto(){

    }
    public  SysOpLogDto(String uuid,String module,String operateObject,String operateContent,String operatorUuid,
                        Date operateTime){
        this.uuid = uuid;
        this.module = module;
        this.operateObject = operateObject;
        this.operateContent = operateContent;
        this.operatorUuid = operatorUuid;
        this.operateTime = operateTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setOperateObject(String operateObject) {
        this.operateObject = operateObject;
    }

    public String getOperateObject() {
        return operateObject;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperatorUuid(String operatorUuid) {
        this.operatorUuid = operatorUuid;
    }

    public String getOperatorUuid() {
        return operatorUuid;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }
}
