package cn.mobilizer.channel.servlet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.exception.BusinessException;


public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	/**
	 * 业务逻辑拦截器
	 */
	private static final long serialVersionUID = -5280077683441025979L;

	@Override
	@ResponseBody
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		ResultMessage message = null;
		PrintWriter pw = null;
		// 正常标志
		String msg = null;
		message = null;

		try {
			super.doDispatch(request, response);
		} catch (BusinessException e) {
			msg = e.getMessage();
			logger.error(msg);
			message = new ResultMessage("error", msg);
		} catch (Throwable e1) {
			msg = e1.getMessage();
			logger.error(msg, e1);
			message = new ResultMessage("error", "系统内部异常！");
		} finally {
			if (msg != null) {
				JSONObject obj = JSONObject.fromObject(message);
				pw = response.getWriter();
				pw.write(obj.toString());
				if (pw != null) {
					pw.close();
				}
			}
		}

	}
	
}
