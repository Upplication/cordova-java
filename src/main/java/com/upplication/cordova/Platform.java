package com.upplication.cordova;

/**
 * available platforms
 */
public enum Platform {
    IOs, Android, Browser, OSx, Windows, WWW;

    public static Platform build (String avaiableString) {
        switch (avaiableString){
            case "android":
                return Android;
            case "browser":
                return Browser;
            case "osx":
                return OSx;
            case "ios":
                return IOs;
            case "windows":
                return Windows;
            case "www":
                return WWW;
            default:
                throw new IllegalArgumentException("String: " + avaiableString + " are unknown");
        }
    }
}
