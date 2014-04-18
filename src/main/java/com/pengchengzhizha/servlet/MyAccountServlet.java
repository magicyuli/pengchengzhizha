package com.pengchengzhizha.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.exception.IllegalInputException;
import com.pengchengzhizha.service.AccountService;
import com.pengchengzhizha.util.HttpServletHelper;

public class MyAccountServlet extends HttpServlet {
	private static final long serialVersionUID = -3103809158558221023L;
	
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
		if (httpServletHelper.requestSubpathMatches(req, "/modifyPassword")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modify_password.jsp").forward(req, resp);
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/my_account.jsp").forward(req, resp);
			return;
		} else {
			resp.sendError(404, "请求的资源不存在！");
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/modifyPassword")) {
			String oldPassword = req.getParameter("oldPassword");
			String newPassword = req.getParameter("newPassword");
			int userId = Integer.parseInt(req.getParameter("userId"));
			
			try {
				if (accountService.modifyPassword(userId, oldPassword, newPassword) == 1) {
					httpServletHelper.sendRedirectAfterAlerting(resp, "密码修改成功！", "/manage/myAccount/modifyPassword/");
				}
			} catch (IllegalInputException e) {
				httpServletHelper.sendRedirectAfterAlerting(resp, e.getMessage(), "/manage/myAccount/modifyPassword/");
				return;
			} catch (Exception e) {
				resp.sendError(500, "修改密码失败！");
				e.printStackTrace();
				return;
			}
		} else {
			resp.sendError(404, "请求的资源不存在！");
			return;
		}
	}
}
