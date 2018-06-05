package com.upplication.cordova;

/**
 * available platforms
 */
public class Platform {

    public static Platform Android = Android();
    public static Platform IOs = IOs();
    public static Platform Browser = Browser();
    public static Platform OSx = OSx();
    public static Platform Windows = Windows();
    public static Platform WWW = WWW();

    private String version;
    private String name;

    public Platform(String name) {
        this.name = name;
    }

    public Platform(String name, String version) {
        this.version = version;
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Platform platform = (Platform) o;

        if (version != null ? !version.equals(platform.version) : platform.version != null) return false;
        return name != null ? name.equals(platform.name) : platform.name == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    private static Platform Android() {
        return Android(null);
    }

    public static Platform Android(String version) {
        return new Platform("Android", version);
    }

    private static Platform Browser() {
        return Browser(null);
    }

    public static Platform Browser(String version) {
        return new Platform("Browser", version);
    }

    private static Platform OSx() {
        return OSx(null);
    }

    public static Platform OSx(String version) {
        return new Platform("OSx", version);
    }

    private static Platform IOs() {
        return IOs(null);
    }

    public static Platform IOs(String version) {
        return new Platform("IOs", version);
    }

    private static Platform Windows() {
        return Windows(null);
    }

    public static Platform Windows(String version) {
        return new Platform("Windows", version);
    }

    private static Platform WWW() {
        return WWW(null);
    }

    public static Platform WWW(String version) {
        return new Platform("WWW", version);
    }


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

    public static Platform[] values() {

        return new Platform[]{
                Android,
                Browser,
                OSx,
                IOs,
                Windows,
                WWW
        };
    }
}
