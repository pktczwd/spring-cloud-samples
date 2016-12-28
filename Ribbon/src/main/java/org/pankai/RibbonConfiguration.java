package org.pankai;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pktczwd on 2016/12/27.
 */
@Configuration
@RibbonClient(name = "COMPUTE-SERVICE", configuration = RibbonConfiguration.class)
public class RibbonConfiguration {

    @Bean
    public IClientConfig iClientConfig() {
        DefaultClientConfigImpl config = new DefaultClientConfigImpl();
        //Max number of retries on the same server (excluding the first try)
        config.set(CommonClientConfigKey.MaxAutoRetries, 1);
        //Max number of next servers to retry (excluding the first server)
        config.set(CommonClientConfigKey.MaxAutoRetriesNextServer, 1);
        //Whether all operations can be retried for this client.
        config.set(CommonClientConfigKey.OkToRetryOnAllOperations, Boolean.TRUE);
        //Interval to refresh the server list from the source.
        //config.set(CommonClientConfigKey.ServerListRefreshInterval, 2000);
        //Connect timeout used by Apache HttpClient.
        config.set(CommonClientConfigKey.ConnectTimeout, 2000);
        //Read timeout used by Apache HttpClient.
        config.set(CommonClientConfigKey.ReadTimeout, 2000);
        config.loadProperties("COMPUTE-SERVICE");
        return config;
    }
}
