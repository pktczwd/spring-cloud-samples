package org.pankai.eureka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by pktczwd on 2016/11/25.
 */
@JsonRootName("applications")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Applications {

    @JsonProperty("apps__hashcode")
    private String appsHashCode;
    @JsonProperty("versions__delta")
    private String versionDelta;
    @JsonProperty("application")
    private List<Application> application;

    public String getAppsHashCode() {
        return appsHashCode;
    }

    public void setAppsHashCode(String appsHashCode) {
        this.appsHashCode = appsHashCode;
    }

    public String getVersionDelta() {
        return versionDelta;
    }

    public void setVersionDelta(String versionDelta) {
        this.versionDelta = versionDelta;
    }

    public List<Application> getApplication() {
        return application;
    }

    public void setApplication(List<Application> application) {
        this.application = application;
    }
}
