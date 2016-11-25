package org.pankai.cache;

import org.pankai.eureka.entity.Applications;
import org.springframework.stereotype.Component;

/**
 * Created by pktczwd on 2016/11/25.
 */
@Component
public class ApplicationsCache {

    private Applications applications;

    public Applications getApplications() {
        return applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }
}
