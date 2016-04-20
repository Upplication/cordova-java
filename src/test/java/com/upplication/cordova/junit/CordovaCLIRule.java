package com.upplication.cordova.junit;


import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import com.upplication.cordova.Cordova;
import com.upplication.cordova.CordovaCLI;
import org.junit.rules.ExternalResource;

public class CordovaCLIRule extends ExternalResource {

    private CordovaCLI cordovaCLI;

    public CordovaCLI get() {
        return this.cordovaCLI;
    }

    @Override
    protected void before() throws Throwable {
        if (cordovaCLI == null ){
            Properties props = new Properties();

            try (InputStream streamResources = this.getClass().getResourceAsStream("/cordova.properties")){
                if (streamResources != null) {
                    props.load(streamResources);
                }
            }

            String nodePath = props.getProperty("node_path");
            String cordovaPath = props.getProperty("cordova_path");
            if (nodePath != null && !nodePath.isEmpty() &&
                    cordovaPath != null && !cordovaPath.isEmpty()) {
                cordovaCLI = new Cordova(nodePath, cordovaPath).getCLI();
            } else {
                cordovaCLI = new Cordova().getCLI();
            }
        }
    }

    @Override
    protected void after() {
        cordovaCLI = null;
    }
}