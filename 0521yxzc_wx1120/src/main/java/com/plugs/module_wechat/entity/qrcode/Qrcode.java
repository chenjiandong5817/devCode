package com.plugs.module_wechat.entity.qrcode;

/**
 * 带参二维码实体类
 * 
 * @author Administrator
 *
 */
public class Qrcode {
	/**
	 * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 */
	private String expire_seconds;
	/**
	 * 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
	 */
	private String action_name;
	/**
	 * 二维码详细信息
	 */
	private Scene action_info;

	public String getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(String expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public Scene getAction_info() {
		return action_info;
	}

	public void setAction_info(Scene action_info) {
		this.action_info = action_info;
	}

	/*
	 * 现在只做永久带参二维码， 所以只封装以下符串json对象格式 (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"action_name\":\"" + this.getAction_name() + "\",\"action_info\":{\"scene\":{\"scene_str\":\""
				+ this.getAction_info().getScene_str() + "\"}}}";
	}

}
