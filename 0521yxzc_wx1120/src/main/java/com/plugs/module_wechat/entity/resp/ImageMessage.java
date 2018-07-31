package com.plugs.module_wechat.entity.resp;

import com.plugs.module_wechat.entity.resp.type.Image;

/**
 * 图片消息
 * 
 * @author Administrator
 *
 */
public class ImageMessage extends BaseMessage {

	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
