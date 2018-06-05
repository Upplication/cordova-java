package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.Preference;
import com.upplication.cordova.util.ConfigProcessor;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * cordova.config().preferences().add("name", "value");
 */
public class PreferencesConfig {

    private IConfigProcessor configProcessor;
    private Platform platform;

    public PreferencesConfig(IConfigProcessor configProcessor, Platform platform){
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(String name, String value) throws IOException {
        configProcessor.addPreference(getPlatform(), name, value);
    }

    public List<Preference> getAll() throws IOException {
        return configProcessor.getPreferences(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.getName().toLowerCase();
        } else {
            return null;
        }
    }
}