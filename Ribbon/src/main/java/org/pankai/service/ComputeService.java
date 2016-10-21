package org.pankai.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by pktczwd on 2016/10/13.
 */
@Service
public class ComputeService {

    @Autowired
    private RestTemplate restTemplate;

    //断路器更加详细的配置介绍
    //https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-javanica#configuration
    //https://github.com/Netflix/Hystrix/wiki/Configuration
    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService() {
        return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }

    public String addServiceFallback() {
        return "error";
    }


}
