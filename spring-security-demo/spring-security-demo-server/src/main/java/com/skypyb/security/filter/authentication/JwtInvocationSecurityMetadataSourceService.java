package com.skypyb.security.filter.authentication;

import com.skypyb.user.model.dto.MinimumPermissionDTO;
import com.skypyb.user.model.dto.MinimumRoleDTO;
import com.skypyb.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 权限数据源
 * 判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
 */
@Service
public class JwtInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private static Logger logger = LoggerFactory.getLogger("SECURITY");

    /*
        key 是url+method ,value 是对应url资源的角色列表
        即：这个url对应了哪一些权限可以访问
    */
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();


    @Autowired
    private UserService userService;

    /**
     * 加载权限表中所有权限,和其对应的角色
     */
    @PostConstruct
    public void init() {

        //所有权限(接口)
        List<MinimumPermissionDTO> permissions = userService.findAllMinimumPermission();
        List<AntPathRequestMatcher> matchers = permissions.stream()
                .map(x -> new AntPathRequestMatcher(x.getUrl(), x.getMethod()))
                .collect(Collectors.toList());

        //所有角色
        List<MinimumRoleDTO> roles = userService.findAllMinimumRoleDTO();

    }


    /**
     * getAttributes方法返回本次访问需要的权限，可以有多个权限。
     * 在上面的实现中如果没有匹配的url直接返回null，
     * 也就是没有配置权限的url默认都为白名单，想要换成默认是黑名单只要修改这里即可。
     *
     * @param object 安全的对象
     * @return 用于传入的安全对象的属性。 如果没有适用的属性，则应返回空集合。
     * @throws IllegalArgumentException 如果传递的对象不是SecurityDatasource实现支持的类型，则抛出异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        final HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        //所有与该请求匹配的URL
        List<RequestMatcher> matchUrl = requestMap.keySet().stream()
                .filter(matcher -> matcher.matches(request))
                .collect(Collectors.toList());

        //通过所有 URL(权限) 找到需要的角色集
        List<ConfigAttribute> rolesResult = matchUrl.stream().map(requestMap::get)
                .filter(roles -> !CollectionUtils.isEmpty(roles))
                .flatMap(roles -> roles.stream())
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(rolesResult)) return rolesResult;

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
