package com.upplication.cordova;


import java.util.ArrayList;
import java.util.List;

/**
 * https://cordova.apache.org/docs/en/7.x/config_ref/images.html#ios
 * Checked against "real test" to get only the real icons that is allowed
 */
public enum IconIos {
    Icon60x2("icon-60@2x.png", 120, 120), Icon60x3("icon-60@3x.png", 180, 180),
    Icon76("icon-76.png", 76, 76), Icon76x2("icon-76@2x.png", 152, 152),
    Icon40("icon-40.png", 40, 40), Icon40x2("icon-40@2x.png", 80, 80),
    Icon("icon.png", 57, 57), Iconx2("icon@2x.png", 114, 114),
    Icon72("icon-72.png", 72, 72), Icon72x2("icon-72@2x.png", 144, 144),
    IconSmall("icon-small.png", 29, 29), IconSmallx2("icon-small@2x.png", 58, 58), IconSmallx3("icon-small@3x.png", 87, 87),
    Icon50("icon-50.png", 50, 50), Icon50x2("icon-50@2x.png", 100, 100),
    Icon83x2("icon-83.5@2x.png", 167, 167);

    private String value;
    private int width;
    private int height;

    IconIos(String value, int width, int height) {
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
        for (IconIos icon : IconIos.values()) {
            result.add(icon.getValue());
        }

        return result;
    }
}
