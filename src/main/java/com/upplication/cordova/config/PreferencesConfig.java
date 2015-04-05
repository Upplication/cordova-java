package com.upplication.cordova.config;

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
    private Path configXml;

    public PreferencesConfig(Path configXml, ConfigProcessor configProcessor){
        this.configXml = configXml;
        this.configProcessor = configProcessor;
    }

    public void add(String name, String value) throws IOException {
        configProcessor.addPreference(configXml, name, value);
    }

    public List<Preference> getAll() throws IOException {
        return configProcessor.getPreferences(configXml);
    }
}