package com.pengchengzhizha.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.entity.Message;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.exception.IllegalInputException;
import com.pengchengzhizha.service.MessageService;
import com.pengchengzhizha.util.HttpServletHelper;

public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = -8163842317409639088L;
	
	private MessageService messageService;
	private HttpServletHelper httpServletHelper;
	
	@Override
	public void init() throws ServletException {
		super.init();
		messageService = new MessageService();
		httpServletHelper = new HttpServletHelper();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/messageList")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/message_list.jsp").forward(req, resp);
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/detail")) {
			try {
				int id = Integer.parseInt(req.getParameter("id"));
				Message message = messageService.getMessageById(id);
				req.setAttribute("message", message);
				req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/message_detail.jsp").forward(req, resp);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取留言失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, resp);
			return;
		} else {
			resp.sendError(404, "请求资源不存在！");
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/save")) {
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String company = req.getParameter("company");
			String phone = req.getParameter("phone");
			String subject = req.getParameter("subject");
			String content = req.getParameter("content");
			try {
				if (messageService.saveMessage(subject, content, name, email, company, phone) == 1) {
					httpServletHelper.sendRedirectAfterAlerting(resp, "留言成功！", "/contactus.html");
					return;
				}
			} catch(IllegalInputException e) {
				httpServletHelper.sendRedirectAfterAlerting(resp, e.getMessage(), "/contactus.html");
				return;
			} catch(Exception e) {
				e.printStackTrace();
				resp.sendError(500, "留言失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/messageListJSON")) {
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			int pageSize = Integer.parseInt(req.getParameter("pageSize"));
			int page = Integer.parseInt(req.getParameter("page"));
			int range = Integer.parseInt(req.getParameter("range"));
			try {
				out.append(messageService.getMessageListJSON(page, pageSize, range));
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取留言列表失败！");
				return;
			}
			resp.flushBuffer();
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/reply")) {
			int id = Integer.parseInt(req.getParameter("id"));
			String content = req.getParameter("content");
			int method = Integer.parseInt(req.getParameter("method"));
			String email = req.getParameter("email");
			String subject = req.getParameter("subject");
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			try {
				if (messageService.replyMessage(id, method, subject, content, (User) req.getSession().getAttribute("user"), email) == 1) {
					out.append("{\"succeeded\":1}");
				} else {
					out.append("{\"succeeded\":0}");
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "回复失败！" + e.getMessage());
			}
		} else {
			resp.sendError(404, "请求资源不存在！");
			return;
		}
	}

}
