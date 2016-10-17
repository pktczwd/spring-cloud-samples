package org.pankai.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试从配置中心获取属性值
 * Created by pktczwd on 2016/10/13.
 */
//@RefreshScope该注解下的bean在每次访问的时候都会经历完整的bean生命周期,所以其使用的属性值会重新计算得到.
@RefreshScope
@RestController
public class PropertyTestController {

    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    public String from() {
        return this.from;
    }


}
