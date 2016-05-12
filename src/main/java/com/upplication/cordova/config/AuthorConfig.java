package com.upplication.cordova.config;

import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;

/**
 * Customize author in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#author
 */
public class AuthorConfig {

    private IConfigProcessor configProcessor;

    public AuthorConfig(IConfigProcessor configProcessor) {
        this.configProcessor = configProcessor;
    }

    public void setName(String name) throws IOException {
        configProcessor.setAuthorName(name);
    }

    public String getName() throws IOException {
        return configProcessor.getAuthorName();
    }

    public void setHref(String href) throws IOException {
        configProcessor.setAuthorHref(href);
    }

    public String getHref() throws IOException {
        return configProcessor.getAuthorHref();
    }

    public void setEmail(String email) throws IOException {
        configProcessor.setAuthorEmail(email);
    }

    public String getEmail() throws IOException {
        return configProcessor.getAuthorEmail();
    }
}