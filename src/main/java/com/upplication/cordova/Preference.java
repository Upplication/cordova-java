package com.upplication.cordova;

/**
 * preference element in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#preference
 */
public class Preference {
    private String name;
    private String value;

    public static Preference create(){
        return new Preference();
    }

    public Preference value(String value){
        this.value = value;
        return this;
    }

    public Preference name(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
