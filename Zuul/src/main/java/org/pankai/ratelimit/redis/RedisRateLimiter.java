package org.pankai.ratelimit.redis;

import org.pankai.ratelimit.Policy;
import org.pankai.ratelimit.Rate;
import org.pankai.ratelimit.RateLimiter;

/**
 * Created by pktczwd on 2016/10/31.
 */
public class RedisRateLimiter implements RateLimiter {

    @Override
    public Rate consume(Policy policy, String key) {
        return null;
    }
}
