import com.skypyb.CacheDemoApplication;
import com.skypyb.entity.User;
import com.skypyb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = CacheDemoApplication.class)
@RunWith(SpringRunner.class)
public class CacheTest {

    @Autowired
    private UserService userService;

    @Resource
    private ConcurrentMapCacheManager cacheManager;


    @Test
    public void test1() {

        //第一次查
        User user = userService.getUserById(1);
        userService.getUserByName("tom");
        System.out.println("------");

        //第二次查
        userService.getUserById(1);
        userService.getUserByName("tom");
        System.out.println("------");

        userService.updateUser(user);
        System.out.println("------");

        userService.deleteUserById(1);
        System.out.println("------");

        //第三次查
        userService.getUserById(1);
        userService.getUserByName("tom");
        System.out.println("------");

        test2();
    }

    /**
     * 代码方式操作缓存
     */
    public void test2() {
        Object user = cacheManager.getCache("user").get("com.skypyb.service.UserService1").get();
        System.out.println(user);
    }


}
