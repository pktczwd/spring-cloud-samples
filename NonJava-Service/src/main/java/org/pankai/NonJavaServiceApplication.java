package org.pankai;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NonJavaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NonJavaServiceApplication.class, args);
    }

    @Value("${app.name}")
    private String appName;

    @Value("${server.port}")
    private Integer serverPort;


    @Bean
    public CloseableHttpClient closeableHttpClient() {
        return HttpClients.custom().build();
    }

    @Bean
    public String hostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    @Bean
    public String ipAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @Bean
    public String instanceId() throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        return sb.append(hostName()).append(":").append(appName).append(":").append(serverPort).toString();
    }


}
