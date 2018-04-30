package com.upplication.cordova.commands;

import com.sun.media.sound.StandardMidiFileWriter;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.Plugin;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PluginIT {
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
    public void list_empty_plugins() {
        List<Plugin> plugins = cordova.plugin().get();

        assertTrue(plugins.isEmpty());
    }

    @Test
    public void add_plugin_and_list() {
        cordova.plugin().add("cordova-plugin-geolocation");
        List<Plugin> plugins = cordova.plugin().get();

        assertTrue(!plugins.isEmpty());
        assertTrue(plugins.size() == 1);
        assertEquals("cordova-plugin-geolocation", plugins.get(0).getFullName());
        assertEquals("Geolocation", plugins.get(0).getName());
    }

    @Test
    public void list_two_plugins() {
        cordova.plugin().add("cordova-plugin-geolocation");
        cordova.plugin().add("cordova-plugin-console");
        List<Plugin> plugins = cordova.plugin().get();

        assertTrue(!plugins.isEmpty());
        assertTrue(plugins.size() == 2);

        assertEquals("cordova-plugin-console", plugins.get(0).getFullName());
        assertEquals("Console", plugins.get(0).getName());

        assertEquals("cordova-plugin-geolocation", plugins.get(1).getFullName());
        assertEquals("Geolocation", plugins.get(1).getName());
    }

    @Test
    public void remove_plugin() {
        cordova.plugin().add("cordova-plugin-geolocation");
        cordova.plugin().remove("cordova-plugin-geolocation");
        List<Plugin> plugins = cordova.plugin().get();

        assertTrue(plugins.isEmpty());
    }

    @Test
    public void remove_one_plugin() {
        cordova.plugin().add("cordova-plugin-geolocation");
        cordova.plugin().add("cordova-plugin-console");
        cordova.plugin().remove("cordova-plugin-geolocation");
        List<Plugin> plugins = cordova.plugin().get();

        assertTrue(!plugins.isEmpty());
        assertEquals(1, plugins.size());
        assertEquals("cordova-plugin-console", plugins.get(0).getFullName());
    }

    @Test
    public void add_plugin_with_hooks() throws IOException {

        Path sampleGoogleServices = Paths.get("src/test/resources/sample-google-services.json");
        Files.copy(sampleGoogleServices, cordova.getProject().toPath().resolve("google-services.json"),
                StandardCopyOption.REPLACE_EXISTING);

        cordova.plugin().add("cordova-plugin-firebase@1.0.*");

        Path googleServices = new AndroidProject(cordova).get().resolve("google-services.json");

        assertThat(Files.exists(googleServices), is(false));

        cordova.platform().add(Platform.Android);
        // check hook after_prepare copy
        assertThat(Files.exists(googleServices), is(true));
        assertThat(Files.readAllBytes(googleServices), is(Files.readAllBytes(sampleGoogleServices)));
    }

}
