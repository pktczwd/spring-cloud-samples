package org.pankai.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by pktczwd on 2016/10/13.
 */
@FeignClient(value = "compute-service", fallback = ComputeClientHystrix.class, configuration = ComputeClientConfiguration.class)
public interface ComputeClient {

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

}
