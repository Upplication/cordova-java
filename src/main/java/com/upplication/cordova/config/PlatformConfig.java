package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.util.ConfigProcessor;
import com.upplication.cordova.util.IConfigProcessor;

import java.nio.file.Path;

/**
 * customize platform for the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#platform
 */
public class PlatformConfig {

    private IconConfig iconConfig;
    private SplashConfig splashConfig;
    private PreferencesConfig preferencesConfig;

    public PlatformConfig(Platform platform, IConfigProcessor configProcessor) {
        this.iconConfig = new IconConfig(configProcessor, platform);
        this.splashConfig =  new SplashConfig(configProcessor, platform);
        this.preferencesConfig = new PreferencesConfig(configProcessor, platform);
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