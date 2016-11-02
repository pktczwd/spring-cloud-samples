package org.pankai.ratelimit.redis;

import org.pankai.ratelimit.Policy;
import org.pankai.ratelimit.Rate;
import org.pankai.ratelimit.RateLimiter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Marcos Barbero
 */
public class RedisRateLimiter implements RateLimiter {

    private StringRedisTemplate redisTemplate;

    public RedisRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Rate consume(Policy policy, String key) {
        Long now = System.currentTimeMillis();
        Long time = (now / (1000 * policy.getRefreshInterval()));
        final String tempKey = key + ":" + time;
        List results = (List) redisTemplate.execute(new SessionCallback() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.boundValueOps(tempKey).increment(1L);
                if (operations.getExpire(tempKey) == null) {
                    operations.expire(tempKey, policy.getRefreshInterval(), TimeUnit.SECONDS);
                }
                return operations.exec();
            }
        });
        Long current = (Long) results.get(0);
        return new Rate(policy.getLimit(), Math.max(-1, policy.getLimit() - current), time * (policy.getRefreshInterval() * 1000));
    }
}
