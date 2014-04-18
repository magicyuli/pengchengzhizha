package com.pengchengzhizha.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.json.JSONObject;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.dao.MessageDAO;
import com.pengchengzhizha.entity.Message;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.exception.IllegalInputException;
import com.pengchengzhizha.util.IO;
import com.pengchengzhizha.util.MailSender;
import com.pengchengzhizha.util.StringUtil;

public class MessageService {
	private static final int REPLY_BY_EMAIL = 0;
	private static final int REPLY_BY_PHONE = 1;
	private MessageDAO messageDAO;
	
	public MessageService() {
		messageDAO = new MessageDAO();
	}
	
	public int saveMessage(String subject, String content, String name,
			String email, String company, String phone) throws IOException, IllegalInputException {
		Message message = new Message();
		message.setContent(content);
		message.setSubject(subject);
		message.setSenderName(name);
		message.setSenderEmail(email);
		message.setSenderCompany(company != null ? company : "");
		message.setSenderPhone(phone != null ? phone : "");
		if (! isInputsLegal(message)) {
			throw new IllegalInputException("提交内容不合法！");
		}
		try {
			return messageDAO.save(message);
		} catch (SQLException e) {
			throw new IllegalInputException("提交内容不合法！");
		}
	}

	private boolean isInputsLegal(Message message) {
		Pattern pattern = Pattern.compile("</?[a-zA-Z]+\\s*/*\\s*>");
		if (pattern.matcher(message.getContent()).matches()) {
			return false;
		}
		if (pattern.matcher(message.getSubject()).matches()) {
			return false;
		}
		if (pattern.matcher(message.getSenderName()).matches()) {
			return false;
		}
		if (pattern.matcher(message.getSenderEmail()).matches()) {
			return false;
		}
		if (pattern.matcher(message.getSenderCompany()).matches()) {
			return false;
		}
		if (pattern.matcher(message.getSenderPhone()).matches()) {
			return false;
		}
		return true;
	}

	public Message getMessageById(int id) throws SQLException, IOException {
		return messageDAO.getById(id);
	}

	public String getMessageListJSON(int page, int pageSize, int range) throws SQLException, IOException {
		PageBean<Message> paginatedMessageList = messageDAO.getPaginatedMessageList(page, pageSize, range);
		return new JSONObject(paginatedMessageList).toString();
	}

	public int deleteMessageById(int id) throws SQLException, IOException {
		return messageDAO.deleteById(id);
	}

	public int replyMessage(int id, int method, String subject, String content, User user, String toAddress) throws SQLException, IOException, MessagingException {
		if (method == REPLY_BY_EMAIL) {
			sendEmail(subject, content, user, toAddress);
			return messageDAO.updateDueToReply(id, content, user);
		} else if (method == REPLY_BY_PHONE) {
			return messageDAO.updateDueToReply(id, "已通过电话（或其他方式）回复。（系统生成信息）", user);
		}
		return 0;
	}

	private void sendEmail(String subject, String content, User user, String toAddress) throws IOException, MessagingException {
		String tpl = IO.readFile("/tpl/email_reply_message");
		Map<String, String> mapper = new HashMap<String, String>(1);
		mapper.put("content", content);
		String finalContent = StringUtil.hydrateTpl(tpl, mapper);
		
		MailSender mailSender = MailSender.getHtmlMailSender(user.getName() + "@pengchengzhizha.com", new String[]{toAddress});
		mailSender.setSubject("RE:" + subject);
		mailSender.setContent(finalContent);
		mailSender.send();
	}

}
