package com.upplication.cordova;

/**
 * access element
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#access
 */
public class Access {

    private String origin;
    private Boolean launchExternal;
    private Boolean subdomains;

    public static Access create(){
        return new Access();
    }

    public Access origin(String origin) {
        this.origin = origin;
        return this;
    }

    public Access launchExternal(Boolean launchExternal) {
        this.launchExternal = launchExternal;
        return this;
    }

    public Access subdomains(Boolean subdomains) {
        this.subdomains = subdomains;
        return this;
    }

    public boolean isLaunchExternal() {
        return launchExternal;
    }

    public String getLaunchExternal() {
        if (launchExternal == null){
            return null;
        }
        else if (launchExternal) {
            return "yes";
        }
        else {
            return "no";
        }
    }

    public boolean isSubdomains() {
        return subdomains;
    }

    public String getSubdomains() {
        if (subdomains == null){
            return null;
        }
        else if (subdomains) {
            return "yes";
        }
        else {
            return "no";
        }
    }

    public String getOrigin() {
        return origin;
    }
}
