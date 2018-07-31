package com.plugs.module_wechat.constants;

public class Constants {

	// 与接口配置信息中的 Token 要一致
	public static final String TOKEN = "yxzcToken";

	public static final String APPID = "wx04c8887748cfd860";//测试公众号 wxaf9e466c7fb853b6
	public static final String APPSECRET = "affc58c5966e0f45b7a01fe4b1a2c8cd";//测试公众号？ 23484ef21bf6cf8af20885021a3328f7

	/**
	 * 微信用户openID
	 */
	public static final String WECHAT_USE_OPENID = "WECHAT_USE_OPENID";

	public static final String REDIRECT_URL = "http://ticket.panport-fjexpress.com/FZKX/jump-xm.jsp";


	/**
	 * 微信用户授权后的基本信息
	 */
	public static final String JSON_USERINFO_OBJECT = "JSON_USERINFO_OBJECT";

	/**
	 * 网页授权中的access_token
	 */
	public static final String ACCESS_TOKEN_FOR_OAUTH = "ACCESS_TOKEN_FOR_OAUTH";
	/**
	 * 网页授权refresh_token,有效期为30天
	 */
	public static final String REFRESH_TOKEN_FOR_OAUTH = "REFRESH_TOKEN_FOR_OAUTH";

	/**
	 * 基础支持的access_token
	 */
	public static final String ACCESS_TOKEN_FOR_MORMAL = "ACCESS_TOKEN_FOR_MORMAL";

	/**
	 * 微信code
	 */
	public static final String WECHAT_CODE = "WECHAT_CODE";


	/**
	 * 获取access_token地址
	 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 文件上传地址
	 */
	public static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 创建菜单地址
	 */
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 查询菜单地址
	 */
	public static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	/**
	 * 删除菜单地址
	 */
	public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 微信JSSDK的AccessToken请求URL地址
	 */
	public static final String WEIXIN_JSSDK_ACCETOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx4e85aece0c568f3c&secret=4220ebe32b2640d68f54235d008b2b24";

	/**
	 * 微信JSSDK的ticket请求URL地址 卡券
	 */
	public static final String WEIXIN_JSSDK_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 网页授权 获取code
	 */
	public static final String REQUEST_URL_GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	/**
	 * 网页授权获取access_token
	 */
	public static final String OAUTH_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	
	/**
	 * 使用refresh_token刷新access_token
	 */
	public static final String OAUTH_GET_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	
	
	/**
	 * 网页授权获取用户信息数据
	 */
	public static final String OAUTH_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取临时素材地址
	 */
	public static final String GET_MEDIA_TEMPORARY = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 获取永久素材地址
	 */
	public static final String GET_MEDIA_PERMANENT = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";

	/**
	 * 获取永久带参二维码ticket地址
	 */
	public static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

	/**
	 * 通过ticket换取二维码
	 */
	public static final String GET_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	/**
	 * 二维码类型
	 * 
	 * @author Administrator
	 */
	public enum QRCODE_ACTION_NAME {
		/**
		 * 为永久的字符串
		 */
		QR_LIMIT_STR_SCENE, /**
							 * 为临时
							 */
		QR_SCENE, /**
					 * 为永久
					 */
		QR_LIMIT_SCENE
	}
}
