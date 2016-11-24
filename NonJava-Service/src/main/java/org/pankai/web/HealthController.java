package org.pankai.web;

import org.pankai.web.response.HealthResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pktczwd on 2016/11/24.
 */
@RestController
public class HealthController {

    @RequestMapping(path = "/health")
    public HealthResponse health() {
        HealthResponse response = new HealthResponse();
        response.setStatus("UP");
        return response;
    }

}
