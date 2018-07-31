package com.plugs.module_wechat.entity.resp;

import com.plugs.module_wechat.entity.resp.type.Article;

import java.util.List;

/**
 * 音乐消息
 * 
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class NewsMessage extends BaseMessage {

	/**
	 * 图文消息个数，限制为10条以内
	 */
	private Integer ArticleCount;

	/**
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 */
	private List<Article> Articles;

	public Integer getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
