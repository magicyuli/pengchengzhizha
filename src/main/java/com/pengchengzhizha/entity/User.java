package com.pengchengzhizha.entity;

public class User {
	private Integer id;
	private String name;
	private String password;
	private String email;
	private Integer isActivated;
	private Integer isAdmin;
	private Integer isNewsManager;
	private Integer isMessageManager;
	private String hash;
	
	
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIsActivated() {
		return isActivated;
	}
	public void setIsActivated(int isActivated) {
		this.isActivated = isActivated;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getIsNewsManager() {
		return isNewsManager;
	}
	public void setIsNewsManager(int isNewsManager) {
		this.isNewsManager = isNewsManager;
	}
	public int getIsMessageManager() {
		return isMessageManager;
	}
	public void setIsMessageManager(int isMessageManager) {
		this.isMessageManager = isMessageManager;
	}
	
	
	public boolean isAdmin() {
		return isAdmin == 1;
	}
	
	public boolean isActivated() {
		return isActivated == 1;
	}
	
	public boolean isNewsManager() {
		return isNewsManager == 1;
	}
	
	public boolean isMessageManager() {
		return isMessageManager == 1;
	}
}
