package com.plugs.module_wechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.plugs.module_wechat.entity.resp.ImageMessage;
import com.plugs.module_wechat.entity.resp.MusicMessage;
import com.plugs.module_wechat.entity.resp.NewsMessage;
import com.plugs.module_wechat.entity.resp.TextMessage;
import com.plugs.module_wechat.entity.resp.type.Article;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 消息工具类
 * 
 * @author Administrator
 *
 */
public class MessageUtil {

	/***************************************/
	/****************** 响应类型 *********************/
	/***************************************/
	/**
	 * 回复文本消息
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	/**
	 * 回复图片消息
	 */
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	/**
	 * 回复声音消息
	 */
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 回复视频消息
	 */
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	/**
	 * 回复音乐消息
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	/**
	 * 回复图文消息
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/***************************************/
	/****************** 请求类型 *********************/
	/***************************************/
	/**
	 * 请求文本消息
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	/**
	 * 请求图片消息
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	/**
	 * 请求声音消息
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 请求小视频消息
	 */
	public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";
	/**
	 * 请求地理位置消息
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	/**
	 * 请求链接消息
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求事件类型
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/***************************************/
	/***************** 消息推送事件类型 **********************/
	/***************************************/

	/**
	 * 订阅
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	/**
	 * 取消订阅
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 点击事件
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 扫码事件
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	/**
	 * 上报地理位置事件
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();

		// 从request取得数据流
		InputStream input = request.getInputStream();
		// 读取数据流
		SAXReader reader = new SAXReader();
		Document document = reader.read(input);
		// 获取根节点
		Element root = document.getRootElement();
		// 得到所有根节点下的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList) {

			map.put(e.getName(), e.getText());
		}
		// 释放资源
		input.close();
		input = null;

		return map;
	}

	/**
	 * 文本消息对象转换成 xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {

		xstream.alias("xml", textMessage.getClass());

		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成 xml
	 *
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {

		xstream.alias("xml", musicMessage.getClass());

		return xstream.toXML(musicMessage);
	}

	/**
	 * 图片消息对象转换成 xml
	 *
	 * @param imageMessage
	 *            图片消息对象
	 * @return xml
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {

		xstream.alias("xml", imageMessage.getClass());

		return xstream.toXML(imageMessage);
	}

	/**
	 * 图文消息对象转换成 xml
	 *
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {

		xstream.alias("xml", newsMessage.getClass());

		xstream.alias("item", new Article().getClass());

		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展 xstream，使其支持 CDATA 块
	 */
	private static XStream xstream = new XStream(new XppDriver() {

		public HierarchicalStreamWriter createWriter(Writer out) {

			return new PrettyPrintWriter(out) {
				// 对所有 xml 节点的转换都增加 CDATA 标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
