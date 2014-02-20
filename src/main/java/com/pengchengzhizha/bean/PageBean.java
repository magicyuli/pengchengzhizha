package com.pengchengzhizha.bean;

import java.util.Collection;

public class PageBean<T> {
	private int total;
	private Collection<T> pageContent;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Collection<T> getPageContent() {
		return pageContent;
	}
	public void setPageContent(Collection<T> pageContent) {
		this.pageContent = pageContent;
	}

}
