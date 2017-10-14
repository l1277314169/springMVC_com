package cn.mobilizer.channel.autho.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.autho.MemCache;
import cn.mobilizer.channel.autho.service.MemCacheManagerService;
import cn.mobilizer.channel.comm.utils.MemcachedUtil;

@Service
public class MemCacheManagerServiceImpl implements MemCacheManagerService {
	private static final Log log = LogFactory.getLog(MemCacheManagerServiceImpl.class);
			
	@Override
	public void createCache(String name,Cache<Object, Object> cache) throws CacheException{
		try {
			MemcachedUtil.getInstance().set(name, 0, cache);  
        } catch (Exception e) {  
            throw new CacheException(e);  
        } 
	}

	@Override
	public Cache<Object, Object> getCache(String name) throws CacheException{
		try {
			if(log.isDebugEnabled()) {
				log.debug("getCache=====================================>: " + name);
			}
            @SuppressWarnings("unchecked")
			MemCache<Object, Object> cache = (MemCache<Object, Object>) MemcachedUtil.getInstance().get(name);  
            return cache;  
        } catch (Exception e) {  
            throw new CacheException(e);  
        }  
	}

	@Override
	public void removeCache(String name) throws CacheException{
		try {  
			 MemcachedUtil.getInstance().remove(name);  
        } catch (Exception e) {  
            throw new CacheException(e);  
        }
	}

	@Override
	public void updateCahce(String name,Cache<Object, Object> cache) throws CacheException{
		try {  
			MemcachedUtil.getInstance().replace(name, 0, cache);  
        } catch (Exception e) {  
            throw new CacheException(e);  
        }
	}

	@Override
	public void destroy() throws CacheException{
		// TODO Auto-generated method stub

	}

}
