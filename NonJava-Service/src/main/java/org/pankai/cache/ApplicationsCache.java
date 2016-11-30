package org.pankai.cache;

import org.pankai.eureka.entity.Applications;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by pktczwd on 2016/11/25.
 */
@Component
public class ApplicationsCache {

    private AtomicReference<Applications> applicationsAtomicReference = new AtomicReference<>();

    public Applications getApplications() {
        return applicationsAtomicReference.get();
    }

    public void setApplications(Applications applications) {
        this.applicationsAtomicReference.set(applications);
    }
}
