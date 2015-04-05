package com.upplication.cordova.util;


/**
 * Environments local vars needed by cordova
 */
public class Environment {

    private String cordovaPath;
    private String nodePath;

    public String getCordovaPath() {
        return cordovaPath;
    }

    public void setCordovaPath(String cordovaPath) {
        this.cordovaPath = cordovaPath;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }
}
