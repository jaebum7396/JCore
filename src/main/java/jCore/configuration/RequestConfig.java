package jCore.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import jCore.common.CachedBodyHttpServletWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class RequestConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        CachedBodyHttpServletWrapper cachedBodyHttpServletWrapper = new CachedBodyHttpServletWrapper(httpRequest);
        chain.doFilter(cachedBodyHttpServletWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
