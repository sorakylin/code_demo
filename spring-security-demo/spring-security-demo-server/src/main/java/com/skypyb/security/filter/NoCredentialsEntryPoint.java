package com.skypyb.security.filter;

import com.skypyb.security.model.response.AuthenticationFailResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class NoCredentialsEntryPoint implements AuthenticationEntryPoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String responseJson;

    public NoCredentialsEntryPoint() {
        AuthenticationFailResponse unauthorized = new AuthenticationFailResponse(401, "Unauthorized");
        try {
            this.responseJson = new ObjectMapper().writeValueAsString(unauthorized);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.info("Anonymous user attempt access resource '{}'", httpServletRequest.getRequestURI());

        PrintWriter writer = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json; charset=utf-8");


        writer.print(responseJson);
        writer.flush();
        writer.close();
    }
}
