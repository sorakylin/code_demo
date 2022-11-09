package com.skypyb.semaphore;


import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * Redis 版的分布式信号量
 * 单机情况下可良好运行, 集群下无法保证可用性
 */
public class RedisSemaphore implements DistributedSemaphore {

    private static final String SEMAPHORE_TIME_KEY = "semaphore:time:";
    private static final String SEMAPHORE_OWNER_KEY = "semaphore:owner:";
    private static final String SEMAPHORE_COUNTER_KEY = "semaphore:counter:";

    private final RedisTemplate redisTemplate;

    private final String timeKey;
    private final String ownerKey;
    private final String counterKey;

    //信号量的信息
    private final SemaphoreInfo info;

    //信号量实体
    private final DistributedSemaphore semaphore;

    //身份证明
    private final String identification;

    public RedisSemaphore(SemaphoreInfo info, RedisTemplate redisTemplate, String identification) {
        this.info = info;
        this.redisTemplate = redisTemplate;
        this.timeKey = SEMAPHORE_TIME_KEY.concat(info.getSemaphoreName());
        this.ownerKey = SEMAPHORE_OWNER_KEY.concat(info.getSemaphoreName());
        this.counterKey = SEMAPHORE_COUNTER_KEY.concat(info.getSemaphoreName());

        this.semaphore = info.isFair() ? new FairSemaphore() : new NonfairSemaphore();

        this.identification = identification;
    }

    @Override
    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    @Override
    public void release() {
        semaphore.release();
    }


    private class NonfairSemaphore implements DistributedSemaphore {

        @Override
        public boolean tryAcquire() {
            ZSetOperations zsetOps = redisTemplate.opsForZSet();
            long timeMillis = System.currentTimeMillis();

            //先清除过期的信号量
            zsetOps.removeRangeByScore(timeKey, 0, timeMillis - (info.getExpire() * 1000));

            //尝试获取信号量并比较自身的排名, 如果小于许可证的数量则表示获取成功 (redis rank 指令从0开始计数)
            zsetOps.add(timeKey, identification, timeMillis);
            if (zsetOps.rank(timeKey, identification) < info.getPermits()) return true;

            //获取失败,删除掉上边添加的标识
            release();
            return false;
        }

        @Override
        public void release() {
            redisTemplate.opsForZSet().remove(timeKey, identification);
        }
    }

    private class FairSemaphore implements DistributedSemaphore {

        @Override
        public boolean tryAcquire() {

            long timeMillis = System.currentTimeMillis();

            //用于获取信号量的计数
            Long counter = redisTemplate.opsForValue().increment(counterKey, 1);

            //用流水线把这一堆命令用一次IO全部发过去
            redisTemplate.executePipelined(new SessionCallback<Object>() {

                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    ZSetOperations zsetOps = operations.opsForZSet();

                    //清除过期的信号量
                    zsetOps.removeRangeByScore(timeKey, 0, timeMillis - (info.getExpire() * 1000));
                    zsetOps.intersectAndStore(timeKey, ownerKey, timeKey);

                    //尝试获取信号量
                    zsetOps.add(timeKey, identification, timeMillis);
                    zsetOps.add(ownerKey, identification, counter);
                    return null;
                }
            });


            //这里根据 持有者集合 的分数来进行判断
            Long ownerRank = redisTemplate.opsForZSet().rank(ownerKey, identification);
            if (ownerRank<info.getPermits()) return true;

            release();
            return false;
        }

        @Override
        public void release() {
            redisTemplate.executePipelined(new SessionCallback<Object>() {

                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    ZSetOperations zetOps = operations.opsForZSet();
                    zetOps.remove(timeKey, identification);
                    zetOps.remove(ownerKey, identification);
                    return null;
                }
            });
        }
    }


}
