package com.skypyb.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 安全相关配置
 * 从 application.yml 文件里读取
 */
@ConfigurationProperties(
        prefix = "demo.security"
)
@Component
public class SecurityProperties {

    private String header;//请求头部属性名
    private String signingKey;// JWT 签名秘钥
    private Long tokenExpiration;//token失效时间 毫秒值

    private Route route = new Route();//认证路径 强制POST
    private Ignore ignore = new Ignore();//忽略的路径

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public Long getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Long tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public Ignore getIgnore() {
        return ignore;
    }

    public void setIgnore(Ignore ignore) {
        this.ignore = ignore;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    class Route {
        private String authPath;
        private String refreshPath;

        public String getAuthPath() {
            return authPath;
        }

        public void setAuthPath(String authPath) {
            this.authPath = authPath;
        }

        public String getRefreshPath() {
            return refreshPath;
        }

        public void setRefreshPath(String refreshPath) {
            this.refreshPath = refreshPath;
        }
    }


    class Ignore {
        private List<String> get;
        private List<String> post;
        private List<String> put;
        private List<String> delete;
        private List<String> head;
        private List<String> patch;
        private List<String> options;
        private List<String> trace;

        public Map<HttpMethod, List<String>> asMap() {
            Map<HttpMethod, List<String>> ignoreMap = new HashMap();

            ignoreMap.put(HttpMethod.GET, getGet());
            ignoreMap.put(HttpMethod.POST, getPost());
            ignoreMap.put(HttpMethod.PUT, getPut());
            ignoreMap.put(HttpMethod.DELETE, getDelete());
            ignoreMap.put(HttpMethod.HEAD, getHead());
            ignoreMap.put(HttpMethod.PATCH, getPatch());
            ignoreMap.put(HttpMethod.OPTIONS, getOptions());
            ignoreMap.put(HttpMethod.TRACE, getTrace());

            return ignoreMap;
        }

        public List<String> getGet() {
            return get;
        }

        public void setGet(List<String> get) {
            this.get = get;
        }

        public List<String> getPost() {
            return post;
        }

        public void setPost(List<String> post) {
            this.post = post;
        }

        public List<String> getPut() {
            return put;
        }

        public void setPut(List<String> put) {
            this.put = put;
        }

        public List<String> getDelete() {
            return delete;
        }

        public void setDelete(List<String> delete) {
            this.delete = delete;
        }

        public List<String> getHead() {
            return head;
        }

        public void setHead(List<String> head) {
            this.head = head;
        }

        public List<String> getPatch() {
            return patch;
        }

        public void setPatch(List<String> patch) {
            this.patch = patch;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        public List<String> getTrace() {
            return trace;
        }

        public void setTrace(List<String> trace) {
            this.trace = trace;
        }
    }
}
