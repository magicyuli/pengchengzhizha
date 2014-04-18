package com.pengchengzhizha.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.service.AccountService;
import com.pengchengzhizha.util.HttpServletHelper;
import com.pengchengzhizha.util.IO;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = -9083061972972850597L;
	
	private HttpServletHelper httpServletHelper;
	private AccountService accountService;
	
	@Override
	public void init() throws ServletException {
		httpServletHelper = new HttpServletHelper();
		accountService = new AccountService();
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(req, resp);
			return;
		} else if(httpServletHelper.requestSubpathMatches(req, "/adminList")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin_list.jsp").forward(req, resp);
			return;
		} else {
			resp.sendError(404, "请求资源不存在！");
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/adminListJSON")) {
			resp.setContentType("application/json");
			int page = Integer.parseInt(req.getParameter("page"));
			int pageSize = Integer.parseInt(req.getParameter("pageSize"));
			PrintWriter out = resp.getWriter();
			try {
				out.append(accountService.getUserListJSON(page, pageSize));
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取用户列表失败！");
				return;
			}
			resp.flushBuffer();
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/updateAuth")) {
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			String body = IO.readInputStream(req.getInputStream());
			try {
				accountService.updateAuthFromJSON(body);
				out.append("{\"success\":1}");
			} catch (Exception e) {
				e.printStackTrace();
				out.append("{\"success\":0}");
			}
		} else {
			resp.sendError(404, "请求资源不存在！");
			return;
		}
	}

}
