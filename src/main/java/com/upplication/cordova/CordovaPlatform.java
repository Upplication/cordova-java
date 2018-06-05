package com.upplication.cordova;


import com.upplication.cordova.util.CordovaCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * cordova platform commands
 */
public class CordovaPlatform {

    private CordovaCommand cordovaCommand;

    public CordovaPlatform(CordovaCommand cordovaCommand){
        this.cordovaCommand = cordovaCommand;
    }

    public PlatformResume list() {
        String result = cordovaCommand.exec("platform", "ls");
        List<Platform> installeds = new ArrayList<>();
        List<Platform> availables = new ArrayList<>();

        String[] twoParts = result.split("Available platforms:");

        for (String avaiableString : twoParts[1].split("\\n  ")) {

            if (!avaiableString.trim().isEmpty()) {
                Platform available = Platform.build(avaiableString.split(" ")[0]);
                availables.add(available);
            }
        }
        String[] installedsString = twoParts[0].split("\\n  ");
        for (int i=1; i< installedsString.length; i++) {
            String installedString = installedsString[i];
            if (!installedString.trim().isEmpty()) {
                Platform installed = Platform.build(installedString.split(" ")[0]);
                installeds.add(installed);
            }
        }

        return new PlatformResume(installeds, availables);
    }

    public void add(Platform platform){
        String command = platform.getName().toLowerCase() + (platform.getVersion() != null ? "@" + platform.getVersion() : "");
        this.add(command);
    }

    public void add(String platform){
        cordovaCommand.exec("platform", "add", platform);
    }

    public void remove(Platform platform){
        cordovaCommand.exec("platform", "remove", platform.getName().toLowerCase());
    }
}
