package com.upplication.cordova.config;

import com.upplication.cordova.EditConfig;
import com.upplication.cordova.Platform;
import com.upplication.cordova.Preference;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.util.List;

/**
 * cordova.config().editConfig().add(...);
 * Edit config 'internal' XML files
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#edit-config
 */
public class EditConfigConfig {

    private IConfigProcessor configProcessor;
    private Platform platform;

    public EditConfigConfig(IConfigProcessor configProcessor, Platform platform){
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(EditConfig editConfig) throws IOException {
        this.add(getPlatform(), editConfig.getFile(), editConfig.getTarget(), editConfig.getMode(), editConfig.getContent());
    }

    public void add(String platform, String file, String target, String mode, String content) throws IOException {
        configProcessor.addEditConfig(platform, file, target, mode, content);
    }

    public List<EditConfig> getAll() throws IOException {
        return configProcessor.getEditConfig(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.getName().toLowerCase();
        } else {
            return null;
        }
    }
}