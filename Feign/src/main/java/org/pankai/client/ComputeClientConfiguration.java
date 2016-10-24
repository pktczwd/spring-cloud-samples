package org.pankai.client;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pktczwd on 2016/10/24.
 */
@Configuration
public class ComputeClientConfiguration {

    @Bean
    public Logger.Level ComputeClientLoggerLevel() {
        return Logger.Level.FULL;
    }

}
