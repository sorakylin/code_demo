package com.skypyb.sc.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class ZuulFilterConfig {

    /**
     * 用起这个过滤器来
     * @return
     */
    @Bean
    public PreRequestLogFilter preRequestLogFilter() {
        return new PreRequestLogFilter();
    }

    /**
     * 请求在路由之前的过滤器
     */
    public class PreRequestLogFilter extends ZuulFilter {

        private Logger logger = LoggerFactory.getLogger(PreRequestLogFilter.class);

        @Override
        public String filterType() {
            return FilterConstants.PRE_TYPE;
        }

        @Override
        public int filterOrder() {
            return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
        }

        @Override
        public boolean shouldFilter() {
            return true;
        }

        @Override
        public Object run() throws ZuulException {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();

            logger.info("send {} request to {}.", request.getMethod(), request.getRequestURI());
            return null;
        }
    }//internal class end...
}