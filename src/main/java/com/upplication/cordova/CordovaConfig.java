package com.upplication.cordova;

import com.upplication.cordova.config.*;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Cordova config.xml API
 */
public class CordovaConfig {

    private Path configXml;
    private IConfigProcessor configProcessor;
    private AccessConfig accessConfig;
    private AllowNavigationConfig allowNavigationConfig;
    private PreferencesConfig preferencesConfig;
    private FeaturesConfig featuresConfig;
    private AuthorConfig authorConfig;
    private EditConfigConfig editConfigConfig;
    private ConfigFileConfig configFileConfig;

    private IconConfig iconConfigBase;

    public CordovaConfig(Path configXml, IConfigProcessor configProcessor){
        this.configXml = configXml;
        // TODO: https://keyholesoftware.com/2014/02/17/dependency-injection-options-for-java/
        // TODO: http://stackoverflow.com/questions/22184736/dependency-injection-using-guice-for-a-client-sdk-library-design-pattern
        this.configProcessor = configProcessor;
        this.accessConfig = new AccessConfig(configProcessor);
        this.allowNavigationConfig = new AllowNavigationConfig(configProcessor);
        this.preferencesConfig = new PreferencesConfig(configProcessor, null);
        this.featuresConfig = new FeaturesConfig(configProcessor, null);
        this.authorConfig = new AuthorConfig(configProcessor);
        this.iconConfigBase = new IconConfig(configProcessor, null);
        this.editConfigConfig = new EditConfigConfig(configProcessor, null);
        this.configFileConfig = new ConfigFileConfig(configProcessor, null);
    }

    public void setName(String name) throws IOException {
       configProcessor.setName(name);
    }

    public String getName() throws IOException {
        return configProcessor.getName();
    }

    public void setVersion(int mandatory, int minor, int revision) throws IOException {
        String version = mandatory + "." + minor + "." + revision;
        setVersion(Version.create().version(version));
    }

    public void setVersion(Version version) throws IOException {
        configProcessor.setVersion(version.getVersion(), version.getIosCfBundleVersion(), version.getAndroidVersionCode());
    }

    public Version getVersion() throws IOException {
        return configProcessor.getVersion();
    }

    public void setDescription(String description) throws IOException {
        configProcessor.setDescription(description);
    }

    public String getDescription() throws IOException {
        return configProcessor.getDescription();
    }

    public void add(String xml) throws IOException {
        configProcessor.add(xml);
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

    public FeaturesConfig features() {
        return featuresConfig;
    }

    public IconConfig icon(){
        return iconConfigBase;
    }

    public EditConfigConfig editConfig(){
        return editConfigConfig;
    }

    public ConfigFileConfig configFile(){
        return configFileConfig;
    }

    public PlatformConfig platform(Platform platform){
        return new PlatformConfig(platform, configProcessor);
    }

    public File getConfigXml(){
        return configXml.toFile();
    }

}
