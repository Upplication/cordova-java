package com.upplication.cordova;

import com.upplication.cordova.config.*;
import com.upplication.cordova.util.ConfigProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Cordova config.xml API
 */
public class CordovaConfig {

    private File project;
    private ConfigProcessor configProcessor;
    private AccessConfig accessConfig;
    private AllowNavigationConfig allowNavigationConfig;
    private PreferencesConfig preferencesConfig;
    private AuthorConfig authorConfig;

    private IconConfig iconConfigBase;

    public CordovaConfig(File project){
        this.project = project;
        // TODO: https://keyholesoftware.com/2014/02/17/dependency-injection-options-for-java/
        // TODO: http://stackoverflow.com/questions/22184736/dependency-injection-using-guice-for-a-client-sdk-library-design-pattern
        this.configProcessor = new ConfigProcessor();
        this.accessConfig = new AccessConfig(getConfigXml(), configProcessor);
        this.allowNavigationConfig = new AllowNavigationConfig(getConfigXml(), configProcessor);
        this.preferencesConfig = new PreferencesConfig(getConfigXml(), configProcessor, null);
        this.authorConfig = new AuthorConfig(getConfigXml(), configProcessor);
        this.iconConfigBase = new IconConfig(getConfigXml(), configProcessor, null);
    }

    public void setName(String name) throws IOException {
       configProcessor.setName(getConfigXml(), name);
    }

    public String getName() throws IOException {
        return configProcessor.getName(getConfigXml());
    }

    public void setVersion(int mandatory, int minor, int revision) throws IOException {
        String version = mandatory + "." + minor + "." + revision;
        setVersion(Version.create().version(version));
    }

    public void setVersion(Version version) throws IOException {
        configProcessor.setVersion(getConfigXml(), version.getVersion(), version.getIosCfBundleVersion(), version.getAndroidVersionCode());
    }

    public Version getVersion() throws IOException {
        return configProcessor.getVersion(getConfigXml());
    }

    public void setDescription(String description) throws IOException {
        configProcessor.setDescription(getConfigXml(), description);
    }

    public String getDescription() throws IOException {
        return configProcessor.getDescription(getConfigXml());
    }

    public AuthorConfig author() {
        return authorConfig;
    }

    public AccessConfig access() {
        return accessConfig;
    }

    public AllowNavigationConfig allowNavigation() {
        return allowNavigationConfig;
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
