package org.pankai.web;

import org.pankai.web.response.InfoResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pktczwd on 2016/11/24.
 */
@RestController
public class InfoController {

    @RequestMapping(path = "/info")
    public InfoResponse health() {
        InfoResponse response = new InfoResponse();
        response.setStatus("UP");
        return response;
    }

}
