package com.upplication.cordova;

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
