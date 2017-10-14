/**
 * 
 */
package www.lvmama.com.conf;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author fengyonggang
 *
 */
@Configuration
@PropertySource("classpath:memcached.properties")
public class MemcacheConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemcacheConfig.class);
	
	private static final String SESSION_POOL_NAME = "sessionServer";
	
	@Value("${session.cache.server}")
	private String sessionCacheServer;
	
	@Bean
	public MemCachedClient memCachedClient() {
		LOGGER.info("Initialize memCachedClient with servers: {}", sessionCacheServer);
		String[] sessionServers = sessionCacheServer.replaceAll(" ", "").split(",");
		SockIOPool sessionPool = SockIOPool.getInstance(SESSION_POOL_NAME);
		sessionPool.setServers(sessionServers);
		sessionPool.setFailover(true);
		sessionPool.setInitConn(10);
		sessionPool.setMinConn(5);
		sessionPool.setMaxConn(50);
		sessionPool.setMaintSleep(30);
		sessionPool.setNagle(false);
		sessionPool.setSocketTO(3000);
		sessionPool.setBufferSize(1024*1024*5);
		sessionPool.setAliveCheck(true);
		sessionPool.initialize();
		return new MemCachedClient(SESSION_POOL_NAME);
	}
	
}
