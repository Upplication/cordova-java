package com.upplication.cordova.commands;

import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.PlatformResume;
import com.upplication.cordova.junit.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static com.upplication.cordova.Platform.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        for (Platform platform : Arrays.asList(IOs, AmazonFireos, Android, Blackberry10, Browser, FirefoxOS, WebOS, OSx)) {
            assertTrue(platformResume.getAvailable().contains(platform));
        }
    }

    @Test
    @Condition(OnlyWindows.class)
    public void list_with_windows_available_installed() {
        PlatformResume platformResume = cordova.platform().list();

        assertFalse(platformResume.getAvailable().isEmpty());
        assertTrue(platformResume.getInstalled().isEmpty());
        for (Platform platform : Arrays.asList(AmazonFireos, Android, Blackberry10, Browser, FirefoxOS, WebOS, Windows, WP8)) {
            assertTrue(platformResume.getAvailable().contains(platform));
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
}
