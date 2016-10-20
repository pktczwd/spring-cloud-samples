package org.pankai.health;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;

/**
 * Created by pktczwd on 2016/10/20.
 * 在原有health check基础上,实现自定义的health check.
 */
public class CustomizedHealthCheckHandler implements HealthCheckHandler {
    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus currentStatus) {
        return InstanceInfo.InstanceStatus.OUT_OF_SERVICE;
    }
}
