package com.pengchengzhizha.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.util.HttpServletHelper;

public class AuthorizationFilter extends AbstractPathAwareFilter {
	private HttpServletHelper httpServletHelper;

	public void doFilter(ServletRequest arg0, ServletResponse arg1,	FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		User user = (User) request.getSession().getAttribute("user");
		if (httpServletHelper.requestSubpathMatches(request, this, "/news") && ! user.isNewsManager()) {
			httpServletHelper.sendRedirectAfterAlerting(response, "权限不足！", "/manage/");
			return;
		}
		if (httpServletHelper.requestSubpathMatches(request, this, "/message") && ! user.isMessageManager()) {
			httpServletHelper.sendRedirectAfterAlerting(response, "权限不足！", "/manage/");
			return;
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		httpServletHelper = new HttpServletHelper();
		super.init(arg0);
	}
	
	public void destroy() {
		
	}

}
