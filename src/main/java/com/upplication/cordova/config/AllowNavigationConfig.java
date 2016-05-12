package com.upplication.cordova.config;

import com.upplication.cordova.AllowNavigation;
import com.upplication.cordova.util.ConfigProcessor;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * cordova.config().allowNavigation().add("*");
 * cordova.config().allowNavigation().add(AllowNavigation.create().href("*"));
 */
public class AllowNavigationConfig {

    private IConfigProcessor configProcessor;

    public AllowNavigationConfig(IConfigProcessor configProcessor) {
        this.configProcessor = configProcessor;
    }

    public void add(String href) throws IOException {
        add(AllowNavigation.create().href(href));
    }

    public void add(AllowNavigation allowNavigation) throws IOException {
        configProcessor.addAllowNavigation(allowNavigation.getHref());
    }

    public List<AllowNavigation> getAll() throws IOException {
        return configProcessor.getAllowNavigation();
    }
}