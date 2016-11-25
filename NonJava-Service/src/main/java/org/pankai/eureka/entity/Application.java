package org.pankai.eureka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by pktczwd on 2016/11/25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
