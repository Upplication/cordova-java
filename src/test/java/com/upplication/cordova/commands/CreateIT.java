package com.upplication.cordova.commands;


import com.upplication.cordova.Cordova;
import com.upplication.cordova.CordovaCLI;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.exception.CordovaCommandException;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreateIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();

    private CordovaCLI cordovaCLI;

    @Before
    public void setUp() {
        cordovaCLI = cordovaCLIRule.get();
    }


    @Test
    public void folder_with_space() throws IOException {
        Path projectFolder = Files.createTempDirectory("cordova-temp").resolve("name with spaces");
        CordovaProject project = cordovaCLI.create(projectFolder.toFile(), "com.upplication.test", "Hello");

        assertNotNull(project);
        assertTrue(Files.exists(projectFolder));
        assertTrue(Files.isDirectory(projectFolder));
    }

    @Test
    public void name_with_space() throws IOException {
        Path projectFolder = Files.createTempDirectory("cordova-temp");
        CordovaProject project = cordovaCLI.create(projectFolder.toFile(), "com.upplication.test", "Hello World");

        assertNotNull(project);
        assertTrue(Files.exists(projectFolder));
        assertTrue(Files.isDirectory(projectFolder));
    }

    @Test
    public void name_and_folder_with_space() throws IOException {
        Path projectFolder = Files.createTempDirectory("cordova-temp").resolve("name with spaces");
        CordovaProject project = cordovaCLI.create(projectFolder.toFile(), "com.upplication.test", "Hello World");

        assertNotNull(project);
        assertTrue(Files.exists(projectFolder));
        assertTrue(Files.isDirectory(projectFolder));
    }

    @Test(expected = CordovaCommandException.class)
    public void reverse_name_with_space() throws IOException {
        cordovaCLI.create(Files.createTempDirectory("cordova-temp").toFile(),
                "com upplication test", "Hello World");
    }


}
