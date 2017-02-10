package com.upplication.cordova.commands;


import com.upplication.cordova.BuildAndroidOpts;
import static com.upplication.cordova.BuildAndroidOpts.GArg.*;
import com.upplication.cordova.CordovaCLI;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.exception.CordovaCommandException;
import com.upplication.cordova.junit.Condition;
import com.upplication.cordova.junit.ConditionRule;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.junit.OnlyMacOSX;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

        Path androidFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("android");
        assertTrue(Files.isDirectory(androidFolder));
        assertTrue(Files.exists(androidFolder.resolve("build").resolve("outputs").resolve("apk").resolve("android-debug.apk")));
    }

    @Test
    public void build_release() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withRelease(true));

        Path androidFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("android");
        assertTrue(Files.isDirectory(androidFolder));
        assertTrue(Files.exists(androidFolder.resolve("build").resolve("outputs").resolve("apk").resolve("android-release-unsigned.apk")));
    }

    @Test
    public void build_device() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withDevice(true));

        Path androidFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("android");
        assertTrue(Files.isDirectory(androidFolder));
        assertTrue(Files.exists(androidFolder.resolve("build").resolve("outputs").resolve("apk").resolve("android-debug.apk")));
    }

    @Test
    public void build_change_version() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withVersionCode(10));

        Path androidManifestCompiled = cordovaProject.getProject().toPath().resolve("platforms/android/build/intermediates/manifests/full/debug/AndroidManifest.xml");
        assertTrue(Files.exists(androidManifestCompiled));
        assertTrue(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8).contains("android:versionCode=\"10\""));
    }

    @Test
    public void build_change_version_negative_then_save_10000() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withVersionCode(-10));

        Path androidManifestCompiled = cordovaProject.getProject().toPath().resolve("platforms/android/build/intermediates/manifests/full/debug/AndroidManifest.xml");
        assertTrue(Files.exists(androidManifestCompiled));
        assertTrue(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8).contains("android:versionCode=\"10000\""));
    }

    @Test
    public void build_change_minSdkVersion() throws IOException {

        cordovaProject.build(BuildAndroidOpts.create().withMinSdkVersion(15));

        Path androidManifestCompiled = cordovaProject.getProject().toPath().resolve("platforms/android/build/intermediates/manifests/full/debug/AndroidManifest.xml");
        assertTrue(Files.exists(androidManifestCompiled));
        assertTrue(new String(Files.readAllBytes(androidManifestCompiled), StandardCharsets.UTF_8).contains("android:minSdkVersion=\"15\""));
    }

    @Test(expected = CordovaCommandException.class)
    public void build_change_minSdkVersion_cannot_be_smaller_than_version_14_declared_in_library() throws IOException {
        cordovaProject.build(BuildAndroidOpts.create().withMinSdkVersion(2));
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
