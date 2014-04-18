package com.pengchengzhizha.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.entity.Message;
import com.pengchengzhizha.entity.User;

public class MessageDAO extends BaseDAO {
	private static final String SAVE_MESSAGE_SQL = "insert into message (subject,content,sender_name,sender_email,sender_company,sender_phone,is_replied) values (?,?,?,?,?,?,?)";
	private static final String GET_MESSAGE_BY_ID_SQL = "select * from message as m left join users as u on m.replying_user_id=u.id where m.id=?";
	private static final String GET_MESSAGE_LIST_ALL_SQL = "select m.id,subject,sender_name,sender_company,sending_time,is_replied,u.name from message as m left join users as u on u.id=replying_user_id order by sending_time desc limit ?,?";
	private static final String GET_MESSAGE_LIST_NOT_REPLIED_SQL = "select m.id,subject,sender_name,sender_company,sending_time,is_replied,u.name from message as m left join users as u on u.id=replying_user_id where is_replied=0 order by sending_time desc limit ?,?";
	private static final String GET_MESSAGE_LIST_REPLIED_SQL = "select m.id,subject,sender_name,sender_company,sending_time,is_replied,u.name from message as m left join users as u on u.id=replying_user_id where is_replied=1 order by sending_time desc limit ?,?";
	private static final String UPDATE_DUE_TO_REPLY_SQL = "update message set reply_content=?,replying_time=?,replying_user_id=?,is_replied=1 where id=?";
	
	private static final String GET_TOTAL_WHERE_CLAUSE_ALL = "";
	private static final String GET_TOTAL_WHERE_CLAUSE_NOT_REPLIED = "where is_replied=0";
	private static final String GET_TOTAL_WHERE_CLAUSE_REPLIED = "where is_replied=1";
	
	private static final String TABLE_NAME = "message";
	
	public static final int LIST_ALL = 0;
	public static final int LIST_NOT_REPLIED = 1;
	public static final int LIST_REPLIED = 2;
	
	private Map<Integer, String> listSqlMapper;
	private Map<Integer, String> whereClauseMapper;
	
	public MessageDAO() {
		super(TABLE_NAME);
		
		listSqlMapper = new HashMap<Integer, String>(3);
		listSqlMapper.put(LIST_ALL, GET_MESSAGE_LIST_ALL_SQL);
		listSqlMapper.put(LIST_NOT_REPLIED, GET_MESSAGE_LIST_NOT_REPLIED_SQL);
		listSqlMapper.put(LIST_REPLIED, GET_MESSAGE_LIST_REPLIED_SQL);
		
		whereClauseMapper = new HashMap<Integer, String>(3);
		whereClauseMapper.put(LIST_ALL, GET_TOTAL_WHERE_CLAUSE_ALL);
		whereClauseMapper.put(LIST_NOT_REPLIED, GET_TOTAL_WHERE_CLAUSE_NOT_REPLIED);
		whereClauseMapper.put(LIST_REPLIED, GET_TOTAL_WHERE_CLAUSE_REPLIED);
		
	}
	
	public int save(Message message) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(SAVE_MESSAGE_SQL);
			ps.setString(1, message.getSubject());
			ps.setString(2, message.getContent());
			ps.setString(3, message.getSenderName());
			ps.setString(4, message.getSenderEmail());
			ps.setString(5, message.getSenderCompany());
			ps.setString(6, message.getSenderPhone());
			ps.setInt(7, 0);
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}

	public Message getById(int id) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(GET_MESSAGE_BY_ID_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Message message = new Message();
				message.setId(id);
				message.setSubject(rs.getString("subject"));
				message.setContent(rs.getString("content"));
				message.setSendingTime(rs.getTimestamp("sending_time"));
				message.setSenderName(rs.getString("sender_name"));
				message.setSenderCompany(rs.getString("sender_company"));
				message.setSenderEmail(rs.getString("sender_email"));
				message.setSenderPhone(rs.getString("sender_phone"));
				message.setIsReplied(rs.getInt("is_replied"));
				if (message.isReplied()) {
					message.setReplyContent(rs.getString("reply_content"));
					message.setReplyingTime(rs.getTimestamp("replying_time"));
					User user = new User();
					user.setId(rs.getInt("replying_user_id"));
					user.setName(rs.getString("name"));
					user.setIsAdmin(rs.getInt("is_admin"));
					message.setReplyingUser(user);
				}
				return message;
			}
			return null;
		} finally {
			closeAll();
		}
	}

	public PageBean<Message> getPaginatedMessageList(int page, int pageSize, int range) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			List<Message> result = new ArrayList<Message>();
			ps = prepareStatementBySQL(listSqlMapper.get(range));
			ps.setInt(1, (page - 1) * pageSize);
			ps.setInt(2, pageSize);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("m.id"));
				message.setSubject(rs.getString("subject"));
				message.setSenderName(rs.getString("sender_name"));
				message.setSenderCompany(rs.getString("sender_company"));
				message.setIsReplied(rs.getInt("is_replied"));
				message.setSendingTime(rs.getTimestamp("sending_time"));
				
				User user = new User();
				user.setName(rs.getString("u.name"));
				
				message.setReplyingUser(user);
				result.add(message);
			}
			PageBean<Message> pageBean = new PageBean<Message>();
			pageBean.setPageContent(result);
			pageBean.setTotal(getTotalCount(whereClauseMapper.get(range)));
			return pageBean;
		} finally {
			closeAll();
		}
	}

	public int updateDueToReply(int id, String content, User user) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(UPDATE_DUE_TO_REPLY_SQL);
			ps.setString(1, content);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, user.getId());
			ps.setInt(4, id);
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
		
	}
	
	

}
