package cn.mobilizer.channel.comm.utils.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.mobilizer.channel.util.Constants;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	static final long serialVersionUID = 1L;

	private int width = 100;

	private int height = 30;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		String key = request.getParameter("key");
		// 生成随机字串
		String verifyCodekey = VerifyCodeUtils.generateVerifyCode(5);
		HttpSession session = request.getSession(true);

		if(null != key && key.trim().length() != 0){
			session.setAttribute(Constants.VERIFICATION_CODE+"_"+key, verifyCodekey);
		}else{
			session.setAttribute(Constants.VERIFICATION_CODE, verifyCodekey);
		}
		System.out.println(verifyCodekey);
			VerifyCodeUtils.outputImage(width, height, response.getOutputStream(),	verifyCodekey);
	}


}
