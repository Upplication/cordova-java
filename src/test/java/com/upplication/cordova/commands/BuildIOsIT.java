package com.upplication.cordova.commands;


import com.upplication.cordova.*;
import com.upplication.cordova.exception.CordovaCommandException;
import com.upplication.cordova.internal.XCodeProject;
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
import java.nio.file.Files;

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
    public void build_with_default_params_then_create_debug_app_to_run_in_emulator() throws IOException {

        cordovaProject.build();

        // TODO: how to check if the app is in debug mode or not?

        XCodeProject xCodeProject = new XCodeProject(cordovaProject);
        assertTrue(Files.isDirectory(xCodeProject.get()));
        assertTrue(Files.exists(xCodeProject.getApp()));
    }

    @Test
    public void build_with_release_then_create_app_to_run_in_emulator() throws IOException {
        cordovaProject.build(BuildIOsOpts.create().withRelease(true));

        XCodeProject xCodeProject = new XCodeProject(cordovaProject);

        assertTrue(Files.isDirectory(xCodeProject.get()));
        assertTrue(Files.exists(xCodeProject.getApp()));
    }

    @Test(expected = CordovaCommandException.class)
    public void build_with_device_and_no_sign_then_create_debug_app_to_run_in_device() throws IOException {
        cordovaProject.build(BuildIOsOpts.create().withVerbose(true).withNoSign(true).withDevice(true));

        // You must have the "iPhone Developer: xxxxx" imported in your keychain

        // TODO: how to check if the app is in debug mode or not?
        /*
        Path iosFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("ios");
        assertTrue(Files.isDirectory(iosFolder));
        assertTrue(Files.exists(iosFolder.resolve("build").resolve("device").resolve("Hello.app")));
        */
    }

    @Test(expected = CordovaCommandException.class)
    public void build_with_device_no_sign_and_release_then_create_to_run_in_device() throws IOException {
        cordovaProject.build(BuildIOsOpts.create().withNoSign(true).withDevice(true).withRelease(true));

        // You must have the "iPhone Distribution: xxxxx" imported in your keychain

        /*
        Path iosFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("ios");
        assertTrue(Files.isDirectory(iosFolder));
        assertTrue(Files.exists(iosFolder.resolve("build").resolve("device").resolve("Hello.app")));
        */
    }

    @Test(expected = CordovaCommandException.class)
    public void build_with_device_and_release_then_create_ipa_to_run_in_device() throws IOException {
        cordovaProject.build(BuildIOsOpts.create().withDevice(true).withRelease(true));

        // You must have the "iPhone Distribution: xxxxx" imported in your keychain

        /*
        Path iosFolder = cordovaProject.getProject().toPath().resolve("platforms").resolve("ios");
        assertTrue(Files.isDirectory(iosFolder));
        assertTrue(Files.exists(iosFolder.resolve("build").resolve("device").resolve("Hello.ipa")));
        */
    }



    @Test(expected = CordovaCommandException.class)
    public void build_with_device_and_without_code_sign_then_throws_cordova_command_exeption() throws IOException {
        cordovaProject.build(BuildIOsOpts.create().withDevice(true));
    }
}
