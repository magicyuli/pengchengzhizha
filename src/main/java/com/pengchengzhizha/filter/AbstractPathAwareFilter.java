package com.pengchengzhizha.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public abstract class AbstractPathAwareFilter implements PathAwareFilter {
	private String filterPath;

	public void init(FilterConfig arg0) throws ServletException {
		filterPath = arg0.getInitParameter("filterPath");
		if (filterPath == null) {
			throw new ServletException(this.getClass().getName() + "'s init param was not set.");
		}
	}

	public String getFilterPath() {
		return filterPath;
	}

}
