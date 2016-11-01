package org.pankai.ratelimit;

import org.pankai.filter.RateLimitFilter;
import org.pankai.ratelimit.local.LocalRateLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pktczwd on 2016/10/31.
 */
@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnProperty(name = "zuul.ratelimit.enabled", matchIfMissing = false)
public class RateLimitAutoConfiguration {

    @Bean
    public RateLimitFilter rateLimitFilter(RateLimiter rateLimiter, RateLimitProperties rateLimitProperties, RouteLocator routeLocator) {
        return new RateLimitFilter(rateLimiter, rateLimitProperties, routeLocator);
    }

    @ConditionalOnMissingBean(RateLimiter.class)
    public static class LocalRateLimiterConfiguration {
        @Bean
        public RateLimiter localRateLimiter() {
            return new LocalRateLimiter();
        }
    }


}
