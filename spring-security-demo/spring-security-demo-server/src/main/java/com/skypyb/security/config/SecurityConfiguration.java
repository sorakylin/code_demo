package com.skypyb.security.config;


import com.skypyb.security.service.AuthenticationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

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
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
//                .antMatchers("/static/**").permitAll() //静态资源访问无需认证
                .anyRequest().authenticated()  //默认其它的请求都需要认证，这里一定要添加
                .and()
                .csrf().disable()  //CRSF禁用，因为不使用session
                .sessionManagement().disable()  //禁用session
                .formLogin().disable() //禁用form登录
                .sessionManagement().disable();


        final Header[] headers = new Header[]{
                //支持所有源的访问
                new Header("Access-control-Allow-Origin", "*"),
                //使ajax请求能够取到header中的jwt token信息
                new Header("Access-Control-Expose-Headers", "Authorization")
        };

        //跨域配置，支持跨域
        httpSecurity.cors()
                .and()   //添加header设置，支持跨域和ajax请求
                .headers()
                .addHeaderWriter(new StaticHeadersWriter(Arrays.asList(headers)));

    }


    @Override
    public void configure(WebSecurity web) throws Exception {


        //允许匿名访问认证相关的接口
        SecurityProperties.Route route = this.securityProperties.getRoute();
        web.ignoring().antMatchers(HttpMethod.POST, route.getAuthPath(), route.getRefreshPath());

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
        //主要就是设置 UserDetailsService,会用我设置的 BCryptPasswordEncoder 来进行加密比对
        auth.userDetailsService(authenticationUserService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTION"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     * AuthenticationManager:
     * 用户认证的管理类，所有的认证请求（比如login）都会通过提交一个 token 给 AuthenticationManager 的 authenticate() 方法来实现。
     * 具体校验动作会由 AuthenticationManager 将请求转发给具体的实现类来做。根据实现反馈的结果再调用具体的 Handler 来给用户以反馈。
     * <p>
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider} 则是其中默认的实现
     * DaoAuthenticationProvider 中会调用 UserDetailsService 来得到一个 UserDetails 对象
     *
     * @return
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
