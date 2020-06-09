package com.skypyb.semaphore;

/**
 * 一个信号量的信息
 */
public final class SemaphoreInfo {

    //信号量的名称
    private final String semaphoreName;

    //许可证的数量
    private final int permits;

    //信号量最大持有时间 (过期时间) 单位s
    private final long expire;

    //公平 or 非公平
    private final boolean fair;

    public SemaphoreInfo(String semaphoreName, int permits, long expire) {
        this(semaphoreName, permits, expire, false);
    }

    public SemaphoreInfo(String semaphoreName, int permits, long expire, boolean fair) {
        this.semaphoreName = semaphoreName;
        this.permits = permits;
        this.expire = expire;
        this.fair = fair;
    }

    public String getSemaphoreName() {
        return semaphoreName;
    }

    public int getPermits() {
        return permits;
    }

    public long getExpire() {
        return expire;
    }

    public boolean isFair() {
        return fair;
    }
}