package com.pengchengzhizha.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class News {
	private int id;
	private String title;
	private String content;
	private Timestamp publishTime;
	private User publishUser;
	private User lastUpdateUser;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Timestamp publishTimeAsDate() {
		return publishTime;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishTime() {
		return sdf.format(publishTime);
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public User getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(User publishUser) {
		this.publishUser = publishUser;
	}
	public User getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(User lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
