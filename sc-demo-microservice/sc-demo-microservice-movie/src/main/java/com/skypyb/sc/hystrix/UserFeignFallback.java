package com.skypyb.sc.hystrix;

import com.skypyb.sc.entity.User;
import com.skypyb.sc.feign.UserFeignClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * {@link com.skypyb.sc.feign.UserFeignClient} 类的fallbackFactory
 * Feign 整合 Hystrix 实现回退需要实现该接口，以获得异常检查回退原因
 */
@Component
public class UserFeignFallback implements FallbackFactory<UserFeignClient> {

    private static Logger logger = LoggerFactory.getLogger(UserFeignFallback.class);


    @Override
    public UserFeignClient create(Throwable cause) {

        logger.info("fallback; reason was: {}", cause);

        return new UserFeignClient() {

            @Override
            public User getUser(Long id) {
                User user = new User();
                user.setId(-1L);
                user.setUserName("默认用户");
                return user;
            }
        };

    }
}
