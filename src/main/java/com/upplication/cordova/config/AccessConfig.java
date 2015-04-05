package com.upplication.cordova.config;

import com.upplication.cordova.Access;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * cordova.config().access().add("*");
 * with launch-external
 * cordova.config().access().add("*", "yes");
 * cordova.config().access().add(Access.create().origin("*").subdomains(true));
 */
public class AccessConfig {

    private ConfigProcessor configProcessor;
    private Path configXml;

    public AccessConfig (Path configXml, ConfigProcessor configProcessor) {
        this.configXml = configXml;
        this.configProcessor = configProcessor;
    }

    public void add(String access) throws IOException {
        add(access, null);
    }

    public void add(String access, Boolean launchExternal) throws IOException {
        add(Access.create().origin(access).launchExternal(launchExternal));
    }

    public void add(Access access) throws IOException {
        configProcessor.addAccess(configXml, access.getOrigin(),
                access.getLaunchExternal(), access.getSubdomains());
    }

    public List<Access> getAll() throws IOException {
        return configProcessor.getAccess(configXml);
    }
}