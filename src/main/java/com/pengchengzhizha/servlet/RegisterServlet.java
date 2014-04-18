package com.pengchengzhizha.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.exception.IllegalInputException;
import com.pengchengzhizha.service.AccountService;
import com.pengchengzhizha.util.HttpServletHelper;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 7372908535574844982L;
	
	private AccountService accountService;
	private HttpServletHelper httpServletHelper;
	
	@Override
	public void init() throws ServletException {
		super.init();
		accountService = new AccountService();
		httpServletHelper = new HttpServletHelper();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/resendActivationEmail")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/resend_activation.jsp").forward(req, resp);
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/activate")) {
			int id = Integer.parseInt(req.getParameter("id"));
			String hash = req.getParameter("hash");
			try {
				if (!(accountService.activate(id, hash) == 1)) {
					httpServletHelper.sendRedirectAfterAlerting(resp, "用户不存在！", "/register/");
					return;
				} else {
					httpServletHelper.sendRedirectAfterAlerting(resp, "激活成功！", "/login/");
				}
			} catch (Exception e) {
				e.printStackTrace();
				httpServletHelper.sendRedirectAfterAlerting(resp, "激活失败！", "/register/");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
			return;
		} else {
			resp.sendError(404, "请求的资源不存在！");
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/save/")) {
			String name = req.getParameter("name");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			try {
				accountService.register(name, password, email);
				httpServletHelper.sendRedirectAfterAlerting(resp, "注册成功，请前往邮箱激活！", "/login/");
				return;
			} catch (IllegalInputException e) {
				httpServletHelper.sendRedirectAfterAlerting(resp, e.getMessage(), "/register/");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "注册失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/checkName")) {
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			String name = req.getParameter("name");
			try {
				if (accountService.isNameOccupied(name)) {
					out.append("{\"passed\":0}");
				} else {
					out.append("{\"passed\":1}");
				}
				resp.flushBuffer();
			} catch (SQLException e) {
				e.printStackTrace();
				resp.sendError(500, "服务器故障！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/checkEmail")) {
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			String email = req.getParameter("email");
			try {
				if (accountService.isEmailOccupied(email)) {
					out.append("{\"passed\":0}");
				} else {
					out.append("{\"passed\":1}");
				}
				resp.flushBuffer();
			} catch (SQLException e) {
				e.printStackTrace();
				resp.sendError(500, "服务器故障！");
				return;
			}
		} else {
			resp.sendError(404, "请求的资源不存在！");
			return;
		}
	}
}
