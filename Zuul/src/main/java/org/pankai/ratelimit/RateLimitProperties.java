package org.pankai.ratelimit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pktczwd on 2016/10/31.
 */
@Data
@ConfigurationProperties("zuul.ratelimit")
public class RateLimitProperties {

    private Map<String, Policy> policies = new LinkedHashMap<>();
    private boolean enabled;

}
