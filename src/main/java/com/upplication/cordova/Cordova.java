package com.upplication.cordova;

import com.upplication.cordova.util.Environment;
import java.io.File;

public class Cordova {

    private Environment environment;

    public Cordova(String nodePath, String cordovaPath){
        // try to  check cordova are installed
        // get the local path
        // testear en diferentes environments
        String paths = System.getenv().get("PATH");
        System.out.println(paths);
        // find node path
        File file = null;
        for (String path : paths.split(":")){
            file = new File(path + "/node");
            if (file.exists()){
                break;
            }
        }

        environment = new Environment();
        environment.setCordovaPath(cordovaPath);
        environment.setNodePath(nodePath);
    }

    /**
     * @throws IllegalStateException if cordova are not installed
     * @return CordovaCLI
     */
    public CordovaCLI getCLI() {
        return new CordovaCLI(environment);
    }

    public CordovaProject getProject(File path){

        return new CordovaProject(path, environment);
    }
}
