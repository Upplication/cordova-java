package com.upplication.cordova.config;


import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Feature;
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

public class FeaturesIT {
    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaProject cordova;

    @Before
    public void setUp() throws IOException {
        cordova = cordovaCLIRule.get().create(folder.newFolder("cordova-path"));
    }

    /*
    <feature name="Device">
   <param name="android-package" value="org.apache.cordova.device.Device" />
</feature>
     */

    @Test
    public void set_android_device_feature() throws IOException {
        String featureName = "Device";
        String paramName = "android-package";
        String paramValue = "org.apache.cordova.device.Device";
        Feature.Param expected = Feature.Param.create(paramName, paramValue);
        cordova.config().features().add(featureName, expected);

        assertEquals(1, cordova.config().features().getAll().size());
        assertEquals(featureName, cordova.config().features().getAll().get(0).getName());
        assertEquals(1, cordova.config().features().getAll().get(0).getParams().size());

        Feature.Param param = cordova.config().features().getAll().get(0).getParams().get(0);
        assertEquals(expected.getName(), param.getName());
        assertEquals(expected.getValue(), param.getValue());

        assertThat(new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml"))), containsString("<feature name=\"" + featureName + "\"><param name=\"" + paramName + "\" value=\"" + paramValue + "\"/></feature>"));
    }

    @Test
    public void set_android_device_feature_in_platform_android() throws IOException {
        String featureName = "Device";
        String paramName = "android-package";
        String paramValue = "org.apache.cordova.device.Device";
        Feature.Param expected = Feature.Param.create(paramName, paramValue);
        cordova.config().platform(Platform.Android).feature().add(featureName, expected);

        assertEquals(1, cordova.config().platform(Platform.Android).feature().getAll().size());
        assertEquals(featureName, cordova.config().platform(Platform.Android).feature().getAll().get(0).getName());
        assertEquals(1, cordova.config().platform(Platform.Android).feature().getAll().get(0).getParams().size());

        Feature.Param param = cordova.config().platform(Platform.Android).feature().getAll().get(0).getParams().get(0);
        assertEquals(expected.getName(), param.getName());
        assertEquals(expected.getValue(), param.getValue());

        String platfortmContent = new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml")))
                .split("<platform name=\"android\">")[1].split("</platform>")[0];
        assertThat(platfortmContent, containsString("<feature name=\"" + featureName + "\"><param name=\"" + paramName + "\" value=\"" + paramValue + "\"/></feature>"));
    }

    @Test
    public void set_CDVWKWebViewEngine_feature_in_platform_ios() throws IOException {
        String featureName = "CDVWKWebViewEngine";
        String paramName = "ios-package";
        String paramValue = "CDVWKWebViewEngine";
        Feature.Param expected = Feature.Param.create(paramName, paramValue);
        cordova.config().platform(Platform.IOs).feature().add(featureName, expected);

        assertEquals(1, cordova.config().platform(Platform.IOs).feature().getAll().size());
        assertEquals(featureName, cordova.config().platform(Platform.IOs).feature().getAll().get(0).getName());
        assertEquals(1, cordova.config().platform(Platform.IOs).feature().getAll().get(0).getParams().size());

        Feature.Param param = cordova.config().platform(Platform.IOs).feature().getAll().get(0).getParams().get(0);
        assertEquals(expected.getName(), param.getName());
        assertEquals(expected.getValue(), param.getValue());

        String platfortmContent = new String(Files.readAllBytes(cordova.getProject().toPath().resolve("config.xml")))
                .split("<platform name=\"ios\">")[1].split("</platform>")[0];
        assertThat(platfortmContent, containsString("<feature name=\"" + featureName + "\"><param name=\"" + paramName + "\" value=\"" + paramValue + "\"/></feature>"));
    }
}
