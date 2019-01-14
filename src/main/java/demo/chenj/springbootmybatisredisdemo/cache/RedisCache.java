package demo.chenj.springbootmybatisredisdemo.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chenj
 * @date 2019-01-13 17:21
 * @email 924943578@qq.com
 */
public class RedisCache implements Cache {
    private static final Logger logger= LoggerFactory.getLogger(RedisCache.class);

    private final ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private final String id;//cache instance id
    private RedisTemplate redisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES=30;//redis过期时间

    public  RedisCache(String id){
        if(id==null){
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * put query result to redis
     * @param key
     * @param value
     */
    @Override
    @SuppressWarnings("unchecked")
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate=getRedisTemplate();
        ValueOperations opsForValue=redisTemplate.opsForValue();
        opsForValue.set(key,value,EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
        logger.debug("Put query result to redis");
    }


    /**
     * Get cached query result from redis
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getObject(Object key) {
        RedisTemplate redisTemplate=getRedisTemplate();
        ValueOperations opsForValue=redisTemplate.opsForValue();
        logger.debug("Get cached query result from redis");
        return opsForValue.get(key);
    }

    /**
     * Remove cached query result from redis
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate=getRedisTemplate();
        redisTemplate.delete(key);
        logger.debug("Remove cached query result from redis");
        return null;
    }

    /**
     * Clears this cache instance
     */
    @Override
    public void clear() {
        logger.debug("id:"+id);
        logger.debug("清空缓存");
        try {
            Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
        }
        logger.debug("Clear all the cached query result from redis");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }


    private RedisTemplate getRedisTemplate() {
        if(redisTemplate==null){
            redisTemplate= (RedisTemplate) MyApplicationContext.getBean("redisTemplate");
        }
        return redisTemplate;
    }


}
