package cn.mobilizer.channel.web.freemarker;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MyMultipartResolver extends CommonsMultipartResolver{
	  @Override
	    public boolean isMultipart(HttpServletRequest request) {
	        if(request.getRequestURI().contains("/message/uploadImg")) {
	            return false;
	        } else {
	            return super.isMultipart(request);
	        }
	    }
}
