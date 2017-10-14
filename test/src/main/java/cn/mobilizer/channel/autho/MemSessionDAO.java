package cn.mobilizer.channel.autho;

import java.io.Serializable;  
import java.util.Collection;  
  

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.session.Session;  
import org.apache.shiro.session.UnknownSessionException;  
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;  
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

import cn.mobilizer.channel.comm.utils.MemcachedUtil;
  
public class MemSessionDAO extends AbstractSessionDAO {  
  
    private final static Logger log = LoggerFactory.getLogger(MemSessionDAO.class);

    private ConcurrentMap<Serializable, Session> sessions;
    
    public MemSessionDAO() {
    	this.sessions = new ConcurrentHashMap<Serializable, Session>();
    }
    
    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        return sessions.putIfAbsent(id, session);
    }
    
	@Override
	public void update(Session session) throws UnknownSessionException{
		try {
			MemcachedUtil.getInstance().replace(session.getId().toString(), (int) session.getTimeout() / 1000, session);
			storeSession(session.getId(), session);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void delete(Session session){
		if (session == null) { throw new NullPointerException("session argument cannot be null."); }
		Serializable id = session.getId();
		try {
			if (id != null) {
				MemcachedUtil.getInstance().remove(id.toString());
				sessions.remove(id);
	        }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/*
	 * 用来统计当前活动的session
	 * @see org.apache.shiro.session.mgt.eis.SessionDAO#getActiveSessions()
	 */
	@Override
	public Collection<Session> getActiveSessions(){
		Collection<Session> values = sessions.values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableCollection(values);
        }
	}

	@Override
	protected Serializable doCreate(Session session){
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		try {
			MemcachedUtil.getInstance().set(sessionId.toString(), (int) session.getTimeout() / 1000, session);
			storeSession(sessionId, session);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId){
		Session session = (Session) MemcachedUtil.getInstance().get(sessionId.toString());
		try {
			if(session != null) {
				storeSession(sessionId, session);
			} else {
				session = sessions.get(sessionId);
				if(session != null) {
					MemcachedUtil.getInstance().set(sessionId.toString(), (int) session.getTimeout() / 1000, session);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
//		Session session = sessions.get(sessionId);
//		try {
//			if(session == null) {
//				session = (Session) MemcachedUtil.getInstance().get(sessionId.toString());
//				if(session != null) {
//					storeSession(sessionId, session);
//				}
//			} else {
//				
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
		
		return session;
	}
}  