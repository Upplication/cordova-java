package com.upplication.cordova.internal;

import com.upplication.cordova.CordovaProject;

import java.io.IOException;
import java.nio.file.Path;

/**
 * XcodeProject structure to easy testing the internal modifications
 * of cordova.
 *
 * for internal usage only
 */
public class XCodeProject {

    private CordovaProject cordovaProject;

    public XCodeProject(CordovaProject cordova) {
        this.cordovaProject = cordova;
    }

    public Path get() {
        return cordovaProject.getProject().toPath()
                .resolve("platforms/ios/");
    }

    public Path getInfoPlist() throws IOException {
        String projectName = cordovaProject.config().getName();
        return get().resolve(projectName + "/" + projectName + "-Info.plist");
    }


    public Path getIconsFolder() throws IOException {
        String projectName = cordovaProject.config().getName();
        return get().resolve(projectName + "/Images.xcassets/AppIcon.appiconset");
    }

    public Path getIcon(String iconName) throws IOException {
        return getIconsFolder().resolve(iconName);
    }

    public Path getApp() throws IOException {
        String projectName = cordovaProject.config().getName();
        return get().resolve("build").resolve("emulator").resolve(projectName + ".app");
    }
}
