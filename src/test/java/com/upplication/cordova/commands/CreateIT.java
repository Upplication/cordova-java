package com.upplication.cordova.commands;


import com.upplication.cordova.CordovaCLI;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.exception.CordovaCommandException;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreateIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaCLI cordovaCLI;

    @Before
    public void setUp() {
        cordovaCLI = cordovaCLIRule.get();
    }


    @Test
    public void folder_with_space() throws IOException {
        File projectFolder = folder.newFolder("name with spaces");
        CordovaProject project = cordovaCLI.create(projectFolder, "com.upplication.test", "Hello");

        assertNotNull(project);
        assertTrue(Files.exists(projectFolder.toPath()));
        assertTrue(Files.isDirectory(projectFolder.toPath()));
    }

    @Test
    public void name_with_space() throws IOException {
        File projectFolder = folder.newFolder("cordova-temp");
        CordovaProject project = cordovaCLI.create(projectFolder, "com.upplication.test", "Hello World");

        assertNotNull(project);
        assertTrue(Files.exists(projectFolder.toPath()));
        assertTrue(Files.isDirectory(projectFolder.toPath()));
    }

    @Test
    public void name_and_folder_with_space() throws IOException {
        File projectFolder = folder.newFolder("name with spaces");
        CordovaProject project = cordovaCLI.create(projectFolder, "com.upplication.test", "Hello World");

        assertNotNull(project);
        assertTrue(Files.exists(projectFolder.toPath()));
        assertTrue(Files.isDirectory(projectFolder.toPath()));
    }

    @Test(expected = CordovaCommandException.class)
    public void reverse_name_with_space() throws IOException {
        cordovaCLI.create(Files.createTempDirectory("cordova-temp").toFile(),
                "com upplication test", "Hello World");
    }


}
