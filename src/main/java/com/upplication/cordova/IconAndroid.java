package com.upplication.cordova;


import java.util.ArrayList;
import java.util.List;

/**
 * https://cordova.apache.org/docs/en/7.x/config_ref/images.html#android
 * Checked against "real test" to get only the real icons that is allowed
 */
public enum IconAndroid {
    ldpi("ldpi.png", "ldpi"), mdpi("mdpi.png", "mdpi"),
    hdpi("hdpi.png", "hdpi"), xhdpi("xhdpi.png", "xhdpi"),
    xxhdpi("xxhdpi.png", "xxhdpi"), xxxhdpi("xxxhdpi.png", "xxxhdpi");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    private String name;
    private String density;

    IconAndroid(String name, String density) {
        this.name = name;
        this.density = density;
    }


    public static List<String> getNames() {
        List<String> result = new ArrayList<>();
        for (IconAndroid icon : IconAndroid.values()) {
            result.add(icon.getName());
        }

        return result;
    }
}
