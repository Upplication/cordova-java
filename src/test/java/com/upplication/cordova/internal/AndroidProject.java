package com.upplication.cordova.internal;

import com.upplication.cordova.CordovaProject;

import java.io.File;
import java.nio.file.Path;

/**
 * AndroidProject structure to easy testing the internal modifications
 * of cordova.
 *
 * For internal usage only
 */
public class AndroidProject {

    private CordovaProject cordovaProject;

    public AndroidProject(CordovaProject cordovaProject) {
        this.cordovaProject = cordovaProject;
    }

    public Path getAndroidManifest() {
        return get().resolve("app/src/main/AndroidManifest.xml");
    }

    public Path getIcon(String density) {
        return get()
                .resolve("app/src/main/res/mipmap-" + density)
                .resolve("icon.png");
    }

    public Path get() {
        return cordovaProject.getProject().toPath().resolve("platforms/android");
    }

    public Path getApkDebug() {
        return get().resolve("app/build/outputs/apk/debug/app-debug.apk");
    }

    public Path getApkReleaseUnsigned() {
        return get().resolve("app/build/outputs/apk/release/app-release-unsigned.apk");
    }
}
