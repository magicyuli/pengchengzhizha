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

public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 7221870732072747731L;
	private String subpath;
	private NewsService newsService;
	private User user;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		subpath = getRequestSubPath(req);
		if (subpathMatchs("/newsList")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_list.jsp").forward(req, resp);
		} else if (subpathMatchs("/publish")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_publish.jsp").forward(req, resp);
		} else if (subpathMatchs("/edit")) {
			newsService = new NewsService();
			try {
				News news = newsService.getNewsById(Integer.parseInt(req.getParameter("id")));
				req.setAttribute("news", news);
				req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_publish.jsp").forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取新闻失败！");
			}
		} else if (subpathMatchs("/display")) {
			newsService = new NewsService();
			try {
				News news = newsService.getNewsById(Integer.parseInt(req.getParameter("id")));
				req.setAttribute("news", news);
				req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news_display.jsp").forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取新闻失败！");
			}
		} else if (subpathMatchs("/delete")) {
			newsService = new NewsService();
			try {
				if (newsService.deleteNewsById(Integer.parseInt(req.getParameter("id"))) == 1) {
					redirectToNewsListAfterSuccess(resp, "删除成功！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "删除新闻失败！");
			}
		} else if (subpathMatchs("/")) {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/news.jsp").forward(req, resp);
		} else {
			resp.sendError(404, "资源不存在！");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		subpath = getRequestSubPath(req);
		user = (User) req.getSession().getAttribute("user");
		if (subpathMatchs("/save")) {
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			boolean isEdit = req.getParameter("isEdit").equals("1") ? true : false;
			int id = 0;
			if (isEdit) {
				id = Integer.parseInt(req.getParameter("newsId"));
			}
			try {
				newsService = new NewsService();
				newsService.saveNews(title, content, user, isEdit, id);
				if (isEdit) {
					redirectToNewsListAfterSuccess(resp, "编辑成功！点击新闻列表标题查看。");
				} else {
					redirectToNewsListAfterSuccess(resp, "发布成功！点击新闻列表标题查看。");
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "新闻存储失败！");
			}
		} else if (subpathMatchs("/newsListJSON")) {
			newsService = new NewsService();
			PrintWriter out = resp.getWriter();
			int pageSize = Integer.parseInt(req.getParameter("pageSize"));
			int page = Integer.parseInt(req.getParameter("page"));
			try {
				out.append(newsService.getNewsListJSON(page, pageSize));
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(500, "获取新闻列表失败！");
			}
			resp.flushBuffer();
		}
	}

	private void redirectToNewsListAfterSuccess(HttpServletResponse resp, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.write("<head>");
		out.write("<script>");
		out.write("alert('" + msg + "');");
		out.write("</script>");
		out.write("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf8\" />");
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=/news/newsList/\" />");
		out.write("</head>");
		resp.flushBuffer();
	}

	private String getRequestSubPath(HttpServletRequest req) {
		return req.getRequestURI().substring(req.getServletPath().length());
	}
	
	
	private boolean subpathMatchs(String string) {
		return subpath.equals(string) || subpath.equals(string + "/");
	}
}
