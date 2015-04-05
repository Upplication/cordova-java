package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.Preference;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * cordova.config().preferences().add("name", "value");
 */
public class PreferencesConfig {

    private ConfigProcessor configProcessor;
    private Platform platform;
    private Path configXml;

    public PreferencesConfig(Path configXml, ConfigProcessor configProcessor, Platform platform){
        this.configXml = configXml;
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(String name, String value) throws IOException {
        configProcessor.addPreference(configXml, getPlatform(), name, value);
    }

    public List<Preference> getAll() throws IOException {
        return configProcessor.getPreferences(configXml, getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.name().toLowerCase();
        } else {
            return null;
        }
    }
}