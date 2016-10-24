package org.pankai.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pktczwd on 2016/10/24.
 */
@FeignClient(value = "user-service")
public interface UserClient {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    //这里有坑,@PathVariable必须带上value,并没有绑定到URI template里面
    public Integer get(@PathVariable(value = "id") Integer id);

}
