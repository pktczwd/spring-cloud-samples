package org.pankai.eureka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by pktczwd on 2016/11/25.
 */
@JsonRootName("instance")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceInfo {

    private String instanceId;
    @JsonProperty("app")
    private String appName;
    @JsonProperty("ipAddr")
    private String ipAddress;
    private String hostName;
    private String statusPageUrl;
    private DataCenterInfo dataCenterInfo;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }

    public DataCenterInfo getDataCenterInfo() {
        return dataCenterInfo;
    }

    public void setDataCenterInfo(DataCenterInfo dataCenterInfo) {
        this.dataCenterInfo = dataCenterInfo;
    }

    public static class DataCenterInfo {
        @JsonProperty("@class")
        private String clazz;
        private Integer name;

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        public Integer getName() {
            return name;
        }

        public void setName(Integer name) {
            if (name > 2 || name < 0) {
                throw new IllegalArgumentException("Valid name is 0,1,2.");
            }
            this.name = name;
        }
    }

}
