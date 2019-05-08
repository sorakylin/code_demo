package com.skypyb.service;

import com.skypyb.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * CRUD 都是直接返回的假数据，不连数据库查了
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserService {
    private User user = new User(1, "tom", "man");

    /**
     * @Cacheable 表示需要缓存
     * key 属性是指定如何生成key(可用SpEL表达式)。也可以手动实现 keyGenerator
     * 在查询时，若是在Cache中(我这里是指定的名为"user的"Cache)有对应的key的话则不会执行方法
     * -
     * cacheManager 属性可以指定使用的CacheManager，CacheManager可以自己实现
     * 若是什么都没有导入则默认使用 SimpleCacheConfiguration 创建的 ConcurrentMapCacheManager
     * 要是导入了拥有自己实现的CacheManager的第三方依赖,比如 spring-boot-starter-data-redis，就会覆盖掉默认的实现
     * -
     * condition 属性，满足条件就缓存(可用SpEL表达式写条件)，默认始终缓存
     * unless 属性，满足条件就不缓存，默认始终缓存
     */
    @Cacheable(key = "#root.targetClass.name+#id")
    public User getUserById(Integer id) {

        System.out.println("执行了 getUserById 该方法..");
        return user;
    }


    @Cacheable(key = "#root.targetClass.name + #name")
    public User getUserByName(String name) {

        System.out.println("执行了 getUserByName 该方法..");
        return user;
    }

    /**
     * @CachePut 表示随你怎么搞，反正方法是要执行的。如果对应的Key有缓存，就给你更新了
     */
    @CachePut(key = "#root.targetClass.name+#result.id")
    public User updateUser(User user) {
        System.out.println("执行了 updateUser 该方法..");
        this.user = user;

        return user;
    }

    /**
     * @CacheEvict 表示会清除key对应的缓存, 方法也是无论如何都会执行的
     */
    @CacheEvict(key = "#root.targetClass.name+#id")
    public void deleteUserById(Integer id) {
        System.out.println("执行了 deleteUserById 该方法..");
    }


}
