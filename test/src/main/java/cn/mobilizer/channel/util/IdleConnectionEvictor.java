package cn.mobilizer.channel.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.HttpClientConnectionManager;


public class IdleConnectionEvictor implements Runnable {

	protected boolean running = true;
	private Log log = LogFactory.getLog(IdleConnectionEvictor.class);
	private final HttpClientConnectionManager connMgr;

	private static IdleConnectionEvictor idleConnectionEvictor = null;
	
    private IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    public static IdleConnectionEvictor getInstance(HttpClientConnectionManager connMgr) {
    	if(null==idleConnectionEvictor){
    		idleConnectionEvictor = new IdleConnectionEvictor(connMgr);
    		Thread idleConnectionEvictorThread = new Thread(idleConnectionEvictor,"IdleConnectionEvictor");
    		idleConnectionEvictorThread.setDaemon(true);
    		idleConnectionEvictorThread.start();
    	}
		return idleConnectionEvictor;
    }
    
    public void closed(){
    	synchronized (idleConnectionEvictor){
			idleConnectionEvictor.notify();
		}
    }
    
	
	 @Override
     public void run() {
         try {
             while (running) {
                 synchronized (this) {
                     // Close expired connections
                     connMgr.closeExpiredConnections(); //过期链接
                     // Optionally, close connections
                     // that have been idle longer than 5 sec
                     connMgr.closeIdleConnections(5, TimeUnit.SECONDS); //一段时间内不活动的连接
                     
                     log.info("IdleConnectionEvictor closeExpiredConnections");
                     wait();
                 }
             }
         } catch (Exception ex) {
             // terminate
         }
     }
}
