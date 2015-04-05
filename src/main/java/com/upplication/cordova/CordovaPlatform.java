package com.upplication.cordova;


import com.upplication.cordova.util.CordovaCommand;

public class CordovaPlatform {

    private CordovaCommand cordovaCommand;

    public CordovaPlatform(CordovaCommand cordovaCommand){
        this.cordovaCommand = cordovaCommand;
    }

    public void add(Platform platform){
        cordovaCommand.exec("platform", "add", platform.name().toLowerCase());
    }

    public void remove(Platform platform){
        cordovaCommand.exec("platform", "remove", platform.name().toLowerCase());
    }
}
