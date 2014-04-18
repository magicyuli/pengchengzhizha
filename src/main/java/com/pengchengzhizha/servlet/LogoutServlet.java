package com.pengchengzhizha.servlet;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.conf.Conf;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = -9177563928254289571L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals(Conf.REMEBER_ME_COOKIE_KEY)) {
				c.setMaxAge(0);
				c.setPath("/");
				response.addCookie(c);
				break;
			}
		}
		response.sendRedirect(response.encodeRedirectURL("/login/"));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}

}
