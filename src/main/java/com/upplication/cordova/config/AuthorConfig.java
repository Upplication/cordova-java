package com.upplication.cordova.config;

import com.upplication.cordova.util.ConfigProcessor;

import java.io.IOException;
import java.nio.file.Path;

public class AuthorConfig {

    private Path configXml;
    private ConfigProcessor configProcessor;

    public AuthorConfig(Path configXml, ConfigProcessor configProcessor) {
        this.configXml = configXml;
        this.configProcessor = configProcessor;
    }

    public void setName(String name) throws IOException {
        configProcessor.setAuthorName(configXml, name);
    }

    public void setHref(String href) throws IOException {
        configProcessor.setAuthorHref(configXml, href);
    }

    public void setEmail(String email) throws IOException {
        configProcessor.setAuthorEmail(configXml, email);
    }
}