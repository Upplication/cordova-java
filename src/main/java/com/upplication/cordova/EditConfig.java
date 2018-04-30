package com.upplication.cordova;

/**
 * preference element in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#edit-config
 */
public class EditConfig {
    private String file;
    private String target;
    private String mode;
    private String content;

    public static EditConfig create(){
        return new EditConfig();
    }

    public EditConfig file(String file){
        this.file = file;
        return this;
    }

    public EditConfig target(String target) {
        this.target = target;
        return this;
    }

    public EditConfig mode(String mode) {
        this.mode = mode;
        return this;
    }

    public EditConfig content(String content) {
        this.content = content;
        return this;
    }


    public String getFile() {
        return file;
    }

    public String getTarget() {
        return target;
    }

    public String getMode() {
        return mode;
    }

    public String getContent() {
        return content;
    }
}
