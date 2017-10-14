package cn.mobilizer.channel.autho;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.autho.service.MemCacheManagerService;

public class ShiroMemCacheManager implements CacheManager, Destroyable{
	private static final Log log = LogFactory.getLog(ShiroMemCacheManager.class);

	@Autowired
	private MemCacheManagerService memCacheManagerService;
	
	@SuppressWarnings("rawtypes")
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException{
		log.debug("获取名称为: " + name + " 的MemCache实例");
		Cache c = caches.get(name);

		if (c == null) {
			c = (Cache) memCacheManagerService.getCache(name);
			if (c == null){
//				return null;
				c = new MemCache(name, new HashMap<Object, Object>());
			}
			caches.put(name, c);
		}
		return c;
	}

	@Override
	public void destroy() throws Exception{
		memCacheManagerService.destroy();

	}
}
