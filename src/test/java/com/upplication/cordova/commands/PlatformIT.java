package com.upplication.cordova.commands;

import com.upplication.cordova.*;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.internal.XCodeProject;
import com.upplication.cordova.junit.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static com.upplication.cordova.Platform.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PlatformIT {
    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    @Rule
    public ConditionRule rule = new ConditionRule();

    private CordovaProject cordova;

    @Before
    public void setUp() throws IOException {
        cordova = cordovaCLIRule.get().create(folder.newFolder("cordova-path"));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void list_with_macosx_available_installed() {
        PlatformResume platformResume = cordova.platform().list();

        assertFalse(platformResume.getAvailable().isEmpty());
        assertTrue(platformResume.getInstalled().isEmpty());
        for (Platform platform : Platform.values()) {
            assertTrue("Platform: " + platform + " not available as expected", platformResume.getAvailable().contains(platform));
        }
    }

    @Test
    @Condition(OnlyWindows.class)
    public void list_with_windows_available_installed() {
        PlatformResume platformResume = cordova.platform().list();

        assertFalse(platformResume.getAvailable().isEmpty());
        assertTrue(platformResume.getInstalled().isEmpty());
        for (Platform platform : Arrays.asList(Android, Browser, Windows, WWW)) {
            assertTrue("Platform: " + platform + " not available as expected", platformResume.getAvailable().contains(platform));
        }
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void list_with_ios_installed() {

        cordova.platform().add(Platform.IOs);
        PlatformResume platformResume = cordova.platform().list();

        assertFalse(platformResume.getAvailable().isEmpty());
        assertEquals(Platform.IOs, platformResume.getInstalled().get(0));
        assertFalse(platformResume.getAvailable().contains(Platform.IOs));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void list_with_android_and_ios_installed() {

        cordova.platform().add(Platform.IOs);
        cordova.platform().add(Platform.Android);
        PlatformResume platformResume = cordova.platform().list();

        assertFalse(platformResume.getAvailable().isEmpty());
        assertFalse(platformResume.getAvailable().contains(Platform.IOs));
        assertFalse(platformResume.getAvailable().contains(Platform.Android));
        assertEquals(Platform.Android, platformResume.getInstalled().get(0));
        assertEquals(Platform.IOs, platformResume.getInstalled().get(1));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void install_ios_and_remove() {

        cordova.platform().add(Platform.IOs);
        PlatformResume platformResume = cordova.platform().list();
        assertEquals(Platform.IOs, platformResume.getInstalled().get(0));

        cordova.platform().remove(Platform.IOs);

        PlatformResume platformResumeFinal = cordova.platform().list();
        assertTrue(platformResumeFinal.getInstalled().isEmpty());
    }

    @Test
    public void add_android_create_AndroidProject_structure() {

        cordova.platform().add(Platform.Android);

        AndroidProject androidProject = new AndroidProject(cordova);

        assertThat(Files.exists(androidProject.getAndroidManifest()), is(true));
        assertThat(Files.exists(androidProject.get()), is(true));
        assertThat(Files.exists(androidProject.getIcon(IconAndroid.xhdpi.getDensity())), is(true));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void add_ios_create_XCodeProject_structure() throws IOException {

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        assertThat(Files.exists(xCodeProject.getInfoPlist()), is(true));
        assertThat(Files.exists(xCodeProject.get()), is(true));
        assertThat(Files.exists(xCodeProject.getIcon(IconIos.Icon40.getValue())), is(true));
    }
}
