package com.comm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by liuhonger on 2016/7/18.
 */
@Service
public class RedisServiceBase {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private <T> T execute(RedisService<T, ShardedJedis> redisService) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return redisService.callBack(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String set(final String key, final String value) {
        return this.execute(new RedisService<String, ShardedJedis>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value);
            }
        });
    }

    public String get(final String key) {
        return this.execute(new RedisService<String, ShardedJedis>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }


    public Long delete(final String key) {
        return this.execute(new RedisService<Long, ShardedJedis>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

}
