package com.pengchengzhizha.service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONObject;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.dao.NewsDAO;
import com.pengchengzhizha.entity.News;
import com.pengchengzhizha.entity.User;

public class NewsService {
	private NewsDAO newsDAO;
	
	public NewsService() {
		newsDAO = new NewsDAO();
	}
	
	public int saveNews(String title, String content, User user, boolean isEdit, int id) throws IOException, SQLException {
		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		if (! isEdit) {
			news.setPublishTime(new Timestamp(System.currentTimeMillis()));
			news.setPublishUser(user);
			news.setLastUpdateUser(user);
			return newsDAO.save(news);
		} else {
			news.setId(id);
			news.setLastUpdateUser(user);
			return newsDAO.update(news);
		}
		
	}

	public News getNewsById(int id) throws SQLException, IOException {
		return newsDAO.getById(id);
	}

	public String getNewsListJSON(int page, int pageSize) throws SQLException, IOException {
		PageBean<News> paginatedNewsList = newsDAO.getPaginatedNewsList(page, pageSize);
		return new JSONObject(paginatedNewsList).toString();
	}

	public int deleteNewsById(int id) throws SQLException, IOException {
		return newsDAO.deleteById(id);
	}

}
