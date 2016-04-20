package com.upplication.cordova;

/**
 * version attr in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#widget
 */
public class Version {

    private String version;
    private String iosCfBundleVersion;
    private Integer androidVersionCode;

    public static Version create(){
        return new Version();
    }

    public Version version(String version) {
        this.version = version;
        return this;
    }

    public Version iosCfBundleVersion(String iosCfBundleVersion) {
        this.iosCfBundleVersion = iosCfBundleVersion;
        return this;
    }

    public Version androidVersionCode(Integer androidVersionCode) {
        this.androidVersionCode = androidVersionCode;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public String getIosCfBundleVersion() {
        return iosCfBundleVersion;
    }

    public Integer getAndroidVersionCode() {
        return androidVersionCode;
    }
}
