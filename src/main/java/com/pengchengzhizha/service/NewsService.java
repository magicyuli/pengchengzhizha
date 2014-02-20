package com.pengchengzhizha.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.dao.NewsDAO;
import com.pengchengzhizha.entity.News;
import com.pengchengzhizha.entity.User;

public class NewsService {
	private NewsDAO newsDAO;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public int saveNews(String title, String content, User user, boolean isEdit, int id) throws IOException, SQLException {
		newsDAO = new NewsDAO();
		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		if (! isEdit) {
			news.setPublishTime(sdf.format(new Date()));
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
		newsDAO = new NewsDAO();
		return newsDAO.getById(id);
	}

	public CharSequence getNewsListJSON(int page, int pageSize) throws SQLException, IOException {
		newsDAO = new NewsDAO();
		PageBean<News> paginatedNewsList = newsDAO.getPaginatedNewsList(page, pageSize);
		return new JSONObject(paginatedNewsList).toString();
	}

	public int deleteNewsById(int id) throws SQLException, IOException {
		newsDAO = new NewsDAO();
		return newsDAO.deleteById(id);
	}

}
