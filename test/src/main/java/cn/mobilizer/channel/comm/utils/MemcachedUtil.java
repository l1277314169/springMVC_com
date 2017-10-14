package cn.mobilizer.channel.comm.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.util.SSCellRange;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * 
 * @author yeeda.tian
 *
 */
public class MemcachedUtil {
	private static Object LOCK = new Object();
	private static Object SESSION_LOCK = new Object();
	private final static  Log log = LogFactory.getLog(MemcachedUtil.class);
	private Properties properties;
	private MemCachedClient memCachedClient;
	private MemCachedClient sessionMemCachedClient;
	public final static int ONE_HOUR=3600;
	public final static int TWO_HOUR=7200;
	public final static String memcacheMapKey="MEMACACHE_MAP_KEY";
	
	public  static final String SSCELLRANGE = "bb";
	
	private static MemcachedUtil instance;
	
	private void init() {
		try {
			properties = new Properties();
			Resource resource=new ClassPathResource("memcached.properties");
			properties.load(resource.getInputStream());
			//数据缓存服务器，“,”表示配置多个memcached服务
			String[] servers = properties.getProperty("cache.server").replaceAll(" ", "").split(",");
			SockIOPool pool = SockIOPool.getInstance("dataServer");
			pool.setServers(servers);
			pool.setFailover(true);//失效的服务器 恢复运行
			pool.setInitConn(10); //设置开始时每个cache服务器的可用连接数
			pool.setMinConn(5); //设置每个服务器最少可用连接数
			pool.setMaxConn(100); //设置每个服务器最大可用连接数
			/*
			 * 设置连接池维护线程的睡眠时间 
			 * 设置为0，维护线程不启动 维护线程主要通过log输出socket的运行状况，
			 * 监测连接数目及空闲等待时间等参数以控制连接创建和关闭。
			 */
			pool.setMaintSleep(30);
			/*
			 * Tcp的规则就是在发送一个包之前，本地机器会等待远程主机 对上一次发送的包的确认信息到来；
			 * 这个方法就可以关闭套接字的缓存， 以至这个包准备好了就发；
			 * 设置是否使用Nagle算法，因为我们的通讯数据量通常都比较大（相对TCP控制数据）而且要求响应及时，因此该值需要设置为false
			 */
			pool.setNagle(false);
			pool.setSocketTO(30000);//连接建立后对超时的控制
			pool.setBufferSize(1024*1024*5);//设置缓冲区大小
			pool.setAliveCheck(true);//设置连接心跳监测开关
			pool.initialize(); /* 建立MemcachedClient实例 */
			memCachedClient = new MemCachedClient("dataServer");
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception ex) {
			log.error(ex, ex);
		}
	}
	
	private void initSessionMemCached() {
		try {
			properties = new Properties();
			Resource resource=new ClassPathResource("memcached.properties");
			properties.load(resource.getInputStream());
			//Session缓存服务器，“,”表示配置多个memcached服务
			String[] sessionServers = properties.getProperty("session.cache.server").replaceAll(" ", "").split(",");
			SockIOPool sessionPool = SockIOPool.getInstance("sessionServer");
			sessionPool.setServers(sessionServers);
			sessionPool.setFailover(true);
			sessionPool.setInitConn(10);
			sessionPool.setMinConn(5);
			sessionPool.setMaxConn(100);
			sessionPool.setMaintSleep(30);
			sessionPool.setNagle(false);
			sessionPool.setSocketTO(30000);
			sessionPool.setBufferSize(1024*1024*5);
			sessionPool.setAliveCheck(true);
			sessionPool.initialize(); /* 建立MemcachedClient实例 */
			sessionMemCachedClient = new MemCachedClient("sessionServer");
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception ex) {
			log.error(ex, ex);
		}
	}
	
	private MemcachedUtil(){
		init();
	}
	
	private boolean isCacheEnabled() {
		boolean useCache = false;
		try {
			useCache = Boolean.valueOf(properties.getProperty("cache.enable"));
		} catch (Exception e) {
			useCache = false;
			log.error("Please enable memcached");
		}
		return useCache;
	}
	
	/**
	 * 静态实始化
	 * @return
	 */
	public static MemcachedUtil getInstance() {
		if(instance == null){
			synchronized(LOCK) {
				if (instance==null) {
					instance=new MemcachedUtil();
				}
			}
		}
		return instance;
	}

	private MemCachedClient getMemCachedClient(boolean isForSession) {
		if(isForSession){
			if(sessionMemCachedClient == null){
				synchronized(SESSION_LOCK) {
					if (sessionMemCachedClient==null) {
						initSessionMemCached();
					}
				}
			}
			return sessionMemCachedClient;
		}
		else
			return memCachedClient;
	}
	
	public boolean replace(String key, int seconds, Object obj) {
		return replace(key, seconds, obj,false);
	}
	
	/**
	 * 替换
	 * @param key
	 * @param seconds 过期秒数
	 * @param obj
	 * @return
	 */
	public boolean replace(String key, int seconds, Object obj,boolean isForSession) {
		if(StringUtils.isEmpty(key)){
			return false;
		}
		if(obj==null){
			return true;
		}
		try{
			if (isCacheEnabled()) {
				Date  expDate = getDateAfter(seconds);
				boolean result = getMemCachedClient(isForSession).replace(key, obj, expDate);
				if(log.isDebugEnabled()) {
					log.debug("replace A OBJECT: KEY:" + key + ", OBJ:" + obj + ", exp:" + expDate  + ", result:" + result);
				}
				return result;
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return false;
	}

	/**
	 * 将KEY保存到memcache中
	 * @param key
	 * @param seconds 过期秒数
	 * @param obj
	 * @return
	 */
	public boolean set(String key, int seconds, Object obj,boolean isForSession) {
		return set(key, getDateAfter(seconds), obj,isForSession);
	}
	
	/**
	 * 将KEY保存到memcache中
	 * @param key
	 * @param seconds
	 * @param obj
	 * @return
	 */
	public boolean set(String key, int seconds, Object obj) {
		return set(key, getDateAfter(seconds), obj,false);
	}

	
	/**
	 * 将KEY保存到memcache中
	 * @param key
	 * @param exp
	 * @param obj
	 * @return
	 */
	public boolean set(String key,Date exp,Object obj,boolean isForSession){
		if(StringUtils.isEmpty(key)){
			return false;
		}
		if(obj==null){
			return true;
		}
		try{
			if (isCacheEnabled()) {
				boolean result = getMemCachedClient(isForSession).set(key, obj, exp);
				if(log.isDebugEnabled()) {
					log.debug("SET A OBJECT: KEY:" + key + ", OBJ:" + obj + ", exp:" + exp + ", result:" + result);
				}
				return result;
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return false;
	}
	
	public boolean set(String key,Date exp,Object obj){
		return set(key,exp,obj,false);
	}
	
	/**
	 * 把相应的Key值和描述存到此方法中；
	 * @param key
	 * @param discript
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean putKeyDisMap(String key,String discript) {
		Map<String,String> memMap ;
		Object obj = memCachedClient.get(memcacheMapKey);
		if(obj == null) {
			memMap = new HashMap<String, String>();
		} else {
			memMap = (HashMap<String, String>) obj;
		}
		memMap.put(key, discript);
		memCachedClient.set(memcacheMapKey,memMap,getDateAfter(60*60*48));
		
		return true;
	}
	
	/**
	 * 将KEY保存到memcache中
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean set(String key, Object obj) {
		return set(key,ONE_HOUR,obj);
	}
	
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public Object get(String key,boolean isForSession) {
		try{
			if (isCacheEnabled()) {
				Object obj = getMemCachedClient(isForSession).get(key);
				if(log.isDebugEnabled()) {
					log.debug("GET A OBJECT: KEY:" + key + " OBJ:" + obj) ;
				}
				return obj;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return null;
	}
	
	public Object get(String key) {
		return get(key,false);
	}
	
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public boolean remove(String key,boolean isForSession) {
		if(StringUtils.isEmpty(key)){
			return false;
		}
		try{
			if (isCacheEnabled()) {
				if(log.isDebugEnabled()) {
					log.debug("delete memcached key: " + key);
				}
				return getMemCachedClient(isForSession).delete(key);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return true;
	}
	public boolean remove(String key) {
		return remove(key,false);
	}
	
    /**
     * 获得当前开始活参数秒的时间日期
    * @Title: getDateAfter
    * @Description:
    * @param
    * @return Date    返回类型
    * @throws
     */
    public static Date getDateAfter(int second) {
        Calendar cal = Calendar.getInstance();
 		cal.add(Calendar.SECOND, second);
 		return cal.getTime();
 	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Map<String, String> map = (Map<String, String>) MemcachedUtil.getInstance().get("MEM_TEST_KEY_LL_6");
		String str = (String) MemcachedUtil.getInstance().get("MEM_TEST_KEY_LL_2");
		if(map == null){
			map = new HashMap<String, String>();
			for(int i=0;i<1;i++) {
				Random r =  new Random(System.currentTimeMillis());
				String key = "KEY_" + r.nextLong();
				byte[] bt = new byte[0];
				String value= new String(bt,"UTF-8");
				for(int j=0;j<10;j++) {
					value= value + "qweqweqweqweqweqwewequyrqwieurpasjdflkasdfasdrwqioeurpqwerqweqwertyuiopqwertyuiopqwertyuiopqwertqqqq+++";
				}
				map.put(key, value);
			}
			boolean a =MemcachedUtil.getInstance().set("MEM_TEST_KEY_LL_6", map);
		}
		
		if(str == null || str == ""){
			boolean b = MemcachedUtil.getInstance().set("MEM_TEST_KEY_LL_2", "2");
		}
		Map<String, String> map_out = (Map<String, String>) MemcachedUtil.getInstance().get("MEM_TEST_KEY_LL_6");
		String str_out = (String) MemcachedUtil.getInstance().get("MEM_TEST_KEY_LL_2");
		
		System.out.println("keys: " + map.size());
		System.out.println("map_out::::"+map_out);
		System.out.println("str_out::::"+str_out);

	}
}
