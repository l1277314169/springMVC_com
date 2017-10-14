package cn.mobilizer.channel.autho.service;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

public interface MemCacheManagerService {
	 /**  
     * 新增缓存堆到管理器  
     *   
     * @param name  
     * @param cache  
     */  
    public abstract void createCache(String name, Cache<Object, Object> cache) throws CacheException;  
  
    /**  
     * 获取缓存堆  
     *   
     * @param name  
     * @return  
     * @throws CacheException  
     */  
    public abstract Cache<Object, Object> getCache(String name) throws CacheException;  
  
    /**  
     * 移除缓存堆  
     *   
     * @param name  
     * @throws CacheException  
     */  
    public abstract void removeCache(String name) throws CacheException;  
  
    /**  
     * 更新缓存堆  
     *   
     * @param name  
     * @param cache  
     */  
    public abstract void updateCahce(String name, Cache<Object, Object> cache) throws CacheException;  
  
    /**  
     * 注销管理器  
     */  
    public abstract void destroy() throws CacheException;  
}
