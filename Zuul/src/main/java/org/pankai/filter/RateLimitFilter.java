package org.pankai.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.pankai.ratelimit.Policy;
import org.pankai.ratelimit.Rate;
import org.pankai.ratelimit.RateLimitProperties;
import org.pankai.ratelimit.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 限流器.
 * 来自于https://github.com/marcosbarbero/spring-cloud-starter-zuul-ratelimit
 * 略有改动
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
        return rateLimitProperties.isEnabled() && policy() != null;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        Policy policy = policy();
        String key = key(request);
        Rate rate = rateLimiter.consume(policy, key);
        response.setHeader(Headers.LIMIT, rate.getLimit().toString());
        response.setHeader(Headers.REMAINING, String.valueOf(Math.max(rate.getRemaining(), 0)));
        response.setHeader(Headers.RESET, rate.getReset().toString());
        if (rate.getRemaining() < 0) {
            ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            ctx.put("rateLimitExceeded", "true");
            ctx.setSendZuulResponse(false);
        }
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

    /**
     * Return the requestedContext from request.
     *
     * @return The requestedContext
     */
    private Route route() {
        return this.routeLocator.getMatchingRoute(this.requestURI());
    }

    private String key(HttpServletRequest request) {
        final Policy policy = this.policy();
        final Route route = route();
        StringBuilder sb = new StringBuilder(route.getId());
        if (policy.getType().contains(Policy.Type.ORIGIN)) {
            sb.append(":").append(request.getRemoteAddr());
        }
        if (policy.getType().contains(Policy.Type.USER)) {
            sb.append(":").append((request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "anonymous");
        }
        return sb.toString();
    }

    private Policy policy() {
        Route route = route();
        return (route != null) ? rateLimitProperties.getPolicies().get(route.getId()) : null;
    }

    interface Headers {
        String LIMIT = "X-RateLimit-Limit";
        String REMAINING = "X-RateLimit-Remaining";
        String RESET = "X-RateLimit-Reset";
    }
}
