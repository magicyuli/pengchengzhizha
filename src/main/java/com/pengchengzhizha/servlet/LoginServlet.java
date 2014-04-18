package com.pengchengzhizha.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.conf.Conf;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.service.LoginService;
import com.pengchengzhizha.util.HttpServletHelper;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 4340158105976915683L;

	private static final String DEFAULT_LOGIN_REDIRECT_URL = "/manage/";
	
	private LoginService loginService;
	private HttpServletHelper httpServletHelper;
	
	@Override
	public void init() throws ServletException {
		super.init();
		loginService = new LoginService();
		httpServletHelper = new HttpServletHelper();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User rememberedUser = null;
		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals(Conf.REMEBER_ME_COOKIE_KEY)) {
					try {
						rememberedUser = loginService.getRemeberedUser(c.getValue());
						if (rememberedUser != null) {
							request.getSession().setAttribute("user", rememberedUser);
							String url = request.getParameter("s");
							if (url == null || url.equals("")) {
								url = DEFAULT_LOGIN_REDIRECT_URL;
							}
							c.setMaxAge(Conf.REMEBER_ME_COOKIE_EXPIRY);
							c.setPath("/");
							response.addCookie(c);
							response.sendRedirect(response.encodeRedirectURL(URLDecoder.decode(url, "UTF-8")));
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String rememberMe = request.getParameter("remember_me");
		String url = request.getParameter("url");
		if (url == null || url.equals("")) {
			url = DEFAULT_LOGIN_REDIRECT_URL;
		}
		
		try {
			User authenticatedUser = loginService.authenticate(name, password);
			if (authenticatedUser != null) {
				if (! authenticatedUser.isActivated()) {
					httpServletHelper.sendRedirectAfterAlerting(response, "用户还未激活，请前往邮箱激活，或重新请求激活邮件！", "/register/resendActivationEmail/");
					return;
				}
				request.getSession().setAttribute("user", authenticatedUser);
				if (rememberMe != null && rememberMe.equals("yes")) {
					String remeberMeCookieValue = loginService.getRemeberMeCookieValue(name, password);
					Cookie c = new Cookie(Conf.REMEBER_ME_COOKIE_KEY, remeberMeCookieValue);
					c.setMaxAge(Conf.REMEBER_ME_COOKIE_EXPIRY);
					c.setPath("/");
					response.addCookie(c);
				}
				response.sendRedirect(response.encodeRedirectURL(url));
				return;
			} else {
				request.setAttribute("msg", "用户名或密码错误！");
				request.setAttribute("url", url);
				request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
