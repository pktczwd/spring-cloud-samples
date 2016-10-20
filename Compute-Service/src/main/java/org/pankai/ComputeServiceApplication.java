package org.pankai;

import org.pankai.health.CustomizedHealthCheckHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by pktczwd on 2016/10/12.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ComputeServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ComputeServiceApplication.class).web(true).run(args);
    }

    //屏蔽自定义的HealthCheckHandler
    //@Bean
    public CustomizedHealthCheckHandler customizedHealthCheckHandler() {
        return new CustomizedHealthCheckHandler();
    }
}
