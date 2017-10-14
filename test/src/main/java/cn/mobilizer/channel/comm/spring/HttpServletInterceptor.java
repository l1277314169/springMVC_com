package cn.mobilizer.channel.comm.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mobilizer.channel.comm.utils.web.HttpServletLocalThread;


/**
 * @author yidan.tian
 *
 */
public class HttpServletInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpServletLocalThread.set(request, response);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HttpServletLocalThread.clean();
		super.afterCompletion(request, response, handler, ex);
	}

	
}
