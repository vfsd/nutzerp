package com.vfsd.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 新闻公告
 * @author new
 *
 */
@Table("t_news")
public class News {
	@Id
	private int id;
	@Column("content")
	private String content;//新闻内容
	@Column("state")
	private int state;//新闻状态(0:正常显示  1:禁止显示)
	@Column("accountId")
	private int accountId;//发布人编号
	
	public News(){}
	
	public News(String content, int state, int accountId) {
		super();
		this.content = content;
		this.state = state;
		this.accountId = accountId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
}
