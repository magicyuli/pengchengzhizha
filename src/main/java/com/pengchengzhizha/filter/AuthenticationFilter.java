package com.pengchengzhizha.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

	public void doFilter(ServletRequest arg0, ServletResponse arg1,	FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String url = request.getRequestURL().toString();
		String q = request.getQueryString();
		url += q != null ? "?" + q : "";
		HttpSession session = request.getSession();
		
		if (session.getAttribute("user") == null) {
			response.sendRedirect(response.encodeRedirectURL("/login/?s=" + URLEncoder.encode(url, "UTF-8")));
			return;
		}
		filterChain.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	public void destroy() {
		
	}

}
