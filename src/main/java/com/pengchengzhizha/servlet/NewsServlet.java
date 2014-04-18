package com.pengchengzhizha.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.entity.News;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.service.NewsService;
import com.pengchengzhizha.util.HttpServletHelper;

public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 7221870732072747731L;
	private static final String NEWS_LIST_REDIRECT_URL = "/news/newsList/";
	
	private NewsService newsService;
	private HttpServletHelper httpServletHelper;
	private User user;
	
	@Override
	public void init() throws ServletException {
		super.init();
		newsService = new NewsService();
		httpServletHelper = new HttpServletHelper();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (httpServletHelper.requestSubpathMatches(req, "/newsList")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_list.jsp").forward(req, resp);
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/publish")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_publish.jsp").forward(req, resp);
			return;
		} else if (httpServletHelper.requestSubpathMatches(req, "/edit")) {
			try {
				News news = newsService.getNewsById(Integer.parseInt(req.getParameter("id")));
				req.setAttribute("news", news);
				req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_publish.jsp").forward(req, resp);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取新闻失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/display")) {
			try {
				News news = newsService.getNewsById(Integer.parseInt(req.getParameter("id")));
				req.setAttribute("news", news);
				req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_display.jsp").forward(req, resp);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取新闻失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/delete")) {
			try {
				if (newsService.deleteNewsById(Integer.parseInt(req.getParameter("id"))) == 1) {
					httpServletHelper.sendRedirectAfterAlerting(resp, "删除成功！", NEWS_LIST_REDIRECT_URL);
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "删除新闻失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news.jsp").forward(req, resp);
			return;
		} else {
			resp.sendError(404, "请求的资源不存在！");
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		user = (User) req.getSession().getAttribute("user");
		if (httpServletHelper.requestSubpathMatches(req, "/save")) {
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			boolean isEdit = req.getParameter("isEdit").equals("1") ? true : false;
			int id = 0;
			if (isEdit) {
				id = Integer.parseInt(req.getParameter("newsId"));
			}
			try {
				newsService.saveNews(title, content, user, isEdit, id);
				if (isEdit) {
					httpServletHelper.sendRedirectAfterAlerting(resp, "编辑成功！点击新闻列表标题查看。", NEWS_LIST_REDIRECT_URL);
					return;
				} else {
					httpServletHelper.sendRedirectAfterAlerting(resp, "发布成功！点击新闻列表标题查看。", NEWS_LIST_REDIRECT_URL);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "新闻存储失败！");
				return;
			}
		} else if (httpServletHelper.requestSubpathMatches(req, "/newsListJSON")) {
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			int pageSize = Integer.parseInt(req.getParameter("pageSize"));
			int page = Integer.parseInt(req.getParameter("page"));
			try {
				out.append(newsService.getNewsListJSON(page, pageSize));
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取新闻列表失败！");
				return;
			}
			resp.flushBuffer();
			return;
		} else {
			resp.sendError(404, "请求的资源不存在！");
			return;
		}
	}

}
