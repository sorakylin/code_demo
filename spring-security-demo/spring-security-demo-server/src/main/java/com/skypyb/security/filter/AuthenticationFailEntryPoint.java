package com.skypyb.security.filter;

import com.skypyb.security.exception.SecurityAuthException;
import com.skypyb.security.model.response.AuthenticationFailResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 有人无凭据就想访问受保护的资源时，用这个处理
 * 返回默认的 json 字符串
 *
 * @author pyb
 * @date 2019/08/31
 */
public class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //用于进行Json处理
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.info("Anonymous user attempt access resource '{}'", httpServletRequest.getRequestURI());

        PrintWriter writer = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json; charset=utf-8");

        AuthenticationFailResponse unauthorized;

        if (e instanceof SecurityAuthException) {

            unauthorized = AuthenticationFailResponse.from((SecurityAuthException) e);
        } else if (e instanceof DisabledException) {

            unauthorized = new AuthenticationFailResponse(401, "User is disabled!");
        } else if (e instanceof BadCredentialsException) {

            unauthorized = new AuthenticationFailResponse(401, "Wrong credentials!");
        } else {

            unauthorized = new AuthenticationFailResponse(401, "Unauthorized");
        }


        writer.print(this.objectMapper.writeValueAsString(unauthorized));
        writer.flush();
        writer.close();
    }
}
