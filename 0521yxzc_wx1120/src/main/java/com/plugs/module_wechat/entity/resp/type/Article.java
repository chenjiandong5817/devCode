package com.plugs.module_wechat.entity.resp.type;

/**
 * 图文消息
 * 
 * @author bryanlin
 */
public class Article {
	// 图文消息名称
	private String Title;
	// 图文消息描述
	private String Description;
	// 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	// 第一条图文的图片大小建议为640*320，其他图文的图片大小建议为80*80。如果使用的图片太大，加载慢，而且耗流量；如果使用的图片太小，显示后会被拉伸，失真了很难看。
	private String PicUrl;
	// 点击图文消息跳转链接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return null == Description ? "" : Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return null == PicUrl ? "" : PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return null == Url ? "" : Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
