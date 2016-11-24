package com.upplication.cordova.commands;


import com.upplication.cordova.*;
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

import static com.upplication.cordova.BuildAndroidOpts.GArg.buildMultipleApk;
import static org.junit.Assert.assertTrue;

@Condition(OnlyMacOSX.class)
public class BuildIOsIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    @Rule
    public ConditionRule rule = new ConditionRule();

    private CordovaProject cordovaProject;

    @Before
    public void setUp() throws IOException {
        CordovaCLI cordovaCLI = cordovaCLIRule.get();
        File projectFolder = folder.newFolder("ios-project");
        cordovaProject = cordovaCLI.create(projectFolder, "com.upplication.test", "Hello");
        cordovaProject.platform().add(Platform.IOs);
    }

    @Test
    public void build_default() throws IOException {

        cordovaProject.build();

        Path iosFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("ios");
        assertTrue(Files.isDirectory(iosFolder));
        assertTrue(Files.exists(iosFolder.resolve("build").resolve("hello").resolve("Hello.build")));
    }

    @Test
    public void build_release_with_no_sign() throws IOException {
        cordovaProject.build(BuildIOsOpts.create()
                .withNoSign(true)
                .withRelease(true)
                .withDevice(true));

        Path iosFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("ios");
        assertTrue(Files.isDirectory(iosFolder));
        assertTrue(Files.exists(iosFolder.resolve("build").resolve("device").resolve("Hello.ipa")));
    }


    // TODO:
}
