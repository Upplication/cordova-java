package com.upplication.cordova;

import com.upplication.cordova.util.Environment;
import java.io.File;

public class Cordova {

    private Environment environment;

    public Cordova(String nodePath, String cordovaPath){

        environment = new Environment();
        environment.setCordovaPath(cordovaPath);
        environment.setNodePath(nodePath);

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
