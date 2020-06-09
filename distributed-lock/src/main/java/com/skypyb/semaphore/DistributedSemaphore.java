package com.skypyb.semaphore;

/**
 * 分布式信号量接口
 * Create by skypyb on 2020-06-07
 */
public interface DistributedSemaphore {


    /**
     * 尝试获取一个信号量
     *
     * @return true 获取成功, false 获取失败
     */
    boolean tryAcquire();

    /**
     * 释放自己持有的信号量
     */
    void release();
}