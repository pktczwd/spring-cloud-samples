package org.pankai.registry;

import com.netflix.discovery.BackupRegistry;
import com.netflix.discovery.shared.Applications;

/**
 * Created by pktczwd on 2016/10/20.
 * 如果客户端无法从注册中心获取信息,使用此类作为后备
 * 需要在配置文件中显示指定此类启用.
 */
public class FallbackRegistry implements BackupRegistry {

    @Override
    public Applications fetchRegistry() {
        return null;
    }

    @Override
    public Applications fetchRegistry(String[] includeRemoteRegions) {
        return null;
    }
}
