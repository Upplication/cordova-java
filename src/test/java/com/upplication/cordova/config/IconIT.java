package com.upplication.cordova.config;

import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.Icon;
import com.upplication.cordova.Platform;
import com.upplication.cordova.junit.Condition;
import com.upplication.cordova.junit.ConditionRule;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.junit.OnlyMacOSX;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class IconIT {
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
    public void set_icon_ldpi_android() throws IOException {
        String content = "sample image";
        String density = "ldpi";
        Path src = createIcon(content);

        cordova.config().icon().add(Icon.create().src(src.toString()).density(density));
        cordova.platform().add(Platform.Android);

        assertEquals(src.toString(), cordova.config().icon().getAll().get(0).getSrc());
        assertEquals(density, cordova.config().icon().getAll().get(0).getDensity());

        Path path = cordova.getProject().toPath().resolve("platforms").resolve("android").resolve("res").resolve("mipmap-" + density).resolve("icon.png");
        assertEquals(content, new String(Files.readAllBytes(path)));
    }

    @Test
    public void set_icon_mdpi_android() throws IOException {
        String content = "sample image";
        String density = "mdpi";
        Path src = createIcon(content);

        cordova.config().icon().add(Icon.create().src(src.toString()).density(density));
        cordova.platform().add(Platform.Android);

        assertEquals(src.toString(), cordova.config().icon().getAll().get(0).getSrc());
        assertEquals(density, cordova.config().icon().getAll().get(0).getDensity());

        Path path = cordova.getProject().toPath().resolve("platforms").resolve("android").resolve("res").resolve("mipmap-" + density).resolve("icon.png");
        assertEquals(content, new String(Files.readAllBytes(path)));
    }

    @Test
    public void set_icon_hdpi_android() throws IOException {
        String content = "sample image";
        String density = "hdpi";
        Path src = createIcon(content);

        cordova.config().icon().add(Icon.create().src(src.toString()).density(density));
        cordova.platform().add(Platform.Android);

        assertEquals(src.toString(), cordova.config().icon().getAll().get(0).getSrc());
        assertEquals(density, cordova.config().icon().getAll().get(0).getDensity());

        Path path = cordova.getProject().toPath().resolve("platforms").resolve("android").resolve("res").resolve("mipmap-" + density).resolve("icon.png");
        assertEquals(content, new String(Files.readAllBytes(path)));
    }

    @Test
    public void set_icon_xhdpi_android() throws IOException {
        String content = "sample image";
        String density = "xhdpi";
        Path src = createIcon(content);

        cordova.config().icon().add(Icon.create().src(src.toString()).density(density));
        cordova.platform().add(Platform.Android);

        assertEquals(src.toString(), cordova.config().icon().getAll().get(0).getSrc());
        assertEquals(density, cordova.config().icon().getAll().get(0).getDensity());

        Path path = cordova.getProject().toPath().resolve("platforms").resolve("android").resolve("res").resolve("mipmap-" + density).resolve("icon.png");
        assertEquals(content, new String(Files.readAllBytes(path)));
    }

    @Test
    public void set_icon_xxhdpi_android() throws IOException {
        String content = "sample image";
        String density = "xxhdpi";
        Path src = createIcon(content);

        cordova.config().icon().add(Icon.create().src(src.toString()).density(density));
        cordova.platform().add(Platform.Android);

        assertEquals(src.toString(), cordova.config().icon().getAll().get(0).getSrc());
        assertEquals(density, cordova.config().icon().getAll().get(0).getDensity());

        Path path = cordova.getProject().toPath().resolve("platforms").resolve("android").resolve("res").resolve("mipmap-" + density).resolve("icon.png");
        assertEquals(content, new String(Files.readAllBytes(path)));
    }

    @Test
    public void set_icon_xxxhdpi_android() throws IOException {
        String content = "sample image";
        String density = "xxxhdpi";
        Path src = createIcon(content);

        cordova.config().icon().add(Icon.create().src(src.toString()).density(density));
        cordova.platform().add(Platform.Android);

        assertEquals(src.toString(), cordova.config().icon().getAll().get(0).getSrc());
        assertEquals(density, cordova.config().icon().getAll().get(0).getDensity());

        Path path = cordova.getProject().toPath().resolve("platforms").resolve("android").resolve("res").resolve("mipmap-" + density).resolve("icon.png");
        assertEquals(content, new String(Files.readAllBytes(path)));
    }

    private Path createIcon(String content) throws IOException {
        Path icon = folder.newFile("icon" + UUID.randomUUID().toString() + ".png").toPath();
        Files.write(icon, content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.TRUNCATE_EXISTING);
        icon = Files.move(icon, cordova.getProject().toPath().resolve("icon.png"));
        return cordova.getProject().toPath().relativize(icon);
    }
}
