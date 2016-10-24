package org.pankai.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pktczwd on 2016/10/13.
 */
@RestController
public class UserController {

    @RequestMapping(value = "/user/{id}")
    public Integer get(@PathVariable Integer id) {
        return id;
    }

}
