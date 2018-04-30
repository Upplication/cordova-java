package com.upplication.cordova.config;


import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PreferencesIT {
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
    public void set_android_minSdkVersion_specific_preference() throws IOException {
        String prefName = "android-minSdkVersion";
        String prefValue = "20";
        cordova.config().preferences().add(prefName, prefValue);

        assertEquals(prefName, cordova.config().preferences().getAll().get(0).getName());
        assertEquals(prefValue, cordova.config().preferences().getAll().get(0).getValue());
        assertThat(new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml"))), containsString("<preference name=\"" + prefName + "\" value=\"" + prefValue + "\"/>"));

        cordova.platform().add(Platform.Android);

        Path manifest = new AndroidProject(cordova).getAndroidManifest();

        assertThat(new String(Files.readAllBytes(manifest)), containsString("android:minSdkVersion=\"" + prefValue + "\""));
    }

    @Test
    public void set_android_maxSdkVersion_specific_preference() throws IOException {
        String prefName = "android-maxSdkVersion";
        String prefValue = "25";
        cordova.config().preferences().add(prefName, prefValue);

        assertEquals(prefName, cordova.config().preferences().getAll().get(0).getName());
        assertEquals(prefValue, cordova.config().preferences().getAll().get(0).getValue());
        assertThat(new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml"))), containsString("<preference name=\"" + prefName + "\" value=\"" + prefValue + "\"/>"));

        cordova.platform().add(Platform.Android);

        Path manifest = new AndroidProject(cordova).getAndroidManifest();

        assertThat(new String(Files.readAllBytes(manifest)), containsString("android:maxSdkVersion=\"" + prefValue + "\""));
    }
}
