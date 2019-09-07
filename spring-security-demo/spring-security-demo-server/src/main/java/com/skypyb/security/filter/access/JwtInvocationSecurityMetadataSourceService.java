package com.skypyb.security.filter.access;

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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限数据源
 * 判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
 */
@Component
public class JwtInvocationSecurityMetadataSourceService
        implements FilterInvocationSecurityMetadataSource {

    private static Logger logger = LoggerFactory.getLogger("SECURITY");

    /*
        key 是url+method ,value 是对应url资源的角色列表
        即：这个url对应了哪一些角色可以访问
    */
    private Map<RequestMatcher, Collection<ConfigAttribute>> relationMap = new LinkedHashMap<>();


    @Autowired
    private UserService userService;

    /**
     * 加载权限表中所有权限,和其对应的角色
     */
    @PostConstruct
    public void init() {

        //所有权限(接口)
        List<MinimumPermissionDTO> permissions = userService.findAllMinimumPermission();

        //所有角色
        List<MinimumRoleDTO> roles = userService.findAllMinimumRoleDTO();

        //权限和角色的关联
        Map<Long, Set<Long>> relation = userService.findAllPermissionRoleRelation();

        //遍历权限，为权限生成可访问的角色集合
        for (MinimumPermissionDTO permission : permissions) {
            AntPathRequestMatcher key = new AntPathRequestMatcher(permission.getUrl(), permission.getMethod());

            //获取拥有此权限的角色id
            Set<Long> valuesId = relation.get(permission.getPermissionId());

            Set<ConfigAttribute> value = roles.stream()
                    .filter(x -> valuesId.contains(x.getRoleId()))
                    .map(x -> new SecurityConfig(x.getEnName()))
                    .collect(Collectors.toSet());

            //将集合放入map中
            if (Objects.isNull(relationMap.get(key))) relationMap.put(key, value);
            else relationMap.get(key).addAll(value);

        }

        logger.info("[权限数据源] 角色与权限初始化映射完毕...总权限数为:{}", relationMap.size());
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

        if (CollectionUtils.isEmpty(relationMap)) init();

        final HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        //所有与该请求匹配的URL
        List<RequestMatcher> matchUrl = relationMap.keySet().stream()
                .filter(matcher -> matcher.matches(request))
                .collect(Collectors.toList());

        //通过所有 URL(权限) 找到需要的角色集
        List<ConfigAttribute> rolesResult = matchUrl.stream().map(relationMap::get)
                .filter(roles -> !CollectionUtils.isEmpty(roles))
                .flatMap(roles -> roles.stream())
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(rolesResult)) return rolesResult;

        return null;
    }

    /**
     * 这个是给SpringSecurity效验的，启动时会检查ConfigAttribute是否配置正确
     * 可以返回null，表示不效验
     *
     * @return 所有的角色
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return relationMap.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
    }

    /**
     * AbstractSecurityInterceptor 调用
     * supports方法返回类对象是否支持校验，web项目一般使用FilterInvocation来判断，或者直接返回true。
     *
     * @param clazz 正在查询的类
     * @return 如果实现可以处理指定的类，则为true
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
