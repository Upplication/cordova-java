package com.upplication.cordova;

/**
 * allow navigation element
 * https://cordova.apache.org/docs/en/latest/config_ref/index.html#allow-navigation
 */
public class AllowNavigation {

    private String href;

    public static AllowNavigation create(){
        return new AllowNavigation();
    }

    public AllowNavigation href(String href) {
        this.href = href;
        return this;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
