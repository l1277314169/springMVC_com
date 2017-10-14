/**
 * 
 */
package cn.mobilizer.channel.comm.utils.web;

import java.io.Serializable;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

/**
 * Action的最顶层基类
 * @author yidan.tian
 *
 */
public abstract class BaseCommonAction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4172813549905256587L;
	
//	protected HttpServletRequest request;
//	protected HttpServletResponse response;
//	protected Model model;
	
	protected Object getSession(final String key){
		if(StringUtils.isEmpty(key)){
			return null;
		}
		return null;
		/**
		 * todo somethings
		 */
	}
	
	protected void putSession(final String key,final Object obj){
		
	}
	
	
	protected boolean hasLocalServer(){
		String host = HttpServletLocalThread.getRequest().getLocalName();
		if(host.contains("127.0.0.1")||host.contains("")){
			return true;
		}
		return false;
	}
	
	
	protected Cookie getCookie(final String key){
		return null;
		/**
		 * todo somethings
		 */
	}
	
//	@ModelAttribute
//	public void setReqAndResp(Model model,HttpServletRequest request,HttpServletResponse response){
//		this.request = request;
//		this.response = response;
//		this.model = model;
//	}
//	
//	protected HttpServletRequest getRequest() {
//		return this.request;  
//	}
//
//	protected HttpServletResponse getResponse() {
//		return  this.response;
//	}
//	
//	protected HttpSession getSession() {
//		return this.getRequest().getSession();
//	}
}
