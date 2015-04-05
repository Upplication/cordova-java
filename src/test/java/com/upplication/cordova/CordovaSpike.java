package com.upplication.cordova;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CordovaSpike {

    @Test
    public void test() throws IOException {

        System.out.println(System.getenv());

        CordovaCLI cordovaCLI = new Cordova("/usr/local/bin/node", "/usr/local/bin/cordova").getCLI();

        Path project = Files.createTempDirectory("cordova-temp");
        CordovaProject cordovaProject = cordovaCLI.create(project.toFile(), "com.upplication.test", "HelloUpp");

        System.out.println(cordovaProject.plugin().get());
       // cordovaProject.platform().add(Platform.Android);

        cordovaProject.plugin().add("org.apache.cordova.statusbar");
        cordovaProject.plugin().add("org.apache.cordova.device");


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
//        cordovaProject.prepare(Platform.Anxdroid);
//        cordovaProject.compile();
//        cordovaProject.build();

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
