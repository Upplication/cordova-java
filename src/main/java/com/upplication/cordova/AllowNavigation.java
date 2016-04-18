package com.upplication.cordova;


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
