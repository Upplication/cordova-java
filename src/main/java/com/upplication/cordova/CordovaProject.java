package com.upplication.cordova;

import com.upplication.cordova.util.ConfigTransactionJob;
import com.upplication.cordova.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * A cordova created project
 */
public class CordovaProject {

    private File project;

    private CordovaCommand cordovaCommand;

    private CordovaPlatform cordovaPlatform;
    private CordovaPlugin cordovaPlugin;
    private CordovaConfig cordovaConfig;

    public CordovaProject(File project, Environment environment){
        this.project = project;
        // TODO: https://keyholesoftware.com/2014/02/17/dependency-injection-options-for-java/
        this.cordovaCommand = new CordovaCommand(project, environment);
        this.cordovaPlatform = new CordovaPlatform(cordovaCommand);
        this.cordovaPlugin = new CordovaPlugin(cordovaCommand);
        this.cordovaConfig = new CordovaConfig(getConfigXml(), new ConfigProcessor(getConfigXml()));
    }

    public void build() {
        cordovaCommand.exec("build");
    }

    public void build(Platform platform) {
        cordovaCommand.exec("build", platform.name().toLowerCase());
    }

    public void prepare() {
        cordovaCommand.exec("prepare");
    }

    public void prepare(Platform platform) {
        cordovaCommand.exec("prepare", platform.name().toLowerCase());
    }

    public void compile() {
        cordovaCommand.exec("compile");
    }

    public void compile(Platform platform) {
        cordovaCommand.exec("compile", platform.name().toLowerCase());
    }

    public void emulate(Platform platform) {
        cordovaCommand.exec("emulate", platform.name().toLowerCase());
    }

    public void run(Platform platform) {
        cordovaCommand.exec("run", platform.name().toLowerCase());
    }

    public CordovaPlatform platform() {
       return cordovaPlatform;
    }

    public CordovaPlugin plugin() {
        return cordovaPlugin;
    }

    public CordovaConfig config() {
        return cordovaConfig;
    }

    public void config(ConfigTransactionJob config) throws IOException {
        try (ConfigProcessorTransaction transaction = new ConfigProcessorTransaction(getConfigXml())) {
            config.exec(new CordovaConfig(getConfigXml(), transaction));
        }
    }

    //
    // TODO: create Project class with the access to the resources as Path
    //

    public File getProject(){
        return project;
    }

    private Path getConfigXml() {
        return project.toPath().resolve("config.xml");
    }


}