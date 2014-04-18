package com.pengchengzhizha.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengchengzhizha.conf.Conf;
import com.pengchengzhizha.util.RandomValidationCodeUtil;

public class ValidationCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 8506916754276187928L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
		resp.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
        RandomValidationCodeUtil vcUtil = new RandomValidationCodeUtil();
        vcUtil.getRandcode(req, resp);//输出图片方法
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		String VC = req.getParameter("vc").toLowerCase();
		int passed = 0;
		try {
			String correctVC = ((String) req.getSession().getAttribute(Conf.RANDOM_VALIDATION_CODE_SESSION_KEY)).toLowerCase();
			if (VC.equals(correctVC)) {
				passed = 1;
			} else {
				passed = 0;
			}
		} catch(Exception e) {
			passed = 0;
		} finally {
			req.getSession().removeAttribute(Conf.RANDOM_VALIDATION_CODE_SESSION_KEY);
			String JSON = "{\"passed\":" + passed + "}";
			PrintWriter out = resp.getWriter();
			out.append(JSON);
			resp.flushBuffer();
		}
	}
}
