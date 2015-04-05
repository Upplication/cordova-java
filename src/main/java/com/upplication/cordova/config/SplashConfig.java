package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.Splash;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SplashConfig {

    private Platform platform;
    private ConfigProcessor configProcessor;
    private Path configXml;

    public SplashConfig(Path configXml, ConfigProcessor configProcessor, Platform platform) {
        this.configProcessor = configProcessor;
        this.platform = platform;
        this.configXml = configXml;
    }

    public void add(Splash splash) throws IOException {
        this.configProcessor.addSplash(configXml, getPlatform(), splash.getSrc(), splash.getWidth(), splash.getHeight(), splash.getDensity());
    }

    public List<Splash> getAll() throws IOException {
        return configProcessor.getSplashs(configXml, getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.name().toLowerCase();
        } else {
            return null;
        }
    }
}