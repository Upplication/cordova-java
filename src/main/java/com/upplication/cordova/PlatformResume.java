package com.upplication.cordova;

import java.util.List;

public class PlatformResume {

    private List<Platform> installed;
    private List<Platform> available;

    public PlatformResume(List<Platform> installed, List<Platform> available) {
        this.installed = installed;
        this.available = available;
    }

    public List<Platform> getInstalled() {
        return installed;
    }

    public List<Platform> getAvailable() {
        return available;
    }
}
