package com.upplication.cordova.config;

import com.upplication.cordova.Platform;
import com.upplication.cordova.Splash;
import com.upplication.cordova.util.ConfigProcessor;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * customize splash for the config.xml
 * cordova-splash-plugin is needed
 * https://cordova.apache.org/docs/en/latest/reference/cordova-plugin-splashscreen/
 */
public class SplashConfig {

    private Platform platform;
    private IConfigProcessor configProcessor;

    public SplashConfig(IConfigProcessor configProcessor, Platform platform) {
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(Splash splash) throws IOException {
        this.configProcessor.addSplash(getPlatform(), splash.getSrc(), splash.getWidth(), splash.getHeight(), splash.getDensity());
    }

    public List<Splash> getAll() throws IOException {
        return configProcessor.getSplashs(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.name().toLowerCase();
        } else {
            return null;
        }
    }
}