package com.upplication.cordova.config;

import com.upplication.cordova.Access;
import com.upplication.cordova.util.ConfigProcessor;
import com.upplication.cordova.util.IConfigProcessor;

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

    private IConfigProcessor configProcessor;

    public AccessConfig (IConfigProcessor configProcessor) {
        this.configProcessor = configProcessor;
    }

    public void add(String access) throws IOException {
        add(access, null);
    }

    public void add(String access, Boolean launchExternal) throws IOException {
        add(Access.create().origin(access).launchExternal(launchExternal));
    }

    public void add(Access access) throws IOException {
        configProcessor.addAccess(access.getOrigin(),
                access.getLaunchExternal(), access.getSubdomains());
    }

    public List<Access> getAll() throws IOException {
        return configProcessor.getAccess();
    }
}