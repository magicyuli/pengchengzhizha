package com.pengchengzhizha.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.entity.News;
import com.pengchengzhizha.entity.User;

public class NewsDAO extends BaseDAO {
	private static final String SAVE_NEWS_SQL = "insert into news (title,content,publish_time,publish_user_id,last_update_user_id) values (?,?,?,?,?)";
	private static final String GET_NEWS_BY_ID_SQL = "select title,content,date_format(publish_time,'%Y-%m-%d %H:%m:%s') as publish_time from news where id=?";
	private static final String GET_TOTAL_NEWS_COUNT_SQL = "select count(*) as total from news";
	private static final String GET_NEWS_LIST_SQL = "select n.id,title,publish_time as pt,date_format(publish_time,'%Y-%m-%d %H:%i:%s') as publish_time,uu.id as uuid,uu.is_admin as uuis_admin,uu.name as uuname,pu.id as puid,pu.is_admin as puis_admin,pu.name as puname from news as n,users as uu,users as pu where pu.id=publish_user_id and uu.id=last_update_user_id order by pt desc limit ?,?";
	private static final String DELETE_NEWS_BY_ID_SQL = "delete from news where id=?";
	private static final String UPDATE_NEWS_BY_ID_SQL = "update news set title=?,content=?,last_update_user_id=? where id=?";

	
	public int save(News news) throws IOException, SQLException {
		prepareStatementBySQL(SAVE_NEWS_SQL);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setString(3, news.getPublishTime());
		ps.setInt(4, news.getPublishUser().getId());
		ps.setInt(5, news.getLastUpdateUser().getId());
		try {
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}
	
	public int update(News news) throws SQLException, IOException {
		prepareStatementBySQL(UPDATE_NEWS_BY_ID_SQL);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setInt(3, news.getLastUpdateUser().getId());
		ps.setInt(4, news.getId());
		try {
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}

	public News getById(int id) throws SQLException, IOException {
		prepareStatementBySQL(GET_NEWS_BY_ID_SQL);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		try {
			if (rs.next()) {
				News news = new News();
				news.setId(id);
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setPublishTime(rs.getString("publish_time"));
				return news;
			}
			return null;
		} finally {
			closeAll();
		}
	}

	public PageBean<News> getPaginatedNewsList(int page, int pageSize) throws SQLException, IOException {
		List<News> result = new ArrayList<News>();
		prepareStatementBySQL(GET_NEWS_LIST_SQL);
		ps.setInt(1, (page - 1) * pageSize);
		ps.setInt(2, pageSize);
		rs = ps.executeQuery();
		try {
			while (rs.next()) {
				News news = new News();
				news.setTitle(rs.getString("title"));
				news.setPublishTime(rs.getString("publish_time"));
				news.setId(rs.getInt("id"));
				
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
			pageBean.setTotal(getTotalNewsCount());
			return pageBean;
		} finally {
			closeAll();
		}
	}
	
	private int getTotalNewsCount() throws SQLException, IOException {
		prepareStatementBySQL(GET_TOTAL_NEWS_COUNT_SQL);
		rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		}
		return 0;
	}

	public int deleteById(int id) throws SQLException, IOException {
		prepareStatementBySQL(DELETE_NEWS_BY_ID_SQL);
		ps.setInt(1, id);
		try {
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}
}
