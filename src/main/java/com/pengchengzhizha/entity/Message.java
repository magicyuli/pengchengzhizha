package com.pengchengzhizha.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
	private int id;
	private String subject;
	private String content;
	private String senderName;
	private String senderEmail;
	private String senderCompany;
	private String senderPhone;
	private Timestamp sendingTime;
	private int isReplied;
	private User replyingUser;
	private Timestamp replyingTime;
	private String replyContent;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Timestamp sendingTimeAsDate() {
		return sendingTime;
	}
	public Timestamp replyingTimeAsDate() {
		return replyingTime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getSendingTime() {
		return sdf.format(sendingTime);
	}
	public void setSendingTime(Timestamp sendingTime) {
		this.sendingTime = sendingTime;
	}
	public int getIsReplied() {
		return isReplied;
	}
	public void setIsReplied(int isReplied) {
		this.isReplied = isReplied;
	}

	public String getSenderCompany() {
		return senderCompany;
	}

	public void setSenderCompany(String senderCompany) {
		this.senderCompany = senderCompany;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public boolean isReplied() {
		return isReplied == 1;
	}

	public User getReplyingUser() {
		return replyingUser;
	}

	public void setReplyingUser(User replyingUser) {
		this.replyingUser = replyingUser;
	}

	public String getReplyingTime() {
		return sdf.format(replyingTime);
	}

	public void setReplyingTime(Timestamp replyingTime) {
		this.replyingTime = replyingTime;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	

}
