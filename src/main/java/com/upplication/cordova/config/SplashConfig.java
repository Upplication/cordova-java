package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.Splash;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;

public class SplashConfig {

    private Platform platform;
    private ConfigProcessor configProcessor;
    private Path configXml;

    public SplashConfig(Platform platform, ConfigProcessor configProcessor, Path configXml) {
        this.configProcessor = configProcessor;
        this.platform = platform;
        this.configXml = configXml;
    }

    public void add(Splash splash) throws IOException {
        this.configProcessor.addSplash(configXml, getPlatform(), splash.getSrc(), splash.getWidth(), splash.getHeight(), splash.getDensity());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.name().toLowerCase();
        } else {
            return null;
        }
    }
}