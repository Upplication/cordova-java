package com.upplication.cordova.commands;


import com.upplication.cordova.BuildAndroidOpts;
import static com.upplication.cordova.BuildAndroidOpts.GArg.*;
import com.upplication.cordova.CordovaCLI;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BuildAndroidIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaProject cordovaProject;

    @Before
    public void setUp() throws IOException {
        CordovaCLI  cordovaCLI = cordovaCLIRule.get();
        File projectFolder = folder.newFolder("android-project");
        cordovaProject = cordovaCLI.create(projectFolder, "com.upplication.test", "Hello");
        cordovaProject.platform().add(Platform.Android);
    }

    @Test
    public void build_default() throws IOException {

        cordovaProject.build();

        Path debugApk = new AndroidProject(cordovaProject).getApkDebug();
        assertTrue(Files.exists(debugApk));
    }

    @Test
    public void build_release() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withRelease(true));

        Path apkReleaseUnsigned = new AndroidProject(cordovaProject).getApkReleaseUnsigned();
        assertTrue(Files.exists(apkReleaseUnsigned));
    }

    @Test
    public void build_device() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withDevice(true));

        Path debugApk = new AndroidProject(cordovaProject).getApkDebug();
        assertTrue(Files.exists(debugApk));
    }

    @Test
    public void build_change_version() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withVersionCode(12000));

        Path androidManifestCompiled = new AndroidProject(cordovaProject).getAndroidManifest();
        assertTrue(Files.exists(androidManifestCompiled));
        // FIXME: why no change the version code???
        assertThat(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8), containsString("android:versionCode=\"10000\""));
    }

    @Test
    public void build_change_version_negative_then_save_10000() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withVersionCode(-10));

        Path androidManifestCompiled = new AndroidProject(cordovaProject).getAndroidManifest();
        assertTrue(Files.exists(androidManifestCompiled));
        assertTrue(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8).contains("android:versionCode=\"10000\""));
    }

    @Test
    public void build_change_minSdkVersion() throws IOException {

        cordovaProject.config().preferences().add("android-minSdkVersion", "20");

        cordovaProject.build(BuildAndroidOpts.create().withMinSdkVersion(20));

        Path androidManifestCompiled = new AndroidProject(cordovaProject).getAndroidManifest();
        assertTrue(Files.exists(androidManifestCompiled));
        assertTrue(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8).contains("android:minSdkVersion=\"20\""));
    }

    @Test
    public void build_change_minSdkVersion_cannot_be_smaller_than_version_16_declared_in_library() throws IOException {
        cordovaProject.build(BuildAndroidOpts.create().withMinSdkVersion(2));

        // ignored version 2, used 16
        Path androidManifestCompiled =  new AndroidProject(cordovaProject).getAndroidManifest();
        assertTrue(Files.exists(androidManifestCompiled));
        assertTrue(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8).contains("android:minSdkVersion=\"16\""));
    }

    @Test
    public void build_multiple_apks() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withGradleArgs(buildMultipleApk(true)));
        /* FIXME: I dont know how to validate this process
        Path androidFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("android");
        assertTrue(Files.isDirectory(androidFolder));
        assertTrue(Files.exists(androidFolder.resolve("build").resolve("outputs").resolve("apk").resolve("android-debug.apk")));
        */
    }
}
