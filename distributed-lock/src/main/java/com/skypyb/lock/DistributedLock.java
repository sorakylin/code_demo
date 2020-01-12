package com.skypyb.lock;


import java.util.concurrent.TimeUnit;

/**
 * 分布式锁的接口
 *
 * @author skypyb
 * @date 2019-10-27
 */
public interface DistributedLock {


    /**
     * 加锁的方法，直到获取锁为止
     *
     * @throws InterruptedException
     */
    void lock() throws InterruptedException;

    /**
     * 尝试获取锁的方法，获取不到锁则直接返回false
     *
     * @return true代表加锁成功，false反之
     */
    boolean tryLock();

    /**
     * 尝试获取锁的方法，与{@link this#tryLock()}不同的是，他最长会等待到超时时间过后才结束
     *
     * @param time 时间参数
     * @param unit 时间单位
     * @return true表示成功获取到锁，false反之，在返回false时 这通常代表已经等待完设置的时间后仍然未获得锁
     * @throws InterruptedException
     */
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @return true表示成功 false反之
     */
    boolean unlock();
}
