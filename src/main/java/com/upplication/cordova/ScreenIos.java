package com.upplication.cordova;


import java.util.ArrayList;
import java.util.List;

/**
 * https://cordova.apache.org/docs/en/7.x/reference/cordova-plugin-splashscreen/index.html#ios-specific-information
 * Checked against "real test" to get only the real screens that is allowed
 *
 * Note: "Launch storyboard images" is not tested
 */
public enum ScreenIos {

    Default568x2("Default-568h@2x~iphone.png", 640, 1136), Default667("Default-667h.png", 750, 1334),
    Default736("Default-736h.png", 1242, 2208), DefaultLandscape736("Default-Landscape-736h.png", 2208, 1242),
    DefaultLandscapex2("Default-Landscape@2x~ipad.png", 2048, 1536), DefaultLandscape("Default-Landscape~ipad.png", 1024, 768),
    DefaultPortraitx2("Default-Portrait@2x~ipad.png", 1536, 2048), DefaultPortrait("Default-Portrait~ipad.png", 768, 1024),
    Defaultx2("Default@2x~iphone.png", 640, 960), Default("Default~iphone.png", 320, 480);

    private String value;
    private int width;
    private int height;

    ScreenIos(String value, int width, int height) {
        this.value = value;
        this.width = width;
        this.height = height;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static List<String> getNames() {
        List<String> result = new ArrayList<>();
        for (ScreenIos icon : ScreenIos.values()) {
            result.add(icon.getValue());
        }

        return result;
    }
}
