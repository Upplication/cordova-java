package com.upplication.cordova;

import com.upplication.cordova.util.CordovaCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CordovaPlugin {

    private CordovaCommand cordovaCommand;

    public CordovaPlugin(CordovaCommand cordovaCommand) {
        this.cordovaCommand = cordovaCommand;
    }

    public void add(String ... plugins) {
        for (String plugin : plugins){
            cordovaCommand.exec("plugin", "add", plugin);
        }
    }

    public void add(File... plugins) {
        for (File plugin : plugins){
            cordovaCommand.exec("plugin", "add", plugin.getAbsolutePath());
        }
    }

    public void remove(String ... plugins) {
        for (String plugin : plugins){
            cordovaCommand.exec("plugin", "rm", plugin);
        }
    }

    public List<Plugin> get() {
        String resultCommand = cordovaCommand.exec("plugin", "ls");
        List<Plugin> results = new ArrayList<>();
        if (!resultCommand.startsWith("No plugins added")) {

            String[] plugins = resultCommand.split("\\n");

            for (String plugin : plugins){

                String[] pluginDetailed = plugin.split(" ");

                Plugin pluginTO = new Plugin();
                pluginTO.setName(pluginDetailed[2].replace("\"", ""));
                pluginTO.setVersion(pluginDetailed[1]);
                pluginTO.setFullName(pluginDetailed[0]);
                results.add(pluginTO);
            }
        }

        return results;
    }
}