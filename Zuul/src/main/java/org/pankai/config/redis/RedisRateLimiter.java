package org.pankai.config.redis;

import org.pankai.config.Policy;
import org.pankai.config.Rate;
import org.pankai.config.RateLimiter;

/**
 * Created by pktczwd on 2016/10/31.
 */
public class RedisRateLimiter implements RateLimiter {

    @Override
    public Rate consume(Policy policy, String key) {
        return null;
    }
}
