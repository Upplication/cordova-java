package com.upplication.cordova;

/**
 * preference element in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#config-file
 */
public class ConfigFile {

    private String parent;
    private String target;
    private String after;
    private String content;

    public static ConfigFile create(){
        return new ConfigFile();
    }

    public ConfigFile parent(String parent){
        this.parent = parent;
        return this;
    }

    public ConfigFile target(String target) {
        this.target = target;
        return this;
    }

    public ConfigFile after(String after) {
        this.after = after;
        return this;
    }

    public ConfigFile content(String content) {
        this.content = content;
        return this;
    }


    public String getParent() {
        return parent;
    }

    public String getTarget() {
        return target;
    }

    public String getAfter() {
        return after;
    }

    public String getContent() {
        return content;
    }
}
