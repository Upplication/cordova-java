package com.upplication.cordova;

/**
 * available platforms
 */
public enum Platform {
    IOs, Android, AmazonFireos, Blackberry10, Browser, FirefoxOS, OSx, WebOS, Windows, WP8;

    public static Platform build (String avaiableString) {
        switch (avaiableString){
            case "amazon-fireos":
                return AmazonFireos;
            case "android":
                return Android;
            case "blackberry10":
                return Blackberry10;
            case "browser":
                return Browser;
            case "firefoxos":
                return FirefoxOS;
            case "osx":
                return OSx;
            case "ios":
                return IOs;
            case "webos":
                return WebOS;
            case "windows":
                return Windows;
            case "wp8":
                return WP8;
            default:
                throw new IllegalArgumentException("String: " + avaiableString + " are unknown");
        }
    }
}
