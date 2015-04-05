package com.upplication.cordova.config;

import com.upplication.cordova.Icon;
import com.upplication.cordova.Platform;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;

/**
 * icon
 * cordova.config().icon().add("src/img/icon.png");
 * cordova.config().icon().add(Icon.create().src("src/img").height(100).width(100).density("zsdad"));
 * platform icon
 * cordova.config().platform("android").icon().add("src/img/android/icon.png")
 * cordova.config().platform("android").icon().add(Icon.create().src("src/img").density("ldpi"));
 */
public class IconConfig {

    private Platform platform;
    private ConfigProcessor configProcessor;
    private Path configXml;

    public IconConfig(Platform platform, ConfigProcessor configProcessor, Path configXml) {
        this.platform = platform;
        this.configProcessor = configProcessor;
        this.configXml = configXml;
    }

    public void add(String src) throws IOException {
        add(Icon.create().src(src));
    }

    public void add(Icon icon) throws IOException {
        configProcessor.addIcon(configXml, getPlatform(), icon.getSrc(), icon.getWidth(), icon.getHeight(), icon.getDensity());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.name().toLowerCase();
        } else {
            return null;
        }
    }
}