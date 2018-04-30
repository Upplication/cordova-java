package com.upplication.cordova.config;

import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AllowNavigationIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaProject cordova;

    @Before
    public void setUp() throws IOException {
        cordova = cordovaCLIRule.get().create(folder.newFolder("cordova-path"));
    }


    @Test
    public void set_allowNavigation() throws IOException {

        cordova.config().allowNavigation().add("*");

        assertEquals("*", cordova.config().allowNavigation().getAll().get(0).getHref());
        assertThat(new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml"))), containsString("<allow-navigation href=\"*\"/></widget>"));
    }
}
