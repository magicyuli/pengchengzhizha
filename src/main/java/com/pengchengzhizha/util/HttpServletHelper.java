package com.pengchengzhizha.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.filter.PathAwareFilter;

public class HttpServletHelper {
	
	public HttpServletHelper() {
	}
	
	public boolean requestSubpathMatches(HttpServletRequest req, String pattern) {
		return getRequestSubPath(req).equals(pattern) || getRequestSubPath(req).startsWith(pattern + "/");
	}
	
	private String getRequestSubPath(HttpServletRequest req) {
		return req.getRequestURI().substring(req.getServletPath().length());
	}
	
	public void sendRedirectAfterAlerting(HttpServletResponse resp, String msg, String url) throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write("<head>");
		out.write("<script>");
		out.write("alert('" + msg + "');");
		out.write("</script>");
		out.write("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf8\" />");
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=" + url + "\" />");
		out.write("</head>");
		resp.flushBuffer();
	}

	public boolean requestSubpathMatches(HttpServletRequest req, PathAwareFilter filter, String pattern) {
		return getRequestSubPath(req, filter).equals(pattern) || getRequestSubPath(req, filter).startsWith(pattern + "/");
	}

	private String getRequestSubPath(HttpServletRequest req, PathAwareFilter filter) {
		return req.getRequestURI().substring(filter.getFilterPath().length());
	}

}
