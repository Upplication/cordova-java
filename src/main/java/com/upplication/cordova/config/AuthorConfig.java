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

    public String getName() throws IOException {
        return configProcessor.getAuthorName(configXml);
    }

    public void setHref(String href) throws IOException {
        configProcessor.setAuthorHref(configXml, href);
    }

    public String getHref() throws IOException {
        return configProcessor.getAuthorHref(configXml);
    }

    public void setEmail(String email) throws IOException {
        configProcessor.setAuthorEmail(configXml, email);
    }

    public String getEmail() throws IOException {
        return configProcessor.getAuthorEmail(configXml);
    }
}