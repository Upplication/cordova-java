package com.upplication.cordova;

import com.upplication.cordova.util.Environment;
import java.io.File;

/**
 * Cordova builder
 */
public class Cordova {

    private Environment environment;

    /**
     * Create an instance of Cordova setting explicit the nodePath and/or the cordovaPath
     *
     * @param nodePath
     * @param cordovaPath
     */
    public Cordova(String nodePath, String cordovaPath){

        assert nodePath != null || cordovaPath != null;

        environment = new Environment();

        if (nodePath != null)
            environment.setNodePath(nodePath);

        if (cordovaPath != null)
            environment.setCordovaPath(cordovaPath);

        assert getCLI().getVersion() != null;
    }

    public Cordova() {
        assert getCLI().getVersion() != null;
    }

    /**
     * @return CordovaCLI
     */
    public CordovaCLI getCLI() {
        return new CordovaCLI(environment);
    }

    public CordovaProject getProject(File path){
        return new CordovaProject(path, environment);
    }
}
