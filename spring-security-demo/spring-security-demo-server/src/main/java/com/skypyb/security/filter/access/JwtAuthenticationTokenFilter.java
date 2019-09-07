package com.skypyb.security.filter.access;


import com.skypyb.security.config.SecurityProperties;
import com.skypyb.security.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 继承 OncePerRequestFilter , 每个请求都会走这个过滤器
 * 这个过滤器的主要作用是效验令牌,要是有效的话，就将该令牌代表的用户(角色、权限)给查出来，然后塞进Spring Security上下文里边
 * 以便之后的权限决策器AccessDecisionManager 可以得到这玩意进行判定
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    private final Logger logger = LoggerFactory.getLogger("SECURITY");

    private UserDetailsService userDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    private SecurityProperties properties;


    public JwtAuthenticationTokenFilter(SecurityProperties properties, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.properties = properties;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String token = request.getHeader(properties.getHeader());

        //没有 token 的话就算了,直接放行
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }


        String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (IllegalArgumentException e) {
            logger.error("An error occured during getting username from token", e);
        } catch (ExpiredJwtException e) {
            logger.warn("The token is expired and not valid anymore", e);
        } catch (MalformedJwtException e) {
            logger.info(" Unable to read JSON", e);
        }

        if (StringUtils.isEmpty(username) || SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        //既然令牌是真的，那就将这个令牌的权限给查出来
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(token, username)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            logger.info("authenticated user {}, setting security context", username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
