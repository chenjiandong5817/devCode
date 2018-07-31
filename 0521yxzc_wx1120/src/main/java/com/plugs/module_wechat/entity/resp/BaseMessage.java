package com.plugs.module_wechat.entity.resp;

/**
 * @author Administrator 消息基类
 */
public class BaseMessage {

	// 开发者微信号
	private String ToUserName;

	// 发送方账号
	private String FromUserName;

	// 小心创建时间
	private long CreateTime;

	// 消息类型
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
