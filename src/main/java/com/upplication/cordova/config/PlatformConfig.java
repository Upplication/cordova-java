package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.util.ConfigProcessor;

import java.nio.file.Path;

public class PlatformConfig {

    private ConfigProcessor configProcessor;
    private Path configXml;

    private IconConfig iconConfig;
    private SplashConfig splashConfig;

    public PlatformConfig(Platform platform, ConfigProcessor configProcessor, Path configXml) {
        this.configXml = configXml;
        this.configProcessor = configProcessor;
        this.iconConfig = new IconConfig(platform, configProcessor, configXml);
        this.splashConfig =  new SplashConfig(platform, configProcessor, configXml);
    }

    public IconConfig icon() {
        return iconConfig;
    }

    public SplashConfig splash() {
        return splashConfig;
    }
}