package com.skypyb.security.filter.authentication;

import com.skypyb.security.config.SecurityProperties;
import com.skypyb.security.model.dto.AuthenticationUser;
import com.skypyb.security.model.request.AuthenticationRequest;
import com.skypyb.security.model.response.AuthenticationFailResponse;
import com.skypyb.security.model.response.AuthenticationResponse;
import com.skypyb.security.util.JwtTokenUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * 给他注册到 AuthenticationManager 里边去,请求访问这路径的时候会给自动处理的
 */
public class CreateAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger("SECURITY");

    private SecurityProperties properties;


    public CreateAuthenticationTokenFilter(SecurityProperties properties) {
        //拦截url为 authPath 的POST请求
        super(new AntPathRequestMatcher(properties.getRoute().getAuthPath(), "POST"));
        this.properties = properties;
    }


    /**
     * 初始化方法,设置好成功的处理器和失败的处理器
     *
     * @return this
     */
    public CreateAuthenticationTokenFilter init() {
        //设置失败的Handler
        this.setAuthenticationFailureHandler(new FailureHandler());

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(properties.getSigningKey(), properties.getTokenExpiration());
        //设置成功的Handler
        this.setAuthenticationSuccessHandler(new SuccessHandler(jwtTokenUtil));

        //不将认证后的context放入session
        this.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        return this;
    }

    /**
     * 认证过滤的方法
     * 将request封装成 UsernamePasswordAuthenticationToken 提交到下一个环节
     * 既然用的是 UsernamePasswordAuthenticationToken,
     * 默认就会提交到{@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}
     * DaoAuthenticationProvider 中会调用系统里的 UserDetailsService 来得到一个 UserDetails 对象
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        //从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));

        //TODO 要有个效验，然后在转换

        AuthenticationRequest authRequest = new ObjectMapper().readValue(body, AuthenticationRequest.class);

        //封装到token中提交
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(), authRequest.getPassword());

        return this.getAuthenticationManager().authenticate(authToken);
    }


    /**
     * 用户名和密码效验正确的处理器
     * 生成一个token
     */
    public class SuccessHandler implements AuthenticationSuccessHandler {

        private JwtTokenUtil jwtTokenUtil;

        public SuccessHandler(JwtTokenUtil jwtTokenUtil) {
            this.jwtTokenUtil = jwtTokenUtil;
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

            AuthenticationUser user = (AuthenticationUser) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(user);
            logger.info("User :{} Create authentication token successful.", user.getUsername());

            //生成token，然后返回
            response.setHeader(properties.getHeader(), token);

            AuthenticationResponse authResponse = new AuthenticationResponse("success");

            PrintWriter writer = response.getWriter();
            response.setContentType("application/json; charset=utf-8");

            writer.print(authResponse.toJson());
            writer.flush();
            writer.close();
        }
    }

    /**
     * 登陆失败了那就将这异常往外边传
     */
    public class FailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException, AuthenticationException {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(AuthenticationFailResponse.asResponse(exception).toJson());
            writer.flush();
            writer.close();
        }
    }

}