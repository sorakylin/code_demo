package com.skypyb;

import com.skypyb.lock.DistributedLock;
import com.skypyb.lock.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DistributedLockTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testOne() throws ReflectiveOperationException, InterruptedException {

        Constructor<RedisLock> constructor = RedisLock.class.getDeclaredConstructor(RedisTemplate.class);
        constructor.setAccessible(true);
        DistributedLock lock = constructor.newInstance(redisTemplate);

        Runnable runnable = () -> {
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "获取到lock");

            boolean unlock = lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁结果:" + unlock);
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(runnable);
            System.out.println(threads[i].getName());
            threads[i].start();
        }
        System.out.println("---------------");


        for (Thread thread : threads) {
            thread.join();
        }
    }

}
