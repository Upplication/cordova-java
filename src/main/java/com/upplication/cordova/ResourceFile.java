package com.upplication.cordova;

/**
 * preference element in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#resource-file
 */
public class ResourceFile {
    private String src;
    private String target;

    public static ResourceFile create(String src){
        return new ResourceFile().src(src);
    }

    public ResourceFile src(String src){
        this.src = src;
        return this;
    }

    public ResourceFile target(String target) {
        this.target = target;
        return this;
    }


    public String getSrc() {
        return src;
    }

    public String getTarget() {
        return target;
    }
}
