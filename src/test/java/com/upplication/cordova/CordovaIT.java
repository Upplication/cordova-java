package com.upplication.cordova;

import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CordovaIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();

    private CordovaCLI cordovaCLI;


    @Before
    public void setUp() {
        cordovaCLI = cordovaCLIRule.get();
    }

    //
    // TODO: spike, change to a real integration tests with assert verify etc...
    //

    @Test
    public void version() throws IOException {
        String version = cordovaCLI.getVersion();
        assertNotNull(version);
        assertEquals("6.1.1", version);
    }

    @Test
    public void android() throws IOException {
        Path project = Files.createTempDirectory("cordova-temp");
        CordovaProject cordovaProject = cordovaCLI.create(project.toFile(), "com.upplication.test", "HelloUpp");

        cordovaProject.platform().add(Platform.Android);
        cordovaProject.prepare();
        cordovaProject.compile();
    }

    @Test
    public void iOS() throws IOException {
        Path project = Files.createTempDirectory("cordova-temp");
        CordovaProject cordovaProject = cordovaCLI.create(project.toFile(), "com.upplication.test", "HelloUpp");

        cordovaProject.platform().add(Platform.IOs);
        cordovaProject.prepare();
        cordovaProject.compile();
    }

    @Test
    public void config() throws IOException {
        Path project = Files.createTempDirectory("cordova-temp");
        CordovaProject cordovaProject = cordovaCLI.create(project.toFile(), "com.upplication.test", "HelloUpp");

        System.out.println(cordovaProject.plugin().get());
        cordovaProject.platform().add(Platform.Android);
        cordovaProject.prepare();
        cordovaProject.compile();

        cordovaProject.plugin().add("cordova-plugin-statusbar");
        cordovaProject.plugin().add("cordova-plugin-device");


        cordovaProject.config().author().setEmail("arnaix@gmail.com");
        cordovaProject.config().author().setName("Javier");
        cordovaProject.config().author().setHref("http://www.upplication.com/cordova-test");
        cordovaProject.config().setVersion(1,0,1);
        cordovaProject.config().setName("UpplicationMola");
        cordovaProject.config().access().add(Access.create().origin("www.upplication.com").launchExternal(true));

        cordovaProject.config().icon().add(Icon.create().src("src/hola.png"));
        cordovaProject.config().icon().add(Icon.create().src("src/asdas.png").height(500).width(350));

        cordovaProject.config().platform(Platform.Android).splash().add(Splash.create().src("src/hola.png").width(100).height(300));
        cordovaProject.config().platform(Platform.Android).icon().add(Icon.create().src("src/hola.png").width(100).height(300));

        cordovaProject.config().platform(Platform.Android).preference().add("android-pref", "pref");

        System.out.println(project);

        System.out.println(cordovaProject.plugin().get());
        System.out.println(cordovaProject.config().getDescription());
        System.out.println(cordovaProject.config().getName());
        System.out.println(cordovaProject.config().getVersion());
        System.out.println(cordovaProject.config().author().getName());
        System.out.println(cordovaProject.config().author().getEmail());
        System.out.println(cordovaProject.config().author().getHref());
        System.out.println(cordovaProject.config().icon().getAll());
        System.out.println(cordovaProject.config().preferences().getAll());
        System.out.println(cordovaProject.config().access().getAll());
        System.out.println(cordovaProject.config().platform(Platform.Android).icon().getAll());
        System.out.println(cordovaProject.config().platform(Platform.Android).splash().getAll());
        System.out.println(cordovaProject.config().platform(Platform.Android).preference().getAll());
    }
}
