package com.upplication.cordova.config;


import com.upplication.cordova.ConfigFile;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
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

public class ConfigFileIT {
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
    public void set_config_file_android() throws IOException {
        ConfigFile configFile = ConfigFile.create()
                .target("AndroidManifest.xml")
                .parent("/manifest/application")
                .content("<activity android:label=\"@string/app_name\" android:name=\"com.foo.Foo\">\n" +
                        "        <intent-filter/>\n" +
                        "    </activity>");
        cordova.config().configFile().add(configFile);

        ConfigFile configFileGet = cordova.config().configFile().getAll().get(0);
        assertEquals(configFile.getContent(), configFileGet.getContent());
        assertEquals(configFile.getTarget(), configFileGet.getTarget());
        assertEquals(configFile.getParent(), configFileGet.getParent());
        assertEquals(configFile.getAfter(), configFileGet.getAfter());

        cordova.platform().add(Platform.Android);

        Path manifest = new AndroidProject(cordova).getAndroidManifest();

        assertThat(new String(Files.readAllBytes(manifest)), containsString("activity android:label=\"@string/app_name\" android:name=\"com.foo.Foo\""));
    }

    @Condition(OnlyMacOSX.class)
    @Test
    public void set_config_file_ios() throws IOException {
        ConfigFile configFile = ConfigFile.create()
                .target("*-Info.plist")
                .parent("CFBundleURLTypes")
                .content("<array>\n" +
                        "      <dict>\n" +
                        "        <key>PackageName</key>\n" +
                        "        <string>tio.pepe</string>\n" +
                        "      </dict>\n" +
                        "    </array>");
        cordova.config().configFile().add(configFile);

        ConfigFile configFileGet = cordova.config().configFile().getAll().get(0);
        assertEquals(configFile.getContent(), configFileGet.getContent());
        assertEquals(configFile.getTarget(), configFileGet.getTarget());
        assertEquals(configFile.getParent(), configFileGet.getParent());
        assertEquals(configFile.getAfter(), configFileGet.getAfter());

        cordova.platform().add(Platform.IOs);

        Path plist = new XCodeProject(cordova).getInfoPlist();

        assertThat(new String(Files.readAllBytes(plist)), containsString(configFile.getContent()));
    }
}
