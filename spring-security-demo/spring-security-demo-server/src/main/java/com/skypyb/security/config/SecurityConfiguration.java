package com.skypyb.security.config;


import com.skypyb.security.filter.AuthenticationFailEntryPoint;
import com.skypyb.security.filter.access.JwtAuthenticationTokenFilter;
import com.skypyb.security.filter.authentication.CreateAuthenticationTokenFilter;
import com.skypyb.security.filter.authentication.OptionsRequestFilter;
import com.skypyb.security.service.AuthenticationUserService;
import com.skypyb.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * Web 安全配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void configure(WebSecurity web) throws Exception {

        //允许匿名访问指定的url
        SecurityProperties.Ignore ignore = this.securityProperties.getIgnore();
        ignore.asMap().entrySet().stream()
                .filter(entry -> !CollectionUtils.isEmpty(entry.getValue()))
                .forEach(entry ->
                        web.ignoring().antMatchers(
                                entry.getKey(),
                                entry.getValue().toArray(new String[entry.getValue().size()])
                        )
                );
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //主要就是设置 UserDetailsService,会用设置的 BCryptPasswordEncoder 来进行加密比对
        auth.userDetailsService(authenticationUserService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()  //默认请求都需要认证(认证就是登录)，这里一定要添加
                .and()
                .csrf().disable()  //CRSF禁用，因为不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //禁用session
                .and().formLogin().disable();//禁用form登录


        final Header[] headers = new Header[]{
                //支持所有源的访问
                new Header("Access-control-Allow-Origin", "*"),
                //使ajax请求能够取到header中的jwt token信息
                new Header("Access-Control-Expose-Headers", securityProperties.getHeader())
        };

        //跨域配置，支持跨域
        httpSecurity.cors()
                .and()   //添加header设置，支持跨域和ajax请求
                .headers()
                .addHeaderWriter(new StaticHeadersWriter(Arrays.asList(headers)))
                .and()
                //拦截OPTIONS请求，直接返回header
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class);

        //设置入口点异常处理
        httpSecurity.exceptionHandling().authenticationEntryPoint(new AuthenticationFailEntryPoint());


        createAuthenticationTokenFilter(httpSecurity);
        createJwtTokenFilterInit(httpSecurity);
    }

    /**
     * 创建一个 {@link CreateAuthenticationTokenFilter}
     * AuthenticationProcessingFilter可以拦截指定的URL，我这的实现是拦截试图创建Token令牌的
     *
     * @param httpSecurity
     * @throws Exception
     */
    public void createAuthenticationTokenFilter(HttpSecurity httpSecurity) throws Exception {
        //实例化创建认证 token 的 Filter
        CreateAuthenticationTokenFilter createAuthenticationTokenFilter
                = new CreateAuthenticationTokenFilter(this.securityProperties, jwtTokenUtil()).init();

        //设置 Filter 使用的 AuthenticationManager,这里取公共的即可
        createAuthenticationTokenFilter.setAuthenticationManager(this.authenticationManagerBean());
        //将认证的 filter 放到 logoutFilter 这个责任链节点之前
        httpSecurity
                .addFilterBefore(createAuthenticationTokenFilter, LogoutFilter.class);
    }

    private void createJwtTokenFilterInit(HttpSecurity httpSecurity) {
        JwtAuthenticationTokenFilter authenticationTokenFilter =
                new JwtAuthenticationTokenFilter(securityProperties, jwtTokenUtil(), authenticationUserService);
        httpSecurity
                .addFilterAfter(authenticationTokenFilter, LogoutFilter.class);
    }


    //跨域设置
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //跨域请求访问配置
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");//请求方法限制:如GET/POST，*表示所有都允许
        //configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");//容许任何来源的跨域访问
        configuration.addExposedHeader(securityProperties.getHeader());
        //对当前这个服务器下所有有的请求都启用这个配置
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }

    //Jwt的工具类
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(securityProperties.getSigningKey(), securityProperties.getTokenExpiration());
    }


    /**
     * AuthenticationManager:
     * 用户认证的管理类，所有的认证请求（比如login）都会通过提交一个 token 给 AuthenticationManager 的 authenticate() 方法来实现。
     * 具体校验动作会由 AuthenticationManager 将请求转发给具体的实现类来做。根据实现反馈的结果再调用具体的 Handler 来给用户以反馈。
     *
     * @return default AuthenticationManager
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * Spring Security 的用户要强制你加密的
     * 这玩意有几个地方用到了，所以注册成Bean，不new了
     *
     * @return PasswordEncoder impl
     */
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

}
