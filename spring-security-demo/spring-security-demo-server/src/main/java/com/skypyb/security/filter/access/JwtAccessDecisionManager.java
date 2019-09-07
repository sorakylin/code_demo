package com.skypyb.security.filter.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AccessDecisionManager 是 SpringSecurity 的决策管理器，
 * AccessDecisionManager实际上是由一个或多个决定是否访问的投票者的组合体。
 * 这个组合封装了允许/拒绝/放弃观看资源的用户逻辑。投票者决定结果是通过
 * ACCESS_GRANTED ， ACCESS_DENIED和ACCESS_ABSTAIN中的AccessDecisionVoter接口中定义的常量字段来表示。
 * 我们可以定义自定义访问决策，并注入到我们的访问决策管理器中。
 * <p>
 * 我这个自定义实现的决策管理器不使用投票器
 */
@Service
public class JwtAccessDecisionManager implements AccessDecisionManager {

    private static Logger logger = LoggerFactory.getLogger("SECURITY");

    private static final String ACCESS_FAIL = "Insufficient permissions!";

    /**
     * 核心方法,用来鉴权
     * 如果方法执行完毕没有抛出异常,则说明可以放行
     * 要是权限不匹配。就抛出AccessDeniedException
     *
     * @param authentication   谁正在访问资源
     * @param object           被访问的资源object
     * @param configAttributes 访问资源要求的权限配置ConfigAttributeDefinition,来源于:
     *                         {@link JwtInvocationSecurityMetadataSourceService#getAttributes(Object)}
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        String resourcePath = ((FilterInvocation) object).getHttpRequest().getServletPath();

        logger.info("User {} tries to access a protected resource : '{}'.",
                authentication.getName(), resourcePath);

        //用户所拥有的权限
        List<? extends GrantedAuthority> permissions = authentication.getAuthorities().stream().collect(Collectors.toList());

        //匹配,只要在{configAttributes}中有一个角色被用户拥有就可以访问资源
        boolean result = configAttributes.stream().map(x -> x.getAttribute()).anyMatch(x -> permissions.contains(x));

        if (result) {
            logger.info("User {} has access to '{}' resources", authentication.getName(), resourcePath);
            return;
        }

        logger.info("User access to '{}' resource was denied, reason: {}", resourcePath, ACCESS_FAIL);
        throw new AccessDeniedException(ACCESS_FAIL);
    }

    /**
     * 跟{@link JwtInvocationSecurityMetadataSourceService#supports(Class)}一样的效用
     * 我这默认返回true了
     *
     * @param attribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
