package com.upplication.cordova;

import java.util.Arrays;
import java.util.List;

/**
 * feature element in the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#feature
 */
public class Feature {

    private String name;
    private List<Param> params;

    private Feature(String name, List<Param> params) {
        this.name = name;
        this.params = params;
    }

    public static Feature create(String name, Param ... params){
         return new Feature(name, Arrays.asList(params));
    }

    public static Feature create(String name, List<Param> params){
        return new Feature(name, params);
    }

    public String getName() {
        return name;
    }

    public List<Param> getParams() {
        return params;
    }

    public static class Param {

        private String name;
        private String value;

        public Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public static Param create(String name, String value) {
            return new Param(name, value);
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
