package com.skypyb.security.filter.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * AbstractSecurityInterceptor:进行访问资源时，会通过这个拦截器拦截
 * 其中会调用FilterInvocationSecurityMetadataSource的方法来获取被拦截url所需的全部权限，在调用授权管理器 AccessDecisionManager
 */
@Component//在spring容器托管的AbstractSecurityInterceptor的bean，都会自动加入到servlet的filter chain，不用在websecurityconfig配置
public class JwtFilterSecurityInterceptor extends AbstractSecurityInterceptor
        implements Filter {

    private static Logger logger = LoggerFactory.getLogger("SECURITY");

    @Autowired
    private JwtAccessDecisionManager accessDecisionManager;

    @Autowired
    private JwtInvocationSecurityMetadataSourceService invocationSecurityMetadataSourceService;

    /**
     * 向父类提供要处理的安全对象类型，因为父亲被调用的方法参数类型大多是Object，框架需要保证传递进去的安全对象类型相同
     *
     * @return 子类为其提供服务的安全对象的类型
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * 设置好自己定义的权限数据源
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.invocationSecurityMetadataSourceService;
    }

    /**
     * 在这个由Spring调用的初始化方法里主要是设置好鉴权的决策管理器
     * 由于父类已经实现了 InitializingBean ,所以这儿用 @PostConstruct
     */
    @PostConstruct
    private void springInit() {
        super.setAccessDecisionManager(accessDecisionManager);
        logger.info("Interceptor --> JwtFilterSecurityInterceptor add to servlet filter chain");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter的doFilter调用前会触发该方法
    }

    @Override
    public void destroy() {
        //Filter的doFilter调用后会触发该方法
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //fi里面有一个被拦截的url
        FilterInvocation fi = new FilterInvocation(request, response, chain);

        //里面调用InvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用AccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
    }


}
