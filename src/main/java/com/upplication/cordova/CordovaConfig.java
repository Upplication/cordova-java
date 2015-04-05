package com.upplication.cordova;

import com.upplication.cordova.config.*;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class CordovaConfig {

    private File project;
    private ConfigProcessor configProcessor;
    private AccessConfig accessConfig;
    private PreferencesConfig preferencesConfig;
    private AuthorConfig authorConfig;

    private IconConfig iconConfigBase;

    public CordovaConfig(File project){
        this.project = project;
        // TODO: https://keyholesoftware.com/2014/02/17/dependency-injection-options-for-java/
        this.configProcessor = new ConfigProcessor();
        this.accessConfig = new AccessConfig(getConfigXml(), configProcessor);
        this.preferencesConfig = new PreferencesConfig(getConfigXml(), configProcessor);
        this.authorConfig = new AuthorConfig(getConfigXml(), configProcessor);
        this.iconConfigBase = new IconConfig(null, configProcessor, getConfigXml());
    }

    public void setName(String name) throws IOException {
       configProcessor.setName(getConfigXml(), name);
    }

    public void setVersion(int mandatory, int minor, int revision) throws IOException {
        configProcessor.setVersion(getConfigXml(), mandatory + "." + minor + "." + revision);
    }

    public AuthorConfig author() {
        return authorConfig;
    }

    public AccessConfig access() {
        return accessConfig;
    }

    public PreferencesConfig preferences() {
        return preferencesConfig;
    }

    public IconConfig icon(){
        return iconConfigBase;
    }

    public PlatformConfig platform(Platform platform){
        return new PlatformConfig(platform, configProcessor, getConfigXml());
    }

    private Path getConfigXml(){
        return project.toPath().resolve("config.xml");
    }


}
