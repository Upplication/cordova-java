package com.upplication.cordova.config;

import com.upplication.cordova.*;
import com.upplication.cordova.internal.AndroidProject;
import com.upplication.cordova.internal.XCodeProject;
import com.upplication.cordova.junit.Condition;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.junit.OnlyMacOSX;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    public void set_icon_android() throws IOException {

        for (IconAndroid icon : IconAndroid.values()) {
            String srcIcon = "res/icon/android/" + icon.getName();
            Files.copy(Paths.get("src/test/resources/" + srcIcon),
                    cordova.getProject().toPath().resolve(srcIcon), StandardCopyOption.REPLACE_EXISTING);
            cordova.config().icon().add(Icon.create().src(srcIcon).density(icon.getDensity()));
        }

        // check config.xml is modified
        List<Icon> iconsConfig = cordova.config().icon().getAll();
        for (int i = 0; i < IconAndroid.values().length; i++) {
            IconAndroid iconAndroid = IconAndroid.values()[i];
            Icon iconConfig = iconsConfig.get(i);

            assertEquals("res/icon/android/" + iconAndroid.getName(), iconConfig.getSrc());
            assertEquals(iconAndroid.getDensity(), iconConfig.getDensity());
        }

        cordova.platform().add(Platform.Android);

        // check icons is copied in the correct location
        for (IconAndroid icon : IconAndroid.values()) {

            Path originalIcon = cordova.getProject().toPath()
                    .resolve("res/icon/android")
                    .resolve(icon.getName());
            Path pathIconCopied = new AndroidProject(cordova).getIcon(icon.getDensity());
            assertEquals("the icon: " + icon + " is not processed correctly",
                    new String(Files.readAllBytes(originalIcon)),
                    new String(Files.readAllBytes(pathIconCopied)));
        }
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void set_icon_iphone() throws IOException {

        // see: https://developer.apple.com/ios/human-interface-guidelines/icons-and-images/app-icon/
        // see: https://developer.apple.com/library/content/qa/qa1686/_index.html

        for (IconIos icon : IconIos.values()) {
            String srcIcon = "res/icon/ios/" + icon.getValue();
            Files.copy(Paths.get("src/test/resources/" + srcIcon),
                    cordova.getProject().toPath().resolve(srcIcon), StandardCopyOption.REPLACE_EXISTING);
            cordova.config().icon().add(Icon.create().src(srcIcon).width(icon.getWidth()).height(icon.getHeight()));
        }

        // check config.xml is modified
        List<Icon> iconsConfig = cordova.config().icon().getAll();
        for (int i = 0; i < IconIos.values().length; i++) {
            IconIos iconIos = IconIos.values()[i];
            Icon iconConfig = iconsConfig.get(i);

            assertEquals("res/icon/ios/" + iconIos.getValue(), iconConfig.getSrc());
            assertEquals(iconIos.getWidth(), iconConfig.getWidth().intValue());
            assertEquals(iconIos.getHeight(), iconConfig.getHeight().intValue());
        }

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        // check icons is copied in the correct location
        for (IconIos icon : IconIos.values()) {

            Path originalIcon = cordova.getProject().toPath()
                    .resolve("res/icon/ios")
                    .resolve(icon.getValue());
            Path pathIconCopied = xCodeProject.getIcon(icon.getValue());
            assertEquals("the icon: " + icon + " is not processed correctly", new String(Files.readAllBytes(originalIcon)), new String(Files.readAllBytes(pathIconCopied)));
        }

        // check no other copied
        // but cordova create four icons more based in our images:
        // icon-20@2x.png, AppIcon29x29@2x.png, AppIcon29x29@3x.png, AppIcon40x40@2x.png

        Path iosIconPlatformDefaultCordova = xCodeProject.getIconsFolder();
        File[] iconsPlatformIos = iosIconPlatformDefaultCordova.toFile().listFiles();
        List<String> iconsPlatformIosName = getFileNames(iconsPlatformIos);
        assertEquals(IconIos.values().length + 4, iconsPlatformIosName.size());

        List<String> iconsGenerated = IconIos.getNames();
        iconsGenerated.add("icon-20@2x.png");
        iconsGenerated.add("AppIcon29x29@2x.png");
        iconsGenerated.add("AppIcon29x29@3x.png");
        iconsGenerated.add("AppIcon40x40@2x.png");
        assertTrue(iconsPlatformIosName.containsAll(iconsGenerated));

        // TODO: check file with the content for ios (generated by xcode)
        Path contentJson = xCodeProject.getIconsFolder().resolve("Contents.json");

    }

    // set all ok but the res wrong

    @Test
    @Condition(OnlyMacOSX.class)
    public void set_icon_wrong_resolution_in_configXml() throws IOException {
        String srcIcon = "res/icon/ios/" + IconIos.Icon40.getValue();
        Files.copy(Paths.get("src/test/resources/" + srcIcon),
                cordova.getProject().toPath().resolve(srcIcon), StandardCopyOption.REPLACE_EXISTING);
        // wrong width and height (must be 40)
        cordova.config().icon().add(Icon.create().src(srcIcon).width(35).height(35));

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        Path originalIcon = cordova.getProject().toPath()
                .resolve("res/icon/ios")
                .resolve(IconIos.Icon40.getValue());
        Path pathIconcopied = xCodeProject.getIcon(IconIos.Icon40.getValue());
        // is not the same image!
        assertNotEquals("the icon: " + IconIos.Icon40 + " is not processed correctly",
                new String(Files.readAllBytes(originalIcon)),
                new String(Files.readAllBytes(pathIconcopied)));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void set_icon_wrong_name_but_good_res_in_configXml() throws IOException {
        String srcIcon = "res/icon/ios/icon-asdadasd.png";
        Files.copy(Paths.get("src/test/resources/res/icon/ios/" + IconIos.Icon40.getValue()),
                cordova.getProject().toPath().resolve(srcIcon));
        // wrong width and height (must be 40)
        cordova.config().icon().add(Icon.create().src(srcIcon).width(IconIos.Icon40.getWidth()).height(IconIos.Icon40.getHeight()));

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        Path originalIcon = cordova.getProject().toPath().resolve(srcIcon);
        // changed with the correct name!
        Path pathIconCopied = xCodeProject.getIcon(IconIos.Icon40.getValue());

        assertEquals("the icon: " + IconIos.Icon40 + " is not processed correctly",
                new String(Files.readAllBytes(originalIcon)),
                new String(Files.readAllBytes(pathIconCopied)));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void set_icon_iphone_two_icons_with_same_resolution_the_first_take_preference() throws IOException {

        // we add first an invented name with the res: 40x40
        String srcIcon40invented = "res/icon/ios/icon-asdasdsad.png";
        Path originalIconInvented = Files.copy(Paths.get("src/test/resources/res/icon/ios/icon-40-different.png"),
                cordova.getProject().toPath().resolve(srcIcon40invented));
        cordova.config().icon().add(Icon.create().src(srcIcon40invented).width(40).height(40));


        // then we add another with res: 40x40 and the correct name
        String srcIcon40 = "res/icon/ios/" + IconIos.Icon40.getValue();
        Path originalIcon = Files.copy(Paths.get("src/test/resources/" + srcIcon40),
                cordova.getProject().toPath().resolve(srcIcon40));
        cordova.config().icon().add(Icon.create().src(srcIcon40).width(IconIos.Icon40.getWidth()).height(IconIos.Icon40.getHeight()));

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        // check image
        // the content of the first (name invented) is used
        Path pathIconCopied = xCodeProject.getIcon(IconIos.Icon40.getValue());
        assertEquals("the icon: " + IconIos.Icon40 + " is not processed correctly",
                new String(Files.readAllBytes(originalIconInvented)),
                new String(Files.readAllBytes(pathIconCopied)));

        // the second image is ignored and not used
        Path iosIconPlatformDefaultCordova = xCodeProject.getIconsFolder();
        File[] iconsPlatformIos = iosIconPlatformDefaultCordova.toFile().listFiles();
        for (File icon : iconsPlatformIos) {
            assertNotEquals(new String(Files.readAllBytes(originalIcon)), new String(Files.readAllBytes(icon.toPath())));
        }
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void set_icon_iphone_custom_location() throws IOException {

        // this is a predefined icon for apple and cordova icon-72.png
        // we are going to save the file in the root project folder
        String srcIcon72 = IconIos.Icon72.getValue();
        // copy the file in the root project folder
        Path pathIcon72 = Files.copy(Paths.get("src/test/resources/res/icon/ios/" + IconIos.Icon72.getValue()),
                cordova.getProject().toPath().resolve(srcIcon72));
        cordova.config().icon().add(Icon.create().src(srcIcon72).width(72).height(72));

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        // check images is in the correct folder once the platform is added
        // the image exists and is the image we add.
        Path pathIcon72copied = xCodeProject.getIcon(IconIos.Icon72.getValue());
        assertTrue(Files.exists(pathIcon72copied));
        assertEquals(new String(Files.readAllBytes(pathIcon72)), new String(Files.readAllBytes(pathIcon72copied)));
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void cordova_ios_by_default_add_some_icons() throws IOException {

        // this is a predefined icon for cordova.
        // but is not processed and is only example, some names doesnt match the real names...

        Path iosIconDefaultCordova = cordova.getProject().toPath().resolve("res/icon/ios/");
        File[] icons = iosIconDefaultCordova.toFile().listFiles();

        // same number of files and same names
        List<String> iconsName = getFileNames(icons);
        assertEquals(4, iconsName.size());
        assertTrue(iconsName.containsAll(Arrays.asList("icon-57-2x.png", "icon-57.png", "icon-72-2x.png", "icon-72.png")));

        // but is not the same than the one added when add ios platform
        // see: IconIosName too know the real files you must change

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        Path iosIconPlatformDefaultCordova = xCodeProject.getIconsFolder();
        File[] iconsPlatformIos = iosIconPlatformDefaultCordova.toFile().listFiles();
        List<String> iconsPlatformIosName = getFileNames(iconsPlatformIos);

        assertEquals(IconIos.values().length, iconsPlatformIosName.size());
        assertTrue(iconsPlatformIosName.containsAll(IconIos.getNames()));

    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void change_cordova_default_icons_without_changing_configXml_doesnt_work() throws IOException {

        String srcIcon72 = "res/icon/ios/icon-72.png";
        Path pathIcon72 = Files.copy(Paths.get("src/test/resources/" + srcIcon72),
                cordova.getProject().toPath().resolve(srcIcon72), StandardCopyOption.REPLACE_EXISTING);

        cordova.platform().add(Platform.IOs);

        XCodeProject xCodeProject = new XCodeProject(cordova);

        Path pathIcon72Copied = xCodeProject.getIcon("icon-72.png");
        assertNotEquals(new String(Files.readAllBytes(pathIcon72)), new String(Files.readAllBytes(pathIcon72Copied)));
    }


    private List<String> getFileNames(File[] icons) {
        List<String> result = new ArrayList<>();
        for (File file : icons) {
            if (!file.getName().equals("Contents.json")) {
                result.add(file.getName());
            }
        }
        return result;
    }
}
