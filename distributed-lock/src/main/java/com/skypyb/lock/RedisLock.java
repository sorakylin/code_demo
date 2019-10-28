package com.skypyb.lock;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLock implements DistributedLock {
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final RedisTemplate redisTemplate;

    //加锁时使用的key
    private final String key;
    //身份证明
    private final String identification;
    //过期时间，单位秒
    private final long expire;
    //获取锁失败重试的间隔 单位毫秒
    private final long interval;


    protected RedisLock(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.key = UUID.randomUUID().toString();
        this.identification = UUID.randomUUID().toString();
        this.expire = 60l;
        this.interval = 50l;
    }

    protected RedisLock(RedisTemplate redisTemplate, String key, String identification, long expire, long interval) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.identification = identification;
        this.expire = expire;
        this.interval = interval;
    }


    @Override
    public void lock() throws InterruptedException {
        while (!tryLock() || waitLockInterval()) ;
        return;
    }

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * 支持重复，线程安全
     *
     * @return true 获取成功
     */
    @Override
    public boolean tryLock() {
        RedisCallback<Boolean> lockCallback = redisConnection -> {

            //TODO SpringBoot2.x开始就默认不用Jedis了
//            JedisCommands jedis = (JedisCommands) redisConnection.getNativeConnection();
//            String result = jedis.set(key, identification, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expire);
//            return LOCK_SUCCESS.equals(result);

            //Lettuce 方式实现
            Object nativeConnection = redisConnection.getNativeConnection();
            String redisResult = "";
            RedisSerializer<String> redisSerializer = redisTemplate.getKeySerializer();
            //lettuce连接包下序列化键值，否知无法用默认的ByteArrayCodec解析
            byte[] keyByte = redisSerializer.serialize(key);
            byte[] valueByte = redisSerializer.serialize(identification);
            // lettuce连接包下 redis 单机模式setnx
            if (nativeConnection instanceof RedisAsyncCommands) {
                RedisAsyncCommands commands = (RedisAsyncCommands) nativeConnection;
                //同步方法执行、setnx禁止异步
                redisResult = commands
                        .getStatefulConnection()
                        .sync()
                        .set(keyByte, valueByte, SetArgs.Builder.nx().ex(expire));
            }
            // lettuce连接包下 redis 集群模式setnx
            if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                redisResult = clusterAsyncCommands
                        .getStatefulConnection()
                        .sync()
                        .set(keyByte, keyByte, SetArgs.Builder.nx().ex(expire));
            }
            //返回加锁结果
            return LOCK_SUCCESS.equalsIgnoreCase(redisResult);

        };

        return redisTemplate.execute(lockCallback) == Boolean.TRUE;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        //等待时间
        long nanoWaitForLock = unit.toNanos(time);
        //开始时间
        long startTime = System.nanoTime();

        while ((System.nanoTime() - startTime) < nanoWaitForLock) {
            if (tryLock() || waitLockInterval()) return true;
        }

        return tryLock();
    }

    @Override
    public boolean unlock() {
        RedisCallback<Boolean> releaseLockCallback = redisConnection -> {
            Object result = redisConnection.eval(
                    RELEASE_LOCK_SCRIPT.getBytes(),
                    ReturnType.INTEGER,
                    1,
                    key.getBytes(),
                    identification.getBytes());
            return RELEASE_SUCCESS.equals(result);
        };

        return redisTemplate.execute(releaseLockCallback) == Boolean.TRUE;
    }

    private boolean waitLockInterval() throws InterruptedException {
        if (interval > 0) TimeUnit.MILLISECONDS.sleep(interval);
        return false;
    }

}
