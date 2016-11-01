package org.pankai.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.pankai.config.RateLimitProperties;
import org.pankai.config.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.util.UrlPathHelper;

/**
 * 限流器.来自于https://github.com/marcosbarbero/spring-cloud-starter-zuul-ratelimit
 * Created by pktczwd on 2016/10/31.
 */
public class RateLimitFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    private final RateLimiter rateLimiter;
    private RateLimitProperties rateLimitProperties;
    private RouteLocator routeLocator;
    private static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();

    public RateLimitFilter(RateLimiter rateLimiter, RateLimitProperties rateLimitProperties, RouteLocator routeLocator) {
        this.rateLimiter = rateLimiter;
        this.rateLimitProperties = rateLimitProperties;
        this.routeLocator = routeLocator;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("RateLimitFilter run method called.");
        log.info(this.requestURI());
        return null;
    }

    /**
     * Get the requestURI from request.
     *
     * @return The request URI
     */
    private String requestURI() {
        return URL_PATH_HELPER.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
    }
}
