package com.pengchengzhizha.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;


public abstract class MailSender {
	protected Session session;
	protected Message message;
	protected Address from;
	protected Address[] to;
	protected Multipart multipart;
	
	protected MailSender(String fromAddress, String[] toAddresses) throws IOException, MessagingException {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("/configs/email.properties"));
		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("magicyuli@pengchengzhizha.com", "mbryant");
			}
		});
		message = new MimeMessage(session);
		from = new InternetAddress(fromAddress);
		to = new InternetAddress[toAddresses.length];
		for (int i = 0; i < toAddresses.length; i++) {
			to[i] = new InternetAddress(toAddresses[i]);
		}
		message.setFrom(this.from);
		message.setRecipients(RecipientType.TO, to);
		multipart = new MimeMultipart();
	}
	
	public void setSubject(String subject) throws MessagingException {
		message.setSubject(subject);
	}
	
	public abstract void setContent(String content) throws MessagingException;
	
	public static MailSender getPlainTextMailSender(String fromAddress, String toAddress) throws IOException, MessagingException {
		return getPlainTextMailSender(fromAddress, new String[]{toAddress});
	}
	
	public static MailSender getPlainTextMailSender(String fromAddress, String[] toAddresses) throws IOException, MessagingException {
		return new MailSender(fromAddress, toAddresses) {
			
			@Override
			public void setContent(String content) throws MessagingException {
				BodyPart bodyPart = new MimeBodyPart();
				bodyPart.setText(content);
				multipart.addBodyPart(bodyPart);
				message.setContent(multipart);
			}
		};
	}
	
	public static MailSender getHtmlMailSender(String fromAddress, String toAddress) throws IOException, MessagingException {
		return getHtmlMailSender(fromAddress, new String[]{toAddress});
	}
	
	public static MailSender getHtmlMailSender(String fromAddress, String[] toAddresses) throws IOException, MessagingException {
		return new MailSender(fromAddress, toAddresses) {
			
			@Override
			public void setContent(String content) throws MessagingException {
				BodyPart bodyPart = new MimeBodyPart();
				bodyPart.setContent(content, "text/html;charset=utf8");
				multipart.addBodyPart(bodyPart);
				message.setContent(multipart);
			}
		};
	}
	
	public void send() throws MessagingException {
		Transport.send(message);
	}

}
