package com.pengchengzhizha.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.entity.News;
import com.pengchengzhizha.entity.User;

public class NewsDAO extends BaseDAO {
	private static final String SAVE_NEWS_SQL = "insert into news (title,content,publish_time,publish_user_id,last_update_user_id) values (?,?,?,?,?)";
	private static final String GET_NEWS_BY_ID_SQL = "select title,content,publish_time from news where id=?";
	private static final String GET_NEWS_LIST_SQL = "select n.id,title,publish_time,uu.id as uuid,uu.is_admin as uuis_admin,uu.name as uuname,pu.id as puid,pu.is_admin as puis_admin,pu.name as puname from news as n,users as uu,users as pu where pu.id=publish_user_id and uu.id=last_update_user_id order by publish_time desc limit ?,?";
	private static final String UPDATE_NEWS_BY_ID_SQL = "update news set title=?,content=?,last_update_user_id=? where id=?";
	
	private static final String TABLE_NAME = "news";
	
	public NewsDAO() {
		super(TABLE_NAME);
	}
	
	public int save(News news) throws IOException, SQLException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(SAVE_NEWS_SQL);
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setTimestamp(3, news.publishTimeAsDate());
			ps.setInt(4, news.getPublishUser().getId());
			ps.setInt(5, news.getLastUpdateUser().getId());
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}
	
	public int update(News news) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(UPDATE_NEWS_BY_ID_SQL);
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setInt(3, news.getLastUpdateUser().getId());
			ps.setInt(4, news.getId());
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}

	public News getById(int id) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(GET_NEWS_BY_ID_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				News news = new News();
				news.setId(id);
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setPublishTime(rs.getTimestamp("publish_time"));
				return news;
			}
			return null;
		} finally {
			closeAll();
		}
	}

	public PageBean<News> getPaginatedNewsList(int page, int pageSize) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			List<News> result = new ArrayList<News>();
			ps = prepareStatementBySQL(GET_NEWS_LIST_SQL);
			ps.setInt(1, (page - 1) * pageSize);
			ps.setInt(2, pageSize);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setTitle(rs.getString("title"));
				news.setPublishTime(rs.getTimestamp("publish_time"));
				news.setId(rs.getInt("n.id"));
				
				User publishUser = new User();
				publishUser.setId(rs.getInt("puid"));
				publishUser.setName(rs.getString("puname"));
				publishUser.setIsAdmin(rs.getInt("puis_admin"));
				
				User updateUser = new User();
				updateUser.setId(rs.getInt("uuid"));
				updateUser.setName(rs.getString("uuname"));
				updateUser.setIsAdmin(rs.getInt("uuis_admin"));
				
				news.setPublishUser(publishUser);
				news.setLastUpdateUser(updateUser);
				result.add(news);
			}
			PageBean<News> pageBean = new PageBean<News>();
			pageBean.setPageContent(result);
			pageBean.setTotal(getTotalCount());
			return pageBean;
		} finally {
			closeAll();
		}
	}
}
