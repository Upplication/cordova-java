package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.util.ConfigProcessor;

import java.nio.file.Path;

/**
 * customize platform for the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#platform
 */
public class PlatformConfig {

    private ConfigProcessor configProcessor;
    private Path configXml;

    private IconConfig iconConfig;
    private SplashConfig splashConfig;
    private PreferencesConfig preferencesConfig;

    public PlatformConfig(Platform platform, ConfigProcessor configProcessor, Path configXml) {
        this.configXml = configXml;
        this.configProcessor = configProcessor;
        this.iconConfig = new IconConfig(configXml, configProcessor, platform);
        this.splashConfig =  new SplashConfig(configXml, configProcessor, platform);
        this.preferencesConfig = new PreferencesConfig(configXml, configProcessor, platform);
    }

    public IconConfig icon() {
        return iconConfig;
    }

    public SplashConfig splash() {
        return splashConfig;
    }

    public PreferencesConfig preference() {
        return preferencesConfig;
    }
}