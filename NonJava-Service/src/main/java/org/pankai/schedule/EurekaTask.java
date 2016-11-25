package org.pankai.schedule;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.pankai.cache.ApplicationsCache;
import org.pankai.eureka.entity.Applications;
import org.pankai.eureka.entity.InstanceInfo;
import org.pankai.utils.JSONConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by pktczwd on 2016/11/25.
 */
@Component
public class EurekaTask implements DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(EurekaTask.class);

    @Autowired
    private CloseableHttpClient closeableHttpClient;
    @Value("${eureka.server.url}")
    private String eurekaServerUrl;
    @Value("${app.name}")
    private String appName;
    @Value("${server.port}")
    private Integer serverPort;
    @Autowired
    private String hostName;
    @Autowired
    private String ipAddress;
    @Autowired
    private String instanceId;
    @Autowired
    private ApplicationsCache applicationsCache;

    @Scheduled(fixedRate = 30000)
    public void registerToEureka() throws Exception {
        if (!renew()) {
            logger.info("Renew instance failed, re-registering.");
            register();
        } else {
            logger.info("Renew instance successfully.");
        }
    }

    private boolean renew() throws Exception {
        String targetUrl = eurekaServerUrl + "/apps/" + appName.toUpperCase() + "/" + instanceId;
        HttpPut put = new HttpPut(targetUrl);
        try (CloseableHttpResponse response = closeableHttpClient.execute(put)) {
            return response.getStatusLine().getStatusCode() == 200;
        }
    }

    private void register() throws IOException {
        InstanceInfo instanceInfo = new InstanceInfo();
        instanceInfo.setInstanceId(instanceId);
        instanceInfo.setAppName(appName);
        instanceInfo.setIpAddress(ipAddress);
        instanceInfo.setHostName(hostName);
        instanceInfo.setStatusPageUrl(new StringBuilder("http://").append(ipAddress).append(":").append(serverPort).append("/info").toString());
        InstanceInfo.DataCenterInfo dataCenterInfo = new InstanceInfo.DataCenterInfo();
        dataCenterInfo.setClazz("com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo");
        dataCenterInfo.setName(2);
        instanceInfo.setDataCenterInfo(dataCenterInfo);

        String targetUrl = eurekaServerUrl + "/apps/" + appName.toUpperCase();
        HttpPost post = new HttpPost(targetUrl);
        post.addHeader("Content-Type", "application/json");
        StringEntity stringEntity = new StringEntity(JSONConverter.toJson(instanceInfo));
        post.setEntity(stringEntity);
        try (CloseableHttpResponse response = closeableHttpClient.execute(post)) {
            if (response.getStatusLine().getStatusCode() != 204) {
                logger.warn("Register instance failed, the status code is {}", response.getStatusLine().getStatusCode());
            } else {
                logger.info("Register instance successfully.");
            }
        }
    }

    @Scheduled(fixedRate = 120000)
    public void getAllApplications() throws IOException {
        String targetUrl = eurekaServerUrl + "/apps";
        HttpGet get = new HttpGet(targetUrl);
        get.addHeader("Accept", "application/json");
        CloseableHttpResponse response = closeableHttpClient.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            logger.warn("Fetch registry info from eureka server failed.");
        } else {
            logger.info("Fetch registry info from eureka server successfully.");
            String responseText = EntityUtils.toString(response.getEntity());
            Applications applications = JSONConverter.fromJson(Applications.class, responseText);
            applicationsCache.setApplications(applications);
        }
    }

    @Scheduled(fixedRate = 60000000)
    public void printApplications() {
        Applications applications = applicationsCache.getApplications();
        if (applications == null) {
            logger.info("No applications to print.");
        } else {
            logger.info("Applications hash code is {}", applications.getAppsHashCode());
        }
    }


    @Override
    public void destroy() throws Exception {
        String targetUrl = eurekaServerUrl + "/apps/" + appName.toUpperCase() + "/" + instanceId;
        HttpDelete delete = new HttpDelete(targetUrl);
        try (CloseableHttpResponse response = closeableHttpClient.execute(delete)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.warn("De-register instance failed, the status code is {}", response.getStatusLine().getStatusCode());
            } else {
                logger.info("De-register instance successfully.");
            }
        }
    }
}
