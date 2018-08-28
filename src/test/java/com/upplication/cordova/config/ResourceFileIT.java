package com.upplication.cordova.config;


import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Platform;
import com.upplication.cordova.ResourceFile;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.internal.XCodeProject;
import com.upplication.cordova.junit.Condition;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.junit.OnlyMacOSX;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ResourceFileIT {
    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaProject cordova;
    private Path mockFile;

    @Before
    public void setUp() throws IOException {
        cordova = cordovaCLIRule.get().create(folder.newFolder("cordova-path"));
        mockFile = Files.createFile(cordova.getProject().toPath().resolve("mock-file.config"));
    }

    @Test
    public void set_resource_file_android() throws IOException {
        ResourceFile resourceFile = ResourceFile.create("mock-file.config")
                .target("app/file.config");

        cordova.config().platform(Platform.Android).resourceFile().add(resourceFile);

        ResourceFile resourceFileGet = cordova.config().platform(Platform.Android).resourceFile().getAll().get(0);
        assertEquals(resourceFile.getSrc(), resourceFileGet.getSrc());
        assertEquals(resourceFile.getTarget(), resourceFileGet.getTarget());

        cordova.platform().add(Platform.Android);

        Path config = new AndroidProject(cordova).get().resolve("app/file.config");

        assertThat(Files.exists(config), is(true));
        assertThat(Files.readAllBytes(config), IsEqual.equalTo(Files.readAllBytes(mockFile)));
    }

    @Condition(OnlyMacOSX.class)
    @Test
    public void set_resource_file_ios() throws IOException {
        ResourceFile resourceFile = ResourceFile.create("mock-file.config")
                .target("file.config");
        cordova.config().platform(Platform.IOs).resourceFile().add(resourceFile);

        ResourceFile resourceFileGet = cordova.config().platform(Platform.IOs).resourceFile().getAll().get(0);
        assertEquals(resourceFile.getSrc(), resourceFileGet.getSrc());
        assertEquals(resourceFile.getTarget(), resourceFileGet.getTarget());

        cordova.platform().add(Platform.IOs);

        Path config = new XCodeProject(cordova).get().resolve("HelloCordova/Resources/file.config");

        assertThat(Files.exists(config), is(true));
        assertThat(Files.readAllBytes(config), IsEqual.equalTo(Files.readAllBytes(mockFile)));
    }
}
