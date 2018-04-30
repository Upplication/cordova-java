package com.upplication.cordova.config;

import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.Version;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.internal.XCodeProject;
import com.upplication.cordova.junit.Condition;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.junit.OnlyMacOSX;
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

public class VersionIT {

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
    public void set_version_android() throws IOException {
        String version = "3.2.1";
        cordova.config().setVersion(Version.create().version(version));

        assertEquals(version, cordova.config().getVersion().getVersion());
        assertThat(new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml"))), containsString("id=\"io.cordova.hellocordova\" version=\"" + version + "\""));

        cordova.platform().add(Platform.Android);

        Path manifest = new AndroidProject(cordova).getAndroidManifest();
        String contentManifest = new String(Files.readAllBytes(manifest));
        assertThat(contentManifest, containsString("android:versionName=\"" + version + "\""));
        assertThat(contentManifest, containsString("android:versionCode=\"30201\""));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void set_version_ios() throws IOException {
        String version = "3.2.1";
        cordova.config().setVersion(Version.create().version(version));

        assertEquals(version, cordova.config().getVersion().getVersion());
        assertThat(new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml"))), containsString("id=\"io.cordova.hellocordova\" version=\"" + version + "\""));

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);
        Path manifest = xCodeProject.getInfoPlist();
        assertThat(new String(Files.readAllBytes(manifest)),
                containsString(
                        "    <key>CFBundleShortVersionString</key>\n" +
                        "    <string>3.2.1</string>"
                ));
    }
}
