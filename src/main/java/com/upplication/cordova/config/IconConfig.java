package com.upplication.cordova.config;

import com.upplication.cordova.Icon;
import com.upplication.cordova.Platform;
import com.upplication.cordova.util.ConfigProcessor;
import com.upplication.cordova.util.IConfigProcessor;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
    private IConfigProcessor configProcessor;

    public IconConfig(IConfigProcessor configProcessor, Platform platform) {
        this.platform = platform;
        this.configProcessor = configProcessor;
    }

    public void add(String src) throws IOException {
        add(Icon.create().src(src));
    }

    public void add(Icon icon) throws IOException {
        configProcessor.addIcon(getPlatform(), icon.getSrc(), icon.getWidth(), icon.getHeight(), icon.getDensity());
    }

    public List<Icon> getAll() throws IOException {
        return configProcessor.getIcons(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.getName().toLowerCase();
        } else {
            return null;
        }
    }
}