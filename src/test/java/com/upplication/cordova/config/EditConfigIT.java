package com.upplication.cordova.config;


import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.EditConfig;
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

public class EditConfigIT {
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
    public void set_edit_config() throws IOException {
        EditConfig editConfig = EditConfig.create()
                .file("AndroidManifest.xml")
                .target("/manifest/uses-sdk")
                .mode("merge")
                .content("<uses-sdk android:maxSdkVersion=\"23\" android:minSdkVersion=\"16\" android:targetSdkVersion=\"20\"/>");
        cordova.config().editConfig().add(editConfig);

        EditConfig editConfigGet = cordova.config().editConfig().getAll().get(0);
        assertEquals(editConfig.getContent(), editConfigGet.getContent());
        assertEquals(editConfig.getFile(), editConfigGet.getFile());
        assertEquals(editConfig.getMode(), editConfigGet.getMode());
        assertEquals(editConfig.getTarget(), editConfigGet.getTarget());

        cordova.platform().add(Platform.Android);

        Path manifest = new AndroidProject(cordova).getAndroidManifest();

        assertThat(new String(Files.readAllBytes(manifest)), containsString("android:maxSdkVersion=\"23\""));
        assertThat(new String(Files.readAllBytes(manifest)), containsString("android:minSdkVersion=\"16\""));
        assertThat(new String(Files.readAllBytes(manifest)), containsString("android:targetSdkVersion=\"20\""));
    }

}
