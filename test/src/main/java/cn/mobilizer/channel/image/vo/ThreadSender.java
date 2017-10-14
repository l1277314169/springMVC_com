package cn.mobilizer.channel.image.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.util.PropertiesHelper;

public class ThreadSender implements Runnable {

	private boolean running = true;
	private static List<String> messages = new ArrayList<String>();
	private Log log = LogFactory.getLog(ThreadSender.class);
	private static String newDir = null;

	private static final ThreadSender threadSender = new ThreadSender();
	
	static{
		newDir = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
		Thread threadSenderThread = new Thread(threadSender, "ThreadSender");
		threadSenderThread.setDaemon(true);
		threadSenderThread.start();
	}
	
	private ThreadSender() {}
	
	public void stop(){
		this.running = false;
	}
	
	/**
	 * 判断消息中是否已经包含该消息
	 * @param message
	 * @return
	 */
	public static boolean isContains(String message){
		synchronized (messages){
			return messages.contains(message);
		}
	}
	
	 public static ThreadSender getInstance() {
		 return threadSender;
	 }

	/**
	 * 将消息加入消息队列
	 * @param message
	 */
	public void send(String message) {
		if(null!=message && !"".equals(message)){
			synchronized (messages){
				messages.add(message);
				messages.notify();
			}
		}
	}

	
	/**
	 * 将消息加入消息队列
	 * @param message
	 */
	public void send(List<String> lists) {
		if(null!=lists && !lists.isEmpty()){
			synchronized (messages){
				messages.addAll(lists);
				messages.notify();
			}
		}
	}
	
	public void run() {
		String msg = null;
		while (running) {
			try {
				synchronized (messages) {
					if (messages.size() == 0) {
						messages.wait();
					}
				}
				synchronized (messages){
					msg = messages.get(0);
				} 
				FileUtils.copyAndCutImageOne(msg,newDir,false);
				synchronized (messages){
					messages.remove(msg);
				}
			} catch (Exception e) {
				log.info(e.getMessage()+","+msg);
				e.printStackTrace();
			}
		}
	}

	
}
