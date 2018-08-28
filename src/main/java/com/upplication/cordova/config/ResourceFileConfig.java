package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.ResourceFile;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.util.List;

/**
 * cordova.config().platform(...).resourceFile().add(...);
 * Resource file 'internal' XML files
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#resource-file
 */
public class ResourceFileConfig {

    private IConfigProcessor configProcessor;
    private Platform platform;

    public ResourceFileConfig(IConfigProcessor configProcessor, Platform platform){
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(ResourceFile resourceFile) throws IOException {
        this.add(getPlatform(), resourceFile.getSrc(), resourceFile.getTarget());
    }

    public void add(String platform, String src, String target) throws IOException {
        configProcessor.addResourceFile(platform, src, target);
    }

    public List<ResourceFile> getAll() throws IOException {
        return configProcessor.getResourceFile(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.getName().toLowerCase();
        } else {
            return null;
        }
    }
}