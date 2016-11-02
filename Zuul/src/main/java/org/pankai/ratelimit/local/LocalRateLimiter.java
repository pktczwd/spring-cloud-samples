package org.pankai.ratelimit.local;

import org.pankai.ratelimit.Policy;
import org.pankai.ratelimit.Rate;
import org.pankai.ratelimit.RateLimiter;
import org.pankai.util.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 限流器本地的实现
 * Created by pktczwd on 2016/11/1.
 */
public class LocalRateLimiter implements RateLimiter {

    private final ConcurrentHashMap<String, RequestLimitValue> values = new ConcurrentHashMap<>();

    @Override
    public Rate consume(Policy policy, String key) {
        Long now = System.currentTimeMillis();
        Long refreshInternal = policy.getRefreshInterval();
        RequestLimitValue value = values.get(key);
        if (value == null) {
            values.putIfAbsent(key, new RequestLimitValue(expirationTime(refreshInternal), new AtomicLong(0L)));
            return new Rate(policy.getLimit(), Math.max(-1, policy.getLimit() - values.get(key).getCount().incrementAndGet()), now + refreshInternal * 1000L);
        } else {
            if (new Date().after(value.getExpiredDate())) {
                value.getCount().set(0);
                value.setExpiredDate(expirationTime(refreshInternal));
                return new Rate(policy.getLimit(), Math.max(-1, policy.getLimit() - values.get(key).getCount().incrementAndGet()), now + refreshInternal * 1000L);
            } else {
                return new Rate(policy.getLimit(), Math.max(-1, policy.getLimit() - values.get(key).getCount().incrementAndGet()), values.get(key).getExpiredDate().getTime());
            }
        }
    }

    private Date expirationTime(Long expire) {
        // 提防由long转为int的时候,如果long值已经针对int溢出的话,这里获得的过期时间为立即.限流功能会失效.
        return DateUtils.add(new Date(), Calendar.SECOND, expire.intValue());
    }

    private static class RequestLimitValue {
        private Date expiredDate;
        private AtomicLong count;

        public RequestLimitValue(Date expiredDate, AtomicLong count) {
            this.expiredDate = expiredDate;
            this.count = count;
        }

        public Date getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(Date expiredDate) {
            this.expiredDate = expiredDate;
        }

        public AtomicLong getCount() {
            return count;
        }

        public void setCount(AtomicLong count) {
            this.count = count;
        }
    }
}
