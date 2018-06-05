package com.upplication.cordova.config;

import com.upplication.cordova.ConfigFile;
import com.upplication.cordova.EditConfig;
import com.upplication.cordova.Platform;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.util.List;

/**
 * cordova.config().configfile().add(...);
 * Add content to config 'internal' XML files
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#config-file
 */
public class ConfigFileConfig {

    private IConfigProcessor configProcessor;
    private Platform platform;

    public ConfigFileConfig(IConfigProcessor configProcessor, Platform platform){
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(ConfigFile configFile) throws IOException {
        this.add(getPlatform(), configFile.getTarget(), configFile.getParent(), configFile.getAfter(), configFile.getContent());
    }

    public void add(String platform, String target, String parent, String after, String content) throws IOException {
        configProcessor.addConfigFile(platform, target, parent, after, content);
    }

    public List<ConfigFile> getAll() throws IOException {
        return configProcessor.getConfigFile(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.getName().toLowerCase();
        } else {
            return null;
        }
    }
}